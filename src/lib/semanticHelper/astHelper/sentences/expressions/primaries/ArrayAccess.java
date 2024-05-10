package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;

public class ArrayAccess extends Primary{
    
    private Expression indexArray;

    public ArrayAccess (Primary rightChained){
        super(rightChained);
    }
}
