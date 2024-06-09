package src.lib.semanticHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.symbolTableHelper.*;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

/**
 * Esta clase se encarga de contener la estructura del código fuente.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 19/04/2024
 */
public class SymbolTable {
    private final HashSet<String> staticStruct;
    // private Struct currentStruct;
    // private Method currentMethod;
    private Method start;
    private HashMap<String, Struct> structs;

    // Estructura que se utiliza para almacenar clases que deben reasignar herencias.
    // En consolidación: Si posee elementos, debe retornar error.
    private HashMap<String, ArrayList<String>> redefinitions;

    // Estructura que se utiliza para almacenar structs que se debe chequear su declaracion.
    private HashMap<String,Token> checkDefinitionStructs;

    //Guarda un contador de sentencias
    private int conditionalCounter, loopCounter,literalStrCounter;

    /**
     * Constructor de la clase.<br/>
     * 
     * Realiza la precarga de clases Object e IO
     */
    public SymbolTable () {
        staticStruct = new HashSet<String>(){{
            add("Object");
            add("IO");
            add("Array");
            add("Array Int");
            add("Array Str");
            add("Array Bool");
            add("Array Char");
            add("Int");
            add("Str");
            add("Char");
            add("Bool");
        }};
        structs = new HashMap<String, Struct>();
        redefinitions = new HashMap<>();
        checkDefinitionStructs = new HashMap<String, Token>();
        conditionalCounter = 0;
        loopCounter = 0;
        init();
    }

