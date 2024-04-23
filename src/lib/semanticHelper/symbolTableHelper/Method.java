package src.lib.semanticHelper.symbolTableHelper;

import java.util.ArrayList;
import java.util.HashMap;

import src.lib.exceptionHelper.SemanticException;
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
    private int currentVarIndex;
    private HashMap<String, Param> params;
    private HashMap<String, Variable> variables;

    /**
     * Constructor de la clase.
     * 
     * @since 19/04/2024
     */
    public Method (Token metadata, ArrayList<Param> parameters, IDToken returnType, boolean isStatic, int position) {
        super(metadata, position);

        variables = new HashMap<String, Variable>();
        params = new HashMap<String, Param>();
        for (Param param : parameters) {
            // si no contiene un parametro con param.getName como key entonces se agrega
            if(params.get(param.getName())==null){
                params.put(param.getName(), param);
            }
            // sino es error, hay identificadores iguales en la definicion de argumentos formales del metodo
            else{
                throw new SemanticException(metadata, "Metodo "+metadata.getLexema() + " contiene argumentos formales con iguales identificadores");
            }
        }

        this.isStatic = isStatic;
        this.returnType = returnType;
        this.currentVarIndex = 0;
    }

    public void addVar (Token token, IDToken type, boolean isPrivate) {
        String name = token.getLexema();

        //Si la variable no existe, la genera
        if (variables.get(name) == null) {
            variables.put(name, new Variable(token, type, isPrivate, currentVarIndex));
            currentVarIndex++;
        }
        //Se intenta definir otra variable
        else {
            throw new SemanticException(token, "La variable '" + name + "' se ha declarado más de una vez en el método '" + getName() + "'.");
        }
    }

    /**
     * @return String con la signature del método.
     */
    public String getSignature (){
        String sParams = "";

        //Genera el string de parametros
        for (String paramName : order(params)) {
            sParams += params.get(paramName).toString() + " ";
        }

        //Retorna la signature
        return (this.isStatic ? "st " : "") + getName() + " " + sParams + "-> " + returnType.toString();
    }
    
    /**
     * Reescritura del método, convierte los datos en JSON.
     * 
     * @since 19/04/2024
     * @return Estructura de datos en formato JSON
     */
    @Override
    public String toJSON(String tabs) {
        String variableJSON = toJSONEntity(variables, tabs), paramsJSON = toJSONEntity(params, tabs);

        return tabs + "{\n" +
            tabs + "    \"nombre\": \"" + getName() + "\",\n" +
            tabs + "    \"static\": \"" + isStatic + "\",\n" +
            tabs + "    \"retorno\": \"" + returnType.toString() + "\",\n" +
            tabs + "    \"posicion\": " + getPosition() + ",\n" +
            tabs + "    \"parámetros\": [" + paramsJSON +  (paramsJSON == "" ? "" : (tabs + "    ")) + "]\n" +
            tabs + "    \"variables\": [" + variableJSON +  (variableJSON == "" ? "" : (tabs + "    ")) + "]\n" +
        tabs + "}";
    }
}
