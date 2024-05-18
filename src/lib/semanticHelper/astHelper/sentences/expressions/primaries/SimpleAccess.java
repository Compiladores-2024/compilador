package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

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
        if (!identifier.getLexema().contains("literal") && !identifier.getLexema().contains("false") && !identifier.getLexema().contains("true") && !identifier.getLexema().contains("nil")) {
            variableExist(st, struct, method, leftExpression);
        }

        //Si tiene encadenado, lo consolida
        if (rightChained != null) {
            rightChained.consolidate(st, struct, method, this);
        }
    }

    public IDToken obtainType(SymbolTable st, String struct, String method){
        // Token type = null;
        // if (this.rightChained==null){
        //     if (this.identifier.getIDToken().equals(IDToken.idOBJECT)){
        //         String varName=this.identifier.getLexema();
        //         // si existe dentro del contexto
        //         if (st.getStruct(struct).checkVariable(varName) ){
        //             type = st.getStruct(struct).getVariableType(varName);
        //         }
        //     }
        //     else{
        //         type=identifier.getIDToken();
        //     }
        // }

        // // mas
        // return type;
        return null;
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
