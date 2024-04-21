package src.lib.semanticHelper;

import java.util.ArrayList;
import java.util.HashMap;

import src.lib.semanticHelper.symbolTableHelper.*;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

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
     * Constructor de la clase
     */
    public SymbolTable () {
    }

    /**
     * Método que agrega una estructura a la tabla de símbolos.
     * 
     * @since 19/04/2024
     */
    public void addStruct(Token token, IDToken parent, boolean isFromStruct) {
        System.out.println("Agrega estructura: " + token.getLexema() + " con herencia " + parent.toString() + " se lee desde " + (isFromStruct ? "struct" : "impl"));
    }

    /**
     * Método que agrega una variable a la tabla de símbolos.
     * 
     * @since 19/04/2024
     */
    public void addVar(Token token, IDToken type, boolean isPrivate) {
        System.out.println("Se agrega Var: " + token.getLexema() + " con tipo " + type.toString() + (isPrivate ? " y SI" : " y NO") + " es privada");
    }

    /**
     * Método que agrega un método a la tabla de símbolos.
     * 
     * @since 19/04/2024
     */
    public void addMethod(ArrayList<Param> params, boolean isStatic, IDToken returnType, Token token) {
        System.out.println("Se agrega Method: " + token.getLexema() + (isStatic ? " SI" : " NO") +" es estatico y retorna tipo " + returnType.toString());
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
