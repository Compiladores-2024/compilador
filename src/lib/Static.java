package src.lib;

import java.util.ArrayList;

import src.lib.exceptionHelper.CustomException;
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
    public static void writeTokens (ArrayList<Token> tokens, String path) {
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
            write(text, path);
        }
    }

    /**
     * Imprime o escribe un error.
     * 
     * @since 07/03/2024
     * @param error Tipo de dato error con los detalles a mostrar.
     * @param write Especifica si se debe escribir en un archivo o no.
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

    //Escribe un string en un archivo dado
    private static void write (String text, String path) {
        try {
            java.io.FileWriter writer = new java.io.FileWriter(path);
            writer.write(text);
            writer.close();
        }
        catch (Exception e) {
            System.out.println(Const.ERROR_CREATE_FILE_WRITER);
        }
    }
}
