package src.lib.semanticHelper;

import java.util.ArrayList;

import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Param;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

public class SemanticManager {
    private Struct currentStruct;
    private Method currentMethod;
    private Method start;
    private SymbolTable symbolTable;

    public SemanticManager () {
        //Genera la tabla de símbolos y asigna el token actual
        symbolTable = new SymbolTable();
    }

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
            //Agrega el método a la tabla de simbolos
            currentMethod = symbolTable.addMethod(token, params, isStatic, returnTypeToken, currentStruct);
        }

    }

    public void consolidate (){
        symbolTable.consolidate();
    }

    public String toJSON () {
        return symbolTable.toJSON(start.toJSON("    "));
    }
}
