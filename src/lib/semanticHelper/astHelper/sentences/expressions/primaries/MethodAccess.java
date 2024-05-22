package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import java.util.ArrayList;

import src.lib.Static;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

/**
 * Nodo que representa acceso a método.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 17/05/2024
 */
public class MethodAccess extends Primary{
    private ArrayList<Expression> params;

    public MethodAccess (Token identifier, ArrayList<Expression> params, Primary rightChained) {
        super(identifier, rightChained);
        this.params = params;
    }

    @Override
    public void consolidate(SymbolTable st, Struct struct, Method method, Primary leftExpression) {
        Method methodToCheckParams;

        //Valida que el método exista
        variableMethodExist(st, struct, method, leftExpression);

        //Obtiene el metodo al que hace referencia
        if (leftExpression == null) {
            methodToCheckParams = struct.getMethod(identifier.getLexema());
        } else {
            methodToCheckParams = st.getStruct(leftExpression.getResultType()).getMethod(identifier.getLexema());
        }

        //Consolida los parametros
        Static.consolidateParams(params, st, struct, method, methodToCheckParams, identifier);

        //Si tiene encadenado, lo consolida
        if (rightChained != null) {
            rightChained.consolidate(st, struct, method, this);
        }
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
            tabs + "    \"nombreMetodo\": \"" + identifier.getLexema() +  "\",\n" +
            tabs + "    \"resultadoDeTipo\": \""  + resultType + "\",\n" +
            tabs + "    \"parametros\": " +  (paramsJSON == "" ? ("\"\"") : paramsJSON) + "\n" +
        tabs + "}";
    }
}
