package src.lib.semanticHelper.astHelper.sentences;

import java.util.ArrayList;

import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.Primary;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

/**
 * Nodo que representa un bloque de condicional o bucle.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 17/05/2024
 */
public class Block extends Sentence{
    private ArrayList<Sentence> sentenceList;

    /**
     * Constructor de la clase.
     * @param token Identificador
     * @param list Lista de sentencias del bloque
     */
    public Block(Token token, ArrayList<Sentence> list){
        super(token);
        this.sentenceList = list;
    }

    
    /** 
     * Consolida la sentencia.
     * 
     * @param st Tabla de símbolos
     * @param struct Estructura actual
     * @param method Método actual
     * @param leftExpression Expresión previa
     */
    @Override
    public void consolidate(SymbolTable st, Struct struct, Method method, Primary leftExpression) {
        //Consolida las sentencias
        for (Sentence sentence : sentenceList) {
            if (sentence != null) {
                sentence.consolidate(st, struct, method, null);
            }
        }

        //Setea la tabla de simbolos
        setSymbolTable(st);
    }
    
    
    /** 
     * Convierte los datos en JSON.
     * 
     * @param tabs Cantidad de separaciones
     * @return String
     */
    public String toJSON(String tabs){
        int count = sentenceList.size();
        String blocksString = "[" + (count > 0 ? "\n" : "]");
        if (count > 0) {
            for (Sentence sentence : sentenceList) {
                if (sentence != null) {
                    blocksString += sentence.toJSON(tabs + "    ") + ( count > 1 ? "," : "") + "\n";
                }
                count--;
            }
            blocksString += tabs + "]";
        }
        return blocksString;
    }

    /**
     * Genera código intermedio para bloques
     * @param sStruct
     * @param sMethod
     * @return String
     */
    public String generateCode(String sStruct, String sMethod){
        String asm = "";

        for (Sentence sentence : sentenceList) {
            asm += sentence.generateCode(sStruct, sMethod);
        }
        return asm;
    }
}
