package src.lib.semanticHelper.astHelper.sentences;

import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

public class Conditional extends Sentence{
    
    private Expression condition;
    private Sentence thenBlock; 
    private Sentence elseBlock;


    public Conditional(Token token, Expression condition, Sentence thenBlock, Sentence elseBlock) {
        super(token);
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    public boolean isBool(IDToken conditionType){
        return (conditionType.equals(IDToken.typeBOOL)
        || conditionType.equals(IDToken.pTRUE)
        || conditionType.equals(IDToken.pFALSE) );
    }

    @Override
    public void checkTypes(SymbolTable st, String struct, String method){
        IDToken conditionType = this.condition.obtainType(st, struct, method);
        if (conditionType!=null){
            if (!isBool(conditionType)){
                throw new SemanticException(this.token, "El tipo de la condicion if no es booleano");
            }
        }
    }

    @Override
    public String toJSON(String tabs){
        return "{\n" +
            tabs + "    \"tipo\": \"" + "Conditional" + "\",\n" +
            tabs + "    \"condicion\": " + condition.toJSON(tabs + "    ") + ",\n" +
            tabs + "    \"thenBlock\": " + thenBlock.toJSON(tabs + "    ") + ",\n" +
            tabs + "    \"elseBlock\": " + (elseBlock != null ? elseBlock.toJSON(tabs + "    ") : "[]") + "\n" +
            tabs + "}";
    }
}
