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
    public SymbolTable () {}

    /**
     * Método que agrega una estructura a la tabla de símbolos.<br/>
     * 
     * <br/>Realiza las siguientes validaciones:<br/>
     * - Si ya se ha generado desde un impl o struct.<br/>
     * 
     * <br/>Realiza las siguientes acciones:<br/>
     * - Aumenta el contador de veces que se ha leido desde un struct o impl.<br/>
     * - Sobreescribe o inicializa la herencia.<br/>
     * - Actualiza la estructura actual.<br/>
     * 
     * 
     * 
     * @since 19/04/2024
     * @param token Metadata con el token correspondiente al idStruct
     * @param parent IDToken que representa la clase de la cual hereda el struct
     * @param isFromStruct Booleano que avisa si se está generando desde un struct o un implement
     */
    public void addStruct(Token token, IDToken parent, boolean isFromStruct) {
        System.out.println("Agrega estructura: " + token.getLexema() + " con herencia " + parent.toString() + " se lee desde " + (isFromStruct ? "struct" : "impl"));
    }

    /**
     * Método que agrega una variable a la tabla de símbolos.<br/>
     * 
     * <br/>Realiza las siguientes validaciones:<br/>
     * - Si ya existe un atributo con el mismo nombre.<br/>
     * 
     * 
     * <br/>Realiza las siguientes acciones:<br/>
     * - Aumenta el contador de posición para los atributos de la estructura correspondiente.<br/>
     * 
     * @since 19/04/2024
     * @param token Metadata con el token correspondiente al idVar
     * @param type Tipo de dato
     * @param isPrivate Booleano que avisa si la variable es privada o no
     */
    public void addVar(Token token, IDToken type, boolean isPrivate) {
        System.out.println("Se agrega Var: " + token.getLexema() + " con tipo " + type.toString() + (isPrivate ? " y SI" : " y NO") + " es privada");
    }
    
    /**
     * Método que agrega un método a la tabla de símbolos. <br/>
     * 
     * <br/>Realiza las siguientes validaciones:<br/>
     * - Si ya existe un método con el mismo nombre.<br/>
     * - Si el método existente posee la misma firma.<br/>
     * 
     * <br/>Realiza las siguientes acciones:<br/>
     * - Aumenta el contador de posición para los métodos de la estructura correspondiente.<br/>
     * - Genera el número de posición para los parámetros.<br/>
     * 
     * 
     * @since 19/04/2024
     * @param token Metadata con el token correspondiente al idMethod
     * @param params ArrayList con los parámetros del método
     * @param isStatic Booleano que avisa si es estático o no
     * @param returnType Tipo de retorno del método
     */
    public void addMethod(Token token, ArrayList<Param> params, boolean isStatic, IDToken returnType) {
        String sParams = "";

        for (Param param : params) {
            sParams += param.getType() + " " + param.getName() + ", ";
        }

        System.out.println("Se agrega Method: " + token.getLexema() + (params.size() > 0 ? " con los parámetros " + sParams : " sin parámetros ") + (isStatic ? " SI" : " NO") +" es estatico y retorna tipo " + returnType.toString());
    }

    /**
     * Convierte los datos en JSON.
     * 
     * @since 19/04/2024
     * @return Estructura de datos en formato JSON
     */
    public String toJSON() {
        return "";
    }
}
