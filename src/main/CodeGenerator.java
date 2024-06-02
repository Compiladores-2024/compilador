package src.main;

import src.lib.Static;
import src.lib.exceptionHelper.LexicalException;
import src.lib.exceptionHelper.SemanticException;
import src.lib.exceptionHelper.SyntacticException;

public class CodeGenerator {

    private SyntacticAnalyzer syntacticAnalyzer;
    private String asm="";
    private String resultPath;

    public CodeGenerator(String path){
        resultPath = path.split(".ru")[0];

        //Inicializa el analizador sintactico
        syntacticAnalyzer = new SyntacticAnalyzer(path);
    }

    public void run () throws LexicalException, SyntacticException, SemanticException {
        //Analiza el código fuente
        syntacticAnalyzer.run();

        //Genera el codigo MIPS
        generateAsm();

        //Escribe el resultado de la tabla de simbolos
        //Static.write(syntacticAnalyzer.toJSON().get(0), resultPath + ".ts.json");

        //Escribe el resultado del ast
        //Static.write(syntacticAnalyzer.toJSON().get(1), resultPath + ".ast.json");

        //Escribe el codigo MIPS
        Static.write(asm, resultPath + ".asm");
    }

    private void generateAsm(){
        //Genera la data para las estructuras predefinidas
        generatePredefined();

        asm += "\t.globl main\n\n";

        //incluir los cir de cada struct en .data
        
        asm += "main: \n";

        generateCode();
        //incluir en .text info de vt, metodos y constructores de struts declarados, y metodos de structs predefinidos
        
        //añadir en asm la generacion de codigo de cada nodo del ast

        

        //exit
        asm += "\tli $v0, 10 #exit\n"; //10 es exit syscall
        asm += "\tsyscall";
    }

    private void generatePredefined () {
        String struct, vt;
        asm += ".data\n";
        
        //AGREGA LAS VIRTUAL TABLES

        //Recorre las estructuras
        for (String sStruct : syntacticAnalyzer.getSymbolTable().getStructs().keySet()) {
            //Inicializa el texto de las virtual table
            vt="\t\t.word ";
    
            //Obtiene el nombre de la estructura sin espacios
            struct = sStruct.replaceAll("\\s", "");
            
            //Recorre los metodos de esa estructura
            for (String method : syntacticAnalyzer.getSymbolTable().getStruct(sStruct).getMethods().keySet()){
                //Agrega el metodo a la vtable
                vt += (struct + "_" + method + ", ");
            }
    
            //Reserva memoria solo si posee datos
            if (!vt.equals("\t\t.word ")) {
                asm += "\t" + struct + "_vtable:\n" + vt.substring(0, vt.length() - 2) + "\n";
            }
        }
    
        //add var de instancia

        //AGREGA EL CÓDIGO DE LOS METODOS
        asm += "\n.text #put things into the text segment...\n";
        generatePredefinedCode();
    }

