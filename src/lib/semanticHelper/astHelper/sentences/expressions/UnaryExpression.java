package src.lib.semanticHelper.astHelper.sentences.expressions;

import src.lib.tokenHelper.IDToken;

public class UnaryExpression extends Expression{
    
    private Expression expression;
    private IDToken operator;

    public UnaryExpression (IDToken operator, Expression expression, String struct, String method) {
        super(struct, method);
        this.operator = operator;
        this.expression = expression;
    }


    public String toJSON(String tabs){
        return tabs + "{\n" +
            tabs + "    \"struct\": \"" + this.getNameStruct() + "\",\n" +
            tabs + "    \"method\": \"" + this.getNameMethod() + "\",\n" +
            tabs + "    \"nombre\": \"" + "UnaryExpression" + "\",\n" +
            tabs + "    \"operator\": \"" + operator.toString() + "\",\n" +
            tabs + "    \"expresion\": " + expression.toJSON(tabs) + "\n" +
        tabs + "}";
    }
}
