package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

public class SimpleAccess extends Primary{
    
    private Token value; 

    public SimpleAccess (Token value, Primary rightChained, String struct, String method) {
        super(rightChained, struct, method);
        this.value=value;
    }


    public String toJSON(String tabs){
        return tabs + "{\n" +
            tabs + "    \"struct\": \"" + this.getNameStruct() + "\",\n" +
            tabs + "    \"method\": \"" + this.getNameMethod() + "\",\n" +
            tabs + "    \"nombre\": \"" + "SimpleAccess" + "\",\n" +
            tabs + "    \"tipo\": \"" + value.getIDToken() + "\",\n" +
            tabs + ((value.getIDToken() == IDToken.constSTR) ? "    \"id\": " + value.getLexema() + "\n" :  "    \"id\": \"" + value.getLexema() + "\"\n") +
        tabs + "}";
    }
}
