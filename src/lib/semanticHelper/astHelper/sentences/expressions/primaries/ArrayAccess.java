package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.tokenHelper.Token;

public class ArrayAccess extends Primary{
    
    private Expression indexArray;

    public ArrayAccess (Token value, Expression indexArray, Primary rightChained){
        super(rightChained);
        this.indexArray = indexArray;
    }
}
