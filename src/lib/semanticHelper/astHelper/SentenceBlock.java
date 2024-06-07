package src.lib.semanticHelper.astHelper;

import java.util.ArrayList;

import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.Return;
import src.lib.semanticHelper.astHelper.sentences.Sentence;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

/**
 * Nodo que representa el bloque de sentencias
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 17/05/2024
 */
public class SentenceBlock {

    private Token idBlock;
    private ArrayList<Sentence> sentenceList;

    /**
     * Constructor de la clase.
     * @param idBlock ID del bloque
     * @param list Lista de sentencias pertenecientes
     */
    public SentenceBlock(Token idBlock, ArrayList<Sentence> list){
        this.sentenceList = list;
        this.idBlock = idBlock;
    }

    
    /** 
     * Obtiene el id de bloque.
     * 
     * @return ID del bloque
     */
    public String getIDBlock() {
        return idBlock.getLexema();
    }

    
    /** 
     * Consolida el bloque de sentencia.
     * 
     * @param st Tabla de símbolos
     * @param struct Estructura actual
     * @param method Método actual
     */
    public void consolidate(SymbolTable st, Struct struct, Method method){
        boolean hasReturn = false;
        if (sentenceList.size() > 0) {
            Sentence currentSentence, lastSentence = sentenceList.get(0);

            //Consolida la primer sentencia
            lastSentence.consolidate(st, struct, method, null);

            //Recorre las sentencias
            for (int i = 1; i < sentenceList.size(); i++) {
                currentSentence = sentenceList.get(i);
                
                //Consolida la sentencia actual
                currentSentence.consolidate(st, struct, method, null);
                
                //Valida si la expresion es de tipo return
                hasReturn = hasReturn || lastSentence.getIdentifier().getIDToken().equals(IDToken.pRET);

                //Si la sentencia anterior es un return y la actual tambien, retorna error
                if (currentSentence instanceof Return && lastSentence instanceof Return) {
                    throw new SemanticException(currentSentence.getIdentifier(), "Sentencia inalcanzable.", true);    
                }
    
                //Actualiza la ultima sentencia
                lastSentence = currentSentence;
            }

            //Valida si la expresion es de tipo return
            hasReturn = hasReturn || lastSentence.getIdentifier().getIDToken().equals(IDToken.pRET);
        }

        //Valida si no posee return
        if (!hasReturn) {
            //Si el metodo no es de tipo void, retorna error
            if (!method.getReturnType().equals("void")) {
                throw new SemanticException(method.getMetadata(), "Falta sentencia return.", true);
            } else {
                //Se inserta sentencia return, para eliminar el RA del stack
                sentenceList.add(new Return(idBlock, null));
            }
        }
    }

    public String generateCode (String sStruct, String sMethod) {
        String code = "";
        //Genera el codigo de las sentencias
        for (Sentence sentence : sentenceList) {
            code += sentence.generateCode(sStruct, sMethod);
        }
        return code;
    }
    
    /** 
     * Convierte los datos en JSON.
     * 
     * @param tabs Cantidad de separaciones
     * @return String
     */
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
