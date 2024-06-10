package src.lib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import src.lib.exceptionHelper.CustomException;
import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.symbolTableHelper.Metadata;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

/**
 * Esta clase se utilizará para realizar funciones estáticas en el programa.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 07/03/2024
 */
public class Static {
    private Static () {}
    /**
     * Método que se utiliza para escribir los resultados del analizador léxico
     * ya sea por consola o en un fichero específico.
     * 
     * @since 09/03/2024
     * @param tokens Lista de Tokens que se escribirán.
     * @param path Path hacia el archivo resultante.
     */
    public static void writeTokens (ArrayList<Token> tokens, String path) {
        int count = tokens.size() - 1;
        //Genera el texto que se debe guardar
        String text = Const.SUCCESS_LEXICAL_HEADER + "\n";

        //Escribir cada elemento en una línea separada
        for (Token token : tokens) {
            text += token.toString() + (count > 0 ? "\n" : "");
            count--;
        }
        
        //Escribe o muestra el resultado
        if(path == null){
            System.out.println(text);
        }
        else {
            write(text, path);
        }
    }

    /**
     * Imprime o escribe un error.
     * 
     * @since 07/03/2024
     * @param error Tipo de dato error con los detalles a mostrar.
     * @param path Ubicación del archivo que guardará la respuesta.
     */
    public static void writeError(CustomException error, String path) {
        //Escribe el resultado
        if(path != null){
            write(error.getMessage(), path);
        }
        //Muestra el resultado por consola
        else {
            System.out.println(error.getMessage());
        }
    }

    /**
     * Escribe un string en un archivo dado.
     * 
     * @since 12/03/2024
     * @param text Texto completo a escribir.
     * @param path Ubicación al archivo en el que se guardará el resultado.
     */
    public static void write (String text, String path) {
        try {
            java.io.FileWriter writer = new java.io.FileWriter(path);
            writer.write(text);
            writer.close();
        }
        catch (Exception e) {
            System.out.println(Const.ERROR_CREATE_FILE_WRITER);
        }
    }

    /**
     * Valida si un caracter es mayúscula.
     * 
     * @since 09/03/2024
     * @param c Caracter a validar.
     * @return Booleando con la respuesta.
     */
    public static boolean isUppercase(char c) {
        return 64 < c && c < 91;
    }

    /**
     * Valida si un caracter es minúscula.
     * 
     * @since 09/03/2024
     * @param c Caracter a validar.
     * @return Booleando con la respuesta.
     */
    public static boolean isLowercase(char c) {
        return 96 < c && c < 123;
    }

    /**
     * Valida si un caracter es un número.
     * 
     * @since 09/03/2024
     * @param c Caracter a validar.
     * @return Booleando con la respuesta.
     */
    public static boolean isNumber(char c) {
        return 47 < c && c < 58;
    }

    /** 
     * Mapea el tipo de dato, solo si es de tipo primitivo
     * @param type Tipo de dato
     * @return String
     */
    public static String getPrimitiveDataType (String type) {
        String result = type;
        //Si posee la palabra literal, obtiene el tipo de dato primitivo
        if (type.contains("literal")) {
            result = type.split("literal")[1].trim();
        }
        return result;
    }

    
    /** 
     * Valida que el tipo de dato dado sea válido según herencia.
     * @param st Tabla de símbolos
     * @param origin Tipo de dato de origen
     * @param currentType Tipo de dato a validar
     * @param metadata Metadata para errores
     */
    public static void checkInherited (SymbolTable st, String origin, String currentType, Token metadata) {
        boolean isInherited = false;
        String resultType=currentType;
        //Solo si el tipo de dato no es void
        if (!currentType.equals("void")) {
            //Valida asignacion hereditaria, hasta llegar a Object o se encuentre herencia
            while (!currentType.equals("Object") && !isInherited) {
                //Parseo el tipo de dato
                currentType = getPrimitiveDataType(currentType);
                
                //Obtiene el padre 
                currentType = st.getStruct(currentType).getParent();
    
                //Valida si se obtuvo el tipo correcto
                isInherited = origin.equals(currentType);
            }
        }

        //Si no encuentra herencia, retorna error
        if (!isInherited) {
            throw new SemanticException(metadata, "Se esperaba una variable de tipo " + origin + " y se encontro una de tipo " + resultType + ".", true);
        }
    }

    
    /** 
     * Consolida los parámetros de un método.
     * 
     * @param params Parámetros a consolidar
     * @param st Tabla de símbolos
     * @param struct Estructura actual
     * @param method Método actual
     * @param methodToCheckParams Método a validar
     * @param identifier Identificador para manejo de errores
     */
    public static void consolidateParams (ArrayList<Expression> params, SymbolTable st, Struct struct, Method method, Method methodToCheckParams, Token identifier) {
        String resultType, paramType;

        //Si no se le envia la cantidad necesaria de parametros, retorna null
        if (methodToCheckParams.getParamsSize() != params.size()) {
            throw new SemanticException(identifier, "Cantidad de argumentos inválida.", true);
        }
        
        //Consolida los parametros
        for (Expression param : params) {
            //Consolida la expresion
            param.consolidate(st, struct, method, null);
            
            // Valida que el tipo de dato del parametro sea el mismo
            resultType = getPrimitiveDataType(param.getResultTypeChained());
            paramType = methodToCheckParams.getParamType(param.getPosition());
            //Si son tipos de datos distintos
            if (!paramType.equals(resultType)) {
                //Si el valor a enviar es nil
                if (resultType.equals("NIL")) {
                    //El parametro no debe ser de tipo dato primitivo
                    if (Const.primitiveTypes.contains(paramType)) {
                        throw new SemanticException(identifier, "Se esperaba un tipo de dato " + paramType + ". Se encontró " + resultType, true);
                    }
                }
                else {
                    // si los tipos son distintos y un parametro es de tipo Object                     
                    if (resultType.equals("Object")){
                        throw new SemanticException(identifier, "Se esperaba un tipo de dato " + paramType + ". Se encontró " + resultType, true);                        
                    }
                    //Valida asignacion hereditaria, hasta llegar a Object o se encuentre herencia
                    Static.checkInherited(st, paramType, resultType, identifier);
                }
            }
        }
    }

