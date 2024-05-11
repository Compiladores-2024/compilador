package src.lib.semanticHelper.astHelper;

import java.util.ArrayList;

import src.lib.semanticHelper.astHelper.sentences.Sentence;

public class SentenceBlock {

    private ArrayList<Sentence> sentenceList;

    public SentenceBlock(ArrayList<Sentence> list){
        this.sentenceList=list;
    }


    public void addSentence(Sentence sentence){
        this.sentenceList.add(sentence);
    }

    public String toJSON(String string){
        String blocksString =""; 
        int count = sentenceList.size();
        for (Sentence sentence : sentenceList) {
            blocksString+=sentence.toJSON(string) + ( count > 1 ? "," : "") + "\n";
            count--;
        }
        return blocksString;
    }
}
