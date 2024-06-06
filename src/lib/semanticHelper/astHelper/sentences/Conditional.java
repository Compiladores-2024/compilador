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

        //Setea la tabla de simbolos
        setSymbolTable(st);
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

    public String generateCode(String sStruct, String sMethod){
        String asm="\n#Conditional code\n";
        
        //Obtiene el resultado del condicional
        asm += condition.generateCode(sStruct, sMethod);
        asm += "lw $t0, 4($sp)\naddi $sp, $sp, 4\n\n";
        asm += "bne $t0, 1, else\t\t\t\t#Then block. If $t0 != 1, jumps to else\n";

        //Then block
        asm += thenBlock.generateCode(sStruct, sMethod);
        asm += "j endIfElse\n";

        //Else block
        asm += "else:\t\t\t\t\t\t\t#Else block\n";
        if (elseBlock != null) {
            asm += elseBlock.generateCode(sStruct, sMethod);
        }

        //End if-else
        asm += "endIfElse:\t\t\t\t\t\t#End if-else\n\n";

        //Aumenta el contador de sentencias
        symbolTable.addSentenceCounter();
        return asm;
    }

}
