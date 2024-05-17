package src.lib.semanticHelper.astHelper.sentences;

import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.tokenHelper.Token;

public class Return extends Sentence{

    private Expression expression; 

    public Return(Token token, Expression expression) {
        super(token);
        this.expression = expression;
    }

    @Override
    public void checkTypes(SymbolTable symbolTable, String struct, String method){

    }

    public String toJSON(String tabs){
        return "{\n" +
            tabs + "    \"tipo\": \"" + "Return" + "\",\n" +
            tabs + "    \"expresión\": " + (expression != null ? expression.toJSON(tabs + "    ") : "\"\"") + "\n" +
            tabs + "}";
    }

}
