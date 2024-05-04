package src.lib.semanticHelper.astHelper;

import java.util.LinkedList;

import src.lib.tokenHelper.Token;

public class IF_Node extends SentenceNode{
    private ExpressionNode condition;
    private LinkedList<SentenceNode> sentenceThen;
    private LinkedList<SentenceNode> sentenceElse;

    public IF_Node(Token token, int pos){
        super(token, pos);
        this.sentenceThen = new LinkedList<>();
        this.sentenceElse = new LinkedList<>();
    }

    public void setCondition(ExpressionNode con){
        this.condition=con;
    }

    /**
     * Reescritura del método, consolida cada nodo del ast
     * 
     * @since 04/05/2024
     * @return void
     */
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
