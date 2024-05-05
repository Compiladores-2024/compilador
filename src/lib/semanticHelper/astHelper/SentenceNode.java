package src.lib.semanticHelper.astHelper;

import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

public abstract class SentenceNode extends AST_Node{
    
    public SentenceNode (Token token, Struct struct, Method method){
        super(token, struct,method);
    }


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
