package src.lib.semanticHelper.astHelper;

import src.lib.tokenHelper.Token;

public class ExpressionUnaryNode extends ExpressionNode{
    ExpressionNode rightSide;

    public ExpressionUnaryNode (Token token, int position){
        super(token, position);
    }

    public void setRightSide(ExpressionNode exp){
        this.rightSide=exp;
    }
}
