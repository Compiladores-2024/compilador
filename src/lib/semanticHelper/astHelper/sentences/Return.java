package src.lib.semanticHelper.astHelper.sentences;

import src.lib.Const;
import src.lib.Static;
import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.Primary;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

/**
 * Nodo que representa la sentencia especial Return.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 17/05/2024
 */
public class Return extends Sentence{

    private Expression expression; 

    /**
     * Constructor de la clase.
     * @param token Identificador
     * @param expression Expresión a retornar.
     */
    public Return(Token token, Expression expression) {
        super(token);
        this.expression = expression;
    }

    
    /** 
     * Consolida la sentencia.
     * 
     * @param st Tabla de símbolos
     * @param struct Estructura actual
     * @param method Método actual
     * @param leftExpression Expresión previa
     */
    @Override
    public void consolidate(SymbolTable st, Struct struct, Method method, Primary leftExpression) {
        String resultType = method.getReturnType(), expResult = "NIL";
        //Si posee expresion, la consolida
        if (expression != null) {
            expression.consolidate(st, struct, method, null);
            expResult = Static.getPrimitiveDataType(expression.getResultTypeChained());
        }

        //Valida que el tipo de retorno sea el mismo
        if (!resultType.equals(expResult)) {
            //Si no posee expresion
            if (expression == null) {
                //Solo se permite cuando método retorna void
                if (!resultType.equals("void")) {
                    throw new SemanticException(identifier, "Se esperaba un tipo de retorno " + resultType + ".", true);
                }
            }
            //Posee expresion
            else {
                //Si el resultado de la expresion es nil
                if (expResult.equals("NIL")) {
                    //Solo se permite si no es de tipo primitivo
                    if (Const.primitiveTypes.contains(resultType)) {
                        throw new SemanticException(identifier, "Se esperaba un tipo de retorno " + resultType + ".", true);
                    }
                }
                //Tipos de datos distintos
                else {
                    Static.checkInherited(st, resultType, expResult, identifier);
                }
            }
        }
    }

    
    /** 
     * Convierte los datos en JSON.
     * 
     * @param tabs Cantidad de separaciones
     * @return String
     */
    public String toJSON(String tabs){
        return "{\n" +
            tabs + "    \"tipo\": \"" + "Return" + "\",\n" +
            tabs + "    \"expresión\": " + (expression != null ? expression.toJSON(tabs + "    ") : "\"\"") + "\n" +
            tabs + "}";
    }

}
