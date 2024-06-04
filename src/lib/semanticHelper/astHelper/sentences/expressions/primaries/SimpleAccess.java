package src.lib.semanticHelper.astHelper.sentences.expressions.primaries;

import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.IDToken;
import src.lib.tokenHelper.Token;

/**
 * Nodo que representa un acceso simple
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 17/05/2024
 */
public class SimpleAccess extends Primary{

    /**
     * Constructor de la clase.
     * 
     * @param identifier Identificador
     * @param rightChained Encadenado
     */
    public SimpleAccess (Token identifier, Primary rightChained) {
        super(identifier, rightChained);
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
        //Valida que exista, solo si no es una constante (literal, false, true, nil)
        String idToken = identifier.getIDToken().toString();

        //si se trata de acceder a self en un metodo static es error
        if (idToken.equals("self") && method.isStatic()){
            throw new SemanticException(identifier, "Acceso self invalido en metodo static" + ".", true);
        }
        if (!idToken.contains("literal") && !idToken.contains("false") && !idToken.contains("true") && !idToken.contains("nil")) {
            variableMethodExist(st, struct, method, leftExpression);
        }
        else{
            // se asigna el resultType
            setResultType(idToken.contains("literal") ? idToken : (idToken.contains("nil") ? "NIL" : "Bool"));
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
    public String toJSON(String tabs){
        return "{\n" +
            tabs + "    \"tipo\": \"" + "SimpleAccess" + "\",\n" +
            tabs + "    \"nombreVariable\": " + (identifier.getIDToken().equals(IDToken.constSTR) ? "" : "\"") + identifier.getLexema() + (identifier.getIDToken().equals(IDToken.constSTR) ? "" : "\"") + ",\n" +
            tabs + "    \"tipoDeDato\": \"" + identifier.getIDToken() + "\",\n" +
            tabs + "    \"resultadoDeTipo\": \""  + resultType + "\",\n" +
            tabs + "    \"encadenado\": "  + (rightChained == null ? ("\"\"")  : rightChained.toJSON(tabs + "    ")) + "\n" +
            tabs + "}";
    }

    public String generateCode(String registerResult){
        String asm="";

        //asm += 
        return asm;
    }

}
