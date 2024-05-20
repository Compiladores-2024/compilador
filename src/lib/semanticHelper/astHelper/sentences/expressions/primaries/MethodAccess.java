package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import java.util.ArrayList;

import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

public class MethodAccess extends Primary{
    private ArrayList<Expression> params;

    public MethodAccess (Token identifier, ArrayList<Expression> params, Primary rightChained) {
        super(identifier, rightChained);
        this.params = params;
    }

    @Override
    public void consolidate(SymbolTable st, Struct struct, Method method, Primary leftExpression) {
        String resultType, paramType;

        //Valida que el método exista
        variableMethodExist(st, struct, method, leftExpression);

        //Obtiene el metodo al que hace referencia
        if (leftExpression == null) {
            method = struct.getMethod(identifier.getLexema());
        } else {
            method = st.getStruct(leftExpression.getResultType()).getMethod(identifier.getLexema());
        }

        //Validar que los parámetros existan
        for (Expression param : params) {
            //Consolida la expresion
            param.consolidate(st, struct, method, null);
            
            //Valida si se encuentra parametro (SINO, ESTA FUERA DEL RANGO)
            if (method.getParamType(param.getPosition()) != null) {
                // Valida que el tipo de dato del parametro sea el mismo
                resultType = param.getResultType();
                paramType = method.getParamType(param.getPosition());
                //Valida que sean iguales
                if (!paramType.contains(resultType)) {
                    //Si el valor a enviar es nil
                    if (resultType.equals("NIL")) {
                        //El parametro no debe ser de tipo primitivo
                        if (primitiveTypes.contains(paramType)) {
                            throw new SemanticException(identifier, "Se esperaba un tipo de dato " + paramType + ". Se encontró " + resultType, true);
                        }
                    }
                    else {
                        throw new SemanticException(identifier, "Se esperaba un tipo de dato " + paramType + ". Se encontró " + resultType, true);
                    }
                }
            } else {
                throw new SemanticException(identifier, "Cantidad de argumentos inválida. Máximo: " + (param.getPosition() - 1) + ".", true);
            }
        }

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