    private void init() {
        //Definicion de estructuras
        Struct objectStruct = new Struct(new Token(IDToken.spOBJECT, "Object", 0, 0), null),
            IO = new Struct(new Token(IDToken.spIO, "IO", 0, 0),objectStruct),
            ArrayStr   = new Struct(new Token(IDToken.typeARRAY, "Array Str", 0, 0), objectStruct),
            ArrayBool  = new Struct(new Token(IDToken.typeARRAY, "Array Bool", 0, 0), objectStruct),
            ArrayInt   = new Struct(new Token(IDToken.typeARRAY, "Array Int", 0, 0), objectStruct),
            ArrayChar  = new Struct(new Token(IDToken.typeARRAY, "Array Char", 0, 0), objectStruct),
            Int = new Struct(new Token(IDToken.typeINT, "Int", 0, 0), objectStruct),
            Str = new Struct(new Token(IDToken.typeSTR, "Str", 0, 0), objectStruct),
            Char = new Struct(new Token(IDToken.typeCHAR, "Char", 0, 0), objectStruct),
            Bool = new Struct(new Token(IDToken.typeBOOL, "Bool", 0, 0), objectStruct);

        //Parametros compartidos
        ArrayList<Param> strParams = generateArrayParam(IDToken.typeSTR, "s"),
            arrayParams = generateArrayParam( IDToken.typeARRAY, "a"),
            nullParams = new ArrayList<Param>();

        //Hash con definicion de metodos
        HashMap<String, HashMap<String, ArrayList<Param>>> definitions = new HashMap<String, HashMap<String, ArrayList<Param>>>(){{
            put("IO", new HashMap<String, ArrayList<Param>>(){{
                put("out_str", strParams);
                put("out_int", generateArrayParam(IDToken.typeINT, "i"));
                put("out_bool", generateArrayParam(IDToken.typeBOOL, "b"));
                put("out_char", generateArrayParam(IDToken.typeCHAR, "c"));
                put("out_array_int", arrayParams);
                put("out_array_str", arrayParams);
                put("out_array_bool", arrayParams);
                put("out_array_char", arrayParams);
                put("in_str", nullParams);
                put("in_int", nullParams);
                put("in_bool", nullParams);
                put("in_char", nullParams);
            }});
            put("Array Str", new HashMap<String, ArrayList<Param>>(){{
                put("length", nullParams);
            }});
            put("Array Int", new HashMap<String, ArrayList<Param>>(){{
                put("length", nullParams);
            }});
            put("Array Char", new HashMap<String, ArrayList<Param>>(){{
                put("length", nullParams);
            }});
            put("Array Bool", new HashMap<String, ArrayList<Param>>(){{
                put("length", nullParams);
            }});
            put("Str", new HashMap<String, ArrayList<Param>>(){{
                put("length", nullParams);
                put("concat", strParams);
            }});
        }};

        //Retornos de metodos si tuviesen
        HashMap<String, HashMap<String, Token>> returns = new HashMap<String, HashMap<String, Token>>(){{
            put("IO", new HashMap<String, Token>() {{
                put("in_str", new Token(IDToken.typeSTR, IDToken.typeSTR.toString(), 0, 0));
                put("in_int", new Token(IDToken.typeINT, IDToken.typeINT.toString(), 0, 0));
                put("in_bool", new Token(IDToken.typeBOOL, IDToken.typeBOOL.toString(), 0, 0));
                put("in_char", new Token(IDToken.typeCHAR, IDToken.typeCHAR.toString(), 0, 0));
            }});
            put("Array Str", new HashMap<String, Token>() {{
                put("length", new Token(IDToken.typeINT, IDToken.typeINT.toString(), 0, 0));
            }});
            put("Array Bool", new HashMap<String, Token>() {{
                put("length", new Token(IDToken.typeINT, IDToken.typeINT.toString(), 0, 0));
            }});
            put("Array Int", new HashMap<String, Token>() {{
                put("length", new Token(IDToken.typeINT, IDToken.typeINT.toString(), 0, 0));
            }});
            put("Array Char\"", new HashMap<String, Token>() {{
                put("length", new Token(IDToken.typeINT, IDToken.typeINT.toString(), 0, 0));
            }});
            put("Str", new HashMap<String, Token>() {{
                put("length", new Token(IDToken.typeINT, IDToken.typeINT.toString(), 0, 0));
                put("concat", new Token(IDToken.typeSTR, IDToken.typeSTR.toString(), 0, 0));
            }});
        }};

        //Inserta las estructuras
        structs.put("Object", objectStruct);
        structs.put("IO", IO);
        structs.put("Char", Char);
        structs.put("Str", Str);
        structs.put("Array Str", ArrayStr);
        structs.put("Array Bool", ArrayBool);
        structs.put("Array Int", ArrayInt);
        structs.put("Array Char", ArrayChar);
        structs.put("Int", Int);
        structs.put("Bool", Bool);

        //Inserta los métodos en las estructuras
        definitions.forEach((String sStructKey, HashMap<String, ArrayList<Param>> mapParams) -> {
            //Recorre los metodos
            mapParams.forEach((String sMethodKey, ArrayList<Param> params) -> {
                Boolean isStatic= (sStructKey.equals("IO") ? true : false);
                //Agrega el metodo a la estructura correspondiente
                addVoid(
                    structs.get(sStructKey), 
                    new Token(IDToken.idOBJECT, sMethodKey, 0, 0),
                    params, isStatic,
                    returns.get(sStructKey) != null ? returns.get(sStructKey).get(sMethodKey) : null
                );

            });
        });
    }

    public int addConditionalSentenceCounter() {
        return ++this.conditionalCounter;
    }
    public int addLoopSentenceCounter() {
        return ++this.loopCounter;
    }

    public int addLiteralStrCount(){
        return ++this.literalStrCounter;
    }

