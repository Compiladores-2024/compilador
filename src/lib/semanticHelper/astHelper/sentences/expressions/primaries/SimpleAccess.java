package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

public class SimpleAccess extends Primary{
    

    public SimpleAccess (Token value, Primary rightChained) {
        super(rightChained);
        this.token = value;
    }

    @Override
    public void checkTypes(SymbolTable symbolTable, String struct, String method){

    }

    public String obtainType(SymbolTable st, String struct, String method){
        String type = null;
        String varName =this.token.getLexema();
        if (this.token.getIDToken().equals(IDToken.idOBJECT)){
            // si existe dentro del contexto del struct devuelve el tipo 
            type = st.getStruct(struct).getVariableType(varName, method);
            if (type==""){
                throw new SemanticException(token, "Identificador: "+token.getLexema() 
                + " no declarado en struct: " + struct + " o en metodo: " + method);
            }
        }
        else{
            type=this.token.getIDToken().toString();
        }
        if (this.rightChained!=null){
            
            type = this.rightChained.obtainType(st, type, null);
            
        }

        return type;
    }




    public String toJSON(String tabs){
        return "{\n" +
            tabs + "    \"tipo\": \"" + "SimpleAccess" + "\",\n" +
            tabs + "    \"lexema\": " + (token.getIDToken().equals(IDToken.constSTR) ? "" : "\"") + token.getLexema() + (token.getIDToken().equals(IDToken.constSTR) ? "" : "\"") + ",\n" +
            tabs + "    \"tipoDeDato\": \"" + token.getIDToken() + "\",\n" +
            tabs + "    \"encadenado\": "  + (rightChained == null ? ("\"\"")  : rightChained.toJSON(tabs + "    ")) + "\n" +
            tabs + "}";
    }
}
