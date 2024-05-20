package src.lib.semanticHelper.astHelper.sentences;

import java.util.ArrayList;

import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.Primary;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

public class Block extends Sentence{
    private ArrayList<Sentence> sentenceList;

    public Block(Token token, ArrayList<Sentence> list){
        super(token);
        this.sentenceList = list;
    }

    @Override
    public void consolidate(SymbolTable st, Struct struct, Method method, Primary leftExpression) {
        //Consolida las sentencias
        for (Sentence sentence : sentenceList) {
            sentence.consolidate(st, struct, method, null);
        }
    }
    
    public String toJSON(String tabs){
        int count = sentenceList.size();
        String blocksString = "[" + (count > 0 ? "\n" : "]");
        if (count > 0) {
            for (Sentence sentence : sentenceList) {
                blocksString += sentence.toJSON(tabs + "    ") + ( count > 1 ? "," : "") + "\n";
                count--;
            }
            blocksString += tabs + "]";
        }
        return blocksString;
    }
}
