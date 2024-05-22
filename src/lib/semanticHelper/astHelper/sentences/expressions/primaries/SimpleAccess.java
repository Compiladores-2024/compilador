package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

/**
 * Nodo que representa un acceso simple
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 17/05/2024
 */
public class SimpleAccess extends Primary{

    public SimpleAccess (Token identifier, Primary rightChained) {
        super(identifier, rightChained);
    }

    @Override
    public void consolidate(SymbolTable st, Struct struct, Method method, Primary leftExpression) {
        //Valida que exista, solo si no es una constante (literal, false, true, nil)
        String idToken = identifier.getIDToken().toString();
        if (!idToken.contains("literal") && !idToken.contains("false") && !idToken.contains("true") && !idToken.contains("nil")) {
            variableMethodExist(st, struct, method, leftExpression);
        }
        else{
            // se asigna el resultType
            setResultType(idToken.contains("literal") ? idToken : (idToken.contains("nil") ? "NIL" : "Bool"));
        }

        //Si tiene encadenado, lo consolida
        if (rightChained != null) {
            rightChained.consolidate(st, struct, method, this);
        }
    }

    public String toJSON(String tabs){
        return "{\n" +
            tabs + "    \"tipo\": \"" + "SimpleAccess" + "\",\n" +
            tabs + "    \"nombreVariable\": " + (identifier.getIDToken().equals(IDToken.constSTR) ? "" : "\"") + identifier.getLexema() + (identifier.getIDToken().equals(IDToken.constSTR) ? "" : "\"") + ",\n" +
            tabs + "    \"tipoDeDato\": \"" + identifier.getIDToken() + "\",\n" +
            tabs + "    \"resultadoDeTipo\": \""  + resultType + "\",\n" +
            tabs + "    \"encadenado\": "  + (rightChained == null ? ("\"\"")  : rightChained.toJSON(tabs + "    ")) + "\n" +
            tabs + "}";
    }
}
