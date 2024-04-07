package src.run;

import src.lib.Const;
import src.lib.Static;
import src.lib.exceptionHelper.SyntacticException;
import src.main.SyntacticAnalyzer;

public class SyntacticRunner {
    public static void main(String[] args) {
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
            //Captura cualquier otro tipo de error y lo muestra por consola
            catch (Exception e) {
                System.out.println("Ocurrio un error al analizar sintacticamente." + e.getMessage());
            }
        } else {
            System.out.println(Const.ERROR_READ_SOURCE);
        }
    }
}
