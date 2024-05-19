package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;


import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

public class CreateArray extends Primary{
    
    private Token type;
    private Expression dimention;

    public CreateArray (Token type, Expression dimention, Primary rightChained) {
        super(type, rightChained);
        this.type = type;
        this.dimention = dimention;
    }

    @Override
    public void checkTypes(SymbolTable symbolTable, String struct, String method){

    }

    @Override
    public IDToken obtainType(SymbolTable st, String struct, String method){
        return null;
    }

    @Override
    public void consolidate(SymbolTable st, Struct struct, Method method, Primary leftExpression) {
        //Consolida la expresion de la dimesion
        dimention.consolidate(st, struct, method, this);

        //Valida que la dimensión sea de tipo entero
        if (!dimention.getResultType().toString().contains("Int")) {
            throw new SemanticException(token, "La dimensión debe ser de tipo entero.", true);
        }
    }

    @Override
    public String toJSON(String tabs){
        return "{\n" +
            tabs + "    \"tipo\": \"" + "CreateArray" + "\",\n" +
            tabs + "    \"tipoArray\": \""  + type.getLexema() + "\",\n" +
            tabs + "    \"resultadoDeTipo\": \""  + resultType.getLexema() + "\",\n" +
            tabs + "    \"dimension\": " + dimention.toJSON(tabs + "    ") + "\n" +
            tabs + "}";
    }
    
}
