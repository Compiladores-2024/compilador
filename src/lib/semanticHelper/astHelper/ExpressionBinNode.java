package src.lib.semanticHelper.astHelper;

import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

public class ExpressionBinNode extends ExpressionNode{
    

    private ExpressionNode leftSide;
    private ExpressionNode rightSide;
    // private Token operator;

    public ExpressionBinNode (Token token, Struct struct, Method method){
        super(token, struct,method);
    }

    public void setRightSide(ExpressionNode exp){
        this.rightSide=exp;
    }
}
