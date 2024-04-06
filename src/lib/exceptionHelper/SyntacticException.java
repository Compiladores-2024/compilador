package src.lib.exceptionHelper;

import src.lib.Const;
import src.lib.tokenHelper.Token;

public class SyntacticException extends CustomException {
    /**
     * Constructor de la clase.
     * 
     * @since 06/04/2024
     * @param line Línea en la que se encuentra la excepción.
     * @param column Columna en la que se encuentra la excepción.
     * @param description Descripción de la excepción.
     */
    public SyntacticException(Token token, String description){
        super(Const.ERROR_SYNTACTIC_HEADER, token.getLine(), token.getColumn(), description);
    }
}