    public String generateCode () {
        String code = ".data\n", aux = "", aux1 = "";

        //AGREGA LA INICIALIZACION DE STRINGS
        code += "\tdefault_string: .asciiz \"\"\n";
        
        //AGREGA STRING DE ERROR DIVISION POR CERO
        code += "\tdivision0: .asciiz \"ERROR: DIVISION POR CERO\" \n";
        
        //AGREGA LAS VIRTUAL TABLES DE LOS STRUCTS (EXCEPTO DE LOS STRUCT PREDEFINIDOS)
        for (String sStruct : structs.keySet()) {
            if (!staticStruct.contains(sStruct)){
                aux = "";
                aux1 = "";
    
                //Genera los strings con los metodos estaticos y no estaticos
                for (String method : structs.get(sStruct).getMethods().keySet()) {
                    if (structs.get(sStruct).getMethod(method).isStatic()) {
                        aux1 += method + ", ";
                    } else {
                        aux += method + ", ";
                    }
                }
                
                //Nombre de la estructura sin espacios
                sStruct = sStruct.replaceAll("\\s", "");
    
                //Elimino las comas finales y agrega los nombres de estructuras
                if (aux.length() > 0) {
                    aux = sStruct + "_" +aux.substring(0, aux.length() - 2).replaceAll(", ", ", " + sStruct + "_");
                }
                //Genera la vtable
                aux = "\t" + sStruct + "_vtable: .word " + sStruct + "_Constructor" + (aux.length() > 0 ? ", " : "") + aux + "\n";
    
                //Valida si debe agregar la vtable de metodos estaticos
                if (aux1.length() > 0) {
                    aux1 = aux1.substring(0, aux1.length() - 2).replaceAll(", ", ", " + sStruct + "_");                    
                    aux += "\t" + sStruct + "_vtable_static: .word " + sStruct + "_" + aux1 + "\n";
    
                    //Agrega la variable que referencia a la vtable static
                    aux += "\t" + sStruct + "_struct_static: .word " + sStruct + "_vtable_static\n";
                }
    
                code += aux;
            }
        }
        
        //AGREGA EL CÓDIGO DE LOS METODOS PREDEFINIDOS
        // code += "\n#### PREDEFINED METHODS CODE ####\n.text\n";
        // code += generatePredefinedCode();

        //Reserva los datos del metodo start
        code += "\t#Main\n\t.text\n\t.globl main\n\n";
        code += "main:\n#### MAIN DATA ####\n";
        code += start.generateCode();

        return code;
    }
    private String generatePredefinedCode(){
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

        //Array_Constructor
        int space=0;

        //ArrayInt_Constructor
        String code = "\tArrayInt_Constructor:\n";
        space=0;
        code += "\t\tli $v0, 9\t\t\t#Syscall para reservar memoria en el heap\n";
        code += "\t\taddi $a0, $a0, 8\t\t\t#Add space para vtable y length\n"; //ya trae en $a0 la dimention desde createArray, aca se suma 8
        code += "\t\tsyscall\n";
        code += "\t\tmove $t1, $a0\t\t\t#Moves dimention a $t1\n";
        //Guarda la referencia a la vtable (Inicio del CIR del Array)
        code += "\t\tla $t0, ArrayInt_vtable\n";
        code += "\t\tsw $t0, " + space + "($v0)\t\t\t#Saves the vtable reference\n";
        space += 4;
        code += "\t\tsw $t1, " + space + "($v0)\t\t\t#Saves the dimention en el cir\n";
        //inicializar posiciones del array
        // code += "\t\tli $t2, 0\t\t\t#Init counter\n";
        // code += "\tloop_Array_Int_Constructor:\n";
        // code += "\t\tbge $t2, $t1, end_loop_Array_Int_Constructor\t\t\t#branch if $t2 is greater or equal than $t1 (counter>=dimention)\n";
        // code += "\t\tlw "
        // code += "\t\tsw $0, " +  + "($v0)\t\t\t#Initialize int\n";
        // code += "\t\taddi $t2, $t2, 4\t\t\t#incrementar counter\n";
        // code += "\tend_loop_Array_Int_Constructor:\n";
        code += "\t\tjr $ra\t\t\t# salta a la dirección almacenada en el registro $ra\n";

        //ArrayStr_Constructor
        code += "\tArrayStr_Constructor:\n";
        space=0;
        code += "\t\tli $v0, 9\t\t\t#Syscall para reservar memoria en el heap\n";
        code += "\t\taddi $a0, $a0, 8\t\t\t#Add space para vtable y length\n"; //ya trae en $a0 la dimention desde createArray, aca se suma 8
        code += "\t\tsyscall\n";
        //Guarda la referencia a la vtable (Inicio del CIR del Array)
        code += "\t\tla $t0, ArrayStr_vtable\n";
        code += "\t\tsw $t0, " + space + "($v0)\t\t\t#Saves the vtable reference\n";
        space += 4;
        code += "\t\tsw $t1, " + space + "($v0)\t\t\t#Saves the dimention en el cir\n";

        code += "\t\tjr $ra          # salta a la dirección almacenada en el registro $ra\n";

        //ArrayChar_Constructor
        code += "\tArrayChar_Constructor:\n";
        space=0;
        code += "\t\tli $v0, 9\t\t\t#Syscall para reservar memoria en el heap\n";
        code += "\t\taddi $a0, $a0, 8\t\t\t#Add space para vtable y length\n"; //ya trae en $a0 la dimention desde createArray, aca se suma 8
        code += "\t\tsyscall\n";
        //Guarda la referencia a la vtable (Inicio del CIR del Array)
        code += "\t\tla $t0, ArrayChar_vtable\n";
        code += "\t\tsw $t0, " + space + "($v0)\t\t\t#Saves the vtable reference\n";
        space += 4;
        code += "\t\tsw $t1, " + space + "($v0)\t\t\t#Saves the dimention en el cir\n";

        code += "\t\tjr $ra          # salta a la dirección almacenada en el registro $ra\n";

        //ArrayBool_Constructor
        code += "\tArrayBool_Constructor:\n";
        space=0;
        code += "\t\tli $v0, 9\t\t\t#Syscall para reservar memoria en el heap\n";
        code += "\t\taddi $a0, $a0, 8\t\t\t#Add space para vtable y length\n"; //ya trae en $a0 la dimention desde createArray, aca se suma 8
        code += "\t\tsyscall\n";
        //Guarda la referencia a la vtable (Inicio del CIR del Array)
        code += "\t\tla $t0, ArrayBool_vtable\n";
        code += "\t\tsw $t0, " + space + "($v0)\t\t\t#Saves the vtable reference\n";
        space += 4;
        code += "\t\tsw $t1, " + space + "($v0)\t\t\t#Saves the dimention en el cir\n";

        code += "\t\tjr $ra          # salta a la dirección almacenada en el registro $ra\n";

        //Array_length
        code += "\tArray_length:\n";
        code += "\t\t\n";


        // IO 
        //METODO out str
        code += "\tIO_out_str:\n"
            + initFunction
            + "\t\tlw $a0, 8($sp) #carga un valor de la memoria en el registro $a0. El valor se carga desde la dirección de memoria que se encuentra 8 bytes por encima del puntero de pila ($sp)\n"
            + "\t\tli $v0, 4 #carga el valor 4 (print string) en el registro $v0\n"
            + "\t\tsyscall #syscall\n"
            + endFunctionOut;

        //METODO  out_int
        code += "\tIO_out_int:\n"
            + initFunction
            + "\t\tlw $a0, 8($sp) #carga un valor de la memoria en el registro $a0. El valor se carga desde la dirección de memoria que se encuentra 8 bytes por encima del puntero de pila ($sp)\n"
            + "\t\tli $v0, 1 #carga el valor 1 (print int) en el registro $v0\n"
            + "\t\tsyscall #syscall\n"
            + endFunctionOut;

        //METODO  out_bool
        code += "\tIO_out_bool:\n"
            + initFunction
            + "\t\tlw $a0, 8($sp) #carga un valor de la memoria en el registro $a0. El valor se carga desde la dirección de memoria que se encuentra 8 bytes por encima del puntero de pila ($sp)\n"
            + "\t\tli $v0, 1 #carga el valor 1 (print int) en el registro $v0\n"
            + "\t\tsyscall #syscall\n"
            + endFunctionOut;


        //METODO  out_char
        code += "\tIO_out_char:\n"
            + initFunction
            + "\t\tlw $a0, 8($sp) #carga un valor de la memoria en el registro $a0. El valor se carga desde la dirección de memoria que se encuentra 8 bytes por encima del puntero de pila ($sp)\n"
            + "\t\tli $v0, 11 #carga el valor 11 (print char) en el registro $v0\n"
            + "\t\tsyscall #syscall\n"
            + endFunctionOut;


        //METODO  in_str
        code += "\tIO_in_str:\n"
            + initFunction
            + "\t\tli $v0, 8 #carga el valor 8 (read string) en el registro $v0\n"
            + "\t\tsyscall #syscall\n"
            + "\t\tmove $t1,$v0 #copies the value from register $v0 to register $t1\n"
            + endFunctionIn;

        //METODO  in_int
        code += "\tIO_in_int:\n"
            + initFunction
            + "\t\tli $v0, 5 #carga el valor 5 (read int) en el registro $v0\n"
            + "\t\tsyscall #syscall\n"
            + "\t\tmove $t1,$v0 #copies the value from register $v0 to register $t1\n"
            + endFunctionIn;

        //METODO  in_bool
        code += "\tIO_in_bool:\n"
            + initFunction
            + "\t\tli $v0, 5 #carga el valor 5 (read int) en el registro $v0\n"
            + "\t\tsyscall #syscall\n"
            + "\t\tmove $t1,$v0 #copies the value from register $v0 to register $t1\n"
            + endFunctionIn;

        //METODO  in_char
        code += "\tIO_in_char:\n"
            + initFunction
            + "\t\tli $v0, 12 #carga el valor 12 (read char) en el registro $v0\n"
            + "\t\tsyscall #syscall\n"
            + "\t\tmove $t1,$v0 #copies the value from register $v0 to register $t1\n"
            + endFunctionIn;


        //METODO  out_array_int
        code += "\tIO_out_array_int:\n";

        //METODO  out_array_str
        code += "\tIO_out_array_str:\n";

        //METODO  out_array_bool
        code += "\tIO_out_array_bool:\n";
        
        //METODO  out_array_char
        code += "\tIO_out_array_char:\n";


        //ARRAY
        //METODO ARRAY LENGTH
        code += "\tArrayStr_length:\n";
        code += "\tArrayInt_length:\n";
        code += "\tArrayChar_length:\n";
        code += "\tArrayBool_length:\n";



        //STRING
        //METODO STRING CONCAT
        code += "\tStr_concat:\n";
        
        //METODO STRING LENGTH
        code += "\tStr_length:\n";
        

        return code;
    } 