    private void generatePredefinedCode(){
        String initFunction, endFunctionOut, endFunctionIn;

        initFunction = "\t\tmove $fp, $sp #mueve el contenido de $sp a $fp\n"
        + "\t\tsw $ra, 0($sp) #copia el contenido de $ra a $sp (direccion de retorno)\n"
        + "\t\taddiu $sp, $sp, -4 #mueve el $sp 1 pos arriba\n";

        endFunctionOut = "\t\tlw $ra 4($sp)  #carga un valor de la memoria en el registro $ra. El valor se carga desde la dirección de memoria que se encuentra 4 bytes por encima del puntero de pila ($sp)\n"            
        + "\t\taddiu $sp $sp 12 # mueve el $sp \n"  //12 porque z=4*n + 8 (n cant de parametros=1)
        + "\t\tlw $fp 0($sp)   #Esta instrucción carga un valor de la memoria en el registro $fp. El valor se carga desde la dirección de memoria que se encuentra en la parte superior de la pila (0($sp))\n"
        + "\t\tjr $ra          # salta a la dirección almacenada en el registro $ra\n";


        endFunctionIn = "\t\tlw $ra 4($sp)  #carga un valor de la memoria en el registro $ra. El valor se carga desde la dirección de memoria que se encuentra 4 bytes por encima del puntero de pila ($sp)\n"            
        + "\t\taddiu $sp $sp 8 # mueve el $sp \n"  //8 porque z=4*n + 8 (n cant de parametros=0)
        + "\t\tlw $fp 0($sp)   #Esta instrucción carga un valor de la memoria en el registro $fp. El valor se carga desde la dirección de memoria que se encuentra en la parte superior de la pila (0($sp))\n"
        + "\t\tjr $ra          # salta a la dirección almacenada en el registro $ra\n";

        // IO 
        //METODO out str
        asm += "\tIO_out_str:\n"
            + initFunction
            + "\t\tlw $a0, 8($sp) #carga un valor de la memoria en el registro $a0. El valor se carga desde la dirección de memoria que se encuentra 8 bytes por encima del puntero de pila ($sp)\n"
            + "\t\tli $v0, 4 #carga el valor 4 (print string) en el registro $v0\n"
            + "\t\tsyscall #syscall\n"
            + endFunctionOut;

        //METODO  out_int
        asm += "\tIO_out_int:\n"
            + initFunction
            + "\t\tlw $a0, 8($sp) #carga un valor de la memoria en el registro $a0. El valor se carga desde la dirección de memoria que se encuentra 8 bytes por encima del puntero de pila ($sp)\n"
            + "\t\tli $v0, 1 #carga el valor 1 (print int) en el registro $v0\n"
            + "\t\tsyscall #syscall\n"
            + endFunctionOut;

        //METODO  out_bool
        asm += "\tIO_out_bool:\n"
            + initFunction
            + "\t\tlw $a0, 8($sp) #carga un valor de la memoria en el registro $a0. El valor se carga desde la dirección de memoria que se encuentra 8 bytes por encima del puntero de pila ($sp)\n"
            + "\t\tli $v0, 1 #carga el valor 1 (print int) en el registro $v0\n"
            + "\t\tsyscall #syscall\n"
            + endFunctionOut;


        //METODO  out_char
        asm += "\tIO_out_char:\n"
            + initFunction
            + "\t\tlw $a0, 8($sp) #carga un valor de la memoria en el registro $a0. El valor se carga desde la dirección de memoria que se encuentra 8 bytes por encima del puntero de pila ($sp)\n"
            + "\t\tli $v0, 11 #carga el valor 11 (print char) en el registro $v0\n"
            + "\t\tsyscall #syscall\n"
            + endFunctionOut;


        //METODO  in_str
        asm += "\tIO_in_str:\n"
            + initFunction
            + "\t\tli $v0, 8 #carga el valor 8 (read string) en el registro $v0\n"
            + "\t\tsyscall #syscall\n"
            + "\t\tmove $t1,$v0 #copies the value from register $v0 to register $t1\n"
            + endFunctionIn;

        //METODO  in_int
        asm += "\tIO_in_int:\n"
            + initFunction
            + "\t\tli $v0, 5 #carga el valor 5 (read int) en el registro $v0\n"
            + "\t\tsyscall #syscall\n"
            + "\t\tmove $t1,$v0 #copies the value from register $v0 to register $t1\n"
            + endFunctionIn;

        //METODO  in_bool
        asm += "\tIO_in_bool:\n"
            + initFunction
            + "\t\tli $v0, 5 #carga el valor 5 (read int) en el registro $v0\n"
            + "\t\tsyscall #syscall\n"
            + "\t\tmove $t1,$v0 #copies the value from register $v0 to register $t1\n"
            + endFunctionIn;

        //METODO  in_char
        asm += "\tIO_in_char:\n"
            + initFunction
            + "\t\tli $v0, 12 #carga el valor 12 (read char) en el registro $v0\n"
            + "\t\tsyscall #syscall\n"
            + "\t\tmove $t1,$v0 #copies the value from register $v0 to register $t1\n"
            + endFunctionIn;


        //METODO  out_array_int
        //METODO  out_array_str
        //METODO  out_array_bool
        //METODO  out_array_char


        //ARRAY
        //METODO ARRAY LENGTH


        //STRING
        //METODO STRING CONCAT
        //METODO STRING LENGTH
    } 

    private void generateCode () {
        
    }
}
