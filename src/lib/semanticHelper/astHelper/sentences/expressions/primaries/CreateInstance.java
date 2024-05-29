package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import java.util.ArrayList;

import src.lib.Static;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

/**
 * Nodo que representa instanciación de clases
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 17/05/2024
 */
public class CreateInstance extends Primary{
    
    private ArrayList<Expression> params;

    /**
     * Constructor de la clase.
     * @param id Identificador
     * @param params Expresiones de los parámetros del método constructor de la clase.
     * @param rightChained Encadenado
     */
    public CreateInstance (Token id, ArrayList<Expression> params, Primary rightChained) {
        super(id, rightChained);
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
        //Valida que la estructura exista
        structExist(st);

        //si el lexema es distinto de Object
        if (!identifier.getLexema().equals("Object")){
            //Consolida los parametros
            Static.consolidateParams(params, st, struct, method, st.getStruct(identifier.getLexema()).getMethod("Constructor"), identifier);
        }

        //Si tiene encadenado, lo consolida
        if (rightChained != null) {
            rightChained.consolidate(st, struct, method, this);
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
        int count = params.size();
        String paramsJSON = count > 0 ? "[\n" : "[";

        for (Expression expression : params) {
            paramsJSON += tabs + "        " + expression.toJSON(tabs + "        ") +
                (count > 1 ? "," : "") + "\n";
            count--;
        }

        paramsJSON += (params.size() > 0 ? (tabs + "    ]") : "]");
        return "{\n" +
            tabs + "    \"tipo\": \"" + "CreateInstance" + "\",\n" +
            tabs + "    \"identificador\": \"" + identifier.getLexema() +  "\",\n" +
            tabs + "    \"resultadoDeTipo\": \""  + resultType + "\",\n" +
            tabs + "    \"parámetros\": " +  (paramsJSON=="" ? ("\"\"") : paramsJSON) + ",\n" +
            tabs + "    \"encadenado\": "  + (rightChained == null ? ("\"\"")  : rightChained.toJSON(tabs + "    ")) + "\n" +
        tabs + "}";
    }

}