    public static String getCodeDataType (IDToken id) {
        String result;
        switch (id) {
            case typeINT:
            case typeBOOL:
                result = ": .word 0 \n"; //reserva var int o bool con valor 0
                break;
            case typeCHAR:
            case typeSTR:
                result = ": .asciiz \"\" \n"; //reserva var str o char con valor ""
                break;
            case idSTRUCT:
            case spOBJECT:
                result = ": .word 0 \n"; //reserva objetos
                break;
            default:
                //Array 
                result = ": .space 8 \n"; //reserva espacio para Array
                break;
        }
        return result;
    }


    /**
     * SE DEBEN COMPLETAR LOS DATOS DE LOS REGISTROS TEMPORALES CON ATERIORIDAD
     * @param type
     * @param index
     * @return
     */
    public static String initStackData (IDToken type, int index) {
        //Siempre inicializa con 0
        String result = "sw $0, ";

        //Si es string o char inicializa con string predefinido
        if (type.equals(IDToken.typeCHAR) || type.equals(IDToken.typeSTR)) {
            result = "sw $t0, ";
        }

        //Agrega el puntero
        result += index +"($sp)";
        return result;
    }

    public static String initCirData (IDToken type, int index) {
        //Siempre inicializa con 0
        String result = "sw $0, ";

        //Si es string o char inicializa con string predefinido
        if (type.equals(IDToken.typeCHAR) || type.equals(IDToken.typeSTR)) {
            result = "sw $t0, ";
        }

        //Agrega el puntero
        result += index +"($v0)";
        return result;
    }

    /**
     * Ordena una estructura dada
     * @param entity Estructura a ordenar
     * @return Arreglo con elementos ordenados
     */
    @SuppressWarnings("unchecked")
    static public String [] order (HashMap<String, ?> entity) {
        String[] result = new String[entity.size()];
        for (Metadata object : (Collection<Metadata>)entity.values()) {
            result[object.getPosition()] = object.getName();
        }
        return result;
    }

