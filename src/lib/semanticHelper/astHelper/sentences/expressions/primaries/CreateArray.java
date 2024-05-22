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
    public void consolidate(SymbolTable st, Struct struct, Method method, Primary leftExpression) {
        String resultType;

        //Consolida la expresion de la dimesion
        dimention.consolidate(st, struct, method, null);

        //Valida que la dimensión sea de tipo entero
        if (!dimention.getResultTypeChained().contains("Int")) {
            throw new SemanticException(identifier, "La dimensión debe ser de tipo entero. Se encontró un tipo de dato " + dimention.getResultType() + ".", true);
        }

        //Setea el tipo de retorno
        switch (type.getIDToken()) {
            case typeINT:
                resultType = IDToken.typeArrayINT.toString();
                break;
            case typeSTR:
                resultType = IDToken.typeArraySTR.toString();
                break;
            case typeCHAR:
                resultType = IDToken.typeArrayCHAR.toString();
                break;
            default: //BOOL
                resultType = IDToken.typeArrayBOOL.toString();
                break;
        }
        
        setResultType(resultType);

        //Si tiene encadenado, lo consolida
        if (rightChained != null) {
            rightChained.consolidate(st, struct, method, this);
        }
    }

    @Override
    public String toJSON(String tabs){
        return "{\n" +
            tabs + "    \"tipo\": \"" + "CreateArray" + "\",\n" +
            tabs + "    \"tipoArray\": \""  + type.getLexema() + "\",\n" +
            tabs + "    \"resultadoDeTipo\": \""  + resultType + "\",\n" +
            tabs + "    \"dimension\": " + dimention.toJSON(tabs + "    ") + "\n" +
            tabs + "}";
    }
    
}
