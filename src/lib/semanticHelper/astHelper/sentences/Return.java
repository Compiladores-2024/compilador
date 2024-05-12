package src.lib.semanticHelper.astHelper.sentences;

import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;

public class Return extends Sentence{

    private Expression expression; 

    public Return(Expression expression, String struct, String method) {
        super(struct, method);
        this.expression=expression;
    }

    public String toJSON(String tabs){
        return tabs + "{\n" +
            tabs + "    \"nombre\": \"" + "Return" + "\",\n" +
            tabs + "    \"struct\": \"" + this.getNameStruct() + "\",\n" +
            tabs + "    \"method\": \"" + this.getNameMethod() + "\",\n" +
            tabs + "    \"expressionReturn\": " + (expression == null ? ("\"\"") : expression.toJSON(tabs)) + "\n" +
        tabs + "}";
    }

}
