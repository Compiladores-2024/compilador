package src.lib.exceptionHelper;

import src.lib.Error;
import src.lib.Static;

/**
 * Clase LexicalException encargada de generar la excepcion lexica
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 06/03/2024
 */
public class LexicalException extends RuntimeException {
    public LexicalException(Error e, String path){
        Static.lexicalError(e, path);
        System.exit(1);
    }
}