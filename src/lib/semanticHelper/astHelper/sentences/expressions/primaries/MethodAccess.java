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

    @Override
    public String toJSON(String tabs){
        String paramsJSON="[";
        int count = params.size();
        for (Expression expression : params) {
            paramsJSON += tabs +"      "+ expression.toJSON(tabs) + (count > 1 ? ",\n" : "\n" );
            count--;
        }
        paramsJSON+=tabs + "]";

        return tabs + "{\n" +
            tabs + "    \"nombre\": \"" + "MethodAccess" + "\",\n" +
            tabs + "    \"struct\": \"" + this.getNameStruct() + "\",\n" +
            tabs + "    \"method\": \"" + this.getNameMethod() + "\",\n" +
            tabs + "    \"MetodoAcceso\": \"" + method.getLexema() +  "\",\n" +
            tabs + "    \"params\": " +  (paramsJSON == "" ? ("\"\"") : paramsJSON) + "\n" +
        tabs + "}";
    }
}
