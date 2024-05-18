package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;


import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

public class SimpleSentence extends Primary{
    private Expression exp;

    public SimpleSentence(Token identifier, Expression exp, Primary rightChained) {
        super(identifier, rightChained);
        this.exp = exp;
    }


    @Override
    public IDToken obtainType(SymbolTable st, String struct, String method) {
        return null;
    }

    
    @Override
    public void checkTypes(SymbolTable symbolTable, String struct, String method) {
    }
    
    @Override
    public void consolidate(SymbolTable st, Struct struct, Method method, Primary leftExpression) {
        
    }

    @Override
    public String toJSON(String tabs) {
        return exp.toJSON(tabs);
    }
}