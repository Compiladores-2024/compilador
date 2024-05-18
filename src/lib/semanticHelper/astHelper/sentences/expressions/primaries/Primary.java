package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

public abstract class Primary extends Expression{
    protected Token identifier;
    

    public Primary(Token identifier, Primary rightChained) {
        this.identifier = identifier;
        this.rightChained = rightChained;
    }

    public void setChained(Primary primary){
        this.rightChained = primary;
    }

    protected void variableExist (SymbolTable st, Struct struct, Method method, Primary leftSide) {
        Token type = null;
        //Si posee lado izquierdo, validamos atributos y metodos de allí
        if (leftSide != null) {
            struct = st.getStruct(leftSide.getResultType());
            
            //La estructura de tipo del lado izquierdo debe existir
            if (struct == null) {
                throw new SemanticException(identifier, "Estructura no declarada " + leftSide.getResultType() + ".", true);
            }
        }


        //Obtiene el tipo de dato del atributo
        type = struct.getAttributeType(identifier.getLexema());

        //Si no es atributo, valida que sea metodo
        if (type == null) {
            type = struct.getReturnMethodType(identifier.getLexema());

            //Si no es método, valida si proviene de encadenado y si es variable de metodo
            if (type == null && leftSide == null) {
                type = method.getParamType(position);

                //Si no es parametro, valida si es variable local
                if (type == null) {
                    type = method.getVariableType(identifier.getLexema());
                }
            }
        }
        
        //Si no se ha obtenido, no existe
        if (type == null) {
            throw new SemanticException(identifier, "Variable no declarada.", true);
        }

        //Setea el tipo de resultado
        setResultType(type);
    }

    protected void methodExist (SymbolTable st, Struct struct) {
        Method m;
        //Valida que la estructura exista
        if (struct == null) {
            throw new SemanticException(token, "Estructura " + identifier.getLexema() + " no definida.", true);
        }
        
        //Valida que el metodo exista
        m = struct.getMethod(identifier.getLexema());
        if (m == null) {
            throw new SemanticException(token, "Estructura " + identifier.getLexema() + " no definida.", true);
        }
        setResultType(struct.getMetadata());
    }

    protected void structExist (SymbolTable st) {
        Struct struct = st.getStruct(identifier.getLexema());
        //Valida que la estructura exista
        if (struct == null) {
            throw new SemanticException(token, "Estructura " + identifier.getLexema() + " no definida.", true);
        }
        //Si existe, setea el tipo de dato. En lexema se obtiene el nombre de la estructura
        else {
            setResultType(struct.getMetadata());
        }
    }


}
