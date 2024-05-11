package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.symbolTableHelper.Struct;

public abstract class Primary extends Expression{
    
    private Struct referredStruct; 
    protected Primary rightChained;

    public Primary(Primary rightChained, String struct, String method) {
        super(struct, method);
        this.rightChained = rightChained;
    }

    public void setChained(Primary primary){
        this.rightChained=primary;
    }
}