package src.lib.semanticHelper.symbolTableHelper;

import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

/**
 * Esta clase se encarga de contener los parámetros de métodos declarados en el código fuente.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 19/04/2024
 */
public class Param extends Metadata{
    IDToken type;

    /**
     * Constructor de la clase.
     * 
     * @since 19/04/2024
     */
    public Param (IDToken type, Token token) {
        super(token, 0);
        this.type = type;
    }

    /**
     * @return IDToken que identifica el tipo del parámetro
     */
    public IDToken getType() {
        return type;
    }
    
    /**
     * Reescritura del método, convierte los datos en JSON.
     * 
     * @since 19/04/2024
     * @return Estructura de datos en formato JSON
     */
    @Override
    public String toJSON() {
        return null;
    }
}
