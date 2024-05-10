package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import java.beans.Expression;
import java.util.HashMap;

import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.tokenHelper.Token;

public class MethodAccess extends Primary{
    
    private Method method;
    private HashMap<String, Expression> params;

    public MethodAccess (Token value, HashMap<String,Expression> params, Primary rightChained) {
        super(rightChained);
        this.params = params;
    }
}
