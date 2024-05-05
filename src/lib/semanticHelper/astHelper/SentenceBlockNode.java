package src.lib.semanticHelper.astHelper;

import java.util.ArrayList;

import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

public class SentenceBlockNode extends SentenceNode{
    
    private ArrayList<SentenceNode> sentenceList; 

    public SentenceBlockNode (Token token, Struct struct, Method method, ArrayList<SentenceNode> list){
        super(token, struct,method);
        this.sentenceList=list;
    }


    @Override
    public void consolidate(){

    }
    /**
     * Reescritura del método, convierte los datos en JSON.
     * 
     * @since 04/05/2024
     * @return Estructura de datos en formato JSON
     */
    @Override
    public String toJSON(String tabs) {
        return "";
    }
}
