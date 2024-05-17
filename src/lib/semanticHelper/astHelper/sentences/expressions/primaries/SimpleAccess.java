package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import src.lib.semanticHelper.SymbolTable;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

public class SimpleAccess extends Primary{
    
    private Token value; 

    public SimpleAccess (Token value, Primary rightChained) {
        super(rightChained);
        this.value = value;
    }

    @Override
    public void checkTypes(SymbolTable symbolTable, String struct, String method){

    }

    public IDToken obtainType(SymbolTable st, String struct, String method){
        IDToken type = null;
        if (this.rightChained==null){
            if (this.value.getIDToken().equals(IDToken.idOBJECT)){
                String varName=this.value.getLexema();
                // si existe dentro del contexto
                if (st.getStruct(struct).checkVariable(varName) ){
                    type = st.getStruct(struct).getVariableType(varName);
                }
            }
            else{
                type=value.getIDToken();
            }
        }

        // mas
        return type;
    }




    public String toJSON(String tabs){
        return "{\n" +
            tabs + "    \"tipo\": \"" + "SimpleAccess" + "\",\n" +
            tabs + "    \"lexema\": " + (value.getIDToken().equals(IDToken.constSTR) ? "" : "\"") + value.getLexema() + (value.getIDToken().equals(IDToken.constSTR) ? "" : "\"") + ",\n" +
            tabs + "    \"tipoDeDato\": \"" + value.getIDToken() + "\",\n" +
            tabs + "    \"encadenado\": "  + (rightChained == null ? ("\"\"")  : rightChained.toJSON(tabs + "    ")) + "\n" +
            tabs + "}";
    }
}
