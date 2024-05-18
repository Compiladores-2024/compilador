package src.lib.semanticHelper.astHelper.sentences;

import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.Primary;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

public abstract class Sentence {
    
    protected Token token;

    public Sentence(Token token){
        this.token = token;
    }

    public Sentence(){}

    public abstract String toJSON(String tabs);
    
    public abstract void checkTypes(SymbolTable symbolTable, String struct, String method);

    public abstract void consolidate(SymbolTable st, Struct struct, Method method, Primary leftExpression);
    
}
