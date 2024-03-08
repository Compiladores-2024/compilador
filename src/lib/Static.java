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
     * Método que se utiliza para escribir los resultados del analizador léxico.
     * 
     * @since 07/03/2024
     * @param tokens Lista de Tokens que se escribirían en el archivo de salida.
     * @param path Path hacia el archivo resultante.
     */
    public static void writeTokenResults (ArrayList<Token> tokens, String path) {
        try {
            java.io.FileWriter writer = new java.io.FileWriter(path);

            //Agrega la cabecera
            writer.write(Const.SUCCESS_LEXICAL_HEADER + "\n");

            //Escribir cada elemento en una línea separada
            for (Token token : tokens) {
                writer.write(token.toString() + "\n");
            }

            //Cierra el escritor
            writer.close();
        } catch (Exception e) {
            System.out.println(Const.ERROR_CREATE_FILE);
        }
    }

    /**
     * Imprime una lista de tokens dada.
     * 
     * @since 07/03/2024
     * @param tokens ArrayList con elementos de la clase Token
     */
    public static  void showTokens (ArrayList<Token> tokens) {
        System.out.println(Const.SUCCESS_LEXICAL_HEADER);

        for (Token token : tokens) {
            System.out.println(token.toString());
        }
    }

    /**
     * Imprime un error.
     * 
     * @since 07/03/2024
     * @param line Línea en la que ocurrió el error.
     * @param column Columna en la que ocurrió el error.
     * @param description Descripción del error.
     * @param type Tipo de error. Puede ser LEXICO, SEMANTICO, SINTACTICO, etc.
     */
    public static void showErrors (Integer line, Integer column, String descripcion) {
        System.out.println(Const.ERROR_LEXICAL_HEADER);
        System.out.println("| LINEA " + line.toString() + " | COLUMNA " + column.toString() + " | " + descripcion + " |");
    }
}
