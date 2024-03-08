package src.lib;

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
}
