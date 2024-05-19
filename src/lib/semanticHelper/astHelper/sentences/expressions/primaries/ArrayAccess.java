package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

public class ArrayAccess extends Primary{
    
    private Expression indexArray;

    public ArrayAccess (Token identifier, Expression indexArray, Primary rightChained) {
        super(identifier, rightChained);
        this.indexArray = indexArray;
    }

    @Override
    public void checkTypes(SymbolTable symbolTable, String struct, String method){

    }

    @Override
    public IDToken obtainType(SymbolTable st, String struct, String method){
        return null;
    }

    @Override
    public void consolidate(SymbolTable st, Struct struct, Method method, Primary leftExpression) {
        //Valida que la variable exista
        variableExist(st, struct, method, leftExpression);


        //Consolida la expresion
        indexArray.consolidate(st, struct, method, this);

        //Valida que la expresion sea de tipo entero
        if (!indexArray.getResultType().toString().contains("Int")) {
            throw new SemanticException(token, "El acceso a un array debe ser de tipo entero.", true);
        }
    }

    @Override
    public String toJSON(String tabs){
        return tabs + "{\n" +
            tabs + "    \"tipo\": \"" + "ArrayAccess" + "\",\n" +
            tabs + "    \"nombreVariable\": \""  + identifier.getLexema() + "\",\n" +
            tabs + "    \"resultadoDeTipo\": \""  + resultType.getLexema() + "\",\n" +
            tabs + "    \"índice\": " + indexArray.toJSON(tabs + "    ") + "\n" +
        tabs + "}";
    }
    
}
