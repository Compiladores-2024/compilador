package src.lib;

import java.util.ArrayList;
import src.lib.tokenHelper.Token;

/**
 * Esta clase se encarga de mostrar resultados por consola siempre que sea
 * necesario.
 * 
 * @author Cristian Serrano
 * @since 07/03/2024
 */
public class Printer {
    Printer(){}

    /**
     * Imprime una lista de tokens dada.
     * 
     * @since 07/03/2024
     * @param tokens ArrayList con elementos de la clase Token
     */
    public static void showTokens(ArrayList<Token> tokens) {
        System.out.println("CORRECTO: ANALISIS LEXICO");
        System.out.println("| TOKEN | LEXEMA | NUMERO DE LINEA (NUMERO DE COLUMNA) |");

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
    public static void showErrors(Integer line, Integer column, String description, String type) {
        System.out.println("ERROR: " + type);
        System.out.println("| NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |");
        System.out.println("| LINEA " + line.toString() + " | COLUMNA " + column.toString() + " | " + description + " |");
    }
}
