package src.lib.semanticHelper.astHelper.sentences;

public abstract class Sentence {
    
    private String currentMethod;
    private String currentStruct;

    public Sentence(String struct, String method){
        this.currentStruct = struct;
        this.currentMethod = method;
    }

    public String getNameStruct(){
        return this.currentStruct;
    }
    public String getNameMethod(){
        return this.currentMethod;
    }
    public abstract String toJSON(String tabs);
}
