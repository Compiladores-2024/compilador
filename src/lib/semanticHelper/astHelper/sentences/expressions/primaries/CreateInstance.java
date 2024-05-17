package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import java.util.ArrayList;

import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

public class CreateInstance extends Primary{
    
    private ArrayList<Expression> params;
    private Token identifier;

    public CreateInstance (Token id, ArrayList<Expression> params, Primary rightChained) {
        super(rightChained);
        this.identifier = id;
        this.params = params;
    }

    @Override
    public void checkTypes(SymbolTable symbolTable, String struct, String method){

    }

    @Override
    public IDToken obtainType(SymbolTable st, String struct, String method){
        return null;
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
            tabs + "    \"tipo\": \"" + "CreateInstance" + "\",\n" +
            tabs + "    \"identificador\": \"" + identifier.getLexema() +  "\",\n" +
            tabs + "    \"parámetros\": " +  (paramsJSON=="" ? ("\"\"") : paramsJSON) + "\n" +
        tabs + "}";
    }

}
