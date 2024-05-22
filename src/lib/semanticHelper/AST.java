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

    
    /** 
     * Agrega un bloque al AST.
     * @param currentStruct Estructura actual.
     * @param block Bloque a agregar.
     */
    public void addBlock(Struct currentStruct, SentenceBlock block){
        String structName = currentStruct != null ? currentStruct.getName() : "start";
        //Nombre del metodo actual e informacion del bloque
        if (this.blocks.get(structName) == null) {
            this.blocks.put(structName, new HashMap<String, SentenceBlock>());
        }
        this.blocks.get(structName).put((block.getIDBlock().equals(".") ? "Constructor" : block.getIDBlock()), block);
    }


    
    /** 
     * Método que consolida el arbol sintáctico abstracto.
     * 
     * @param symbolTable Tabla de símbolos.
     */
    public void consolidate(SymbolTable symbolTable){
        Struct currentStruct;
        //Recorre las estructuras
        for (String sStruct : blocks.keySet()) {
            //Recorre los métodos
            for (String sMethod : blocks.get(sStruct).keySet()) {
                //Consolida el bloque pasandole el contexto de su correspondiente estrucutra
                if (sStruct == "start") {
                    //Si es el metodo start, define como estructura a Object
                    currentStruct = symbolTable.getStruct("Object");
                } else {
                    currentStruct = symbolTable.getStruct(sStruct);
                }
                blocks.get(sStruct).get(sMethod).consolidate(symbolTable, currentStruct, (
                    currentStruct != null ? (
                        sStruct == "start" ? symbolTable.getStartMehod() : currentStruct.getMethod(sMethod)
                    ) : null
                ));
            }
        }
    }

    /**
     * Convierte los datos en JSON.
     * 
     * @since 22/04/2024
     * @return Estructura de datos en formato JSON
     */
    public String toJSON(String tabs) {
        String blocksJSON = "";
        SentenceBlock block;
        int countStructs = blocks.size(), countMethods;

        //Recorro las estructuras
        for (String sStruct : blocks.keySet()) {
            blocksJSON += tabs + (sStruct != "start" ? "\"bloquesDe" : "\"") + sStruct + "\" : [\n";

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
