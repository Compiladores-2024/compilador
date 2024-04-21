package src.lib.semanticHelper.symbolTableHelper;

import java.util.ArrayList;
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
    private boolean isStatic;
    private IDToken returnType;
    private HashMap<String, Param> params;

    /**
     * Constructor de la clase.
     * 
     * @since 19/04/2024
     */
    public Method (Token metadata, ArrayList<Param> parameters, IDToken returnType, boolean isStatic, int position) {
        super(metadata, position);

        params = new HashMap<String, Param>();
        for (Param param : parameters) {
            addParam(param);
        }

        this.isStatic = isStatic;
        this.returnType = returnType;
    }

    public Method(ArrayList<Param> params, boolean isStatic, IDToken returnType){
        super(new Token(null, "", 0, 0), 0);
        for (Param param : params) {
            this.params.put(param.getMetadata().getLexema(), param);
        }
        this.isStatic=isStatic;
        this.returnType=returnType;
    }

    /**
     * Método que agrega un parámetro al método correspondiente.
     * 
     * @since 19/04/2024
     * @param param Datos específicos del parámetro.
     */
    private void addParam(Param param) {
        params.put(param.getName(), param);
    }

    /**
     * @return String con la signature del método.
     */
    public String getSignature (){
        String[] parameters = new String[params.size()];
        String sParams = "";

        //Ordena los parametros segun la posicion en la que fueron encontrados
        for (Param param : params.values()) {
            parameters[param.getPosition()] = param.toString() + " ";
        }

        //Genera el string de parametros
        for (String string : parameters) {
            sParams += string;
        }

        //Retorna la signature
        return (this.isStatic ? "st" : "") + getName() + " " + sParams + "-> " + returnType.toString();
    }
    
    /**
     * Reescritura del método, convierte los datos en JSON.
     * 
     * @since 19/04/2024
     * @return Estructura de datos en formato JSON
     */
    @Override
    public String toJSON(String tabs) {
        return tabs + "{}";
    }
}
