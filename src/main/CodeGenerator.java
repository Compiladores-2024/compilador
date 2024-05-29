package src.main;

import src.lib.semanticHelper.AST;
import src.lib.semanticHelper.SymbolTable;

public class CodeGenerator {

    private SymbolTable st;
    private AST ast;
    private String asm;

    public CodeGenerator(SymbolTable st, AST ast){
        this.st=st;
        this.ast=ast;
    }

    public String generateAsm(){
        
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
