package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;


import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

/**
 * Nodo que representa una sentencia simple
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 17/05/2024
 */
public class SimpleSentence extends Primary{
    private Expression exp;

    /**
     * Constructor de la clase.
     * 
     * @param identifier Identificador
     * @param exp Expresión que representa la sentencia simple
     * @param rightChained Encadenado
     */
    public SimpleSentence(Token identifier, Expression exp, Primary rightChained) {
        super(identifier, rightChained);
        this.exp = exp;
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
        exp.consolidate(st, struct, method, leftExpression);
        setResultType(exp.getResultTypeChained());
    }

    
    /** 
     * Convierte los datos en JSON.
     * 
     * @param tabs Cantidad de separaciones
     * @return String
     */
    @Override
    public String toJSON(String tabs) {
        return exp.toJSON(tabs);
    }

    public String generateCode(String registerResult){
        String asm="";

        //asm += exp.generateCode();
        return asm;
    }

}