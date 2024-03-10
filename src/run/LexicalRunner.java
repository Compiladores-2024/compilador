package src.run;


import java.io.IOException;
import java.util.ArrayList;

import src.lib.tokenHelper.Token;
import src.main.LexicalAnalyzer;
import src.lib.Static;
import src.lib.exceptionHelper.LexicalException;
import src.lib.lexicalHelper.FileManager;
import src.lib.CustomError;
/**
 * Clase LexicalRunner encargada de ejecutar el analizador lexico
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 06/03/2024
 */

public class LexicalRunner {
    
    public static void main(String[] args) throws IOException {
        

        if (args.length<1){

            throw new LexicalException(new CustomError(0, 0, "No se ingreso codigo fuente"), null);

        }

        LexicalAnalyzer lexA= new LexicalAnalyzer(args[0]);

        ArrayList<Token> tokenList = new ArrayList<Token>();

        boolean flag=true;
        while (flag){
            Token token = lexA.nextToken();
            
            // System.out.println("lex: "+token.getLexema());
            //si el token recibido es distinto de null
            if (token != null){
                
                tokenList.add(token);

            }
            else{
                flag=false;
            }
        }


        // si se solicita generar un archivo de salida con los tokens
        if (args.length == 2) {
            Static.showTokens(tokenList, args[1]);
            
            // sino solo imprime por pantalla
        }else{
            Static.showTokens(tokenList, null);
        }

    }
}

