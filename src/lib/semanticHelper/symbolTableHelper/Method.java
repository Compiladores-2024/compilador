package src.lib.semanticHelper.symbolTableHelper;

import java.util.HashMap;

import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

/**
 * Esta clase se encarga de contener los métodos declarados en el código fuente.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 19/04/2024
 */
public class Method extends Metadata{
    boolean isStatic;
    int currentParamIndex;
    IDToken returnType;
    HashMap<String, Param> params;

    /**
     * Constructor de la clase.
     * 
     * @since 19/04/2024
     */
    public Method () {
        super(new Token(null, "", 0, 0), 0);
    }

    /**
     * Método que agrega un parámetro al método correspondiente.
     * 
     * @since 19/04/2024
     * @param name Nombre del parámetro.
     * @param param Datos específicos del parámetro.
     */
    public void addParam(String name, Param param) {

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
