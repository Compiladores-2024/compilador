package src.lib.exceptionHelper;

import src.lib.Const;
import src.lib.tokenHelper.Token;

public class SemanticException extends CustomException{
    /**
     * Constructor de la clase.
     * 
     * @since 06/04/2024
     * @param token token que genera la excepción
     * @param expected string que representa el token que se esperaba
     */
    public SemanticException(Token token, String description){
        super(Const.ERROR_SEMANTIC_DEC_HEADER, token.getLine(), token.getColumn(), description);
    }
}
