package src.lib.semanticHelper.astHelper.sentences;

import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;

public class Loop extends Sentence{
    
    Expression condition;
    Sentence loopBlock;

    public Loop(Expression condition, Sentence loop) {
        super("struct", "method");
        this.condition = condition;
        this.loopBlock = loop;
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
