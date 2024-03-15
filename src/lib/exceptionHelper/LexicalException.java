package src.lib.exceptionHelper;

import src.lib.Const;

/**
 * Clase encargada de generar la excepcion lexica. Extiende de CustomException.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 06/03/2024
 */
public class LexicalException extends CustomException {
    /**
     * Constructor de la clase.
     * 
     * @since 06/03/2024
     * @param line Línea en la que se encuentra la excepción.
     * @param column Columna en la que se encuentra la excepción.
     * @param description Descripción de la excepción.
     */
    public LexicalException(int line, int column, String description){
        super(Const.ERROR_LEXICAL_HEADER, line, column, description);
    }
}