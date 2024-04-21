package src.lib.semanticHelper.symbolTableHelper;

import java.util.HashMap;

import src.lib.tokenHelper.Token;

/**
 * Esta clase se encarga de contener las estructuras declaradas en el código fuente.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 19/04/2024
 */
public class Struct extends Metadata {
    Struct parent;
    Method constructor;
    int currentMethodIndex, currentVarIndex;
    HashMap<String, Variable> variables;
    HashMap<String, Method> methods;

    /**
     * Constructor de la clase.
     * 
     * @since 19/04/2024
     */
    public Struct (Token token, Struct parent) {
        super(token, 0);
        this.parent = parent;
    }

    /**
     * Método que agrega un método al struct correspondiente.
     * 
     * @since 19/04/2024
     * @param name Nombre del método.
     * @param method Datos específicos del método.
     */
    public void addMethod(String name, Method method) {
        this.methods.put(name, method);
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