    /** 
     * Método interno que se utiliza para agregar métodos estáticos predefinidos.
     * 
     * @param struct Estructura a la cual agregar el método.
     * @param token Metadata del método a agregar.
     * @param params Lista de parámetros.
     * @param returnType Tipo de dato a retornar.
     */
    private void addVoid (Struct struct, Token token, ArrayList<Param> params, Boolean isStatic, Token returnType) {
        struct.addMethod(token, params, isStatic, returnType == null ? new Token(IDToken.typeVOID, "void", token.getLine(), token.getColumn()) : returnType);
    }

    /** 
     * Método que genera un array de parámetros
     * 
     * @param type IDToken con el tipo de parámetro
     * @param lexema Lexema para generar el token
     * @return ArrayList<Param>
     */
    private ArrayList<Param> generateArrayParam(IDToken type, String lexema){
        return new ArrayList<Param>(){{
            add(
                new Param(
                    new Token(type, lexema, 0, 0),
                    new Token(type, type.toString(), 0, 0), 
                    0
                )
            );
        }};
    }

    /**
     * Método que agrega una estructura a la tabla de símbolos.<br/>
     * 
     * <br/>Realiza las siguientes validaciones:<br/>
     * - Herencias cíclicas.<br/>
     * - Si ya se ha generado desde un impl o struct.<br/>
     * 
     * <br/>Realiza las siguientes acciones:<br/>
     * - Aumenta el contador de veces que se ha leido desde un struct o impl.<br/>
     * - Sobreescribe o inicializa la herencia.<br/>
     * - Actualiza la estructura actual.<br/>
     * - Asigna superclases cuando se define (Si se utiliza antes).<br/>
     * 
     * 
     * @since 19/04/2024
     * @param token Metadata con el token correspondiente al idStruct
     * @param parent IDToken que representa la clase de la cual hereda el struct (Por defecto Object)
     * @param isFromStruct Booleano que avisa si se está generando desde un struct o un implement
     * @return Struct
     */
    public Struct addStruct(Token token, Token parent, boolean isFromStruct) {
        String sStruct = token.getLexema(), sParent = parent != null ? parent.getLexema() : "Object";
        Struct parentStruct = structs.get(sParent),
            currentStruct;


        //Valida que no se herede de los tipos primitivos
        if (!sParent.equals("Object") && staticStruct.contains(sParent)) {
            throw new SemanticException(token, "No se puede heredar de un tipo de dato predefinido. Tipo: " + sParent);
        }
        
        //Valida que no herede de el mismo
        if (sParent.equals(sStruct)) {
            throw new SemanticException(token, "No se permite heredar de sí mismo. Struct: " + sStruct);
        }
        
        //Si no se ha definido la superclase, la agrega a un stack de redefinición y asigna object
        if (parentStruct == null) {
            //Avisa que cuando se defina, se debe setear como superclase de esta struct.
            if (!redefinitions.containsKey(sParent)) {
                redefinitions.put(sParent, new ArrayList<String>());
            }
            redefinitions.get(sParent).add(sStruct);
            
            //Por ahora, asigna Object
            parentStruct = structs.get("Object");
        }

        //Actualiza la estructura actual
        currentStruct = structs.get(sStruct);
        
        //Genera la estructura si no existe
        if (currentStruct == null) {
            currentStruct = new Struct(token, parentStruct);
        }
        //Si ya existe, valida si se esta definiendo desde un struct para sobreescribir la superclase
        else {
            if (isFromStruct) {
                currentStruct.setParent(parentStruct);
            }
        }

        //Valida si debe asignarse como superclase de otras estructuras y lo hace
        if (redefinitions.get(sStruct) != null) {
            for (String children : redefinitions.get(sStruct)) {
                //Se asigna como padre
                structs.get(children).setParent(currentStruct);
                
                //Lo agrega como hijo
                //currentStruct.addChildren(structs.get(children), isFromStruct);
            }

            //Avisa que ya se ha asignado a las structs incompletas
            redefinitions.remove(sStruct);
        }

        //Valida si debe notificar que se ha definido en parametro o retorno
        if (checkDefinitionStructs.get(sStruct) != null) {
            checkDefinitionStructs.remove(sStruct);
        }

        //Valida herencia cíclica
        checkParents(currentStruct, token);

        //Avisa si se está definiendo desde un struct o impl (Puede retornar error)
        currentStruct.updateCount(isFromStruct);

        //Agrega la estructura al hash
        structs.put(sStruct, currentStruct);

        //Retorna el dato recien creado
        return currentStruct;
        //Agrega la relacion con el padre solo si se llama desde un struct
        //addParentRelationships(sParent, currentStruct, isFromStruct);
    }

