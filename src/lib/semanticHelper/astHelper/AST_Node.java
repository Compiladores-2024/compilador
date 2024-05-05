package src.lib.semanticHelper.astHelper;

import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

public abstract class AST_Node  {
    Boolean isConsolidated;
    Struct struct;
    Method method;
    Token token; 
    public AST_Node(Token token, Struct struct, Method method){
        this.token=token;
        this.struct=struct;
        this.method=method;
    }

    public abstract void consolidate();

    public abstract String toJSON(String tabs);

}
