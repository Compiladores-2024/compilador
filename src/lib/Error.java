package src.lib;

/**
 * Esta clase se utilizará para guardar la metadata de un Error específico.
 * Esta metadata contiene datos de relevancia para facilitar el manejo de
 * errores en todo el proyecto.
 *  
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 07/03/2024
 */
public class Error {
    private int line;
    private int column;
    private String description;

    /**
     * Constructor de la clase.
     * 
     * @since 07/03/2024
     * @param line Línea en la que se encuentra el error.
     * @param column Columna en la que se encuentra el error.
     * @param description Descripción del error.
     */
    public Error (int line, int column, String description) {
        this.line = line;
        this.column = column;
        this.description = description;
    }

    /**
     * Método que retorna un string con los datos formateados de tal manera que
     * cumplan los estándares de la cátedra.
     * 
     * @since 07/03/2024
     */
    @Override
    public String toString() {
        return "| LINEA " + Integer.toString(line) + " | COLUMNA " + Integer.toString(column) + " | " + description + " |";
    }
}
