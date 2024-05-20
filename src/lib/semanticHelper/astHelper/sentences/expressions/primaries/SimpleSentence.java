package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;


import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

public class SimpleSentence extends Primary{
    private Expression exp;

    public SimpleSentence(Token identifier, Expression exp, Primary rightChained) {
        super(identifier, rightChained);
        this.exp = exp;
    }
    
    @Override
    public void consolidate(SymbolTable st, Struct struct, Method method, Primary leftExpression) {
        exp.consolidate(st, struct, method, leftExpression);
        setResultType(exp.getResultType());
    }

    @Override
    public String toJSON(String tabs) {
        return exp.toJSON(tabs);
    }
}