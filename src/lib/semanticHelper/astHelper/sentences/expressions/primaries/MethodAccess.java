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

    /**
     * SIEMPRE VA A SER LADO DERECHO
     * POSEE LA REFERENCIA A LA VARIABLE EN $v0
     * POSEE EL NOMBRE DE LA ESTRUCTURA A LA QUE HACE REFERENCIA EN LEFTSIDE
     */
    public String generateCode(String sStruct, String sMethod){
        String asm="#Method access code\n";
        Method m = symbolTable.getStruct(getsLeftSide()).getMethod(identifier.getLexema());
        int position = 0;

        //Obtiene la referencia a la vtable
        asm += "lw $v0, 0($v0)\t\t\t\t\t#Get the VTable reference\n";
        //$v0 ahora posee la direccion de memoria de la vtable

        //Valida si es metodo estatico o no para calcular el offset. Tiene en cuenta el constructor
        position = m.getPosition() + (m.isStatic() ? 0 : 1);
        
        //Obtiene la posicion del metodo en la vtable. Index: (Position + 1) * 4. Porque el constructor esta primero
        asm += "lw $t0, " + (position * 4) + "($v0)\t\t\t\t\t#Get the method reference\n";
        
        //Calcula los parametros
        for (Expression expression : params) {
            asm += expression.generateCode(sStruct, sMethod);
            if (! ((expression.getResultType().contains("literal")) 
                || (expression.getIdentifier().getLexema().equals("true")) 
                || (expression.getIdentifier().getLexema().equals("false"))) ){
                
                asm += "lw $v0, 0($v0)\n";
            }
            asm += "sw $v0, 0($sp)\naddiu $sp, $sp, -4\n";
        }

        //Realiza la llamada al metodo
        asm += "#Call method\njal " + getsLeftSide() + "_" + identifier.getLexema() + "\n";



        // if (sStruct.equals("IO")){
        //     Expression param = params.get(0);
        //     switch (this.identifier.getLexema()) {
                
        //         case "out_int":
        //             if (param.getResultTypeChained().equals("Int")){
        //                 // hay que calcular el offset si es varLocal, parametro de un metodo o atributo de clase
        //             }
        //             else{
        //                 //es literal Int
        //                 if (param.getResultTypeChained().equals("literal Int")){
        //                     asm += "la $a0, " + param.getIdentifier().getLexema() + "\n";
        //                 }
        //             }
        //             break;
        //         case "out_str":
        //             if (param.getResultTypeChained().equals("Str")){
        //                 // hay que calcular el offset si es varLocal, parametro de un metodo o atributo de clase
        //             }
        //             else{
        //                 //es literal Str
        //                 if (param.getResultTypeChained().equals("literal Str")){
        //                     //declarar str
        //                     asm += ".data\n";
        //                     int countLiteralStr = symbolTable.addLiteralStrCount();
        //                     asm += "literal_str_" + countLiteralStr + ":" + " .asciiz " + param.getIdentifier().getLexema() + "\n";
        //                     asm += ".text\n";
        //                     asm += "la $a0, " + "literal_str_" + countLiteralStr + "\n";
        //                 }
                            
        //             }
        //             break;
        //         case "out_char":
        //             if (param.getResultTypeChained().equals("Char")){
        //                 // hay que calcular el offset si es varLocal, parametro de un metodo o atributo de clase
                        
        //             }
        //             else{
        //                 //es literal Char
        //                 if (param.getResultTypeChained().equals("literal Char")){
        //                     asm += "li $a0, " + param.getIdentifier().getLexema() + "\n";
        //                 }
        //             }
        //             break;
        //         case "out_bool":
        //             // es pTrue
        //             if (param.getIdentifier().getLexema().equals("true")){
        //                 asm += "li $a0, 1\n";
        //             }
        //             else{
        //                 // es pFalse
        //                 if (param.getIdentifier().getLexema().equals("false")){
        //                     asm += "li $a0, 0\n";
        //                 }
        //                 else{
        //                     // es una variable de tipo Bool
        //                     // hay que calcular el offset si es varLocal, parametro de un metodo o atributo de clase
        //                 }
        //             }
        //             break;
        //         case "out_array_int":
        //             //
        //             break;
        //         case "out_array_str":
        //             //
        //             break;
        //         case "out_array_bool":
        //             //
        //             break;
        //         case "out_array_char":
        //             //
        //             break;
        //         case "in_str":
        //         case "in_int":
        //         case "in_bool":
        //         case "in_char":
        //             //solo realizan el jal al metodo
        //             break;
        //         default:
        //             break;
        //     }
        //     asm += "jal " + "IO_" + this.identifier.getLexema() + "\n";
        // }
        // //asm += 
        return asm;
    }
}
