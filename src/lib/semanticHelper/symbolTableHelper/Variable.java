package src.lib.semanticHelper.symbolTableHelper;

import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

/**
 * Esta clase se encarga de contener las variables declaradas en el código fuente.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 19/04/2024
 */
public class Variable extends Metadata{
    boolean isPrivate;
    IDToken type;

    /**
     * Constructor de la clase.
     * 
     * @since 19/04/2024
     */
    public Variable (Token token, IDToken type, boolean isPrivate, int position) {
        super(token, position);
        this.type = type;
        this.isPrivate = isPrivate;
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
            tabs + "    \"tipo\": \"" + type.toString() + "\",\n" +
            tabs + "    \"public\": \"" + !isPrivate + "\",\n" +
            tabs + "    \"posicion\": " + getPosition() + "\n" +
        tabs + "}";
    }
}
