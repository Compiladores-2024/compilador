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
    private Boolean isInherited;

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
        this.isInherited=false;
    }

    /**
     * Obtiene el token correspondiente al tipo de dato de la variable
     * @return Token
     */
    public Token getTypeToken(){
        return this.type;
    }

    
    /** 
     * Obtiene el tipo de dato de la variable.
     * 
     * @return Tipo de dato.
     */
    public String getType(){
        return this.type.getLexema();
    }
    
    /** 
     * Obtiene si es privado o no.
     * @return Booleano que avisa si es privado o no.
     */
    public boolean isPrivate() {
        return isPrivate;
    }

    /**
     * Setea booleano que identifica si es heredada o no
     * @param bool Booleano
     */
    public void setIsInherited(Boolean bool){
        this.isInherited= bool;
    }

    /**
     * Obtiene el booleano que indica si es heredado o no
     * @return Booleano
     */
    public Boolean isInherited(){
        return this.isInherited.equals(true);
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
