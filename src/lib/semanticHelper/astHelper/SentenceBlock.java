package src.lib.semanticHelper.astHelper;

import java.util.ArrayList;

import src.lib.semanticHelper.astHelper.sentences.Sentence;

public class SentenceBlock {

    private ArrayList<Sentence> sentenceList;

    public void addSentence(Sentence sentence){
        this.sentenceList.add(sentence);
    }
}
