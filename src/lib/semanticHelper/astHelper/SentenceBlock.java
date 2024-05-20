package src.lib.semanticHelper.astHelper;

import java.util.ArrayList;

import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.Return;
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
        if (sentenceList.size() > 0) {
            Sentence currentSentence, lastSentence = sentenceList.get(0);

            //Consolida la primer sentencia
            lastSentence.consolidate(st, struct, method, null);

            //Recorre las sentencias
            for (int i = 1; i < sentenceList.size(); i++) {
                currentSentence = sentenceList.get(i);
                
                //Consolida la sentencia actual
                currentSentence.consolidate(st, struct, method, null);
                
                //Si la sentencia anterior es un return y la actual tambien, retorna error
                if (lastSentence instanceof Return && currentSentence instanceof Return) {
                    throw new SemanticException(currentSentence.getIdentifier(), "Sentencia inalcanzable.", true);    
                }
    
                //Actualiza la ultima sentencia
                lastSentence = currentSentence;
            }
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
