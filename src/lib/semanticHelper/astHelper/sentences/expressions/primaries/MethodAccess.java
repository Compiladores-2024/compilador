package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import java.util.ArrayList;
import java.util.HashMap;

import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.tokenHelper.Token;

public class MethodAccess extends Primary{
    
    private Method method;
    private ArrayList<Expression> params;

    public MethodAccess (Token value, ArrayList<Expression> params, Primary rightChained, String struct, String method) {
        super(rightChained, struct, method);
        this.params = params;
    }

    @Override
    public String toJSON(String tabs){
        return "";

    }

}
