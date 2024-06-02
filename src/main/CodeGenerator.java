package src.main;

import src.lib.Static;
import src.lib.exceptionHelper.LexicalException;
import src.lib.exceptionHelper.SemanticException;
import src.lib.exceptionHelper.SyntacticException;

public class CodeGenerator {

    private SyntacticAnalyzer syntacticAnalyzer;
    private String resultPath;

    public CodeGenerator(String path){
        resultPath = path.split(".ru")[0];

        //Inicializa el analizador sintactico
        syntacticAnalyzer = new SyntacticAnalyzer(path);
    }

    public void run () throws LexicalException, SyntacticException, SemanticException {
        //Analiza el código fuente y obtiene el codigo MIPS
        String code = syntacticAnalyzer.run();

        //Genera el codigo MIPS
        // generateAsm();

        //Escribe el resultado de la tabla de simbolos
        //Static.write(syntacticAnalyzer.toJSON().get(0), resultPath + ".ts.json");

        //Escribe el resultado del ast
        //Static.write(syntacticAnalyzer.toJSON().get(1), resultPath + ".ast.json");

        //Escribe el codigo MIPS
        Static.write(code, resultPath + ".asm");
    }
}
