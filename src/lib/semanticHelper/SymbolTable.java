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
    Token currentToken;
    HashMap<String, Struct> structs;

    /**
     * Constructor de la clase
     * 
     * @param token Token inicial del código fuente.
     */
    public SymbolTable (Token token) {
        updateToken(token);
    }

    /**
     * Actualiza el token actual que se utilza en la tabla de símbolos.
     * 
     * @param token Nuevo token.
     */
    public void updateToken(Token token){
        this.currentToken = token;
    }

    /**
     * Método que agrega una estructura a la tabla de símbolos.
     * 
     * @since 19/04/2024
     */
    public void addStruct(IDToken parent, boolean isFromStruct) {

    }

    /**
     * Método que agrega una variable a la tabla de símbolos.
     * 
     * @since 19/04/2024
     */
    public void addVar(IDToken type, boolean isPrivate) {

    }

    /**
     * Método que agrega un método a la tabla de símbolos.
     * 
     * @since 19/04/2024
     */
    public void addMethod(ArrayList<Param> params, boolean isStatic, IDToken returnType) {

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
