package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

public class ArrayAccess extends Primary{
    
    private Expression indexArray;

    public ArrayAccess (Token value, Expression indexArray, Primary rightChained) {
        super(rightChained);
        this.indexArray = indexArray;
        this.token = value;
    }

    @Override
    public void checkTypes(SymbolTable symbolTable, String struct, String method){

    }

    @Override
    public String obtainType(SymbolTable st, String struct, String method){
        return null;
    }

    @Override
    public String toJSON(String tabs){
        return tabs + "{\n" +
            tabs + "    \"tipo\": \"" + "ArrayAccess" + "\",\n" +
            tabs + "    \"lexema\": \""  + token.getLexema() + "\",\n" +
            tabs + "    \"índice\": " + indexArray.toJSON(tabs + "    ") + "\n" +
        tabs + "}";
    }
    
}
