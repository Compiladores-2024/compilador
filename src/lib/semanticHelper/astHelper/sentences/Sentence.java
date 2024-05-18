package src.lib.semanticHelper.astHelper.sentences;

import src.lib.semanticHelper.SymbolTable;
import src.lib.tokenHelper.Token;

public abstract class Sentence {
    
    protected Token token;

    public Sentence(Token token){
        this.token = token;
    }

    public Sentence(){}

    public String getLexema(){
        return this.token.getLexema();
    }

    public abstract String toJSON(String tabs);

    public abstract void checkTypes(SymbolTable symbolTable, String struct, String method);
}
