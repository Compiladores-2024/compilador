package src.lib;

import java.util.ArrayList;
import src.lib.tokenHelper.Token;

/**
 * Esta clase se utilizará para realizar funciones estáticas en el programa.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 07/03/2024
 */
public class Static {
    /**
     * Método que se utiliza para escribir los resultados del analizador léxico
     * ya sea por consola o en un fichero específico.
     * 
     * @since 09/03/2024
     * @param tokens Lista de Tokens que se escribirán.
     * @param path Path hacia el archivo resultante.
     */
    public static void showTokens (ArrayList<Token> tokens, String path) {
        //Genera el texto que se debe guardar
        String text = Const.SUCCESS_LEXICAL_HEADER + "\n";

        //Escribir cada elemento en una línea separada
        for (Token token : tokens) {
            text += token.toString() + "\n";
        }
        
        //Escribe o muestra el resultado
        if(path == null){
            System.out.println(text);
        }
        else {
            try {
                java.io.FileWriter writer = new java.io.FileWriter(path);
                writer.write(text);
                writer.close();
            }
            catch (Exception e) {
                System.out.println(Const.ERROR_CREATE_FILE);
            }
        }
    }

    /**
     * Imprime un error lexico.
     * 
     * @since 07/03/2024
     * @param error Tipo de dato error con los detalles a mostrar.
     * @param write Especifica si se debe escribir en un archivo o no.
     */
    public static void lexicalError (CustomError error, String path) {
        //Escribe o muestra el resultado
        if(path != null){
            writeError(error, "LEXICO", path);
        }
        else {
            showError(error, "LEXICO");
        }
    }

    //Muestra un error por pantalla
    private static void showError (CustomError e, String errorType) {
        switch (errorType) {
            case "LEXICO":
                System.out.println(Const.ERROR_LEXICAL_HEADER);
                break;
            default:
                System.out.println(Const.ERROR_LEXICAL_HEADER);
                break;
        }
        System.out.println(e.toString());
    }
    //Escribe un error en el archivo indicado
    private static void writeError (CustomError e, String errorType, String path){
        switch (errorType) {
            case "LEXICO":
                System.out.println(Const.ERROR_LEXICAL_HEADER);
                break;
            default:
                System.out.println(Const.ERROR_LEXICAL_HEADER);
                break;
        }
    }
    
}
