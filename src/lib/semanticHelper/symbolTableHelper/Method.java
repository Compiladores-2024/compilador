package src.lib.semanticHelper.symbolTableHelper;

import java.util.ArrayList;
import java.util.HashMap;

import src.lib.exceptionHelper.SemanticException;
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
    private Token returnType;
    private int currentVarIndex;
    private HashMap<String, Param> params;
    private HashMap<String, Variable> variables;

    /**
     * Constructor de la clase.
     * 
     * @since 19/04/2024
     * @param metadata Metadata del método
     * @param parameters Parámetros formales
     * @param returnType Tipo de retorno
     * @param isStatic Booleano para identificar si es estático o no
     * @param position Posición dentro de la tabla de símbolos
     */
    public Method (Token metadata, ArrayList<Param> parameters, Token returnType, boolean isStatic, int position) {
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

    
    /** 
     * Obtiene la cantidad de parámetros que posee el método.
     * 
     * @return Cantidad de parámetros del método 
     */
    public int getParamsSize () {
        return params.size();
    }

    /** 
     * Método que agrega una variable local al método.
     * 
     * @param token Metadata de la variable
     * @param type Metadata del tipo de dato de la variable
     */
    public void addVar (Token token, Token type) {
        String name = token.getLexema();

        //Si la variable no se ha pasado por parámetro
        if (params.get(name) == null) {
            //Si la variable no existe, la genera
            if (variables.get(name) == null) {
                variables.put(name, new Variable(token, type, true, currentVarIndex));
                currentVarIndex++;
            }
            //Se intenta definir otra variable
            else {
                throw new SemanticException(token, "La variable '" + name + "' se ha declarado más de una vez en el método '" + getName() + "'.");
            }
        } 
        //Se está definiendo una variable que se ha pasado por parámetro
        else {
            throw new SemanticException(token, "Variable '" + name + "' ya declarada como parámetro del método '" + getName() + "'.");
        }
    }

    /**
     * Retorna booleano true en caso de que el metodo sea static. Caso contrario retorna false
     * @return Boolean con la signature del método.
     */
    public Boolean isStatic(){
        return this.isStatic;
    }

    /**
     * Retorna la firma de un método.
     * @return String con la signature del método.
     */
    public String getSignature (){
        String sParams = "";

        //Genera el string de parametros
        for (String paramName : order(params)) {
            sParams += params.get(paramName).toString() + " ";
        }

        //Retorna la signature
        return (this.isStatic ? "st " : "") + getName() + " " + sParams + "-> " + returnType.getLexema();
    }
    

    
    /** 
     * Obtiene el tipo de dato de una variable dada
     * 
     * @param name Nombre de la variable a obtener
     * @return Tipo de dato de la variable
     */
    public String getVariableType (String name) {
        //Valida si es una variable local
        String result = variables.get(name) != null ? variables.get(name).getType().toString() : null;

        //Si no lo es, valida que sea un param
        if (result == null) {
            result = params.get(name) != null ? params.get(name).getType().toString() : null;
        }
        
        return result;
    }
    
    
    /** 
     * Obtiene el tipo de dato de una variable dada
     * 
     * @param position Posición de la variable a obtener
     * @return Tipo de dato de la variable
     */
    public String getParamType(int position) {
        for (Param param : params.values()) {
            if (param.getPosition() == position) {
                return param.getType().getLexema();
            }
        }
        return null;
    }
    
    /** 
     * Obtiene el tipo de parámetro
     * 
     * @param name Nombre del parámetro
     * @return String
     */
    public String getParamType(String name) {
        return params.get(name) != null ? params.get(name).getType().getLexema() : null;
    }

    /**
     * Obtiene el tipo de dato
     * @return String
     */
    public String getReturnType () {
        return returnType.getLexema();
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
            tabs + "    \"retorno\": \"" + returnType.getLexema() + "\",\n" +
            tabs + "    \"posicion\": " + getPosition() + ",\n" +
            tabs + "    \"parámetros\": [" + paramsJSON +  (paramsJSON == "" ? "" : (tabs + "    ")) + "],\n" +
            tabs + "    \"variables\": [" + variableJSON +  (variableJSON == "" ? "" : (tabs + "    ")) + "]\n" +
        tabs + "}";
    }
}
