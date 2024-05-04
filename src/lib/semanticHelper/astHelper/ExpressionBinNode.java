package src.lib.semanticHelper.astHelper;

import src.lib.tokenHelper.Token;

public class ExpressionBinNode extends ExpressionNode{
    

    private ExpressionNode leftSide;
    private ExpressionNode rightSide;
    // private Token operator;

    public ExpressionBinNode (Token token, int position){
        super(token, position);
    }
}
