package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import java.beans.Expression;
import java.util.HashMap;

public class CreateInstance extends Primary{
    
    private HashMap<String,Expression> params;

    public CreateInstance (Primary rightChained) {
        super(rightChained);
    }
}
