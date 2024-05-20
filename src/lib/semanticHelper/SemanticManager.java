package src.lib.semanticHelper;

import java.util.ArrayList;

import src.lib.semanticHelper.astHelper.SentenceBlock;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Param;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

public class SemanticManager {
    private Struct currentStruct;
    private Method currentMethod;
    
    private SymbolTable symbolTable;
    private AST ast;

    public SemanticManager () {
        //Genera la tabla de símbolos
        symbolTable = new SymbolTable();

        //Genera el arbol sintactico abstracto
        ast = new AST();
    }

    //METODOS PARA INSERTAR DATOS A LA TABLA DE SIMBOLOS
    public void addStruct(Token token, Token parent, boolean isFromStruct) {
        currentStruct = symbolTable.addStruct(token, parent, isFromStruct);
    }

    public void addVar(Token token, Token type, boolean isPrivate, boolean isAtribute) {
        symbolTable.addVar(token, type, isPrivate);
        
        //Agrego metodo o atributo
        if(isAtribute){
            currentStruct.addVar(token, type, isPrivate);
        } else {
            currentMethod.addVar(token, type);;
        }
    }

    public void addMethod(Token token, ArrayList<Param> params, boolean isStatic, Token returnTypeToken) {
        //Agrega el método a la tabla de simbolos
        currentMethod = symbolTable.addMethod(token, params, isStatic, returnTypeToken, currentStruct);
    }



    //METODOS PARA INSERTAR DATOS AL AST
    public void addBlock(SentenceBlock block){
        //Si el bloque método es start, las sentencias no pertenecen a ninguna estructura
        this.ast.addBlock(
            (!block.getIDBlock().equals("start")) ? currentStruct : null,
            block
        );
    }

    public void consolidate (){
        symbolTable.consolidate();
        ast.consolidate(symbolTable);
    }

    /** 
     * Genera un ArrayList<String> con los json generados para tabla de símbolos y ast
     * @return ArrayList<String> con tabla de símbolos (posicion 0) y ast (posicion 1) en formato json
     */
    public ArrayList<String> toJSON () {
        ArrayList<String> generacionIntermedias = new ArrayList<String>(2);
        generacionIntermedias.add(symbolTable.toJSON());
        generacionIntermedias.add(ast.toJSON("    "));
        return generacionIntermedias;
    }
}
