package src.run;

import src.lib.Const;
import src.lib.Static;
import src.lib.exceptionHelper.LexicalException;
import src.lib.exceptionHelper.SemanticException;
import src.lib.exceptionHelper.SyntacticException;
import src.main.SyntacticAnalyzer;
import src.main.CodeGenerator;

/**
 * Clase GenerationCodeRunner encargada de ejecutar el analizador sintáctico, semantico 
 * y finalmente genera un archivo .asm con instrucciones en mips para ser ejecutadas 
 * en mars 4.5
 * 
 * @author Cristian Serrano
 * @author Federico Gimenez
 * @since 29/05/2024
 */
public class GenerationCodeRunner {
    private GenerationCodeRunner () {}
    
    /** 
     * Main
     * @param args args
     */
    public static void main(String[] args) {
        // args = new String[] {"src/test/resources/semantic/sentences/error/TC_ERROR_OPERATION3.ru"};
        if (args.length > 0) {
            try{
                String asm;
                SyntacticAnalyzer syntacticAnalyzer= new SyntacticAnalyzer(args[0]);

                //Comienza la ejecución
                syntacticAnalyzer.run();

                // imprimir mensaje de exito semantico sentencias
                System.out.println("CORRECTO: SEMANTICO - SENTENCIAS");

                CodeGenerator codeGenerator = new CodeGenerator(syntacticAnalyzer.getSymbolTable(), syntacticAnalyzer.getAST());

                asm = codeGenerator.generateAsm();
                
                String ruta = args[0].split(".ru")[0];
                String asmPath = ruta+".asm"; 
                Static.write(asm, asmPath);

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
