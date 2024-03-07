package src.lib;

public class LexicalException extends RuntimeException {

    
    public LexicalException(int line, int column, String msj){
        System.out.println("ERROR: LEXICO");
        System.out.println("| NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |");
        System.out.println("| LINEA " + Integer.toString(line) + " | COLUMNA " + Integer.toString(column) + " | " + msj + "|");
        // Terminamos la ejecucion del programa con error lexico
        System.exit(1);
    }
}