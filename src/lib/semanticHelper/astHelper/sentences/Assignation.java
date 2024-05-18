package src.lib.semanticHelper.astHelper.sentences;

import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.Primary;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

public class Assignation extends Sentence{
    
    private Primary leftSide;
    private Expression rightSide;

    public Assignation (Token token, Primary leftSide, Expression rightSide) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.token=token;
    }

    @Override
    public void checkTypes(SymbolTable symbolTable, String struct, String method){

    }

    @Override
    public void consolidate(SymbolTable st, Struct struct, Method method, Primary leftExpression) {
        String leftType, rightType;
        //Consolida el lado izquierdo
        leftSide.consolidate(st, struct, method, leftExpression);

        //Consolida el lado derecho
        rightSide.consolidate(st, struct, method, leftExpression);

        //Valida que posean el mismo tipo de dato
        leftType = leftSide.getResultType();
        rightType = rightSide.getResultType();
        if (!leftType.equals(rightType)) {
            throw new SemanticException(token, "Se esperaba una variable de tipo " + leftType + " y se encontro una de tipo " + rightType + ".", true);
        }
    }

    @Override
    public String toJSON(String tabs){
        return "{\n" +
            tabs + "    \"tipo\": \"" + "Asignation" + "\",\n" +
            tabs + "    \"leftSide\": " + leftSide.toJSON(tabs + "    ") + ",\n" +
            tabs + "    \"rightSide\": " + rightSide.toJSON(tabs + "    ") + "\n" +
        tabs + "}";
    }
}