    /**
     * Método que valida herencia cíclica.
     * 
     * @param struct
     * @param token
     * @since 21/04/2024
     */
    private void checkParents (Struct struct, Token token) {
        HashSet<String> stack = new HashSet<String>();
        String parent = struct.getParent();

        //Inicializa el stack
        stack.add(struct.getName());

        //Valida hasta que se herede de object o no se haya definido el parent
        while (parent != "Object") {
            //Si el padre de la estructura actual ya existe en el stack, es porque existe herencia cíclica.
            if (stack.contains(parent)) {
                throw new SemanticException(token, "La estructura posee herencia cíclica. Estructura que genera el ciclo: " + struct.getName());
            }
            stack.add(parent);
            struct = structs.get(parent);
            parent = struct.getParent();
        }
    }

    /**
     * Método que agrega una variable a la tabla de símbolos. Este deriva la lógica en el método de la estructura o método correspondiente.
     * 
     * @since 19/04/2024
     * @param token Metadata con el token correspondiente al idVar
     * @param type Tipo de dato
     * @param isPrivate Booleano que avisa si la variable es privada o no
     */
    public void addVar(Token token, Token type, boolean isPrivate) {
        //Valida si se ha definido la estructura de tipo
        if (!type.getLexema().contains("Array") && structs.get(type.getLexema()) == null) {
            checkDefinitionStructs.put(type.getLexema(), type);
        }
    }

