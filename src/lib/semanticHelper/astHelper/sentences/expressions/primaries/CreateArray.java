package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;


import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

public class CreateArray extends Primary{
    
    private IDToken type;
    private Expression dimention;

    public CreateArray (Token value, IDToken type, Expression dimention){
        super(null);
        this.type = type;
        this.dimention = dimention;
    }
}
