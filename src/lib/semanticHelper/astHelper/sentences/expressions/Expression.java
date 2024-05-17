package src.lib.semanticHelper.astHelper.sentences.expressions;

import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.Sentence;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

public abstract class Expression extends Sentence{
    
    private IDToken resultType;
    protected Expression rightChained;


    public Expression(Token token) {
        super(token);
    }
    public Expression(){
        
    }

    public void setChained(Expression primary){
        this.rightChained = primary;
    }

    public IDToken getResultType() {
        return resultType;
    }

    public abstract IDToken obtainType(SymbolTable st, String struct, String method);
}
