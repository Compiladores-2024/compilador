package src.lib;

import java.util.HashMap;
import java.util.HashSet;

import src.lib.tokenHelper.IDToken;

/**
 * Esta clase se encarga de contener los strings u objetos constantes que se
 * utilizarán en todo el programa.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 08/03/2024
 */
public class Const {
    private Const (){}
    
    //Header de errores
    /** Header de errores */
    private static final String ERROR_HEADER = "| NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |";

    //Strings de resultados lexicos
    /** String con header de error léxico */
    public static final String ERROR_LEXICAL_HEADER = "ERROR: LEXICO\n" + ERROR_HEADER;
    /** String con header de resultado léxico */
    public static final String SUCCESS_LEXICAL_HEADER = "CORRECTO: ANALISIS LEXICO\n| TOKEN | LEXEMA | NUMERO DE LINEA (NUMERO DE COLUMNA) |";
    
    //Strings de resultados sintacticos
    /** String con header de error sintáctico */
    public static final String ERROR_SYNTACTIC_HEADER = "ERROR: SINTACTICO\n" + ERROR_HEADER;
    
    /** String con header de resultado sintáctico */
    public static final String SUCCESS_SYNTACTIC_HEADER = "CORRECTO: ANALISIS SINTACTICO";
    
    //Strings de resultados semanticos
    /** String con header de error semantico */
    public static final String ERROR_SEMANTIC_DEC_HEADER = "ERROR: SEMANTICO - DECLARACIONES\n" + ERROR_HEADER;
    /** String con header de resultado semantico */
    public static final String SUCCESS_SEMANTIC_DEC_HEADER = "CORRECTO: SEMANTICO - DECLARACIONES";
    /** String con header de error semantico */
    public static final String ERROR_SEMANTIC_SENT_HEADER = "ERROR: SEMANTICO - SENTENCIAS\n" + ERROR_HEADER;
    /** String con header de resultado semantico */
    public static final String SUCCESS_SEMANTIC_SENT_HEADER = "CORRECTO: SEMANTICO - SENTENCIAS";
    
    //Strings de errores en el compilador
    /** Error a mostrar cuando no se proporciona código fuente */
    public static final String ERROR_READ_SOURCE = "ERROR: No se ha proporcionado un codigo fuente.";
    /** Error a mostrar cuando no se puede crear el archivo resultante */
    public static final String ERROR_CREATE_FILE = "ERROR: No se ha podido crear el archivo resultado.";
    /** Error a mostrar cuando no se puede generar el objeto lector de archivo */
    public static final String ERROR_CREATE_FILE_READER = "ERROR: No se ha podido crear el lector de archivo.";
    /** Error a mostrar cuando no se puede generar el objeto escritor de archivo */
    public static final String ERROR_CREATE_FILE_WRITER = "ERROR: No se ha podido crear el escritor de archivo.";
    /** Error a mostrar cuando el archivo no existe o es un directorio */
    public static final String ERROR_READ_FILE = "ERROR: El archivo no existe o es un directorio. Se busca en: ";
    /** Error a mostrar cuando no se ha podido leer la siguiente linea de un archivo */
    public static final String ERROR_READ_NEXT_LINE = "ERROR: No se ha podido leer la siguiente linea del archivo.";
    
    /** Hash que guarda el valor de las palabra reservada */
    public static final HashMap<String, IDToken> KEY_WORDS = new HashMap<String, IDToken>() {{
        put("struct", IDToken.pSTRUCT);
        put("impl", IDToken.pIMPL);
        put("else", IDToken.pELSE);
        put("false", IDToken.pFALSE);
        put("if", IDToken.pIF);
        put("ret", IDToken.pRET);
        put("while", IDToken.pWHILE);
        put("true", IDToken.pTRUE);
        put("nil", IDToken.pNIL);
        put("new", IDToken.pNEW);
        put("fn", IDToken.pFN);
        put("st", IDToken.pST);
        // put("pub", IDToken.pPUB);
        put("pri", IDToken.pPRI);
        put("self", IDToken.pSELF);
        put("void", IDToken.typeVOID);
    }};

    /** Hash que guarda el valor de los tipos de datos */
    public static final HashMap<String, IDToken> KEY_TYPE_WORDS = new HashMap<String, IDToken>() {{
        put("Int", IDToken.typeINT);
        put("Str", IDToken.typeSTR);
        put("Char", IDToken.typeCHAR);
        put("Bool", IDToken.typeBOOL);
        put("Array", IDToken.typeARRAY);
    }};

    /** Hash que guarda los tipos de datos primitivos */
    public static final HashSet<String> primitiveTypes = new HashSet<String>(){{
        add("Int");
        add("Str");
        add("Char");
        add("Bool");
        add("literal Int");
        add("literal Str");
        add("literal Char");
        add("literal Bool");
    }};

    /** Hash que guarda las estructuras predefinidias*/
    public static final HashSet<String> predefinedStructs = new HashSet<String>(){{
        add("IO");
        add("Object");
        add("Char");
        add("Bool");
        add("Int");
        add("Str");
        add("Array Char");
        add("Array Bool");
        add("Array Int");
        add("Array Str");
    }};
}
