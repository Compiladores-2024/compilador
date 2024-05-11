package src.lib.semanticHelper.astHelper.sentences.expressions;

import src.lib.semanticHelper.astHelper.sentences.Sentence;
import src.lib.tokenHelper.IDToken;

public abstract class Expression extends Sentence{
    
    private IDToken resultType;

    public Expression(String struct, String method) {
        super(struct, method);
    }
}
