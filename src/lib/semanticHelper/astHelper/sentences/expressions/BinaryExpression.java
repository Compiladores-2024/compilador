package src.lib.semanticHelper.astHelper.sentences.expressions;

import src.lib.tokenHelper.Token;

public class BinaryExpression extends Expression{
    
    private Expression leftSide;
    private Expression rightSide;
    private Token operator;

    public BinaryExpression (Expression leftSide, Token operator, Expression rightSide) {
        this.leftSide = leftSide;
        this.operator = operator;
        this.rightSide = rightSide;
    }

}
