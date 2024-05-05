package src.lib.semanticHelper.astHelper;

import java.util.ArrayList;

import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

public class WhileNode extends SentenceNode{
    
    ExpressionNode condition;
    ArrayList<SentenceNode> sentenceLoop;

    public WhileNode (Token token, Struct struct, Method method){
        super(token, struct,method);
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
