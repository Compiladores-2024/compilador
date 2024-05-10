package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import src.lib.tokenHelper.Token;

public class SimpleAccess extends Primary{
    
    private Token value; 

    public SimpleAccess (Token value, Primary rightChained) {
        super(rightChained);
    }
}
