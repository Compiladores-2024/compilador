package src.lib.lexicalHelper;

import src.lib.lexicalHelper.resultTypes.TokenCheckerResult;
import src.lib.lexicalHelper.resultTypes.ValidatorResult;

/**
 * Clase auxiliar de analizador léxico.<br/>
 * 
 * Se encargará de validar si la lectura actual es un identificador válido o no.
 * Además, tiene la tarea de validar el siguiente caracter para detectar si se
 * ha encontrado un error léxico.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 09/03/2024
 */
public class TokenChecker {
    private Validator validator;
    private TokenCheckerResult tokenCheckerResult;

    public TokenChecker () {
        validator = new Validator();
        tokenCheckerResult = new TokenCheckerResult();
    }

    public TokenCheckerResult check (String currentRead, char nextChar) {
        ValidatorResult validatorResult = validator.validate(currentRead, nextChar);

        //Si ha ocurrido un error lo retorna
        if(validatorResult.getDescriptionError() != null){

        }
        else {
            //Si la lectura actual con el siguiente caracter son un id valido
            if(validatorResult.isCurrentReadWithChar()){
    
            }
            else {
                //Si la lectura actual es valida
                if(validatorResult.isCurrentRead()){
    
                }
            }
        }
        return tokenCheckerResult;
    }
    
}
