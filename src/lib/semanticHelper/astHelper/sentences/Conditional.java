package src.lib.semanticHelper.astHelper.sentences;

import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.astHelper.SentenceBlock;

public class Conditional extends Sentence{
    
    private Expression condition;
    private SentenceBlock thenBlock;
    private SentenceBlock elseBlock;


    public Conditional(String struct, String method) {
        super(struct, method);
    }

    @Override
    public String toJSON(String tabs){
        return "";

    }
}
