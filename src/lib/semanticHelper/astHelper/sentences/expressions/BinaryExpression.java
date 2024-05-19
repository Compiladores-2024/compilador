package src.lib.semanticHelper.astHelper.sentences.expressions;

import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.Primary;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
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
    public void consolidate(SymbolTable st, Struct struct, Method method, Primary leftExpression) {
        //Consolida expresion izquierda
        leftSide.consolidate(st, struct, method, leftExpression);

        //Consolida expresion derecha
        rightSide.consolidate(st, struct, method, leftExpression);

        //Valida que posean el mismo tipo de dato
    }

    @Override
    public IDToken obtainType(SymbolTable st, String struct, String method){
        return null;
    }

    public String toJSON(String tabs){
        return "{\n" +
            tabs + "    \"tipo\": \"" + "BinaryExpression" + "\",\n" +
            tabs + "    \"operador\": \"" + operator.toString() + "\",\n" +
            tabs + "    \"resultadoDeTipo\": \""  + resultType.getLexema() + "\",\n" +
            tabs + "    \"leftSide\": " + (leftSide == null ?  ("\"\""):  leftSide.toJSON(tabs + "    ")) + ",\n" +
            tabs + "    \"rightSide\": " + (rightSide == null ? ("\"\"")  : rightSide.toJSON(tabs + "    ")) + "\n" +
        tabs + "}";
    }

}
