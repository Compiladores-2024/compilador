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
        Struct objectStruct = new Struct(new Token(IDToken.spOBJECT, "Object", 0, 0), null);
        //Inserta la clase Object
        structs.put("Object", objectStruct);

        addIO();
        addArray();
        addInt();
        addStr();
        addBool();
        addChar();
    }

    private void addIO(){
        Struct IO = new Struct(new Token(IDToken.spIO, "IO", 0, 0), 
            this.structs.get("Object"));

        IO.addMethod( new Token(null, "out_str", 0, 0), 
            generateArrayParam( new Token(IDToken.typeSTR,"s", 0, 0)), true, IDToken.typeVOID);
        IO.addMethod( new Token(null,"out_int", 0,0), 
            generateArrayParam( new Token(IDToken.typeINT,"i", 0, 0)), true, IDToken.typeVOID);
        IO.addMethod( new Token(null,"out_bool", 0,0),
            generateArrayParam( new Token(IDToken.typeArrayBOOL,"b", 0, 0)), true, IDToken.typeVOID);
        IO.addMethod( new Token(null,"out_char", 0,0), 
            generateArrayParam( new Token(IDToken.typeCHAR, "c", 0, 0)), true, IDToken.typeVOID);
        IO.addMethod( new Token(null,"out_array_int", 0,0), 
            generateArrayParam( new Token(IDToken.typeARRAY, "a", 0, 0)), true, IDToken.typeVOID);
        IO.addMethod( new Token(null,"out_array_str", 0,0), 
            generateArrayParam( new Token(IDToken.typeARRAY, "a", 0, 0)), true, IDToken.typeVOID);
        IO.addMethod( new Token(null,"out_array_bool", 0,0), 
            generateArrayParam( new Token(IDToken.typeARRAY, "a", 0, 0)), true, IDToken.typeVOID);
        IO.addMethod( new Token(null,"out_array_char", 0,0), 
            generateArrayParam( new Token(IDToken.typeARRAY, "a", 0, 0)), true, IDToken.typeVOID);
        IO.addMethod( new Token(null,"in_str", 0,0),
            new ArrayList<Param>(), true, IDToken.typeSTR);
        IO.addMethod( new Token(null,"in_int", 0,0),
            new ArrayList<Param>(), true, IDToken.typeINT);
        IO.addMethod( new Token(null,"in_bool", 0,0),
            new ArrayList<Param>(), true, IDToken.typeBOOL);
        IO.addMethod( new Token(null,"in_char", 0,0),   
            new ArrayList<Param>(), true, IDToken.typeCHAR);

        structs.put("IO", IO);
    }

    private void addArray(){
        Struct Array = new Struct(new Token(IDToken.typeARRAY, "Array", 0, 0), 
            this.structs.get("Object"));
        Array.addMethod(new Token(null, "length", 0, 0),
        new ArrayList<Param>(), false,IDToken.typeINT);
        structs.put("Array", Array);
    }

    private void addInt(){
        Struct Int = new Struct(new Token(IDToken.typeINT, "Int", 0, 0), 
            this.structs.get("Object"));
        structs.put("Int", Int);
    }

    private void addStr(){
        Struct Str = new Struct(new Token(IDToken.typeSTR, "Str", 0, 0), 
            this.structs.get("Object"));
        Str.addMethod(new Token(null, "length", 0, 0),
        new ArrayList<Param>(), false, IDToken.typeINT);
        Str.addMethod(new Token(null, "concat", 0, 0), 
            generateArrayParam(new Token(IDToken.typeSTR, "s", 0, 0)), false, IDToken.typeSTR);
        structs.put("Str", Str);
    }

    private void addChar(){
        Struct Char = new Struct(new Token(IDToken.typeCHAR, "Char", 0, 0), 
            this.structs.get("Object"));
        structs.put("Char", Char);
    }
    
    private void addBool(){
        Struct Bool = new Struct(new Token(IDToken.typeBOOL, "Bool", 0, 0), 
            this.structs.get("Object"));
        structs.put("Bool", Bool);
    }

    
    /** 
     * Método que genera un array de parámetros
     * 
     * @param paramToken IDToken con el tipo de parámetro
     * @param lexema Lexema para generar el token
     * @return ArrayList<Param>
     */
    private ArrayList<Param> generateArrayParam(Token paramToken){
        ArrayList<Param> paramList= new ArrayList<Param>();
        paramList.add(new Param(paramToken, new Token(paramToken.getIDToken(), paramToken.getLexema() , 0, 0), 0));
        return paramList; 

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
        if (staticStruct.contains(sParent) && sParent != "Object") {
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

        //Valida herencia cíclica
        checkParents(currentStruct, token);

        //Avisa si se está definiendo desde un struct o impl (Puede retornar error)
        currentStruct.updateCount(isFromStruct);

        //Agrega la estructura al hash
        structs.put(sStruct, currentStruct);
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
     * Método que agrega una variable a la tabla de símbolos. Este deriva la lógica en el método de la estructura o método correspondiente.
     * 
     * @since 19/04/2024
     * @param token Metadata con el token correspondiente al idVar
     * @param type Tipo de dato
     * @param isPrivate Booleano que avisa si la variable es privada o no
     */
    public void addVar(Token token, Token type, boolean isPrivate, boolean isAtribute) {
        if(isAtribute){
            currentStruct.addVar(token, type, isPrivate);
        } else {
            currentMethod.addVar(token, type, isPrivate);;
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
    public void addMethod(Token token, ArrayList<Param> params, boolean isStatic, IDToken returnType) {
        if (token.getIDToken().equals(IDToken.idSTART)) {
            //Valida si se está generando un constructor y que no se haya generado otro
            if (start == null) {
                start = new Method(token, params, returnType, isStatic, 0);
                currentMethod = start;
            }
            else {
                throw new SemanticException(token, "No se permite definir más de un método start.");
            }
        } else {
            for (Param param : params) {
                // si un tipo de datos del param no pertenece a staticStruct ni se ha definido previamente entonces 
                // se añade a checkDefinitionStructs
                if (!this.staticStruct.contains(param.getType().getLexema()) && !this.structs.containsKey(param.getType().getLexema())){
                    if(checkDefinitionStructs.get(param.getType().getLexema())==null){

                        checkDefinitionStructs.put(param.getType().getLexema(), param.getType());
                    }
                }
            }
            currentMethod = currentStruct.addMethod(token, params, isStatic, returnType);
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
        if (redefinitions.size() > 0) {
            //Obtiene el nombre de la superclase
            sMessage = redefinitions.keySet().iterator().next();

            //Obtiene la metadata del struct que utiliza la superclase.
            metadata = structs.get(redefinitions.get(sMessage).get(0)).getMetadata();

            throw new SemanticException(metadata, "La estructura '" + sMessage + "' no se encuentra definida.");
        }
        // añadir variables y metodos  heredados desde Object
        addMethod_and_variables_inherited(structs.get("Object"));
        //Valida si se han definido todas las clases checkDefinitionStructs
        if (checkDefinitionStructs.size() > 0) {
            //Obtiene el nombre de la superclase
            sMessage = checkDefinitionStructs.keySet().iterator().next();
            
            //Obtiene la metadata del struct que utiliza la superclase.
            metadata = (checkDefinitionStructs.get(sMessage));
            throw new SemanticException(metadata, "La estructura '" + metadata.getLexema() + "' no se encuentra definida.");
        }
        
    }
    
        /**
     * Método que añade metodos y variables heredadas. El objetivo es partir desde Object (parentStruct) e ir añadiendo
     * metodos a las clases entry que utilicen a parentStruct.
     * 
     * @param parent HashMap<String, Method>
     * @since 22/04/2024
     */
    private void addMethod_and_variables_inherited(Struct parentStruct){

        // se itera por cada struct
        for (HashMap.Entry<String, Struct> entry : structs.entrySet()) {

            // se omiten los structs predefinidos
            if (!staticStruct.contains(entry.getKey())){
                
                //Valida que posea al menos un struct
                if(!entry.getValue().hasStruct()){
                    throw new SemanticException(entry.getValue().getMetadata(), "Struct "+ entry.getValue().getName() + " debe definirse. Falta struct.");
                }
                
                //Valida que posea al menos un impl
                if(!entry.getValue().hasImpl()){
                    throw new SemanticException(entry.getValue().getMetadata(), "Struct "+ entry.getValue().getName() + " debe implementarse. Falta impl.");
                }
                
                //Valida que posea un constructor
                if(!entry.getValue().hasConstructor()){
                    throw new SemanticException(entry.getValue().getMetadata(), "Struct "+ entry.getValue().getName() + " no tiene constructor implementado");
                }

                if (checkDefinitionStructs.containsKey(entry.getKey())){
                    checkDefinitionStructs.remove(entry.getKey());
                }

                // si existe algun struct que hereda de parentStruct
                if (entry.getValue().getParent().equals(parentStruct)){
                    if (!parentStruct.equals(structs.get("Object"))){

                        // se toma el HashMap de Variables del struct que hereda de parentStruct
                        HashMap<String, Variable> auxVariables = entry.getValue().getVariable();

                        // se actualizan los position del HashMap de Variable
                        updateIndexVariable(auxVariables,parentStruct.getCurrentVarIndex());
                        
                        // se añade cada variable del parent al struct hijo
                        for (HashMap.Entry<String, Variable> parentVariable : parentStruct.getVariable().entrySet()) {

                            // si no existe la variable en el hijo entonces se agrega
                            if (auxVariables.get(parentVariable.getKey())==null){
                                
                                auxVariables.put(parentVariable.getKey(), parentVariable.getValue());
                            }
                            // si ya existe la variable en el hijo es un error
                            else{
                                throw new SemanticException(auxVariables.get(parentVariable.getKey()).getMetadata(),"Atributo '"+
                                auxVariables.get(parentVariable.getKey()).getMetadata().getLexema() +
                                "' ya declarado en un ancestro");

                            }
                        }

                        // se toma el HashMap de Methods del struct que hereda de parentStruct
                        HashMap<String, Method> auxMethods = entry.getValue().getMethods();
                        
                        // se actualizan los position del HashMap
                        updateIndexMethods(auxMethods,parentStruct.getCurrentMethodIndex());
                        
                        // se genera un HashMap parentCopy que contiene metodos del parent  
                        // que no fueron sobreescritos. Si un metodo fue sobreescrito correctamente
                        // se omite
                        HashMap<String, Method> parentCopy= checksignature(auxMethods, parentStruct.getMethods());
    
                        // se añade cada metodo del parentCopy al struct hijo
                        for (HashMap.Entry<String, Method> parentMethod : parentCopy.entrySet()) {
                            auxMethods.put(parentMethod.getKey(), parentMethod.getValue());
                        }
                        // entry.getValue().updateCurrentMethodIndex();
                        entry.getValue().updateCurrentVarIndex();
                        
                    }
                    
                    // recursion con cada hijo
                    addMethod_and_variables_inherited(entry.getValue());
                    // se actualizan los indices de metodos y variables
                    entry.getValue().updateCurrentMethodIndex();
                    entry.getValue().updateCurrentVarIndex();
                }
            }
        }
    }


    /**
     * Método que retorna un HashMap<String, Method> con metodos de un parent. 
     * Donde se omiten los metodos sobreescritos correctamente.
     * En caso de que un metodo de actual contenga un metodo mal redefinido con respecto a los de parent,
     * entonces se genera una excepcion semantica.
     * 
     * @param actual HashMap<String, Method>
     * @param parent HashMap<String, Method>
     * @return HashMap<String, Method> 
     * @since 22/04/2024
     */
    private HashMap<String, Method> checksignature(HashMap<String, Method> actual, HashMap<String, Method> parent) {
        HashMap<String, Method> parentCopy = new HashMap<String, Method>();
        
        for (HashMap.Entry<String, Method> parentMethod : parent.entrySet()) {
            // si los metodos de actual contienen algun metodo de parent
            if (actual.containsKey(parentMethod.getKey())) {
                // si la signature NO es igual
                if (!(actual.get(parentMethod.getKey()).getSignature().equals(parentMethod.getValue().getSignature()))){
                    
                    throw new SemanticException(actual.get(parentMethod.getKey()).getMetadata(),"METODO MAL REDEFINIDO. NO COINCIDEN LAS SIGNATURE");
                }
                //si coinciden las signature se actualizan las position
                else{
                    actual.get(parentMethod.getValue().getName()).setPosition(parentMethod.getValue().getPosition());
                }
            }
            else{
                parentCopy.put(parentMethod.getKey(), parentMethod.getValue());
            }   
        }
        return parentCopy;
    }

    /**
     * Método que actualiza las position de las Variable de un 
     * HashMap<String,Variable> pasado como parametro.
     * 
     * @param hashVar HashMap<String, Variable>
     * @param count Cantidad de variables de un HashMap padre. 
     * @since 23/04/2024
     */
    private void updateIndexVariable(HashMap<String, Variable> hashVar, int count){
        for (HashMap.Entry<String, Variable> entryVar : hashVar.entrySet()) {
            // se toma la position anterior
            int positionPrev = ((Metadata)entryVar.getValue()).getPosition();
            // se actualiza la posicion anterior sumandole count (cantidad de entradas del padre)
            ((Metadata)entryVar.getValue()).setPosition(positionPrev + count);
        }
    }


    /**
     * Método que actualiza las position de los metodos de un 
     * HashMap<String,Method> pasado como parametro.
     * 
     * @param methods HashMap<String, Method>
     * @param countMethods Cantidad de metodos de un HashMap padre. 
     * @since 22/04/2024
     */
    private void updateIndexMethods(HashMap<String, Method> methods, int countMethods){
        for (HashMap.Entry<String, Method> entryMethod : methods.entrySet()) {
            entryMethod.getValue().setPosition(countMethods);
            ++countMethods;
        }
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
