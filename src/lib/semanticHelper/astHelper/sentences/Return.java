package src.lib.semanticHelper.astHelper.sentences;

import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;

public class Return extends Sentence{

    private Expression expression; 

    public Return(String struct, String method) {
        super(struct, method);
    }

    @Override
    public String toJSON(String tabs){
        return "";

    }

}
