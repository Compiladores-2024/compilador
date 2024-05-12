package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import java.util.ArrayList;

import src.lib.semanticHelper.astHelper.sentences.Sentence;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.tokenHelper.Token;

public class CreateInstance extends Primary{
    
    private ArrayList<Expression> params;
    private Token identifier;

    public CreateInstance (Token id, ArrayList<Expression> params, Primary rightChained, String struct, String method) {
        super(rightChained, struct, method);
        this.identifier=id;
        this.params=params;

    }

    @Override
    public String toJSON(String tabs){
        String paramsJSON="";
        for (Expression expression : params) {
            paramsJSON+= tabs +"      "+ expression.toJSON(tabs) + "\n";
        }

        return tabs + "{\n" +
            tabs + "    \"nombre\": \"" + "CreateInstance" + "\",\n" +
            tabs + "    \"struct\": \"" + this.getNameStruct() + "\",\n" +
            tabs + "    \"method\": \"" + this.getNameMethod() + "\",\n" +
            tabs + "    \"identificador\": \"" + identifier.getLexema() +  "\",\n" +
            tabs + "    \"params\": " +  (paramsJSON=="" ? ("\"" + "\"") : paramsJSON) + "\n" +
        tabs + "}";
    }

}
