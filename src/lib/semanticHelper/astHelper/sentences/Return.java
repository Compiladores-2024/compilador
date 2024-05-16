package src.lib.semanticHelper.astHelper.sentences;

import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;

public class Return extends Sentence{

    private Expression expression; 

    public Return(Expression expression, String struct, String method) {
        super(struct, method);
        this.expression=expression;
    }
    public Return(Expression expression) {
        super("struct", "method");
        this.expression = expression;
    }

    public String toJSON(String tabs){
        return "{\n" +
            tabs + "    \"tipo\": \"" + "Return" + "\",\n" +
            tabs + "    \"expresión\": " + (expression != null ? expression.toJSON(tabs + "    ") : "\"\"") + "\n" +
            tabs + "}";
    }

}
