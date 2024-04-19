package src.lib.semanticHelper;

import java.util.HashMap;

import src.lib.semanticHelper.symbolTableHelper.*;

/**
 * Esta clase se encarga de contener la estructura del código fuente.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 19/04/2024
 */
public class SymbolTable {
    Struct currentStruct;
    Method currentMethod;
    HashMap<String, Struct> structs;

    /**
     * Método que agrega una estructura a la tabla de símbolos.
     * 
     * @since 19/04/2024
     */
    public void addStruct() {

    }

    /**
     * Método que agrega una variable a la tabla de símbolos.
     * 
     * @since 19/04/2024
     */
    public void addVar() {

    }

    /**
     * Método que agrega un método a la tabla de símbolos.
     * 
     * @since 19/04/2024
     */
    public void addMethod() {

    }

    /**
     * Método que agrega un parámetro a la tabla de símbolos.
     * 
     * @since 19/04/2024
     */
    public void addParam() {

    }

    /**
     * Reescritura del método, convierte los datos en JSON.
     * 
     * @since 19/04/2024
     * @return Estructura de datos en formato JSON
     */
    public String toJSON() {
        return "";
    }
}
