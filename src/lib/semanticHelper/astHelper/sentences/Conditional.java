package src.lib.semanticHelper.astHelper.sentences;

import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.astHelper.SentenceBlock;

public class Conditional extends Sentence{
    
    private Expression condition;
    private SentenceBlock thenBlock;
    private SentenceBlock elseBlock;
}
