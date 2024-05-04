package src.lib.semanticHelper.astHelper;

import java.util.ArrayList;

import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

public class MethodNode extends AST_Node {
    
    private ArrayList<SentenceNode> blocks;
    private Method method;
    private Struct parent; 
    private ExpressionNode returnType;
    
    public MethodNode (Token token, int position){
        super(token, position);
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
