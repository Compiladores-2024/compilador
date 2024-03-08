package src.lib;

import java.util.ArrayList;

import src.lib.tokenHelper.Token;

public class Static {
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

    public static  void showTokens (ArrayList<Token> tokens) {
        System.out.println(Const.SUCCESS_LEXICAL_HEADER);

        for (Token token : tokens) {
            System.out.println(token.toString());
        }
    }

    public static void showErrors (Integer line, Integer column, String descripcion) {
        System.out.println(Const.ERROR_LEXICAL_HEADER);
        System.out.println("| LINEA " + line.toString() + " | COLUMNA " + column.toString() + " | " + descripcion + " |");
    }
}
