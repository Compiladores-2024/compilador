package src.lib.semanticHelper.astHelper.sentences.expressions;

import src.lib.tokenHelper.IDToken;


public class BinaryExpression extends Expression{
    
    private Expression leftSide;
    private Expression rightSide;
    private IDToken operator;

    public BinaryExpression (Expression leftSide, IDToken operator, Expression rightSide, String struct, String method) {
        super(struct, method);
        this.leftSide = leftSide;
        this.operator = operator;
        this.rightSide = rightSide;
    }
    public BinaryExpression (Expression leftSide, IDToken operator, Expression rightSide) {
        super("struct", "method");
        this.leftSide = leftSide;
        this.operator = operator;
        this.rightSide = rightSide;
    }

    public String toJSON(String tabs){
        return "{\n" +
            tabs + "    \"tipo\": \"" + "BinaryExpression" + "\",\n" +
            tabs + "    \"operador\": \"" + operator.toString() + "\",\n" +
            tabs + "    \"leftSide\": " + (leftSide == null ?  ("\"\""):  leftSide.toJSON(tabs + "    ")) + ",\n" +
            tabs + "    \"rightSide\": " + (rightSide == null ? ("\"\"")  : rightSide.toJSON(tabs + "    ")) + "\n" +
        tabs + "}";
    }

}
