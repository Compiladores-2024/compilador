package src.lib.semanticHelper.astHelper;

import java.util.ArrayList;

import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.Sentence;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

public class SentenceBlock {

    private Token idBlock;
    private ArrayList<Sentence> sentenceList;

    public SentenceBlock(Token idBlock, ArrayList<Sentence> list){
        this.sentenceList = list;
        this.idBlock = idBlock;
    }

    public String getIDBlock() {
        return idBlock.getLexema();
    }

    public void consolidate(SymbolTable st, Struct struct, Method method){
        for (Sentence sentence : sentenceList) {
            sentence.consolidate(st, struct, method, null);
        }
    }

    public String toJSON(String tabs){
        int count = sentenceList.size();
        String blocksString = count > 0 ? "" : (tabs + "{}\n");

        for (Sentence sentence : sentenceList) {
            blocksString += tabs + sentence.toJSON(tabs) + ( count > 1 ? "," : "") + "\n";
            count--;
        }

        return blocksString;
    }
}
