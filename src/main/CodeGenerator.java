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
        asm=generateAsm();

        //Escribe el resultado de la tabla de simbolos
        //Static.write(syntacticAnalyzer.toJSON().get(0), resultPath + ".ts.json");

        //Escribe el resultado del ast
        //Static.write(syntacticAnalyzer.toJSON().get(1), resultPath + ".ast.json");

        //Escribe el codigo MIPS
        Static.write(asm, resultPath + ".asm");
    }

    private String generateAsm(){
        asm+="\n.data\n";
        asm+=generateData();
        asm+="\n.text #put things into the text segment...\n";
        asm+=generateCodePredefinedMethods();
        asm+="\t.globl main\n";

        //incluir los cir de cada struct en .data
        
        asm += "main: \n";

        //incluir en .text info de vt, metodos y constructores de struts declarados, y metodos de structs predefinidos
        
        //añadir en asm la generacion de codigo de cada nodo del ast

        

        //exit
        asm+="li $v0, 10 #exit"; //10 es exit syscall
        asm+="syscall";

        return asm;
    }


    public String generateData(){
        String out="";
        for (String sStruct : syntacticAnalyzer.getSymbolTable().getStructs().keySet()) {

            String struct = sStruct.replaceAll("\\s", ""); //nombre struct sin espacios
            String vt="\n";
            for (String method : syntacticAnalyzer.getSymbolTable().getStruct(sStruct).getMethods().keySet()){
                vt+= "\t" + ".word " + struct +"_" + method + "\n";
            }

            out += struct + "_vtable: ";
            out += vt;

        }

        //add var de instancia

        return out;

    }

    public void generateText(){

    }


    public String generateCodePredefinedMethods(){
        String out;

        String initFunction= "move $fp, $sp #mueve el contenido de $sp a $fp\n"
                    +"sw $ra, 0($sp) #copia el contenido de $ra a $sp (direccion de retorno)\n"
                    +"addiu $sp, $sp, -4 #mueve el $sp 1 pos arriba\n";

        String endFunctionOut=
            "lw $ra 4($sp)  #carga un valor de la memoria en el registro $ra. El valor se carga desde la dirección de memoria que se encuentra 4 bytes por encima del puntero de pila ($sp)\n"            
            + "addiu $sp $sp 12 # mueve el $sp \n"  //12 porque z=4*n + 8 (n cant de parametros=1)
            + "lw $fp 0($sp)   #Esta instrucción carga un valor de la memoria en el registro $fp. El valor se carga desde la dirección de memoria que se encuentra en la parte superior de la pila (0($sp))\n"
            + "jr $ra          # salta a la dirección almacenada en el registro $ra\n";

            
        String endFunctionIn=
        "lw $ra 4($sp)  #carga un valor de la memoria en el registro $ra. El valor se carga desde la dirección de memoria que se encuentra 4 bytes por encima del puntero de pila ($sp)\n"            
        + "addiu $sp $sp 8 # mueve el $sp \n"  //8 porque z=4*n + 8 (n cant de parametros=0)
        + "lw $fp 0($sp)   #Esta instrucción carga un valor de la memoria en el registro $fp. El valor se carga desde la dirección de memoria que se encuentra en la parte superior de la pila (0($sp))\n"
        + "jr $ra          # salta a la dirección almacenada en el registro $ra\n";

        // IO 
        //METODO out str
        out ="IO_out_str:\n"
            +initFunction
            +"lw $a0, 8($sp) #carga un valor de la memoria en el registro $a0. El valor se carga desde la dirección de memoria que se encuentra 8 bytes por encima del puntero de pila ($sp)\n"
            +"li $v0, 4 #carga el valor 4 (print string) en el registro $v0\n"
            +"syscall #syscall\n"
            +endFunctionOut;

        //METODO  out_int
        out+="IO_out_int:\n"
            +initFunction
            +"lw $a0, 8($sp) #carga un valor de la memoria en el registro $a0. El valor se carga desde la dirección de memoria que se encuentra 8 bytes por encima del puntero de pila ($sp)\n"
            +"li $v0, 1 #carga el valor 1 (print int) en el registro $v0\n"
            +"syscall #syscall\n"
            +endFunctionOut;

        //METODO  out_bool
        out+="IO_out_bool:\n"
            +initFunction
            +"lw $a0, 8($sp) #carga un valor de la memoria en el registro $a0. El valor se carga desde la dirección de memoria que se encuentra 8 bytes por encima del puntero de pila ($sp)\n"
            +"li $v0, 1 #carga el valor 1 (print int) en el registro $v0\n"
            +"syscall #syscall\n"
            +endFunctionOut;


        //METODO  out_char
        out+="IO_out_char:\n"
            +initFunction
            +"lw $a0, 8($sp) #carga un valor de la memoria en el registro $a0. El valor se carga desde la dirección de memoria que se encuentra 8 bytes por encima del puntero de pila ($sp)\n"
            +"li $v0, 11 #carga el valor 11 (print char) en el registro $v0\n"
            +"syscall #syscall\n"
            +endFunctionOut;


        //METODO  in_str
        out+="IO_in_str:\n"
            +initFunction
            +"li $v0, 8 #carga el valor 8 (read string) en el registro $v0\n"
            +"syscall #syscall\n"
            +"move $t1,$v0 #copies the value from register $v0 to register $t1\n"
            +endFunctionIn;

        //METODO  in_int
        out+="IO_in_int:\n"
        +initFunction
        +"li $v0, 5 #carga el valor 5 (read int) en el registro $v0\n"
        +"syscall #syscall\n"
        +"move $t1,$v0 #copies the value from register $v0 to register $t1\n"
        +endFunctionIn;

        //METODO  in_bool
        out+="IO_in_bool:\n"
        +initFunction
        +"li $v0, 5 #carga el valor 5 (read int) en el registro $v0\n"
        +"syscall #syscall\n"
        +"move $t1,$v0 #copies the value from register $v0 to register $t1\n"
        +endFunctionIn;

        //METODO  in_char
        out+="IO_in_char:\n"
        +initFunction
        +"li $v0, 12 #carga el valor 12 (read char) en el registro $v0\n"
        +"syscall #syscall\n"
        +"move $t1,$v0 #copies the value from register $v0 to register $t1\n"
        +endFunctionIn;


        //METODO  out_array_int
        //METODO  out_array_str
        //METODO  out_array_bool
        //METODO  out_array_char


        //ARRAY
        //METODO ARRAY LENGTH


        //STRING
        //METODO STRING CONCAT
        //METODO STRING LENGTH

        return out;
    } 

}
