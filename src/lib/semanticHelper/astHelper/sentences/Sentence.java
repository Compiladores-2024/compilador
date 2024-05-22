package src.lib.semanticHelper.astHelper.sentences;

import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.Primary;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

/**
 * Nodo que representa una sentencia
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 17/05/2024
 */
public abstract class Sentence {
    /** Identificador de la sentencia */
    protected Token identifier;
    
    /** 
     * Constructor de la clase
     * 
     * @param token Identificador de la sentencia
     */
    public Sentence(Token token){
        this.identifier = token;
    }

    
    /** 
     * Obtiene el identificador de la sentencia.
     * 
     * @return Token
     */
    public Token getIdentifier() {
        return identifier;
    }

    /** 
     * Convierte los datos en JSON.
     * 
     * @param tabs Cantidad de separaciones
     * @return String
     */
    public abstract String toJSON(String tabs);

    /** 
     * Consolida la sentencia.
     * 
     * @param st Tabla de símbolos
     * @param struct Estructura actual
     * @param method Método actual
     * @param leftExpression Expresión previa
     */
    public abstract void consolidate(SymbolTable st, Struct struct, Method method, Primary leftExpression);
    
}
