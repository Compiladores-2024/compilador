package src.lib.tokenHelper;

/**
 * La clase Token se utilizará para guardar la metadata de un Token específico.
 * Esta metadata contiene datos de relevancia para facilitar las operaciones
 * de estos en todo el proyecto.
 *  
 * @author Cristian Serrano
 * @since 07/03/2024
 */
public class Token {
    private IDToken name;
    private String lexema;
    private Integer line;
    private Integer column;

    /**
     * Constructor de la clase.
     * 
     * @since 07/03/2024
     * @param name Token.
     * @param lexema Lexema.
     * @param line Línea en la que se encuentra el token.
     * @param column Columna en la que se encuentra el token.
     */
    public Token(IDToken name, String lexema, Integer line, Integer column) {
        this.name = name;
        this.lexema = lexema;
        this.line = line;
        this.column = column;
    }

    /**
     * Método que retorna un string con los datos formateados de tal manera que
     * cumplan los estándares de la cátedra.
     * 
     * @since 07/03/2024
     */
    public String toString() {
        return "| " + name + " | " + lexema + " | LINEA " + line.toString() + " (COLUMNA " + column.toString() + ") |";
    }
}
