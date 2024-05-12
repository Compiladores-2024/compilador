package src.lib.semanticHelper.astHelper.sentences;

import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.Primary;

public class Assignation extends Sentence{
    
    private Primary leftSide;
    private Expression rightSide;

    public Assignation (Primary leftSide, Expression rightSide, String struct, String method) {
        super(struct, method);
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    @Override
    public String toJSON(String tabs){
        return tabs + "{\n" +
            tabs + "    \"nombre\": \"" + "Asignation" + "\",\n" +
            tabs + "    \"struct\": \"" + this.getNameStruct() + "\",\n" +
            tabs + "    \"method\": \"" + this.getNameMethod() + "\",\n" +
            tabs + "    \"leftSide\": " + leftSide.toJSON(tabs) + ",\n" +
            tabs + "    \"rightSide\": " + rightSide.toJSON(tabs) + "\n" +
        tabs + "}";
    }
}
