package src.lib.semanticHelper.astHelper.sentences;

import java.util.ArrayList;

import src.lib.semanticHelper.SymbolTable;

public class Block extends Sentence{
    private ArrayList<Sentence> sentenceList;

    public Block(ArrayList<Sentence> list){
        this.sentenceList = list;
    }

    @Override
    public void checkTypes(SymbolTable symbolTable, String struct, String method){
        for (Sentence sentence : sentenceList) {
            sentence.checkTypes(symbolTable, struct, method);
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
