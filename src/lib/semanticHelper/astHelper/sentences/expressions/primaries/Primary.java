package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

public abstract class Primary extends Expression{
    

    public Primary(Token identifier, Primary rightChained) {
        super(identifier);
        this.rightChained = rightChained;
    }

    public void setChained(Primary primary){
        if (rightChained != null) {
            this.rightChained.setChained(primary);
        } else {
            this.rightChained = primary;
        }
    }

    protected void variableMethodExist (SymbolTable st, Struct struct, Method method, Primary leftSide) {
        String type = null;

        //Si posee lado izquierdo, validamos atributos y metodos del tipo de resultado
        if (leftSide != null) {
            //Obtiene la estructura
            struct = st.getStruct(leftSide.getResultType());
            
            //La estructura de tipo del lado izquierdo debe existir
            if (struct == null) {
                throw new SemanticException(identifier, "Estructura no declarada " + leftSide.getResultType() + ".", true);
            }
        }
        //Si no posee parte izquierda, valida si la variable es parametro o variable local
        else {
            type = method.getParamType(position);

            //Si no es parametro, valida si es variable local
            if (type == null) {
                type = method.getVariableType(identifier.getLexema());

                //Si no es ninguna, revisa si es una estructura definida
                if (type == null) {
                    type = st.getStruct(identifier.getLexema()) != null ? st.getStruct(identifier.getLexema()).getName() : null;
                }
            }
        }

        //Si no es parametro ni variable local (SOLO SI NO POSEE LEFTSIDE)
        if (type == null) {
            //Si es una llamada a metodo, valida que exista
            if(this instanceof MethodAccess) {
                type = struct.getReturnMethodType(identifier.getLexema());
            } else {
                //Obtiene el tipo de dato del atributo
                type = struct.getAttributeType(identifier.getLexema());
            }
        }

        //Si no se ha obtenido, es error
        if (type == null) {
            throw new SemanticException(identifier, "Identificador " + identifier.getLexema()+ " no válido. " + (this instanceof MethodAccess ? "Método" : "Atributo") + " no existe en estructura " + struct.getName() + ".", true);
        }

        //Setea el tipo de resultado
        setResultType(type);
    }

    protected void structExist (SymbolTable st) {
        Struct struct = st.getStruct(identifier.getLexema());
        //Valida que la estructura exista
        if (struct == null) {
            throw new SemanticException(identifier, "Estructura " + identifier.getLexema() + " no definida.", true);
        }
        //Si existe, setea el tipo de dato. En lexema se obtiene el nombre de la estructura
        else {
            setResultType(struct.getName());
        }
    }
}
