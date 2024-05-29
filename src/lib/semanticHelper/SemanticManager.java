package src.lib.semanticHelper;

import java.util.ArrayList;

import src.lib.semanticHelper.astHelper.SentenceBlock;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Param;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

/**
 * Clase encargada de manejar la tablad e símbolos y el AST
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 17/05/2024
 */
public class SemanticManager {
    private Struct currentStruct;
    private Method currentMethod;
    
    private SymbolTable symbolTable;
    private AST ast;

    /**
     * Constructor de la clase.
     */
    public SemanticManager () {
        //Genera la tabla de símbolos
        symbolTable = new SymbolTable();

        //Genera el arbol sintactico abstracto
        ast = new AST();
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
    //METODOS PARA INSERTAR DATOS A LA TABLA DE SIMBOLOS
    public void addStruct(Token token, Token parent, boolean isFromStruct) {
        currentStruct = symbolTable.addStruct(token, parent, isFromStruct);
    }

    
    /**
     * Método que agrega una variable a la tabla de símbolos. Este deriva la lógica en el método de la estructura o método correspondiente.
     * 
     * @since 19/04/2024
     * @param token Metadata con el token correspondiente al idVar
     * @param type Tipo de dato
     * @param isPrivate Booleano que avisa si la variable es privada o no
     * @param isAtribute Booleano que avisa si es un atributo o variable local
     */
    public void addVar(Token token, Token type, boolean isPrivate, boolean isAtribute) {
        symbolTable.addVar(token, type, isPrivate);
        
        //Agrego metodo o atributo
        if(isAtribute){
            currentStruct.addVar(token, type, isPrivate);
        } else {
            currentMethod.addVar(token, type);;
        }
    }

    /**
     * Método que agrega un método a la tabla de símbolos. Este deriva la lógica en el método de la estructura.
     * 
     * @since 19/04/2024
     * @param token Metadata con el token correspondiente al idMethod
     * @param params ArrayList con los parámetros del método
     * @param isStatic Booleano que avisa si es estático o no
     * @param returnTypeToken Tipo de retorno del método
     */
    public void addMethod(Token token, ArrayList<Param> params, boolean isStatic, Token returnTypeToken, Boolean fromStruct) {
        //Agrega el método a la tabla de simbolos
        currentMethod = symbolTable.addMethod(token, params, isStatic, returnTypeToken, currentStruct, fromStruct);
    }


    /**
     * Método que agrega un bloque de sentencias al AST.
     * 
     * @since 22/05/2024
     * @param block Bloque de sentencias.
     */
    //METODOS PARA INSERTAR DATOS AL AST
    public void addBlock(SentenceBlock block, Boolean fromStruct){
        //Si el bloque método es start, las sentencias no pertenecen a ninguna estructura
        this.ast.addBlock(
            (! (block.getIDBlock().equals("start") && fromStruct.equals(false)) ? currentStruct : null),
            block
        );
    }

    /**
     * Consolida la tabla de símbolos y el arbol sintáctico abstracto
     */
    public void consolidate (){
        symbolTable.consolidate();
        ast.consolidate(symbolTable);
    }

    /** 
     * Genera un ArrayList de strings con los json generados para tabla de símbolos y ast
     * @return ArrayList de strings con tabla de símbolos (posicion 0) y ast (posicion 1) en formato json
     */
    public ArrayList<String> toJSON () {
        ArrayList<String> generacionIntermedias = new ArrayList<String>(2);
        generacionIntermedias.add(symbolTable.toJSON());
        generacionIntermedias.add(ast.toJSON("    "));
        return generacionIntermedias;
    }
}
