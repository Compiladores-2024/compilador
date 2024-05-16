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
    public UnaryExpression (IDToken operator, Expression expression) {
        super("struct", "method");
        this.operator = operator;
        this.expression = expression;
    }


    public String toJSON(String tabs){
        return "{\n" +
            tabs + "    \"tipo\": \"" + "UnaryExpression" + "\",\n" +
            tabs + "    \"operador\": \"" + operator.toString() + "\",\n" +
            tabs + "    \"expresion\": " + expression.toJSON(tabs + "    ") + "\n" +
        tabs + "}";
    }
}
