package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import java.util.ArrayList;

import src.lib.Static;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

/**
 * Nodo que representa acceso a método.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 17/05/2024
 */
public class MethodAccess extends Primary{
    private ArrayList<Expression> params;

    /**
     * Constructor de la clase.
     * @param identifier Identificador 
     * @param params Expresiones de los parámetros del método
     * @param rightChained Encadenado
     */
    public MethodAccess (Token identifier, ArrayList<Expression> params, Primary rightChained) {
        super(identifier, rightChained);
        this.params = params;
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
        Method methodToCheckParams;

        //Valida que el método exista
        variableMethodExist(st, struct, method, leftExpression);

        //Obtiene el metodo al que hace referencia
        if (leftExpression == null) {
            methodToCheckParams = struct.getMethod(identifier.getLexema());
        } else {
            methodToCheckParams = st.getStruct(leftExpression.getResultType()).getMethod(identifier.getLexema());
        }

        //Consolida los parametros
        Static.consolidateParams(params, st, struct, method, methodToCheckParams, identifier);

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
        int count = params.size();
        String paramsJSON = count > 0 ? "[\n" : "[";

        for (Expression expression : params) {
            paramsJSON += tabs + "        " + expression.toJSON(tabs + "        ") +
                (count > 1 ? "," : "") + "\n";
            count--;
        }

        paramsJSON += (params.size() > 0 ? (tabs + "    ]") : "]");

        return "{\n" +
            tabs + "    \"tipo\": \"" + "MethodAccess" + "\",\n" +
            tabs + "    \"nombreMetodo\": \"" + identifier.getLexema() +  "\",\n" +
            tabs + "    \"resultadoDeTipo\": \""  + resultType + "\",\n" +
            tabs + "    \"parametros\": " +  (paramsJSON == "" ? ("\"\"") : paramsJSON) + ",\n" +
            tabs + "    \"encadenado\": "  + (rightChained == null ? ("\"\"")  : rightChained.toJSON(tabs + "    ")) + "\n" +
        tabs + "}";
    }

    public String generateCode(String sStruct, String sMethod){
        String asm="";

        if (sStruct.equals("IO")){
            Expression param = params.get(0);
            switch (this.identifier.getLexema()) {
                
                case "out_int":
                    if (param.getResultTypeChained().equals("Int")){
                        // hay que calcular el offset si es varLocal, parametro de un metodo o atributo de clase
                    }
                    else{
                        //es literal Int
                        if (param.getResultTypeChained().equals("literal Int")){
                            asm += "la $a0, " + param.getIdentifier().getLexema() + "\n";
                        }
                    }
                    asm += "jal " + "IO_" + this.identifier.getLexema() + "\n";
                    break;
                case "out_str":
                    if (param.getResultTypeChained().equals("Str")){
                        // hay que calcular el offset si es varLocal, parametro de un metodo o atributo de clase
                    }
                    else{
                        //es literal Str
                        if (param.getResultTypeChained().equals("literal Str")){
                            //declarar str
                            asm += ".data\n";
                            int countLiteralStr = symbolTable.addLiteralStrCount();
                            asm += "literal_str_" + countLiteralStr + ":" + " .asciiz " + param.getIdentifier().getLexema() + "\n";
                            asm += ".text\n";
                            asm += "la $a0, " + "literal_str_" + countLiteralStr + "\n";
                        }
                            
                    }
                    asm += "jal IO_out_str\n"; 
                    break;
                case "out_char":
                    if (param.getResultTypeChained().equals("Char")){
                        // hay que calcular el offset si es varLocal, parametro de un metodo o atributo de clase
                        
                    }
                    else{
                        //es literal Char
                        if (param.getResultTypeChained().equals("literal Char")){
                            asm += "li $a0, " + param.getIdentifier().getLexema() + "\n";
                        }
                    }
                    asm += "jal IO_out_char\n";
                    break;
                case "out_bool":
                    // es pTrue
                    if (param.getIdentifier().getLexema().equals("true")){
                        asm += "la $a0, 1\n";
                    }
                    else{
                        // es pFalse
                        if (param.getIdentifier().getLexema().equals("false")){
                            asm += "la $a0, 0\n";
                        }
                        else{
                            // es una variable de tipo Bool
                            // hay que calcular el offset si es varLocal, parametro de un metodo o atributo de clase
                        }
                    }
                    asm += "jal IO_out_bool\n";
                    break;
                case "out_array_int":
                    break;
                case "out_array_str":
                    break;
                case "out_array_bool":
                    break;
                case "out_array_char":
                    break;
                case "in_str":
                    break;    
                case "in_int":
                    break;
                case "in_bool":
                    break;
                case "in_char":
                    break;

                default:
                    break;
            }

        }
        //asm += 
        return asm;
    }
}
