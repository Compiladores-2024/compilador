package src.main;

import src.lib.Static;
import src.lib.exceptionHelper.LexicalException;
import src.lib.exceptionHelper.SemanticException;
import src.lib.exceptionHelper.SyntacticException;

public class CodeGenerator {

    private SyntacticAnalyzer syntacticAnalyzer;
    private String asm;
    private String resultPath;

    public CodeGenerator(String path){
        resultPath = path.split(".ru")[0];

        //Inicializa el analizador sintactico
        syntacticAnalyzer = new SyntacticAnalyzer(path);
    }

    public void run () throws LexicalException, SyntacticException, SemanticException {
        //Analiza el código fuente
        syntacticAnalyzer.run();

        //Genera el codigo MIPS
        generateAsm();

        //Escribe el resultado de la tabla de simbolos
        Static.write(syntacticAnalyzer.toJSON().get(0), resultPath + ".ts.json");

        //Escribe el resultado del ast
        Static.write(syntacticAnalyzer.toJSON().get(1), resultPath + ".ast.json");

        //Escribe el codigo MIPS
        Static.write(asm, resultPath + ".asm");
    }

    private String generateAsm(){
        
        asm+=".data\n";

        //incluir los cir de cada struct en .data
        
        asm += "main: \n";

        //incluir en .text info de vt, metodos y constructores de struts declarados, y metodos de structs predefinidos
        
        //añadir en asm la generacion de codigo de cada nodo del ast

        return asm;
    }


    public void generateData(){

    }

    public void generateText(){

    }
}
