package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;


import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.tokenHelper.IDToken;

public class CreateArray extends Primary{
    
    private IDToken type;
    private Expression dimention;

    public CreateArray (IDToken type, Expression dimention, Primary rightChained, String struct, String method) {
        super(rightChained, struct, method);
        this.type = type;
        this.dimention = dimention;
    }
    public CreateArray (IDToken type, Expression dimention, Primary rightChained) {
        super(rightChained, "struct", "method");
        this.type = type;
        this.dimention = dimention;
    }

    @Override
    public String toJSON(String tabs){
        return "{\n" +
            tabs + "    \"tipo\": \"" + "CreateArray" + "\",\n" +
            tabs + "    \"tipoArray\": \""  + type.toString() + "\",\n" +
            tabs + "    \"dimension\": " + dimention.toJSON(tabs + "    ") + "\n" +
            tabs + "}";
    }
    
}
