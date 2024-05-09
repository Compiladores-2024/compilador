package src.lib.semanticHelper;

import java.util.HashMap;

import src.lib.semanticHelper.astHelper.SentenceBlockNode;


/**
 * Esta clase se encarga de contener la estructura del AST (Árbol de sintaxis abstracta). 
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 04/05/2024
 */
public class AST {
    private HashMap<String,SentenceBlockNode> blockHash;
    private SymbolTable st; 

    public AST(){
        this.blockHash = new HashMap<>();
    }

    public void addBlock(SentenceBlockNode block){
        // this.blockHash.put(st.getCurrentStructName()+st.getCurrentMethodName(), block);
    }

    public void consolidate(){

    }

    public String toJSON(String tabs){
        String json = "";

        return json;
    }
}
