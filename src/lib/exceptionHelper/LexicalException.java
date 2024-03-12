package src.lib.exceptionHelper;

import src.lib.Const;

/**
 * Clase LexicalException encargada de generar la excepcion lexica
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 06/03/2024
 */
public class LexicalException extends CustomException {
    public LexicalException(int line, int column, String description){
        super(Const.ERROR_LEXICAL_HEADER, line, column, description);
    }
}