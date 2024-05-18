package src.lib.semanticHelper.astHelper.sentences;

import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

public class Return extends Sentence{

    private Expression expression; 

    public Return(Token token, Expression expression) {
        super(token);
        this.expression = expression;
    }

    public boolean isBool(String type){
        return (type.equals(IDToken.typeBOOL.toString())
        || type.equals(IDToken.pTRUE.toString())
        || type.equals(IDToken.pFALSE.toString()) );
    }


    @Override
    public void checkTypes(SymbolTable symbolTable, String struct, String method){
        String returnMethod = symbolTable.getStruct(struct).getMethodType(method);
        //SI ES IDStruct hay que asignar el id del struct

        String returnType;
        returnType =  (this.expression!= null ? expression.obtainType(symbolTable, struct, method) : "");

        boolean error = false;
        // si returnMethod es Bool, entonces returnType puede ser Bool, true o false
        switch (returnMethod) {
            case "Bool":                
                if (!isBool(returnType)){
                    error=true;
                }
                break;
            case "Int":
                
                if (!(returnType.equals(IDToken.typeINT.toString()) || returnType.equals(IDToken.constINT.toString())) ) {
                    error=true;
                }
                break;

            case "Str":
                if (!(returnType.equals(IDToken.typeSTR.toString()) || returnType.equals(IDToken.constSTR.toString())) ) {
                    error=true;
                }
                break;

            case "Char":
                if (!(returnType.equals(IDToken.typeCHAR.toString()) || returnType.equals(IDToken.constCHAR.toString())) ) {
                    error=true;
                }
                break;

            case "void":
                if (this.expression==null && !returnMethod.equals(IDToken.typeVOID.toString()) ) {
                    error=true;
                }
                break;
            
            default:
                if (!returnMethod.equals(returnType)){
                    error=true;
                }
                break;
        }
        if (error){
            throw new SemanticException(this.token, "El retorno del metodo " + method +  ": "
            + returnType + ", no coincide con su tipo de retorno declarado: " + returnMethod);
        }

    }

    public String toJSON(String tabs){
        return "{\n" +
            tabs + "    \"tipo\": \"" + "Return" + "\",\n" +
            tabs + "    \"expresión\": " + (expression != null ? expression.toJSON(tabs + "    ") : "\"\"") + "\n" +
            tabs + "}";
    }

}
