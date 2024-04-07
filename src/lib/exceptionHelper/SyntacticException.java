package src.lib.exceptionHelper;

import src.lib.Const;
import src.lib.tokenHelper.Token;

public class SyntacticException extends CustomException {
    /**
     * Constructor de la clase.
     * 
     * @since 06/04/2024
     * @param token token que genera la excepción
     * @param expected string que representa el token que se esperaba
     * @param found string que representa el token encontrado
     */
    public SyntacticException(Token token, String expected, String found){
        super(Const.ERROR_SYNTACTIC_HEADER, token.getLine(), token.getColumn(), "SE ESPERABA: " + expected + " SE ENCONTRO: " + found);
    }
}
