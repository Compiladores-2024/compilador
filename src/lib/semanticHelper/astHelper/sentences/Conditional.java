package src.lib.semanticHelper.astHelper.sentences;

import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.Primary;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

/**
 * Nodo que representa el condicional
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 17/05/2024
 */
public class Conditional extends Sentence{
    
    private Expression condition;
    private Sentence thenBlock; 
    private Sentence elseBlock;

    /**
     * Constructor de la clase.
     * 
     * @param token Identificador
     * @param condition Condición del condicional
     * @param thenBlock Bloque de sentencias then
     * @param elseBlock Bloque de sentencias else
     */
    public Conditional(Token token, Expression condition, Sentence thenBlock, Sentence elseBlock) {
        super(token);
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
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
        //Consolida la condicion
        condition.consolidate(st, struct, method, null);
        
        // si la condicion no es bool es un error
        if (!condition.getResultTypeChained().equals(IDToken.typeBOOL.toString())){
            throw new SemanticException(this.identifier, "El tipo de la condicion if no es booleano", true);
        }

        //Consolida el boque if
        thenBlock.consolidate(st, struct, method, null);

        //Si posee bloque else, lo consolida
        if (elseBlock != null) {
            elseBlock.consolidate(st, struct, method, null);
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
            tabs + "    \"tipo\": \"" + "Conditional" + "\",\n" +
            tabs + "    \"condicion\": " + condition.toJSON(tabs + "    ") + ",\n" +
            tabs + "    \"thenBlock\": " + thenBlock.toJSON(tabs + "    ") + ",\n" +
            tabs + "    \"elseBlock\": " + (elseBlock != null ? elseBlock.toJSON(tabs + "    ") : "[]") + "\n" +
            tabs + "}";
    }

    public String generateCode(){
        String asm="";

        int numIf; //numero de if para diferenciar los labels
        asm += condition.generateCode();
        asm += "bne $a0, 1, else #Branching Condition: If the value in $a0 is not equal to 1, the program execution jumps to the instruction labeled else\n";
        asm += thenBlock.generateCode();
        asm += "\tj endIfElse #Jump endIfElse label\n";
        asm += "else: \n";
        asm += elseBlock.generateCode();
        asm += "endIfElse:\n";
        return asm;
    }

}
