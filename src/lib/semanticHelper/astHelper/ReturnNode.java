package src.lib.semanticHelper.astHelper;

import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

public class ReturnNode extends SentenceNode{
    

    public ReturnNode (Token token, Struct struct, Method method){
        super(token, struct,method);
    }
}
