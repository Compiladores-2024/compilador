package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import java.util.ArrayList;

import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

public class MethodAccess extends Primary{
    private ArrayList<Expression> params;

    public MethodAccess (Token identifier, ArrayList<Expression> params, Primary rightChained) {
        super(identifier, rightChained);
        this.params = params;
    }

    @Override
    public void checkTypes(SymbolTable st, String struct, String method){

    }

    @Override
    public IDToken obtainType(SymbolTable st, String struct, String method){
        return null;
    }
    

    @Override
    public void consolidate(SymbolTable st, Struct struct, Method method, Primary leftExpression) {
        String resultType, paramType;

        //Valida que el método exista
        boolean structError=false;
        boolean structErrorLeftExpression = false;
        if (method == null) {
            structError=true;
        }
        String resultTypeLeft="";
        if (leftExpression!=null){
            resultTypeLeft = leftExpression.getResultType().getLexema();
            // si no existe en el struct de la izquierda
            if (st.getStruct(resultTypeLeft).getMethod(this.identifier.getLexema())==null){
                structErrorLeftExpression=true;
            } 
        }

        if (structError){
            throw new SemanticException(token, "Método " + identifier.getLexema() + " no existe en la estructura " + struct.getName() + ".", true);
        }

        if(structErrorLeftExpression){
            throw new SemanticException(this.identifier, "Método " + identifier.getLexema() + " no existe en la estructura " + resultTypeLeft + ".", true);

        }

        String structName = (resultTypeLeft!=null ? resultTypeLeft : struct.getName());
        if (params.size()!= st.getStruct(structName).getMethod(this.identifier.getLexema()).getParamSize()){
            throw new SemanticException(identifier, "El numero de argumentos actuales del metodo "+ identifier.getLexema()   +  " no coincide con el numero de argumentos formales", true);
        }

        // se asigna el tipo al metodo
        this.setResultType(st.getStruct(resultTypeLeft!="" ? resultTypeLeft : struct.getName()).getMethod(this.identifier.getLexema()).getReturnType());
        
        //Validar que los parámetros existan
        for (Expression param : params) {
            //Consolida la expresion
            param.consolidate(st, struct, method, this);
            
            // Valida que el tipo de dato del parametro sea el mismo
            resultType = param.getResultType().getLexema();
            paramType = method.getParamType(param.getPosition()).getLexema();
            if (!resultType.equals(paramType)) {
                throw new SemanticException(identifier, "Se esperaba un tipo de dato " + paramType + ". Se encontró " + resultType, true);
            }
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
            tabs + "    \"resultadoDeTipo\": \""  + resultType.getLexema() + "\",\n" +
            tabs + "    \"parametros\": " +  (paramsJSON == "" ? ("\"\"") : paramsJSON) + "\n" +
        tabs + "}";
    }
}
