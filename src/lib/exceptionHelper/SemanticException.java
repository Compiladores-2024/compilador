package src.lib.exceptionHelper;

import src.lib.Const;
import src.lib.tokenHelper.Token;

/**
 * Clase encargada de generar la excepción semántica. Extiende de CustomException.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 19/04/2024
 */
public class SemanticException extends CustomException{
    /**
     * Constructor de la clase.
     * 
     * @since 19/04/2024
     * @param token token que genera la excepción
     * @param description String con la descripción del error
     */
    public SemanticException(Token token, String description){
        super(Const.ERROR_SEMANTIC_DEC_HEADER, token.getLine(), token.getColumn(), description);
    }
    /**
     * Constructor de la clase.
     * @param token Identificador
     * @param description Descripción del error
     * @param isSentence Booleano que identifica si es sentencia o no
     */
    public SemanticException(Token token, String description, boolean isSentence){
        super(Const.ERROR_SEMANTIC_SENT_HEADER, token.getLine(), token.getColumn(), description);
    }
}
