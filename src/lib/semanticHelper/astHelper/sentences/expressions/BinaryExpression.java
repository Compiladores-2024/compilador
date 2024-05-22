package src.lib.semanticHelper.astHelper.sentences.expressions;

import src.lib.Const;
import src.lib.Static;
import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.Primary;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

/**
 * Nodo que representa una expresión binaria.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 17/05/2024
 */
public class BinaryExpression extends Expression{
    
    private Expression leftSide;
    private Expression rightSide;
    private IDToken operator;

    /**
     * Constructor de la clase.
     * @param token Identificador
     * @param leftSide Expresión del lado izquierdo del operador.
     * @param operator Operador.
     * @param rightSide Expresión del lado derecho del operador.
     */
    public BinaryExpression (Token token, Expression leftSide, IDToken operator, Expression rightSide) {
        super(token);
        this.leftSide = leftSide;
        this.operator = operator;
        this.rightSide = rightSide;
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
        String leftType, rightType;
        //Consolida expresion izquierda
        leftSide.consolidate(st, struct, method, leftExpression);

        //Consolida expresion derecha
        rightSide.consolidate(st, struct, method, leftExpression);

        //Valida que posean el mismo tipo de dato
        leftType = Static.getPrimitiveDataType(leftSide.getResultTypeChained());
        rightType = Static.getPrimitiveDataType(rightSide.getResultTypeChained());

        //Si los operadores no son iguales
        if (!leftType.equals(rightType)){

            //Si el lado derecho es nil
            if (rightType.equals("NIL")) {
                //El lado izquierdo no debe ser tipo primitivo
                if (Const.primitiveTypes.contains(leftType)) {
                    throw new SemanticException(identifier, "Se esperaba un tipo de dato " + leftType + ". Se encontró " + rightType, true);
                }
            }
            else {
                throw new SemanticException(identifier, "Se esperaba un tipo de dato " + leftType + ". Se encontró " + rightType, true);
            }
        }

        checkOperation(leftSide.getResultTypeChained());

        setResultType(getType(leftSide.getResultTypeChained()));
    }

    
    /** 
     * Obtiene el tipo de dato según el operador.
     * 
     * @param result Tipo de dato de la expresión
     * @return String
     */
    private String getType(String result) {
        switch (operator) {
            case oAND:
            case oOR:
            case oMIN:
            case oMIN_EQ:
            case oMAX:
            case oMAX_EQ:
            case oNOT_EQ:
            case oEQUAL:
                result = "Bool";
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * Método que verifica si la operacion realizada es aceptada para los tipos de los operandos
     * 
     * @since 22/05/2024
     * @param type tipo de operandos
     */
    private void checkOperation(String type){
        switch(operator){
            case oDIV:
            case oMOD:
            case oMULT:
            case oSUM:
            case oSUB:
                if ( !(type.equals("Int") || type.equals("literal Int") ) ){
                    throw new SemanticException(identifier, "Operacion " + operator + " no soportada para operandos de tipo " + type, true);
                }
                break;
            default:
                break;
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
            tabs + "    \"tipo\": \"" + "BinaryExpression" + "\",\n" +
            tabs + "    \"operador\": \"" + operator.toString() + "\",\n" +
            tabs + "    \"resultadoDeTipo\": \""  + resultType + "\",\n" +
            tabs + "    \"leftSide\": " + (leftSide == null ?  ("\"\""):  leftSide.toJSON(tabs + "    ")) + ",\n" +
            tabs + "    \"rightSide\": " + (rightSide == null ? ("\"\"")  : rightSide.toJSON(tabs + "    ")) + "\n" +
        tabs + "}";
    }

}
