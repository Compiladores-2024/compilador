package src.lib.semanticHelper.astHelper;

import java.util.HashMap;

import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

public class StructNode extends AST_Node{
    
    private Struct struct;
    private MethodNode constructor;
    private HashMap<String, MethodNode> methodsNode;

    public StructNode(Token token, int pos){
        super(token,pos);
        this.methodsNode=new HashMap<>();
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
