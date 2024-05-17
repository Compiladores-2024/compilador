package src.lib.semanticHelper.astHelper.sentences;

import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.Primary;
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
    public String toJSON(String tabs){
        return "{\n" +
            tabs + "    \"tipo\": \"" + "Asignation" + "\",\n" +
            tabs + "    \"leftSide\": " + leftSide.toJSON(tabs + "    ") + ",\n" +
            tabs + "    \"rightSide\": " + rightSide.toJSON(tabs + "    ") + "\n" +
        tabs + "}";
    }
}