    public int getVariableOffset(String sStruct, String sMethod, String name) {
        int offset;
        
        //Valida si es del metodo start
        if (sStruct.equals("start")) {
            offset = start.getVariableOffset(name);
        } else {
            offset = structs.get(sStruct).getMethod(sMethod).getVariableOffset(name);
        }

        return offset;
    }
    
    /** 
     * Obtiene una estructura dada
     * @param name Nombre de la estructura
     * @return Struct
     */
    public Struct getStruct(String name){
        return this.structs.get(name);
    }

    
    /** 
     * Obtiene el método start.
     * @return Method
     */
    public Method getStartMehod () {
        return start;
    }
    
    /**
     * Método que agrega un método a la tabla de símbolos. Este deriva la lógica en el método de la estructura.
     * 
     * @since 19/04/2024
     * @param token Metadata con el token correspondiente al idMethod
     * @param params ArrayList con los parámetros del método
     * @param isStatic Booleano que avisa si es estático o no
     * @param returnTypeToken Tipo de retorno del método
     * @param currentStruct Estructura actual a la cual hace referencia
     * @return Method
     */
    public Method addMethod(Token token, ArrayList<Param> params, boolean isStatic, Token returnTypeToken, Struct currentStruct, Boolean fromStruct) {
        Method result;
        // si fromStruct es false solo puede ser el metodo start principal
        if (fromStruct.equals(false)) {
            start = new Method(token, params, returnTypeToken, isStatic, 0);
            result = start;
        } else {
            // Valida si el tipo de retorno está definido
            if (!returnTypeToken.getIDToken().toString().contains("Array") && !IDToken.typeVOID.equals(returnTypeToken.getIDToken()) && !this.structs.containsKey(returnTypeToken.getLexema())){
                checkDefinitionStructs.put(returnTypeToken.getLexema(), returnTypeToken);
            }

            // si el tipo de dato del param no se ha definido previamente entonces 
            // se añade a checkDefinitionStructs para validarlo en la consolidación
            for (Param param : params) {
                if (!param.getType().getLexema().contains("Array") && !this.structs.containsKey(param.getType().getLexema())){
                    checkDefinitionStructs.put(param.getType().getLexema(), param.getType());
                }
            }

            //Agrega el método al hash
            result = currentStruct.addMethod(token, params, isStatic, returnTypeToken);
        }

        //Agrega el método al hash
        return result;
    }

