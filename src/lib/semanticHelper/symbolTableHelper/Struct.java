package src.lib.semanticHelper.symbolTableHelper;

import java.util.HashMap;

import src.lib.exceptionHelper.SemanticException;
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

    /**
     * Método que agrega un método al struct correspondiente. <br/>
     * 
     * <br/>Realiza las siguientes validaciones:<br/>
     * - Si ya existe un método con el mismo nombre.<br/>
     * - Si el método existente posee la misma firma.<br/>
     * 
     * <br/>Realiza las siguientes acciones:<br/>
     * - Aumenta el contador de posición para los métodos de la estructura correspondiente.<br/>
     * - Genera el número de posición para los parámetros.<br/>
     * 
     * @since 19/04/2024
     * @param method Datos específicos del método.
     */

    public void addMethod(Method method) {
        System.out.println(method.getSignature());
    }

    /**
     * Método que agrega una variable al struct correspondiente.
     * 
     * @since 19/04/2024
     * @param name Nombre de la variable.
     * @param variable Datos específicos de la variable.
     */
    public void addVar(String name, Variable variable) {

    }

    /**
     * @param parent Clase padre de la cual hereda la estructura.
     */
    public void setParent (Struct parent) {
        this.parent = parent;
    }

    /**
     * @return Struct con los datos de la superclase.
     */
    public Struct getParent() {
        return parent;
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
        int varCount = variables.size(), methodCount = methods.size();
        String varJSON = varCount > 0 ? "\n" : "", methodJSON = methodCount > 0 ? "\n" : "";

        //Genera el json de var
        for (Variable var : variables.values()) {
            varJSON += var.toJSON(tabs + "        ") + (varCount > 1 ? "," : "") + "\n";
            varCount--;
        }

        //Genera el json de metodos
        for (Method method : methods.values()) {
            methodJSON += method.toJSON(tabs + "        ") + (methodCount > 1 ? "," : "") + "\n";
            methodCount--;
        }

        //Genrea json de method

        return tabs + "{\n" +
            tabs + "    \"nombre\": \"" + getName() + "\",\n" +
            tabs + "    \"heredaDe\": \"" + (parent != null ? parent.getName() : "No posee") + "\",\n" +
            tabs + "    \"methodIndex\": " + String.valueOf(currentMethodIndex) + ",\n" +
            tabs + "    \"varIndex\": " + String.valueOf(currentVarIndex) + ",\n" +
            tabs + "    \"variables\": [" + varJSON +  (varJSON == "" ? "" : (tabs + "    ")) + "],\n" +
            tabs + "    \"métodos\": [" + methodJSON + (methodJSON == "" ? "" : (tabs + "    ")) + "]\n" +
        tabs + "}";
    }
}
