package src.lib.fileManager;

import java.util.ArrayList;
import src.lib.tokenHelper.Token;

/**
 * Esta clase se utilizará para escribir los resultados de la ejecución o
 * testing de los diferentes analizadores
 * 
 * @author Cristian Serrano
 * @since 07/03/2024
 */
public class FileWriter {
    FileWriter () {}

    /**
     * Método que se utiliza para escribir los resultados del analizador léxico.
     * 
     * @since 07/03/2024
     * @param tokens Lista de Tokens que se escribirían en el archivo de salida.
     * @param path Path hacia el archivo resultante.
     */
    public static void writeResults(ArrayList<Token> tokens, String path) {
        try {
            java.io.FileWriter writer = new java.io.FileWriter(path);

            //Agrega la cabecera
            writer.write("CORRECTO: ANALISIS LEXICO\n");
            writer.write("| TOKEN | LEXEMA | NUMERO DE LINEA (NUMERO DE COLUMNA) |\n");

            //Escribir cada elemento en una línea separada
            for (Token token : tokens) {
                writer.write(token.toString() + "\n");
            }

            //Cierra el escritor
            writer.close();
        }
        catch (Exception e) {
            System.out.println("ERROR: No se ha podido crear el archivo resultado.");
        }
    }
}
