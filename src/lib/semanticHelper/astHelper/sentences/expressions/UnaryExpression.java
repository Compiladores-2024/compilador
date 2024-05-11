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
    
    @Override
    public String toJSON(String tabs){
        return "";

    }
}
