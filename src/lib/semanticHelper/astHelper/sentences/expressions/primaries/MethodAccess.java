package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import java.util.ArrayList;

import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.tokenHelper.Token;

public class MethodAccess extends Primary{
    
    private Token method;
    private ArrayList<Expression> params;

    public MethodAccess (Token value, ArrayList<Expression> params, Primary rightChained, String struct, String method) {
        super(rightChained, struct, method);
        this.params = params;
        this.method=value;
    }
    public MethodAccess (Token value, ArrayList<Expression> params, Primary rightChained) {
        super(rightChained, "struct", "method");
        this.params = params;
        this.method=value;
    }

    @Override
    public String toJSON(String tabs){
        int count = params.size();
        String paramsJSON = count > 0 ? "[\n" : "[";

        for (Expression expression : params) {
            paramsJSON += tabs + "        " + expression.toJSON(tabs + "        ") +
                (count > 1 ? "," : "") + "\n";
            count--;
        }

        paramsJSON += (params.size() > 0 ? (tabs + "    ]") : "]");

        return "{\n" +
            tabs + "    \"tipo\": \"" + "MethodAccess" + "\",\n" +
            tabs + "    \"método\": \"" + method.getLexema() +  "\",\n" +
            tabs + "    \"params\": " +  (paramsJSON == "" ? ("\"\"") : paramsJSON) + "\n" +
        tabs + "}";
    }
}
