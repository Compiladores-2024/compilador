package src.lib.semanticHelper.astHelper.sentences.expressions;

import src.lib.semanticHelper.astHelper.sentences.Sentence;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.Primary;
import src.lib.tokenHelper.IDToken;

public abstract class Expression extends Sentence{
    
    private IDToken resultType;
    protected Primary rightChained;


    public Expression(String struct, String method) {
        super(struct, method);
    }

    public void setChained(Primary primary){
        this.rightChained=primary;
    }
}
