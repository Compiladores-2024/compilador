package src.lib.exceptionHelper;

/**
 * Clase abstracta que se utilizara para aceptar errores del tipo indicado
 * y para generar el string de error a escribir o mostrar en consola
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 12/03/2024
 */
public abstract class CustomException extends RuntimeException {
    /**
     * Constructor de la clase.
     * 
     * @since 12/03/2024
     * @param type Tipo de excepción.
     * @param line Línea en la que se encuentra la excepción.
     * @param column Columna en la que se encuentra la excepción.
     * @param description Descripción de la excepción.
     */
    public CustomException (String type, int line, int column, String description) {
        super(
            type +
            "\n| LINEA " + Integer.toString(line) +
            " | COLUMNA " + Integer.toString(column) +
            " | " + description + " |"
        );
    }
}