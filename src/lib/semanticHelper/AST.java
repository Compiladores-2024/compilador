package src.lib.semanticHelper;

import java.util.HashMap;
import java.util.Map;

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

    public void addBlock(String structName, HashMap<String, SentenceBlock> block){
        this.blocks.put(structName, block);
    }


    public void consolidate(){

    }

    public String toJSON(String tabs) {
        String blocksJSON = "";

        int countBlocks = blocks.size();
        for (Map.Entry<String, HashMap<String, SentenceBlock>> set : blocks.entrySet()) {

            
            String key= (set.getKey());
            blocksJSON+= tabs + "    \"Bloques de: " + key + "\" : [\n";
            for (HashMap<String, SentenceBlock> block : blocks.values()) {
                    

                int count = block.size();
                for (SentenceBlock sentenceb : block.values()) {
                    
                    if(sentenceb.getStructName()==key){

                        blocksJSON += tabs + sentenceb.toJSON(tabs) + ( count > 1 ? "," : "") + "\n";
                    }
                    count--;
                    
                }
                
            }

            blocksJSON+="    ]" +  ( countBlocks > 1 ? "," : "") + "\n";
            --countBlocks;
        }

        return "{\n" +
            blocksJSON +
        "\n}";
    }
}
