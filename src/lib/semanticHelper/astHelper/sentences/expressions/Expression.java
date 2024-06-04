package src.lib.semanticHelper.astHelper.sentences.expressions;

import src.lib.semanticHelper.astHelper.sentences.Sentence;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.Primary;
import src.lib.tokenHelper.Token;

/**
 * Nodo que representa una expresión.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 17/05/2024
 */
public abstract class Expression extends Sentence{
    
    /** Tipo de dato de la expresión */
    protected String resultType;
    /** Encadenado derecho */
    protected Primary rightChained;
    /** Posición del parámetro */
    protected int position;
    /** Guarda un booleano que avisa si el valor es constante u offset del stack*/
    protected boolean isOffset;
    
    /**
     * Constructor de la clase.
     * @param token Identificador
     */
    public Expression(Token token) {
        super(token);
        this.position = -1;
    }
    /**
     * Constructor de la clase.
     * @param token Identificador
     * @param position Posición del parámetro
     */
    public Expression(Token token, int position){
        super(token);
        this.position = position;
    }
    
    /**
     * Setea el tipo de resultado de la expresión
     * @param resultType Tipo de resultado
     */
    public void setResultType(String resultType) {
        this.resultType = resultType;
    }
    
    /** 
     * Obtiene el tipo que posee la expresión
     * @return String
     */
    public String getResultType() {
        return resultType;
    }

    public boolean isOffset() {
        return isOffset;
    }

    
    /** 
     * Obtiene el tipo que posee el resultado del encadenado.
     * @return String
     */
    public String getResultTypeChained() {
        if (this.rightChained!=null){
            return this.rightChained.getResultTypeChained();
        }
        
        return resultType;
    }

    
    /** 
     * Obtiene la posición del parámetro
     * @return int
     */
    public int getPosition() {
        return position;
    }

    /** 
     * Setea la posición del parámetro
     * @param position Posición actual
     */
    public void setPosition(int position) {
        this.position = position;
    }

}
