package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;

public abstract class Primary extends Expression{

    public Primary(Primary rightChained) {
        this.rightChained = rightChained;
    }

}
