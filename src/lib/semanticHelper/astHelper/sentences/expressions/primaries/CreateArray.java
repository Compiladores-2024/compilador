package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;


import src.lib.Static;
import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

/**
 * Nodo que representa creación de arreglos
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 17/05/2024
 */
public class CreateArray extends Primary{
    
    private Token type;
    private Expression dimention;

    /**
     * Constructor de la clase.
     * 
     * @param type Tipo de array a crear
     * @param dimention Expresión con la dimensión del array
     * @param rightChained Encadenado
     */
    public CreateArray (Token type, Expression dimention, Primary rightChained) {
        super(type, rightChained);
        this.type = type;
        this.dimention = dimention;
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
        String resultType;

        //Consolida la expresion de la dimesion
        dimention.consolidate(st, struct, method, null);

        //Valida que la dimensión sea de tipo entero
        if (!dimention.getResultTypeChained().contains("Int")) {
            throw new SemanticException(identifier, "La dimensión debe ser de tipo entero. Se encontró un tipo de dato " + dimention.getResultType() + ".", true);
        }

        //Setea el tipo de retorno
        switch (type.getIDToken()) {
            case typeINT:
                resultType = IDToken.typeArrayINT.toString();
                break;
            case typeSTR:
                resultType = IDToken.typeArraySTR.toString();
                break;
            case typeCHAR:
                resultType = IDToken.typeArrayCHAR.toString();
                break;
            default: //BOOL
                resultType = IDToken.typeArrayBOOL.toString();
                break;
        }
        
        setResultType(resultType);

        //Si tiene encadenado, lo consolida
        if (rightChained != null) {
            rightChained.consolidate(st, struct, method, this);
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
            tabs + "    \"tipo\": \"" + "CreateArray" + "\",\n" +
            tabs + "    \"tipoArray\": \""  + type.getLexema() + "\",\n" +
            tabs + "    \"resultadoDeTipo\": \""  + resultType + "\",\n" +
            tabs + "    \"dimension\": " + dimention.toJSON(tabs + "    ") + "\n" +
            tabs + "}";
    }
    
    public String generateCode(String sStruct, String sMethod){
        String asm="";

        //obtener dimention
        int dimentionArray=0;
        int space = 0;
        //int dimentionArray = this.dimention*4;
        
        // asm += "li $v0, 9\t\t\t\t\t\t#Reserve memory in the heap for the CIR\n";
        // asm += "li $a0,"+dimentionArray + "\n";
        // asm += "syscall\n";

        // //Guarda la referencia a la vtable de Array (en el inicio del CIR)
        // asm += "la $t0, " + "Array_vtable\n";
        // asm += "sw $t0, 0($v0)\n";
        // space += 4;

        // //en la posicion siguiente guardar la dimension del array. 4($v0)
        // asm += "sw $t0, " + space + "($v0)" + "\n";
        // space += 4;

        // //en las posiciones siguientes inicializar las posiciones del array
        // for (int i = 0; i < dimentionArray; i++) {
        //     asm += Static.initCirData(identifier.getIDToken(), space + (i * 4)) +"\n";
        //     asm += 4;
        // }

        // //Guarda el puntero al CIR en el stack
        // asm += "sw $v0, 0($sp)\t\t\t\t\t#Saves the pointer in stack\naddi $sp, $sp, -4\n";
    
        //asm += "move $a0, $v0 # $a0 contiene el puntero al CIR del arreglo ";
            
        
        return asm;
    }
}
