package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;


/**
 * Nodo que representa sentencias primarias.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 17/05/2024
 */
public abstract class Primary extends Expression{
    
    /**
     * Constructor de la clase.
     * @param identifier Identificador
     * @param rightChained Encadenado
     */
    public Primary(Token identifier, Primary rightChained) {
        super(identifier);
        this.rightChained = rightChained;
    }

    
    /** 
     * Setea una expresión encadenada
     * 
     * @param primary Expresión derecha del encadenado
     */
    public void setChained(Primary primary){
        if (rightChained != null) {
            this.rightChained.setChained(primary);
        } else {
            this.rightChained = primary;
        }
    }

    
    /** 
     * Consolida la sentencia.
     * 
     * @param st Tabla de símbolos
     * @param struct Estructura actual
     * @param method Método actual
     * @param leftSide Expresión previa
     */
    protected void variableMethodExist (SymbolTable st, Struct struct, Method method, Primary leftSide) {
        String type = null;
        boolean validateStatic = false;

        //Si posee lado izquierdo, validamos atributos y metodos del tipo de resultado
        if (leftSide != null) {
            //Obtiene la estructura
            struct = st.getStruct(leftSide.getResultType());

            validateStatic = leftSide.getIdentifier().getIDToken().equals(IDToken.idSTRUCT);

            //La estructura de tipo del lado izquierdo debe existir
            if (struct == null) {
                throw new SemanticException(identifier, "Estructura no declarada " + leftSide.getResultType() + ".", true);
            }
        }
        //Si no posee parte izquierda, valida si es acceso self
        else {
            if (identifier.getIDToken().equals(IDToken.pSELF)) {
                type = struct.getName();
            } else {
                //Si no, valida si la variable es parametro o variable local
                type = method.getParamType(identifier.getLexema());
    
                //Si no es parametro, valida si es variable local
                if (type == null) {
                    type = method.getVariableType(identifier.getLexema());
    
                    //Si no es ninguna, revisa si es una estructura definida
                    if (type == null) {
                        type = st.getStruct(identifier.getLexema()) != null ? st.getStruct(identifier.getLexema()).getName() : null;
                    }
                }
            }
        }

        //Si no es parametro ni variable local (SOLO SI NO POSEE LEFTSIDE)
        if (type == null) {
            //Si es una llamada a metodo, valida que exista
            if(this instanceof MethodAccess) {
                //Si la variable del lado izquierdo es de tipo object, puede acceder a cualquier metodo, sino debe validar que sea estatico
                type = struct.getReturnMethodType(identifier.getLexema(), validateStatic);
            } else {
                //Obtiene el tipo de dato del atributo
                type = struct.getAttributeType(identifier.getLexema(), struct.getName());
            }
        }

        //Si no se ha obtenido, es error
        if (type == null) {
            throw new SemanticException(identifier, "Identificador " + identifier.getLexema()+ " no válido. " + (this instanceof MethodAccess ? "Método" : "Atributo") + " no existe en estructura " + struct.getName() + ".", true);
        }

        //Setea el tipo de resultado
        setResultType(type);
    }

    
    /** 
     * Valida que la estructura exista
     * 
     * @param st Tabla de símbolos
     */
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
