package src.lib.semanticHelper.astHelper.sentences.expressions;

import src.lib.semanticHelper.SymbolTable;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;


public class BinaryExpression extends Expression{
    
    private Expression leftSide;
    private Expression rightSide;
    private IDToken operator;

    public BinaryExpression (Token token, Expression leftSide, IDToken operator, Expression rightSide) {
        super(token);
        this.leftSide = leftSide;
        this.operator = operator;
        this.rightSide = rightSide;
    }
    
    @Override
    public void checkTypes(SymbolTable symbolTable, String struct, String method){

    }

    @Override
    public String obtainType(SymbolTable st, String struct, String method){
        return null;
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
