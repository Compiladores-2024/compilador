package src.lib.semanticHelper.astHelper.sentences;

import java.util.ArrayList;

public class Block extends Sentence{
    private ArrayList<Sentence> sentenceList;

    public Block(ArrayList<Sentence> list){
        super("struct", "method");
        this.sentenceList = list;
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
