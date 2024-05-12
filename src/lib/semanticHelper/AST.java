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

    public void addBlock(String structName, HashMap<String, SentenceBlock> block){
        this.blocks.put(structName, block);
    }


    public void consolidate(){

    }

    public String toJSON(String tabs) {
        String blocksJSON = "";
        int countBlocks = blocks.size(), count = 0;

        //Recorro las estructuras
        for (String sStruct : blocks.keySet()) {
            blocksJSON += tabs + "    \"Bloques de: " + sStruct + "\" : [\n";

            //Recorro los bloques de esa estructura
            count = blocks.get(sStruct).values().size();
            for (SentenceBlock oBlock : blocks.get(sStruct).values()) {
                blocksJSON += tabs + oBlock.toJSON(tabs) + ( count > 1 ? "," : "") + "\n";
                count--;
            }

            blocksJSON += "    ]" + (countBlocks > 1 ? "," : "") + "\n";
            --countBlocks;
        }

        return "{\n" +
            blocksJSON +
        "\n}";
    }
}
