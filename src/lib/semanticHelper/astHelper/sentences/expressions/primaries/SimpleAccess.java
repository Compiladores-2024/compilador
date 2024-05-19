package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

public class SimpleAccess extends Primary{

    public SimpleAccess (Token identifier, Primary rightChained) {
        super(identifier, rightChained);
    }

    @Override
    public void checkTypes(SymbolTable symbolTable, String struct, String method){

    }

    @Override
    public void consolidate(SymbolTable st, Struct struct, Method method, Primary leftExpression) {
        //Valida que exista, solo si no es una constante (literal, false, true, nil)
        String idToken = identifier.getIDToken().toString();
        if (!idToken.contains("literal") && !idToken.contains("false") 
            && !idToken.contains("true") && !idToken.contains("nil")) {
            variableExist(st, struct, method, leftExpression);
        }
        else{
            // se asigna un token a resultType
            this.setResultType(createToken(idToken));
        }

        //Si tiene encadenado, lo consolida
        if (rightChained != null) {
            rightChained.consolidate(st, struct, method, this);
        }
    }

    public Token createToken(String idToken){
        Token newToken=null;
        switch (idToken) {
            case "literal Int":
                newToken = new Token(IDToken.typeINT, IDToken.typeINT.toString(), 0, 0);
                break;
            case "literal Str": 
                newToken = new Token(IDToken.typeSTR, IDToken.typeSTR.toString(), 0, 0);
                break;
            case "literal Char":
                newToken = new Token(IDToken.typeCHAR, IDToken.typeCHAR.toString(), 0, 0);
                break;
            case "true":
            case "false":
                newToken = new Token(IDToken.typeBOOL, IDToken.typeBOOL.toString(), 0, 0);
                break;
            default:
                newToken = this.identifier;
                break;
        }
        return newToken;
    }


    @Override
    public IDToken obtainType(SymbolTable st, String struct, String method) {
        return null;
    }


    public String toJSON(String tabs){
        return "{\n" +
            tabs + "    \"tipo\": \"" + "SimpleAccess" + "\",\n" +
            tabs + "    \"nombreVariable\": " + (identifier.getIDToken().equals(IDToken.constSTR) ? "" : "\"") + identifier.getLexema() + (identifier.getIDToken().equals(IDToken.constSTR) ? "" : "\"") + ",\n" +
            tabs + "    \"tipoDeDato\": \"" + identifier.getIDToken() + "\",\n" +
            tabs + "    \"resultadoDeTipo\": \""  + resultType.getLexema() + "\",\n" +
            tabs + "    \"encadenado\": "  + (rightChained == null ? ("\"\"")  : rightChained.toJSON(tabs + "    ")) + "\n" +
            tabs + "}";
    }
}