    static public String generatePredefinedMethods(){
        String code;
        code = """
.data
	IO_false: .asciiz "false"
	IO_true: .asciiz "true"
	IO_newL: .asciiz ""
	IO_ingresar_int: .asciiz "Ingresar valor entero: "
	IO_ingresar_str: .asciiz "Ingresar valor str: "
	IO_ingresar_bool: .asciiz "Ingresar valor bool (0 para false, 1 para true: "
	IO_ingresar_char: .asciiz "Ingresar valor char: "
	IO_buffer_str: .space 1024	
	Str_vtable: .word Str_Constructor, length, Str_concat
	ArrayStr_vtable: .word ArrayStr_Constructor, length
	ArrayInt_vtable: .word ArrayInt_Constructor, length
	ArrayChar_vtable: .word ArrayChar_Constructor, length
	Bool_vtable: .word Bool_Constructor
	IO_vtable: .word IO_Constructor
	IO_vtable_static: .word IO_out_array_int, IO_out_array_char, IO_in_str, IO_out_char, IO_out_array_str, IO_in_int, IO_out_int, IO_in_bool, IO_out_str, IO_in_char, IO_out_bool, IO_out_array_bool
	IO_struct_static: .word IO_vtable_static
	Char_vtable: .word Char_Constructor
	Object_vtable: .word Object_Constructor
	Int_vtable: .word Int_Constructor
	ArrayBool_vtable: .word ArrayBool_Constructor, length

.text 
	IO_out_int:

		lw $a0, 4($sp) 
		li $v0, 1 #carga el valor 1 (print int) en el registro $v0
		syscall #syscall
		li $v0, 4
		la $a0, IO_newL
 		syscall
		addiu $sp, $sp, 4 # mueve el $sp 
		jr $ra          # salta a la dirección almacenada en el registro $ra

	IO_out_bool:
		lw $a0, 4($sp) 
 		beq $a0, $0,IO_out_false
 		j IO_out_true
 		
	IO_out_true:
		li $v0, 4
		la $a0, IO_true
 		syscall
 		li $v0, 4
		la $a0, IO_newL
 		syscall
 		addiu $sp, $sp, 4 # mueve el $sp (saca el parametro de la pila)  
 		jr $ra
	IO_out_false:
		li $v0, 4
		la $a0, IO_false
 		syscall
 		li $v0, 4
		la $a0, IO_newL
 		syscall
 		addiu $sp, $sp, 4 # mueve el $sp (saca el parametro de la pila) 
 		jr $ra
	IO_out_str:
		lw $a0, 4($sp) 
		li $v0, 4
 		syscall
 		li $v0, 4
		la $a0, IO_newL
 		syscall
 		addiu $sp, $sp, 4  # mueve el $sp (saca el parametro de la pila) 
 		#return
 		jr $ra
 		
 		
 	IO_out_char:
		lw $a0, 4($sp) 
    		li $v0, 11 #carga el valor 11 (print char) en el registro $v0
		syscall #syscall
 		li $v0, 4
		la $a0, IO_newL
 		syscall
 		addiu $sp, $sp, 4  # mueve el $sp (saca el parametro de la pila) 
 		jr $ra

 	IO_in_int:
 	 	li $v0, 4
		la $a0, IO_ingresar_int
 		syscall
 		li $v0, 5 #carga el valor 5 (read int) en el registro $v0
		syscall #syscall
 		jr $ra

 		
 	 IO_in_bool:
 	  	li $v0, 4
		la $a0, IO_ingresar_bool
 		syscall
 		li $v0, 5 #carga el valor 5 (read int) en el registro $v0
 		li $a1, 4
		syscall #syscall
 		jr $ra
 	 	
 	 	
 	  IO_in_char:
 	 	li $v0, 4
		la $a0, IO_ingresar_char
 		syscall
 		li $v0, 12 #carga el valor 12 (read char) en el registro $v0
		syscall #syscall
 		jr $ra
 		
 	IO_in_str:
 	 	li $v0, 4
		la $a0, IO_ingresar_str
 		syscall
		la $a0, IO_buffer_str
 		li $v0, 8 #carga el valor 8 (read str) en el registro $v0
 		li $a1, 1024  #especificar tamaño del argumento de entrada
		syscall #syscall
		move $v0, $a0
 		jr $ra

 	ArrayInt_Constructor:
		li $v0, 9			#Syscall para reservar memoria en el heap
		addi $a0, $a0, 8			#Add space para vtable y length
		syscall
		move $t1, $a0			#Moves dimention a $t1
		la $t0, ArrayInt_vtable
		sw $t0, 0($v0)			#Saves the vtable reference
		sw $t1, 4($v0)			#Saves the dimention en el cir
		jr $ra			# salta a la dirección almacenada en el registro $ra
	ArrayStr_Constructor:
		li $v0, 9			#Syscall para reservar memoria en el heap
		addi $a0, $a0, 8			#Add space para vtable y length
		syscall
		la $t0, ArrayStr_vtable
		sw $t0, 0($v0)			#Saves the vtable reference
		sw $t1, 4($v0)			#Saves the dimention en el cir
		jr $ra          # salta a la dirección almacenada en el registro $ra
	ArrayChar_Constructor:
		li $v0, 9			#Syscall para reservar memoria en el heap
		addi $a0, $a0, 8			#Add space para vtable y length
		syscall
		la $t0, ArrayChar_vtable
		sw $t0, 0($v0)			#Saves the vtable reference
		sw $t1, 4($v0)			#Saves the dimention en el cir
		jr $ra          # salta a la dirección almacenada en el registro $ra
	ArrayBool_Constructor:
		li $v0, 9			#Syscall para reservar memoria en el heap
		addi $a0, $a0, 8			#Add space para vtable y length
		syscall
		la $t0, ArrayBool_vtable
		sw $t0, 0($v0)			#Saves the vtable reference
		sw $t1, 4($v0)			#Saves the dimention en el cir
		jr $ra          # salta a la dirección almacenada en el registro $ra
	Array_length:
		
	IO_out_array_int:
	IO_out_array_str:
	IO_out_array_bool:
	IO_out_array_char:
	ArrayStr_length:
	ArrayInt_length:
	ArrayChar_length:
	ArrayBool_length:
	Str_concat:
	Str_length:
	Str_Constructor: 
	IO_Constructor: 
	Bool_Constructor: 
	Char_Constructor:
	length: 
	Object_Constructor: 
	Int_Constructor:

                """;
    return code;
    }
}
