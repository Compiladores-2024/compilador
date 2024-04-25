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
    private Token typeToken;

    /**
     * Constructor de la clase.
     * 
     * @since 19/04/2024
     */
    public Param (Token type, Token token, int position) {
        super(token, position);
        this.typeToken = type;
    }

    /**
     * @return IDToken que identifica el tipo del parámetro
     */
    public Token getType() {
        return typeToken;
    }


    public String toString() {
        return typeToken.getLexema().toString() + " " + getName();
    }
    
    /**
     * Reescritura del método, convierte los datos en JSON.
     * 
     * @since 19/04/2024
     * @return Estructura de datos en formato JSON
     */
    @Override
    public String toJSON(String tabs) {
        return tabs + "{\n" +
            tabs + "    \"nombre\": \"" + getName() + "\",\n" +
            tabs + "    \"tipo\": \"" + typeToken.getLexema() + "\",\n" +
            tabs + "    \"posicion\": " + getPosition() + "\n" +
        tabs + "}";
    }
}
