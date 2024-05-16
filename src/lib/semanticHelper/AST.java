package src.lib.semanticHelper;

import java.util.HashMap;

import src.lib.semanticHelper.astHelper.SentenceBlock;
import src.lib.semanticHelper.symbolTableHelper.Struct;


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

    public void addBlock(Struct currentStruct, SentenceBlock block){
        String structName = currentStruct.getName();
        //Nombre del metodo actual e informacion del bloque
        if (this.blocks.get(structName) == null) {
            this.blocks.put(structName, new HashMap<String, SentenceBlock>());
        }
        this.blocks.get(structName).put((block.getIDBlock().equals(".") ? "Constructor" : block.getIDBlock()), block);
    }


    public void consolidate(){

    }

    public String toJSON(String tabs) {
        String blocksJSON = "";
        SentenceBlock block;
        int countStructs = blocks.size(), countMethods;

        //Recorro las estructuras
        for (String sStruct : blocks.keySet()) {
            blocksJSON += tabs + "\"bloquesDe" + sStruct + "\" : [\n";

            //Recorro los metodos de esa estructura
            countMethods = blocks.get(sStruct).size();
            for (String sMethod : blocks.get(sStruct).keySet()) {
                block = blocks.get(sStruct).get(sMethod);
                blocksJSON += tabs + "    {\n" +
                    tabs + "        \"nombreMetodo\": \"" + sMethod + "\",\n" +
                    tabs + "        \"sentencias\": [\n" + block.toJSON(tabs + "            ") +
                    tabs + "        ]\n" +
                    tabs + "    }" + (countMethods > 1 ? ",\n" : "\n");
                countMethods--;
            }

            blocksJSON += "    ]" + (countStructs > 1 ? "," : "") + "\n";
            --countStructs;
        }

        return "{\n" +
            blocksJSON +
        "\n}";
    }
}
