package src.run;

import src.lib.Const;
import src.lib.Static;
import src.lib.exceptionHelper.LexicalException;
import src.lib.exceptionHelper.SemanticException;
import src.lib.exceptionHelper.SyntacticException;
import src.main.SyntacticAnalyzer;

/**
 * Clase SyntacticRunner encargada de ejecutar el analizador sintáctico
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 08/04/2024
 */
public class SyntacticRunner {
    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        args = new String[] {"src/test/resources/syntactic/00.ru"};
        if (args.length > 0) {
            try{
                SyntacticAnalyzer syntacticAnalyzer= new SyntacticAnalyzer(args[0]);

                //Comienza la ejecución
                syntacticAnalyzer.run();
            }
            //Captura el error sintactico y lo muestra por pantalla 
            catch (SyntacticException e) {
                Static.writeError(e, null);
            }
            catch (SemanticException e) {
                Static.writeError(e, null);
            }
            catch (LexicalException e) {
                Static.writeError(e, null);
            }
            //Captura cualquier otro tipo de error y lo muestra por consola
            catch (Exception e) {
                System.out.println("Ocurrio un error al analizar sintacticamente." + e.getMessage());
            }
        } else {
            System.out.println(Const.ERROR_READ_SOURCE);
        }
    }
}
