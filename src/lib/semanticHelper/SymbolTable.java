package src.lib.semanticHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import src.lib.exceptionHelper.SemanticException;
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

    // Estructura que se utiliza para almacenar clases que deben reasignar herencias.
    // En consolidación: Si posee elementos, debe retornar error.
    HashMap<String, ArrayList<String>> redefinitions;

    /**
     * Constructor de la clase.<br/>
     * 
     * Realiza la precarga de clases Object e IO
     */
    public SymbolTable () {
        structs = new HashMap<String, Struct>();
        redefinitions = new HashMap<>();
        init();
    }

    private void init() {
        Struct objectStruct = new Struct(new Token(IDToken.spOBJECT, "Object", 0, 0), null);
        //Inserta la clase Object
        structs.put("Object", objectStruct);
    }

    /**
     * Método que agrega una estructura a la tabla de símbolos.<br/>
     * 
     * <br/>Realiza las siguientes validaciones:<br/>
     * - Herencias cíclicas.<br/>
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
     * @param parent IDToken que representa la clase de la cual hereda el struct (Por defecto Object)
     * @param isFromStruct Booleano que avisa si se está generando desde un struct o un implement
     */
    public void addStruct(Token token, IDToken parent, boolean isFromStruct) {
        System.out.println("Agrega estructura: " + token.getLexema() + " con herencia " + parent.toString() + " se lee desde " + (isFromStruct ? "struct" : "impl"));

        String sStruct = token.getLexema(), sParent = parent.toString();
        Struct struct = structs.get(sStruct), parentStruct = structs.get(sParent);
        
        //Genera la estructura si no existe
        if (struct == null) {
            //Si no se ha definido la superclase, la agrega a un stack de redefinición y asigna object
            if (parentStruct == null) {
                //Avisa que cuando se defina, se debe setear como superclase de esta struct.
                if (!redefinitions.containsKey(sParent)) {
                    redefinitions.put(sParent, new ArrayList<String>());
                }
                redefinitions.get(sParent).add(sStruct);

                //Por ahora, asigna Object
                parentStruct = structs.get("Object");
            }

            //Genera la estructura
            struct = new Struct(token, parentStruct);
        }

        //Valida si debe asignarse como superclase de otras estructuras y lo hace
        if (redefinitions.get(sStruct) != null) {
            for (String childrens : redefinitions.get(sStruct)) {
                structs.get(childrens).setParent(struct);
            }

            //Avisa que ya se ha asignado a las structs incompletas
            redefinitions.remove(sStruct);
        }

        //Valida herencia cíclica
        checkParents(struct, token);


        //Agrega la estructura al hash
        structs.put(sStruct, struct);
    }

    /**
     * Método que valida herencia cíclica.
     * 
     * @param struct
     * @param token
     * @since 21/04/2024
     */
    private void checkParents (Struct struct, Token token) {
        HashSet<String> stack = new HashSet<String>();
        Struct parent = struct.getParent();

        //Inicializa el stack
        stack.add(struct.getName());

        //Valida hasta que se herede de object o no se haya definido el parent
        while (parent.getName() != "Object") {
            //Si el padre de la estructura actual ya existe en el stack, es porque existe herencia cíclica.
            if (stack.contains(parent.getName())) {
                throw new SemanticException(token, "La estructura posee herencia cíclica. Estructura que genera el ciclo: " + struct.getName());
            }
            stack.add(parent.getName());
            struct = parent;
            parent = struct.getParent();
        }
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
    public void addVar(Token token, IDToken type, boolean isPrivate, boolean isAtribute) {
        System.out.println("Se agrega: "+(isAtribute ? "atributo" : "variable") + token.getLexema() + " con tipo " + type.toString() + (isPrivate ? " y SI" : " y NO") + " es privada");
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
