package src.lib.semanticHelper.astHelper.sentences;

import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import java.util.ArrayList;

public class Loop extends Sentence{
    
    Expression condition;
    ArrayList<Sentence> loopBlock;

    public Loop(Expression condition,ArrayList<Sentence>loop,  String struct, String method) {
        super(struct, method);
        this.condition=condition;
        this.loopBlock=loop;
    }

    @Override
    public String toJSON(String tabs){
        String loopJSON="";
        for (Sentence sentence : loopBlock) {
            loopJSON+= tabs +"      "+ sentence.toJSON(tabs) + "\n";
        }

        return tabs + "{\n" +
            tabs + "    \"nombre\": \"" + "Loop" + "\",\n" +
            tabs + "    \"struct\": \"" + this.getNameStruct() + "\",\n" +
            tabs + "    \"method\": \"" + this.getNameMethod() + "\",\n" +
            tabs + "    \"condition\": " + condition.toJSON(tabs) + ",\n" +
            tabs + "    \"loopBlock\": " +  (loopJSON=="" ? ("\"" + "\"") : loopJSON) + "\n" +
        tabs + "}";
    }

}
