package src.lib.lexicalHelper;

import src.lib.lexicalHelper.resultTypes.ValidatorResult;

/**
 * Clase auxiliar de analizador léxico.<br/>
 * 
 * Valida un string de entrada y el siguiente token. Contendrá toda la lógica
 * especial requerida para saber si debe relacionar la lectura actual a algún
 * token específico.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 09/03/2024
 */
public class Validator {
    public Validator () {}

    public ValidatorResult validate (String current, char next) {
        boolean currentRead = false, currentReadWithChar = false;
        String descriptionError = "";
        

        return new ValidatorResult(currentRead, currentReadWithChar, descriptionError);
    }
}
