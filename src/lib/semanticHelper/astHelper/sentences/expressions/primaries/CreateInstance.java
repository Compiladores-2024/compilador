package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import java.util.HashMap;

import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.tokenHelper.Token;

public class CreateInstance extends Primary{
    
    private HashMap<String,Expression> params;

    public CreateInstance (Token id, HashMap<String, Expression> params, Primary rightChained) {
        super(rightChained);
    }
}