    /**
     * Método que consolida la tabla de símbolos.<br/>
     * 
     * <br/>Realiza las siguientes validaciones:<br/>
     * - Que se hayan definido todas las clases de la cual se hereda.<br/>
     * 
     * <br/>Realiza las siguientes acciones:<br/>
     * 
     * 
     * @since 22/04/2024
     */
    public void consolidate () {
        Token metadata;
        String sMessage = "";

        //Valida si se han definido todas las clases
        if (!redefinitions.isEmpty()) {
            //Obtiene el nombre de la superclase
            sMessage = redefinitions.keySet().iterator().next();

            //Obtiene la metadata del struct que utiliza la superclase.
            metadata = structs.get(redefinitions.get(sMessage).get(0)).getMetadata();

            throw new SemanticException(metadata, "La estructura '" + sMessage + "' no se encuentra definida.");
        }

        //Valida si se han definido todos los tipos de datos
        if (!checkDefinitionStructs.isEmpty()) {
            //Obtiene el nombre del tipo
            sMessage = checkDefinitionStructs.keySet().iterator().next();
            
            //Obtiene la metadata del struct que utiliza la superclase.
            metadata = checkDefinitionStructs.get(sMessage);

            throw new SemanticException(metadata, "La estructura '" + sMessage + "' no se encuentra definida.");
        }
        
        // Consolida las estructuras a partir de Object
        structs.get("Object").consolidate(staticStruct);
        System.out.println("");
    }

    public HashMap<String,Struct> getStructs(){
        return this.structs;
    }

    /**
     * Convierte los datos en JSON.
     * 
     * @since 19/04/2024
     * @return Estructura de datos en formato JSON
     */
    public String toJSON() {
        String structJSON = "", startJSON = start.toJSON("    ");
        int count = structs.size();

        for (Struct struct : structs.values()) {

            structJSON += struct.toJSON("        ") + ( count > 1 ? "," : "") + "\n";
            count--;
            
        }

        return "{\n" +
            "    \"structs\": [\n" +
                structJSON +
            "    ],\n"+
            "    \"start\": " + startJSON.substring(4) +
        "\n}";
    }
}
