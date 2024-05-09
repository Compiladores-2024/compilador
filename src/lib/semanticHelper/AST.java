package src.lib.semanticHelper;

import java.util.HashMap;

import src.lib.semanticHelper.astHelper.SentenceBlock;


/**
 * Esta clase se encarga de contener la estructura del AST (Árbol de sintaxis abstracta). 
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 04/05/2024
 */
public class AST {
    private HashMap<String, HashMap<String, SentenceBlock>> blocks;

    public AST(){
        this.blocks = new HashMap<String, HashMap<String, SentenceBlock>>();
    }


    public void consolidate(){

    }

    public String toJSON(String tabs){
        String json = "";

        return json;
    }
}
