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
    private final HashSet<String> staticStruct;
    private Struct currentStruct;
    private Method currentMethod;
    private Method start;
    private HashMap<String, Struct> structs;

    // Estructura que se utiliza para almacenar clases que deben reasignar herencias.
    // En consolidación: Si posee elementos, debe retornar error.
    private HashMap<String, ArrayList<String>> redefinitions;

    // Estructura que se utiliza para almacenar structs que se debe chequear su declaracion.
    private HashMap<String,Token> checkDefinitionStructs;

    /**
     * Constructor de la clase.<br/>
     * 
     * Realiza la precarga de clases Object e IO
     */
    public SymbolTable () {
        staticStruct = new HashSet<String>(){{
            add("Object");
            add("IO");
            add("Array");
            add("Int");
            add("Str");
            add("Char");
            add("Bool");
        }};
        structs = new HashMap<String, Struct>();
        redefinitions = new HashMap<>();
        checkDefinitionStructs = new HashMap<String, Token>();
        init();
    }

    private void init() {
        //Definicion de estructuras
        Struct objectStruct = new Struct(new Token(IDToken.spOBJECT, "Object", 0, 0), null),
            IO = new Struct(new Token(IDToken.spIO, "IO", 0, 0),objectStruct),
            Array = new Struct(new Token(IDToken.typeARRAY, "Array", 0, 0), objectStruct),
            Int = new Struct(new Token(IDToken.typeINT, "Int", 0, 0), objectStruct),
            Str = new Struct(new Token(IDToken.typeSTR, "Str", 0, 0), objectStruct),
            Char = new Struct(new Token(IDToken.typeCHAR, "Char", 0, 0), objectStruct),
            Bool = new Struct(new Token(IDToken.typeBOOL, "Bool", 0, 0), objectStruct);

        //Parametros compartidos
        ArrayList<Param> strParams = generateArrayParam(IDToken.typeSTR, "s"),
            arrayParams = generateArrayParam( IDToken.typeARRAY, "a"),
            nullParams = new ArrayList<Param>();

        //Hash con definicion de metodos
        HashMap<String, HashMap<String, ArrayList<Param>>> definitions = new HashMap<String, HashMap<String, ArrayList<Param>>>(){{
            put("IO", new HashMap<String, ArrayList<Param>>(){{
                put("out_str", strParams);
                put("out_int", generateArrayParam(IDToken.typeINT, "i"));
                put("out_bool", generateArrayParam(IDToken.typeBOOL, "b"));
                put("out_char", generateArrayParam(IDToken.typeCHAR, "c"));
                put("out_array_int", arrayParams);
                put("out_array_str", arrayParams);
                put("out_array_bool", arrayParams);
                put("out_array_char", arrayParams);
                put("in_str", nullParams);
                put("in_int", nullParams);
                put("in_bool", nullParams);
                put("in_char", nullParams);
            }});
            put("Array", new HashMap<String, ArrayList<Param>>(){{
                put("length", nullParams);
            }});
            put("Str", new HashMap<String, ArrayList<Param>>(){{
                put("length", nullParams);
                put("concat", strParams);
            }});
        }};

        //Retornos de metodos si tuviesen
        HashMap<String, HashMap<String, IDToken>> returns = new HashMap<String, HashMap<String, IDToken>>(){{
            put("IO", new HashMap<String, IDToken>() {{
                put("in_str", IDToken.typeSTR);
                put("in_int", IDToken.typeINT);
                put("in_bool", IDToken.typeBOOL);
                put("in_char", IDToken.typeCHAR);
            }});
            put("Array", new HashMap<String, IDToken>() {{
                put("length", IDToken.typeINT);
            }});
            put("Str", new HashMap<String, IDToken>() {{
                put("length", IDToken.typeINT);
                put("concat", IDToken.typeSTR);
            }});
        }};

        //Inserta las estructuras
        structs.put("Object", objectStruct);
        structs.put("IO", IO);
        structs.put("Char", Char);
        structs.put("Str", Str);
        structs.put("Array", Array);
        structs.put("Int", Int);
        structs.put("Bool", Bool);

        //Inserta los métodos en las estructuras
        definitions.forEach((String sStructKey, HashMap<String, ArrayList<Param>> mapParams) -> {
            //Recorre los metodos
            mapParams.forEach((String sMethodKey, ArrayList<Param> params) -> {
                //Agrega el metodo a la estructura correspondiente
                addVoid(
                    structs.get(sStructKey), 
                    new Token(IDToken.idOBJECT, sMethodKey, 0, 0),
                    params,
                    returns.get(sStructKey) != null ? returns.get(sStructKey).get(sMethodKey) : null
                );

            });
        });
    }

    private void addVoid (Struct struct, Token token, ArrayList<Param> params, IDToken returnType) {
        struct.addMethod(token, params, true, returnType == null ? IDToken.typeVOID : returnType);
    }

    /** 
     * Método que genera un array de parámetros
     * 
     * @param type IDToken con el tipo de parámetro
     * @param lexema Lexema para generar el token
     * @return ArrayList<Param>
     */
    private ArrayList<Param> generateArrayParam(IDToken type, String lexema){
        return new ArrayList<Param>(){{
            add(
                new Param(
                    new Token(type, lexema, 0, 0),
                    new Token(type, type.toString(), 0, 0), 
                    0
                )
            );
        }};
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
     * - Asigna superclases cuando se define (Si se utiliza antes).<br/>
     * 
     * 
     * @since 19/04/2024
     * @param token Metadata con el token correspondiente al idStruct
     * @param parent IDToken que representa la clase de la cual hereda el struct (Por defecto Object)
     * @param isFromStruct Booleano que avisa si se está generando desde un struct o un implement
     */
    public void addStruct(Token token, IDToken parent, boolean isFromStruct) {
        String sStruct = token.getLexema(), sParent = parent.toString();
        Struct parentStruct = structs.get(sParent);

        //Valida que no se herede de los tipos primitivos
        if (!sParent.equals("Object") && staticStruct.contains(sParent)) {
            throw new SemanticException(token, "No se puede heredar de un tipo de dato predefinido. Tipo: " + sParent);
        }
        
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

        //Actualiza la estructura actual
        currentStruct = structs.get(sStruct);
        
        //Genera la estructura si no existe
        if (currentStruct == null) {
            currentStruct = new Struct(token, parentStruct);
        }
        //Si ya existe, valida si se esta definiendo desde un struct para sobreescribir la superclase
        else {
            if (isFromStruct) {
                currentStruct.setParent(parentStruct);
            }
        }

        //Valida si debe asignarse como superclase de otras estructuras y lo hace
        if (redefinitions.get(sStruct) != null) {
            for (String childrens : redefinitions.get(sStruct)) {
                structs.get(childrens).setParent(currentStruct);
            }

            //Avisa que ya se ha asignado a las structs incompletas
            redefinitions.remove(sStruct);
        }

        //Valida si debe notificar que se ha definido en parametro o retorno
        if (checkDefinitionStructs.get(sStruct) != null) {
            checkDefinitionStructs.remove(sStruct);
        }

        //Valida herencia cíclica
        checkParents(currentStruct, token);

        //Avisa si se está definiendo desde un struct o impl (Puede retornar error)
        currentStruct.updateCount(isFromStruct);

        //Agrega la estructura al hash
        structs.put(sStruct, currentStruct);

        //Agrega la relacion con el padre solo si se llama desde un struct
        if (isFromStruct) {
            addParentRelationships(sParent, currentStruct);
        }
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
        String parent = struct.getParent();

        //Inicializa el stack
        stack.add(struct.getName());

        //Valida hasta que se herede de object o no se haya definido el parent
        while (parent != "Object") {
            //Si el padre de la estructura actual ya existe en el stack, es porque existe herencia cíclica.
            if (stack.contains(parent)) {
                throw new SemanticException(token, "La estructura posee herencia cíclica. Estructura que genera el ciclo: " + struct.getName());
            }
            stack.add(parent);
            struct = structs.get(parent);
            parent = struct.getParent();
        }
    }

    private void addParentRelationships (String parent, Struct children) {
        structs.get(parent).addChildren(children);
    }

    /**
     * Método que agrega una variable a la tabla de símbolos. Este deriva la lógica en el método de la estructura o método correspondiente.
     * 
     * @since 19/04/2024
     * @param token Metadata con el token correspondiente al idVar
     * @param type Tipo de dato
     * @param isPrivate Booleano que avisa si la variable es privada o no
     */
    public void addVar(Token token, Token type, boolean isPrivate, boolean isAtribute) {
        if(isAtribute){
            currentStruct.addVar(token, type.getIDToken(), isPrivate);
        } else {
            currentMethod.addVar(token, type.getIDToken(), isPrivate);;
        }
    }
    
    /**
     * Método que agrega un método a la tabla de símbolos. Este deriva la lógica en el método de la estructura.
     * 
     * @since 19/04/2024
     * @param token Metadata con el token correspondiente al idMethod
     * @param params ArrayList con los parámetros del método
     * @param isStatic Booleano que avisa si es estático o no
     * @param returnType Tipo de retorno del método
     */
    public void addMethod(Token token, ArrayList<Param> params, boolean isStatic, Token returnTypeToken) {
        if (token.getIDToken().equals(IDToken.idSTART)) {
            //Valida si se está generando el método start y que no se haya generado otro
            if (start == null) {
                start = new Method(token, params, returnTypeToken.getIDToken(), isStatic, 0);
                currentMethod = start;
            }
            else {
                throw new SemanticException(token, "No se permite definir más de un método start.");
            }
        } else {
            // Valida si el tipo de retorno está definido
            if (!IDToken.typeVOID.equals(returnTypeToken.getIDToken()) && !this.structs.containsKey(returnTypeToken.getLexema())){
                checkDefinitionStructs.put(returnTypeToken.getLexema(), returnTypeToken);
            }

            // si el tipo de dato del param no se ha definido previamente entonces 
            // se añade a checkDefinitionStructs para validarlo en la consolidación
            for (Param param : params) {
                if (!this.structs.containsKey(param.getType().getLexema())){
                    checkDefinitionStructs.put(param.getType().getLexema(), param.getType());
                }
            }

            //Agrega el método al hash
            currentMethod = currentStruct.addMethod(token, params, isStatic, returnTypeToken.getIDToken());
        }

    }

    /**
     * Método que consolida la tabla de símbolos.<br/>
     * 
     * <br/>Realiza las siguientes validaciones:<br/>
     * - Que se hayan definido todas las clases de la cual se hereda.<br/>
     * 
     * <br/>Realiza las siguientes acciones:<br/>
     * 
     * 
     * @since 22/04/2024
     */
    public void consolidate () {
        Token metadata;
        String sMessage = "";

        //Valida si se han definido todas las clases
        if (!redefinitions.isEmpty()) {
            //Obtiene el nombre de la superclase
            sMessage = redefinitions.keySet().iterator().next();

            //Obtiene la metadata del struct que utiliza la superclase.
            metadata = structs.get(redefinitions.get(sMessage).get(0)).getMetadata();

            throw new SemanticException(metadata, "La estructura '" + sMessage + "' no se encuentra definida.");
        }

        //Valida si se han definido todos los tipos de datos
        if (!checkDefinitionStructs.isEmpty()) {
            //Obtiene el nombre del tipo
            sMessage = checkDefinitionStructs.keySet().iterator().next();
            
            //Obtiene la metadata del struct que utiliza la superclase.
            metadata = checkDefinitionStructs.get(sMessage);

            throw new SemanticException(metadata, "La estructura '" + sMessage + "' no se encuentra definida.");
        }
        
        // Consolida las estructuras a partir de Object
        structs.get("Object").consolidate();
    }

    /**
     * Convierte los datos en JSON.
     * 
     * @since 19/04/2024
     * @return Estructura de datos en formato JSON
     */
    public String toJSON() {
        String structJSON = "", startJSON = start.toJSON("    ");
        int count = structs.values().size() - staticStruct.size();

        for (Struct struct : structs.values()) {
            if (!staticStruct.contains(struct.getName())) {
                structJSON += struct.toJSON("        ") + ((count>=0) ? "," : "") + "\n";
            }
            count--;
        }

        return "{\n" +
            "    \"structs\": [\n" +
                structJSON +
            "    ],\n"+
            "    \"start\": " + startJSON.substring(4, startJSON.length()) +
        "\n}";
    }
}
