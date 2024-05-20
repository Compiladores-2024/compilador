package src.lib.semanticHelper.astHelper.sentences;

import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.Primary;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

public class Loop extends Sentence{
    
    Expression condition;
    Sentence loopBlock;

    public Loop(Token token, Expression condition, Sentence loop) {
        super(token);
        this.condition = condition;
        this.loopBlock = loop;
    }

    @Override
    public void consolidate(SymbolTable st, Struct struct, Method method, Primary leftExpression) {
        //Consolida la condicion
        condition.consolidate(st, struct, method, null);

        //Consolida el bloque
        loopBlock.consolidate(st, struct, method, null);
    }
    
    @Override
    public String toJSON(String tabs){
        return "{\n" +
            tabs + "    \"tipo\": \"" + "Loop" + "\",\n" +
            tabs + "    \"condicion\": " + condition.toJSON(tabs + "    ") + ",\n" +
            tabs + "    \"bloqueLoop\": " +  loopBlock.toJSON(tabs + "    ") + "\n" +
            tabs + "}";
    }

}
