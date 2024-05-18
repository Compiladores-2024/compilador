package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import java.util.ArrayList;

import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

public class MethodAccess extends Primary{
    

    private ArrayList<Expression> params;

    public MethodAccess (Token value, ArrayList<Expression> params, Primary rightChained) {
        super(rightChained);
        this.params = params;
        this.token=value;
    }

    @Override
    public void checkTypes(SymbolTable st, String struct, String method){

    }

    @Override
    public String obtainType(SymbolTable st, String struct, String method){
        String type = null;
        String methodName=this.token.getLexema();
        if (this.token.getIDToken().equals(IDToken.idOBJECT)){
            // si existe el metodo en el struct, devuelve el tipo del metodo en formato String
            type = st.getStruct(struct).getMethodType(methodName);
            if (type==""){
                throw new SemanticException(token, "Metodo: "+token.getLexema() + " no declarado en struct: " + struct);
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

    @Override
    public String toJSON(String tabs){
        int count = params.size();
        String paramsJSON = count > 0 ? "[\n" : "[";

        for (Expression expression : params) {
            paramsJSON += tabs + "        " + expression.toJSON(tabs + "        ") +
                (count > 1 ? "," : "") + "\n";
            count--;
        }

        paramsJSON += (params.size() > 0 ? (tabs + "    ]") : "]");

        return "{\n" +
            tabs + "    \"tipo\": \"" + "MethodAccess" + "\",\n" +
            tabs + "    \"método\": \"" + token.getLexema() +  "\",\n" +
            tabs + "    \"params\": " +  (paramsJSON == "" ? ("\"\"") : paramsJSON) + "\n" +
        tabs + "}";
    }
}
