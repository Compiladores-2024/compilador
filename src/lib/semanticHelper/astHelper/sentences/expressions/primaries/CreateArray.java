package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import java.beans.Expression;

import src.lib.tokenHelper.IDToken;

public class CreateArray extends Primary{
    
    private IDToken type;
    private Expression dimention;

    public CreateArray (Primary rightChained){
        super(rightChained);
    }
}
