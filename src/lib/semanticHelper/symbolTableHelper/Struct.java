package src.lib.semanticHelper.symbolTableHelper;

import java.util.ArrayList;
import java.util.HashMap;

import src.lib.exceptionHelper.SemanticException;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

/**
 * Esta clase se encarga de contener las estructuras declaradas en el código fuente.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 19/04/2024
 */
public class Struct extends Metadata {
    private Struct parent;
    private Method constructor;
    private int currentMethodIndex, currentVarIndex, countStructDefinition, countImplDefinition;
    private HashMap<String, Variable> variables;
    private HashMap<String, Method> methods;

    /**
     * Constructor de la clase.
     * 
     * @since 19/04/2024
     * @param metadata
     * @param parent
     */
    public Struct (Token metadata, Struct parent) {
        super(metadata, 0);
        this.parent = parent;

        //Inicializa hash
        variables = new HashMap<String, Variable>();
        methods = new HashMap<String, Method>();

        //Contadores de indices
        currentMethodIndex = 0;
        currentVarIndex = 0;

        //Contador para validar cuantas veces se define la estructura en el código fuente (struct e impl)
        countStructDefinition = 0;
        countImplDefinition = 0;
    }

    public void updateCurrentMethodIndex(){
        this.currentMethodIndex=this.methods.size();
    }
    public void updateCurrentVarIndex(){
        this.currentVarIndex=this.variables.size();
    }

    public HashMap<String, Variable> getVariable(){
        return this.variables;
    }

    public int getCurrentVarIndex(){
        return this.currentVarIndex;
    }

    public int getCurrentMethodIndex(){
        return this.currentMethodIndex;
    }

    public HashMap<String, Method> getMethods() {
        return methods;
    }
    public Method getConstructor(){
        return this.constructor;
    }
    /**
     * @return Struct con los datos de la superclase.
     */
    public Struct getParent() {
        return parent;
    }
    /**
     * @param parent Clase padre de la cual hereda la estructura.
     */
    public void setParent (Struct parent) {
        this.parent = parent;
    }

    /**
     * Método que agrega un método al struct correspondiente. <br/>
     * 
     * <br/>Realiza las siguientes validaciones:<br/>
     * - Si ya existe un método con el mismo nombre y firma.<br/>
     * 
     * <br/>Realiza las siguientes acciones:<br/>
     * - Aumenta el contador de posición para los métodos de la estructura correspondiente.<br/>
     * - Genera el método o constructor.<br/>
     * 
     * @since 19/04/2024
     * @param token Metadata del método
     * @param params Parámetros formales
     * @param isStatic Booleano que notifica si es estático o no
     * @param returnType Tipo de retorno
     * @return
     */
    public Method addMethod(Token token, ArrayList<Param> params, boolean isStatic, IDToken returnType) {
        String name = token.getLexema();
        Method method = methods.get(name),
            newMethod = new Method(token, params, returnType, isStatic, (method == null ? currentMethodIndex : method.getPosition()));
        
        //Valida si se está generando un constructor y que no se haya generado otro
        if (IDToken.sDOT.equals(token.getIDToken())) {
            if (constructor == null) {
                constructor = newMethod;
                method = newMethod;
            }
            else {
                throw new SemanticException(token, "No se permite definir más de un constructor. Estructura '" + getName() + "'.");
            }
        }
        else {
            //Si el método no existe, lo genera
            if (method == null) {
                //Inserta el nuevo metodo en la tabla 
                methods.put(name, newMethod);
                
                //Aumenta el indice y asigna el metodo
                currentMethodIndex++;
                method = newMethod;
            }
            //Si existe, retorna error
            else {
                throw new SemanticException(token, "El método '" + name + "' se ha declarado más de una vez en la estructura '" + getName() + "'.");
            }
        }

        //Retorna el método generado
        return method;
    }

    /**
     * Método que agrega una variable al struct correspondiente.<br/>
     * 
     * <br/>Realiza las siguientes validaciones:<br/>
     * - Si ya existe un atributo con el mismo nombre.<br/>
     * 
     * 
     * <br/>Realiza las siguientes acciones:<br/>
     * - Aumenta el contador de posición para los atributos de la estructura correspondiente.<br/>
     * 
     * 
     * @since 19/04/2024
     * @param token Metadata de la variable
     * @param type Tipo de la variable
     * @param isPrivate Booleano que especifica si es privada o no
     */
    public void addVar(Token token, IDToken type, boolean isPrivate) {
        String name = token.getLexema();

        //Si la variable no existe, la genera
        if (variables.get(name) == null) {
            variables.put(name, new Variable(token, type, isPrivate, currentVarIndex));
            currentVarIndex++;
        }
        //Se intenta definir otra variable
        else {
            throw new SemanticException(token, "El atributo '" + name + "' se ha declarado más de una vez en la estructura '" + getName() + "'.");
        }
    }


    /**
     * Aumenta el contador de veces que se define o implementa la estructura.
     */
    public void updateCount(boolean isFromStruct) {
        int count = isFromStruct ? this.countStructDefinition : this.countImplDefinition;
        if (count == 0) {
            if (isFromStruct) {
                this.countStructDefinition = 1;
            }
            else {
                this.countImplDefinition = 1;
            }
        }
        else {
            throw new SemanticException(getMetadata(), "La estructura '" + getName() + "' se ha " + (isFromStruct ? "definido" : "implementado") + " más de una vez.");
        }
    }
    

    /**
     * Reescritura del método, convierte los datos en JSON.
     * 
     * @since 19/04/2024
     * @return Estructura de datos en formato JSON
     */
    @Override
    public String toJSON(String tabs) {
        String variableJSON = toJSONEntity(variables, tabs), methodJSON = toJSONEntity(methods, tabs);

        String constructorJSON = constructor.toJSON(tabs + "        ");
        return tabs + "{\n" +
            tabs + "    \"nombre\": \"" + getName() + "\",\n" +
            tabs + "    \"heredaDe\": \"" + (parent != null ? parent.getName() : "No posee") + "\",\n" +
            tabs + "    \"constructor\": [" + "\n"+ constructorJSON + (constructorJSON == "" ? "" : (tabs + "    ")) + "\n" + (tabs + "    ") +  "],\n" +
            tabs + "    \"methodIndex\": " + currentMethodIndex + ",\n" +
            tabs + "    \"varIndex\": " + currentVarIndex + ",\n" +
            tabs + "    \"atributos\": [" + variableJSON +  (variableJSON == "" ? "" : (tabs + "    ")) + "],\n" +
            tabs + "    \"métodos\": [" + methodJSON + (methodJSON == "" ? "" : (tabs + "    ")) + "]\n" +
        tabs + "}";
    }
}
