package src.lib.exceptionHelper;

/**
 * Clase abstracta que se utilizara para aceptar errores del tipo indicado
 * solamente y para generar el string de error a escribir o mostrar en consola
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 12/03/2024
 */
public abstract class CustomException extends RuntimeException {
    public CustomException (String type, int line, int column, String description) {
        super(
            type +
            "\n| LINEA " + Integer.toString(line) +
            " | COLUMNA " + Integer.toString(column) +
            " | " + description + " |"
        );
    }
}