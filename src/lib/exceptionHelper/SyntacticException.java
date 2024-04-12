package src.lib.exceptionHelper;

import src.lib.Const;
import src.lib.tokenHelper.Token;

/**
 * Clase encargada de generar la excepción sintáctica. Extiende de CustomException.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 06/04/2024
 */
public class SyntacticException extends CustomException {
    /**
     * Constructor de la clase.
     * 
     * @since 06/04/2024
     * @param token token que genera la excepción
     * @param expected string que representa el token que se esperaba
     */
    public SyntacticException(Token token, String expected){
        super(Const.ERROR_SYNTACTIC_HEADER, token.getLine(), token.getColumn(), "SE ESPERABA: " + expected + ". SE ENCONTRO: " + token.getIDToken().toString());
    }
}
