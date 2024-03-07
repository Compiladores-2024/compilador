package src.lib.fileManager;

import java.util.ArrayList;
import src.lib.tokenHelper.Token;

public class FileWriter {

    public static void writeResults (ArrayList<Token> tokens, String path) {
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
        } catch (Exception e) {
            System.out.println("ERROR: No se ha podido crear el archivo resultado.");
        }
    }
}
