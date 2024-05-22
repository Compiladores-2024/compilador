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
 * Nodo que representa una sentencia de asignación.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 17/05/2024
 */
public class Assignation extends Sentence{
    
    private Primary leftSide;
    private Expression rightSide;

    /**
     * Constructor de la clase.
     * 
     * @param token Identificador
     * @param leftSide Expresión del lado izquierdo de la asignación.
     * @param rightSide Expresión del lado derecho de la asignación.
     */
    public Assignation (Token token, Primary leftSide, Expression rightSide) {
        super(token);
        this.leftSide = leftSide;
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

        //Consolida el lado izquierdo
        leftSide.consolidate(st, struct, method, null);

        //Consolida el lado derecho
        rightSide.consolidate(st, struct, method, null);

        //Valida que posean el mismo tipo de dato
        leftType = leftSide.getResultTypeChained();

        rightType = rightSide.getResultTypeChained();

        //Valida que ambos lados sean del mismo tipo
        if (!rightType.contains(leftType)) {
            //Si el lado derecho es nil
            if (rightType.equals("NIL")) {
                //El lado izquierdo no puede ser de tipo primitivo
                if (Const.primitiveTypes.contains(leftType)) {
                    throw new SemanticException(identifier, "Se esperaba una variable de tipo " + leftType + " y se encontro una de tipo " + rightType + ".", true);
                }
            } else {
                //Valida asignacion hereditaria, hasta llegar a Object o se encuentre herencia
                Static.checkInherited(st, leftType, rightType, identifier);
            }
        }
    }

    
    /** 
     * Convierte los datos en JSON.
     * 
     * @param tabs Cantidad de separaciones
     * @return String
     */
    @Override
    public String toJSON(String tabs){
        return "{\n" +
            tabs + "    \"tipo\": \"" + "Asignation" + "\",\n" +
            tabs + "    \"leftSide\": " + leftSide.toJSON(tabs + "    ") + ",\n" +
            tabs + "    \"rightSide\": " + rightSide.toJSON(tabs + "    ") + "\n" +
        tabs + "}";
    }
}
