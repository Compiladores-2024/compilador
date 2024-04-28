package src.lib.semanticHelper.symbolTableHelper;

import src.lib.tokenHelper.Token;

/**
 * Esta clase se encarga de contener los parámetros de métodos declarados en el código fuente.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 19/04/2024
 */
public class Param extends Metadata{
    private Token type;

    /**
     * Constructor de la clase.
     * 
     * @since 19/04/2024
     * @param token Metadata del parámetro
     * @param type Tipo de dato
     * @param position Posición dentro de la tabla de símbolos
     */
    public Param (Token token, Token type, int position) {
        super(token, position);
        this.type = type;
    }

    /**
     * Retorna el tipo de dato
     * @return Lexema que identifica el tipo de dato del parámetro
     */
    public Token getType() {
        return type;
    }


    
    /** 
     * @return String con el formato: Tipo-de-dato Nombre-variable
     */
    public String toString() {
        return type.getLexema() + " " + getName();
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
            tabs + "    \"posicion\": " + getPosition() + "\n" +
        tabs + "}";
    }
}
