package src.lib;

import java.util.HashMap;

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
    //Header de errores
    private static final String ERROR_HEADER = "| NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |";

    //Strings de resultados lexicos
    public static final String ERROR_LEXICAL_HEADER = "ERROR: LEXICO\n" + ERROR_HEADER;
    public static final String SUCCESS_LEXICAL_HEADER = "CORRECTO: ANALISIS LEXICO\n| TOKEN | LEXEMA | NUMERO DE LINEA (NUMERO DE COLUMNA) |";

    //Strings de errores en el compilador
    public static final String ERROR_CREATE_FILE = "ERROR: No se ha podido crear el archivo resultado.";
    public static final String ERROR_CREATE_FILE_READER = "ERROR: No se ha podido crear el lector de archivo.";
    public static final String ERROR_READ_FILE = "ERROR: El archivo no existe o es un directorio. Se busca en: ";
    public static final String ERROR_READ_NEXT_LINE = "ERROR: No se ha podido leer la siguiente linea del archivo.";

    //Hash que guarda el valor de las palabra reservada
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
        put("pub", IDToken.pPUB);
        put("pri", IDToken.pPRI);
        put("self", IDToken.pSELF);
        put("void", IDToken.typeVOID);
    }};

    //Hash que guarda el valor de los tipos de datos
    public static final HashMap<String, IDToken> KEY_TYPE_WORDS = new HashMap<String, IDToken>() {{
        put("Int", IDToken.typeINT);
        put("Str", IDToken.typeSTR);
        put("Char", IDToken.typeCHAR);
        put("Bool", IDToken.typeBOOL);
        put("Array", IDToken.typeARRAY);
    }};
}
