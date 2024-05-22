package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

public class ArrayAccess extends Primary{
    
    private Expression indexArray;

    public ArrayAccess (Token identifier, Expression indexArray, Primary rightChained) {
        super(identifier, rightChained);
        this.indexArray = indexArray;
    }

    @Override
    public void consolidate(SymbolTable st, Struct struct, Method method, Primary leftExpression) {
        //Valida que la variable exista
        variableMethodExist(st, struct, method, leftExpression);

        //Consolida la expresion
        indexArray.consolidate(st, struct, method, null);

        //Valida que la expresion sea de tipo entero
        if (!indexArray.getResultTypeChained().contains("Int")) {
            throw new SemanticException(identifier, "El acceso a un array debe ser de tipo entero.", true);
        }
        
        //Valida si la variable es de tipo array
        if (resultType.contains("Array")) {
            setResultType(resultType.split("Array")[1].trim());
            
            //Si tiene encadenado, lo consolida
            if (rightChained != null) {
                rightChained.consolidate(st, struct, method, this);
            }
        } else {
            throw new SemanticException(identifier, ("La variable no es de tipo Array. Tipo detectado: " + resultType), true);
        }

    }

    @Override
    public String toJSON(String tabs){
        return tabs + "{\n" +
            tabs + "    \"tipo\": \"" + "ArrayAccess" + "\",\n" +
            tabs + "    \"nombreVariable\": \""  + identifier.getLexema() + "\",\n" +
            tabs + "    \"resultadoDeTipo\": \""  + resultType + "\",\n" +
            tabs + "    \"índice\": " + indexArray.toJSON(tabs + "    ") + "\n" +
        tabs + "}";
    }
    
}
