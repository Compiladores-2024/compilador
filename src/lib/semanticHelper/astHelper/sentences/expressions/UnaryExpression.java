package src.lib.semanticHelper.astHelper.sentences.expressions;

import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.Primary;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

public class UnaryExpression extends Expression{
    
    private Expression expression;
    private IDToken operator;

    public UnaryExpression (Token token, IDToken operator, Expression expression) {
        super(token);
        this.operator = operator;
        this.expression = expression;
    }


    @Override
    public void consolidate(SymbolTable st, Struct struct, Method method, Primary leftExpression) {
        //Consolida la expresion
        expression.consolidate(st, struct, method, leftExpression);

        //Valida que la expresion sea del tipo de dato correcto
        checkType();

        setResultType(expression.getResultTypeChained());
    }

    private void checkType () {
        switch (expression.getResultTypeChained()) {
            case "Int":
                if (operator.equals(IDToken.oNOT)) {
                    throw new SemanticException(identifier, "Se esperaba un tipo de dato booleano", true);
                }
                break;
            case "Bool":
                if (!operator.equals(IDToken.oNOT)) {
                    throw new SemanticException(identifier, "Se esperaba un tipo de dato entero", true);
                }
                break;
            case "Char":
            case "Str":
            default:
                throw new SemanticException(identifier, "Se esperaba un tipo de dato entero o booleano", true);
        }
    }

    public String toJSON(String tabs){
        return "{\n" +
            tabs + "    \"tipo\": \"" + "UnaryExpression" + "\",\n" +
            tabs + "    \"operador\": \"" + operator.toString() + "\",\n" +
            tabs + "    \"resultadoDeTipo\": \""  + resultType + "\",\n" +
            tabs + "    \"expresion\": " + expression.toJSON(tabs + "    ") + "\n" +
        tabs + "}";
    }
}
