package src.lib;

import java.util.ArrayList;

import src.lib.exceptionHelper.CustomException;
import src.lib.exceptionHelper.SemanticException;
import src.lib.semanticHelper.SymbolTable;
import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import src.lib.semanticHelper.symbolTableHelper.Method;
import src.lib.semanticHelper.symbolTableHelper.Struct;
import src.lib.tokenHelper.Token;

/**
 * Esta clase se utilizará para realizar funciones estáticas en el programa.
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 07/03/2024
 */
public class Static {
    private Static () {}
    /**
     * Método que se utiliza para escribir los resultados del analizador léxico
     * ya sea por consola o en un fichero específico.
     * 
     * @since 09/03/2024
     * @param tokens Lista de Tokens que se escribirán.
     * @param path Path hacia el archivo resultante.
     */
    public static void writeTokens (ArrayList<Token> tokens, String path) {
        int count = tokens.size() - 1;
        //Genera el texto que se debe guardar
        String text = Const.SUCCESS_LEXICAL_HEADER + "\n";

        //Escribir cada elemento en una línea separada
        for (Token token : tokens) {
            text += token.toString() + (count > 0 ? "\n" : "");
            count--;
        }
        
        //Escribe o muestra el resultado
        if(path == null){
            System.out.println(text);
        }
        else {
            write(text, path);
        }
    }

    /**
     * Imprime o escribe un error.
     * 
     * @since 07/03/2024
     * @param error Tipo de dato error con los detalles a mostrar.
     * @param path Ubicación del archivo que guardará la respuesta.
     */
    public static void writeError(CustomException error, String path) {
        //Escribe el resultado
        if(path != null){
            write(error.getMessage(), path);
        }
        //Muestra el resultado por consola
        else {
            System.out.println(error.getMessage());
        }
    }

    /**
     * Escribe un string en un archivo dado.
     * 
     * @since 12/03/2024
     * @param text Texto completo a escribir.
     * @param path Ubicación al archivo en el que se guardará el resultado.
     */
    public static void write (String text, String path) {
        try {
            java.io.FileWriter writer = new java.io.FileWriter(path);
            writer.write(text);
            writer.close();
        }
        catch (Exception e) {
            System.out.println(Const.ERROR_CREATE_FILE_WRITER);
        }
    }

    /**
     * Valida si un caracter es mayúscula.
     * 
     * @since 09/03/2024
     * @param c Caracter a validar.
     * @return Booleando con la respuesta.
     */
    public static boolean isUppercase(char c) {
        return 64 < c && c < 91;
    }

    /**
     * Valida si un caracter es minúscula.
     * 
     * @since 09/03/2024
     * @param c Caracter a validar.
     * @return Booleando con la respuesta.
     */
    public static boolean isLowercase(char c) {
        return 96 < c && c < 123;
    }

    /**
     * Valida si un caracter es un número.
     * 
     * @since 09/03/2024
     * @param c Caracter a validar.
     * @return Booleando con la respuesta.
     */
    public static boolean isNumber(char c) {
        return 47 < c && c < 58;
    }

    /** 
     * Mapea el tipo de dato, solo si es de tipo primitivo
     * @param type Tipo de dato
     * @return String
     */
    public static String getPrimitiveDataType (String type) {
        String result = type;
        //Si posee la palabra literal, obtiene el tipo de dato primitivo
        if (type.contains("literal")) {
            result = type.split("literal")[1].trim();
        }
        return result;
    }

    
    /** 
     * Valida que el tipo de dato dado sea válido según herencia.
     * @param st Tabla de símbolos
     * @param origin Tipo de dato de origen
     * @param currentType Tipo de dato a validar
     * @param metadata Metadata para errores
     */
    public static void checkInherited (SymbolTable st, String origin, String currentType, Token metadata) {
        boolean isInherited = false;
        String resultType=currentType;
        //Solo si el tipo de dato no es void
        if (!currentType.equals("void")) {
            //Valida asignacion hereditaria, hasta llegar a Object o se encuentre herencia
            while (!currentType.equals("Object") && !isInherited) {
                //Parseo el tipo de dato
                currentType = getPrimitiveDataType(currentType);
                
                //Obtiene el padre 
                currentType = st.getStruct(currentType).getParent();
    
                //Valida si se obtuvo el tipo correcto
                isInherited = origin.equals(currentType);
            }
        }

        //Si no encuentra herencia, retorna error
        if (!isInherited) {
            throw new SemanticException(metadata, "Se esperaba una variable de tipo " + origin + " y se encontro una de tipo " + resultType + ".", true);
        }
    }

    
    /** 
     * Consolida los parámetros de un método.
     * 
     * @param params Parámetros a consolidar
     * @param st Tabla de símbolos
     * @param struct Estructura actual
     * @param method Método actual
     * @param methodToCheckParams Método a validar
     * @param identifier Identificador para manejo de errores
     */
    public static void consolidateParams (ArrayList<Expression> params, SymbolTable st, Struct struct, Method method, Method methodToCheckParams, Token identifier) {
        String resultType, paramType;

        //Si no se le envia la cantidad necesaria de parametros, retorna null
        if (methodToCheckParams.getParamsSize() != params.size()) {
            throw new SemanticException(identifier, "Cantidad de argumentos inválida.", true);
        }
        
        //Consolida los parametros
        for (Expression param : params) {
            //Consolida la expresion
            param.consolidate(st, struct, method, null);
            
            // Valida que el tipo de dato del parametro sea el mismo
            resultType = getPrimitiveDataType(param.getResultTypeChained());
            paramType = methodToCheckParams.getParamType(param.getPosition());
            //Si son tipos de datos distintos
            if (!paramType.equals(resultType)) {
                //Si el valor a enviar es nil
                if (resultType.equals("NIL")) {
                    //El parametro no debe ser de tipo dato primitivo
                    if (Const.primitiveTypes.contains(paramType)) {
                        throw new SemanticException(identifier, "Se esperaba un tipo de dato " + paramType + ". Se encontró " + resultType, true);
                    }
                }
                else {
                    //Valida asignacion hereditaria, hasta llegar a Object o se encuentre herencia
                    Static.checkInherited(st, paramType, resultType, identifier);
                }
            }
        }
    }
}
