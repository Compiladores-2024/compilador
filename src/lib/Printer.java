package src.lib;

import src.lib.tokenHelper.Token;

public class Printer {

    public static  void showTokens (Token tokens []) {
        System.out.println("CORRECTO: ANALISIS LEXICO");
        System.out.println("| TOKEN | LEXEMA | NUMERO DE LINEA (NUMERO DE COLUMNA) |");

        for (Token token : tokens) {
            System.out.println(token.toString());
        }
    }

    public static void showErrors (Integer line, Integer column, String descripcion, String type) {
        System.out.println("ERROR: " + type);
        System.out.println("| NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |");
        System.out.println("| LINEA " + line.toString() + " | COLUMNA " + column.toString() + " | " + descripcion + " |");
    }
}
