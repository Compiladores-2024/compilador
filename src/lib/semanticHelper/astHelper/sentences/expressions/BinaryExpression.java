package src.lib.semanticHelper.astHelper.sentences.expressions;

import src.lib.exceptionHelper.SemanticException;
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
    public void consolidate(SymbolTable st, Struct struct, Method method, Primary leftExpression) {
        String leftType, rightType;
        //Consolida expresion izquierda
        leftSide.consolidate(st, struct, method, leftExpression);

        //Consolida expresion derecha
        rightSide.consolidate(st, struct, method, leftExpression);

        //Valida que posean el mismo tipo de dato
        leftType = leftSide.getResultType();
        rightType = rightSide.getResultType();

        //Si los operadores no son iguales
        if (!leftType.equals(rightType)){

            //Si el lado derecho es nil
            if (rightType.equals("NIL")) {
                //El lado izquierdo no debe ser tipo primitivo
                if (primitiveTypes.contains(leftType)) {
                    throw new SemanticException(identifier, "Se esperaba un tipo de dato " + leftType + ". Se encontró " + rightType, true);
                }
            }
            else {
                throw new SemanticException(identifier, "Se esperaba un tipo de dato " + leftType + ". Se encontró " + rightType, true);
            }
        }

        setResultType(getType(leftSide.getResultType()));
    }

    private String getType(String result) {
        switch (operator) {
            case oAND:
            case oOR:
            case oMIN:
            case oMIN_EQ:
            case oMAX:
            case oMAX_EQ:
            case oNOT_EQ:
            case oEQUAL:
                result = "Bool";
                break;
            default:
                break;
        }
        return result;
    }

    public String toJSON(String tabs){
        return "{\n" +
            tabs + "    \"tipo\": \"" + "BinaryExpression" + "\",\n" +
            tabs + "    \"operador\": \"" + operator.toString() + "\",\n" +
            tabs + "    \"resultadoDeTipo\": \""  + resultType + "\",\n" +
            tabs + "    \"leftSide\": " + (leftSide == null ?  ("\"\""):  leftSide.toJSON(tabs + "    ")) + ",\n" +
            tabs + "    \"rightSide\": " + (rightSide == null ? ("\"\"")  : rightSide.toJSON(tabs + "    ")) + "\n" +
        tabs + "}";
    }

}
