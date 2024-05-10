package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import java.beans.Expression;
import java.util.HashMap;

import src.lib.semanticHelper.symbolTableHelper.Method;

public class MethodAccess extends Primary{
    
    private Method method;
    private HashMap<String,Expression> params;

    public MethodAccess (Primary rightChained) {
        super(rightChained);
    }
}
