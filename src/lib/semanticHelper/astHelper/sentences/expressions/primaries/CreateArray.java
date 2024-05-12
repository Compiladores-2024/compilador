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

    @Override
    public String toJSON(String tabs){
        return tabs + "{\n" +
            tabs + "    \"nombre\": \"" + "CreateArray" + "\",\n" +
            tabs + "    \"struct\": \"" + this.getNameStruct() + "\",\n" +
            tabs + "    \"method\": \"" + this.getNameMethod() + "\",\n" +
            tabs + "    \"typeArray\": \""  + type.toString() + "\",\n" +
            tabs + "    \"dimention\": " + dimention.toJSON(tabs) + "\n" +
        tabs + "}";
    }
    
}
