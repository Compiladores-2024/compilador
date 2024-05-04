package src.lib.semanticHelper.astHelper;

import src.lib.semanticHelper.Metadata;
import src.lib.tokenHelper.Token;

public abstract class AST_Node extends Metadata {
    public AST_Node(Token token, int pos){
        super(token,pos);
    }

    public abstract void consolidate();
}
