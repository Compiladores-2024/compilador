package src.lib.semanticHelper;

import java.util.HashMap;
import java.util.Stack;

import src.lib.semanticHelper.astHelper.AST_Node;
import src.lib.semanticHelper.astHelper.StructNode;

/**
 * Esta clase se encarga de contener la estructura del AST (Árbol de sintaxis abstracta). 
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 04/05/2024
 */
public class AST {
    private HashMap<String,StructNode> structs;
    private Stack<AST_Node> scope;

    public AST(){
        this.structs = new HashMap<>();
        this.scope = new Stack<>();
    }

    public AST_Node popScope(){
        return this.scope.pop();
    }

    public void pushScope(AST_Node node){
        this.scope.push(node);
    }

    public AST_Node peekScope(){
        return this.scope.peek();
    }

    public String toJSON(String tabs){
        String json = "";

        return json;
    }
}
