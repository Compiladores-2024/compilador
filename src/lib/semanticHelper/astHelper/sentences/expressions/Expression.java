package src.lib.semanticHelper.astHelper.sentences.expressions;

import src.lib.semanticHelper.astHelper.sentences.Sentence;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.Primary;
import src.lib.tokenHelper.Token;

public abstract class Expression extends Sentence{
    
    protected String resultType;
    protected Primary rightChained;
    protected int position;
    
    public Expression(Token token) {
        super(token);
        this.position = -1;
    }
    public Expression(Token token, int position){
        super(token);
        this.position = position;
    }

    public String getResultType() {
        return resultType;
    }

    public String getResultTypeChained() {
        if (this.rightChained!=null){
            return this.rightChained.getResultTypeChained();
        }
        
        return resultType;
    }

    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }
}
