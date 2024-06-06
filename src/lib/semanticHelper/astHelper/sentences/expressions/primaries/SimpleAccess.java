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

        //Setea la tabla de simbolos
        setSymbolTable(st);
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

    public String generateCode(String sStruct, String sMethod){
        String asm = "", auxString = "";
        Method auxMethod = null;

        //Guarda el valor correspondiente en $t0
        switch (identifier.getIDToken()) {
            case spIO:
                //Obtiene el metodo al que esta llamando
                if (rightChained != null){
                    //Obtiene el nombre del metodo
                    auxString = rightChained.getIdentifier().getLexema();

                    //Valida que el metodo exista
                    auxMethod = symbolTable.getStruct(identifier.getLexema()).getMethod(auxString);
                    if (auxMethod != null) {
                        //Genera el nombre del metodo (Estructura_nombremetodo)
                        auxString = identifier.getLexema() + "_" + auxString;

                        asm += "";
                    } else {
                        //ERROR, debe realizar un llamado a metodo existente
                    }
                } else {
                    //ERROR, debe tener encadenado
                }
                break;
            case spOBJECT:
                break;
            case typeINT:
                break;
            case typeSTR:
                break;
            case typeBOOL:
                break;
            case typeCHAR:
                break;
            case typeArrayINT:
                break;
            case typeArraySTR:
                break;
            case typeArrayBOOL:
                break;
            case typeArrayCHAR:
                break;
            case constINT: //Asigna el lexema
                asm += "li $t0, " + identifier.getLexema() + "\t\t\t\t\t\t#Assign the value\n";
                break;
            case constSTR:
                break;
            case constCHAR:
                break;
            case idSTRUCT:
                break;
            case idOBJECT: //Asigna la posicion de memoria del stack
                asm += "addi $t0, $fp, " + symbolTable.getLocalVariableOffset(sStruct, sMethod, identifier.getLexema()) + "\t\t\t\t#Saves the memory position of the variable\n";
                isOffset = true;
                break;
            case pFALSE: //Asigna 0
                asm += "li $t0, 0\t\t\t\t\t\t#Assign False (0)\n";
                break;
            case pTRUE: //Asigna 1
                asm += "li $t0, 1\t\t\t\t\t\t#Assign True (1)\n";
                break;
            case pNIL:
                break;
            default:
                break;
        }
        
        //Guarda el resultado en el stack y actualiza el sp
        asm += "sw $t0, 0($sp)\naddi $sp, $sp, -4\n";
        return asm;
    }

}
