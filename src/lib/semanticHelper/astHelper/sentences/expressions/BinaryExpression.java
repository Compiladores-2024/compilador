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

        //Setea la tabla de simbolos
        setSymbolTable(st);
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

    /**
     * Genera código intermedio para expresiones binarias
     * PRIMERO OBTIENE EL LADO DERECHO PARA NO PISAR LA INFORMACION DEL LADO IZQUIERDO.
     * @param sStruct
     * @param sMethod
     * @return String
     */
    public String generateCode(String sStruct, String sMethod){
        String asm="#Binary expression code - Left side\n";

        //Obtiene el valor del lado izquierdo en el registro $v0
        asm += leftSide.generateCode(sStruct, sMethod);
        //Si es offset, obtiene el valor
        if (leftSide.isOffset()) {
            asm += "lw $v0, 0($v0)\t\t\t\t\t#Get the left value\n";
        }
        //Guarda el valor en la pila
        asm += "sw $v0, 0($sp)\naddiu $sp, $sp, -4\n";

        
        asm += "#Binary expression - Right side\n";
        //Obtiene el valor del lado derecho en el registro $v0
        asm += rightSide.generateCode(sStruct, sMethod) + "#Binary expression - Result\n";
        //Si es offset, obtiene el valor
        if (rightSide.isOffset()) {
            asm += "lw $v0, 0($v0)\t\t\t\t\t#Get the right value\n";
        }

        //Obtiene el resultado del lado izquierdo
        asm += "lw $t0, 4($sp)\n";
        
        //Realiza la operacion y guarda el resultado en $v0
        switch (operator){
            case oSUM:
                asm += "addu $v0, $t0, $v0\t\t\t\t# $v0 = $t0 + $v0\n";
                break;
            case oSUB:
                asm += "subu $v0, $t0, $v0\t\t\t\t# $v0 = $t0 - $v0\n";
                break;
            case oMULT:
                asm += "mul $v0, $t0, $v0\t\t\t\t# $v0 = $t0 * $v0\n";
                break;
            case oDIV:
                //CAPTURAR ERROR SI RIGHTSIDE ES 0
                asm += "beq $v0, $0, ErrorDiv0 \n";
                asm += "div $t0, $v0\t\t\t\t\t# $v0 = $t0 / $v0. The quotient saves in LO register\n";
                asm += "mflo $v0\n";
                break;
            case oMOD:
                //CAPTURAR ERROR SI RIGHTSIDE ES 0
                asm += "beq $v0, $0, ErrorDiv0 \n";
                asm += "div $t0, $v0\t\t\t\t# $v0 = $t0 / $v0. The remainder saves in HI register\n";
                asm += "mfhi $v0\n";
                break;
            case oAND: 
                asm += "and $v0, $t0, $v0\t\t\t\t# $v0 = $t0 && $v0\n";
                break;
            case oOR:
                asm += "or $v0, $t0, $v0\t\t\t\t# $v0 = $t0 || $v0\n";
                break;
            case oMIN:
                asm += "slt $v0, $t0, $v0 \t\t\t\t# $v0 = $t0 < $v0\n";
                break;
            case oMIN_EQ:
                asm += "sle $v0, $t0, $v0\t\t\t\t# $v0 = $t0 <= $v0\n";
                break;
            case oMAX:
                asm += "sgt $v0, $t0, $v0 \t\t\t\t# $v0 = $t0 > $v0\n";
                break;
            case oMAX_EQ:
                asm += "sge $v0, $t0, $v0\t\t\t\t# $v0 = $t0 >= $v0\n";
                break;
            case oEQUAL:
                asm += "seq $v0, $t0, $v0\t\t\t\t# $v0 = $t0 == $v0\n";
                break;
            case oNOT_EQ:
                asm += "sne $v0, $t0, $v0\t\t\t\t# $v0 = $t0 != $v0\n";
                break;
            default:
                break;
            }
        //Libera memoria
        asm += "addiu $sp, $sp, 4\t\t\t\t#End Binary expression\n";
        return asm;
    }
}
