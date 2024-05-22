package src.lib.semanticHelper.symbolTableHelper;

import src.lib.tokenHelper.Token;

/**
 * Esta clase se encarga de contener las variables declaradas en el código fuente.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 19/04/2024
 */
public class Variable extends Metadata{
    private boolean isPrivate;
    private Token type;

    /**
     * Constructor de la clase.
     * 
     * @since 19/04/2024
     * @param token Metadata de la variable
     * @param type Tipo de dato
     * @param isPrivate Booleano que avisa si es privada o no
     * @param position Posición dentro de la tabla de símbolos
     */
    public Variable (Token token, Token type, boolean isPrivate, int position) {
        super(token, position);
        this.type = type;
        this.isPrivate = isPrivate;
    }

    public String getType(){
        return this.type.getLexema();
    }
    public boolean isPrivate() {
        return isPrivate;
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
            tabs + "    \"tipo\": \"" + type.getLexema() + "\",\n" +
            tabs + "    \"public\": \"" + !isPrivate + "\",\n" +
            tabs + "    \"posicion\": " + getPosition() + "\n" +
        tabs + "}";
    }
}
