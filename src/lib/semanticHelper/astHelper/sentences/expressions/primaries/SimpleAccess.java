package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

public class SimpleAccess extends Primary{
    
    private Token value; 

    public SimpleAccess (Token value, Primary rightChained, String struct, String method) {
        super(rightChained, struct, method);
        this.value=value;
    }
    public SimpleAccess (Token value, Primary rightChained) {
        super(rightChained, "struct", "method");
        this.value = value;
    }


    public String toJSON(String tabs){
        return "{\n" +
            tabs + "    \"tipo\": \"" + "SimpleAccess" + "\",\n" +
            tabs + "    \"lexema\": " + (value.getIDToken().equals(IDToken.constSTR) ? "" : "\"") + value.getLexema() + (value.getIDToken().equals(IDToken.constSTR) ? "" : "\"") + ",\n" +
            tabs + "    \"tipoDeDato\": \"" + value.getIDToken() + "\",\n" +
            tabs + "    \"encadenado\": "  + (rightChained == null ? ("\"\"")  : rightChained.toJSON(tabs + "    ")) + "\n" +
            tabs + "}";
    }
}
