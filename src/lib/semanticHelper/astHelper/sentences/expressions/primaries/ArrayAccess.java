package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.tokenHelper.Token;

public class ArrayAccess extends Primary{
    
    private Expression indexArray;
    private Token value;

    public ArrayAccess (Token value, Expression indexArray, Primary rightChained, String struct, String method) {
        super(rightChained, struct, method);
        this.indexArray = indexArray;
        this.value=value;
    }

    @Override
    public String toJSON(String tabs){
        return "";

    }
    
}
