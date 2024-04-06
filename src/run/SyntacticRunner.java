package src.run;

import src.lib.Const;
import src.lib.Static;
import src.lib.exceptionHelper.SyntacticException;
import src.main.LexicalAnalyzer;
import src.main.SyntacticAnalyzer;

public class SyntacticRunner {
    public static void main(String[] args) {
        if (args.length > 0) {
            String pathToResult = args.length == 2 ? args[1] : null;
            try{
                SyntacticAnalyzer synA= new SyntacticAnalyzer(args[0]);
                System.out.println("CORRECTO: ANALISIS SINTACTICO");
            }
            //Captura el error sintactico y lo muestra por pantalla 
            catch (SyntacticException e) {
                Static.writeError(e, pathToResult);
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
