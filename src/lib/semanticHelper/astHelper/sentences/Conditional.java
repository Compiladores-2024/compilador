package src.lib.semanticHelper.astHelper.sentences;

import java.util.ArrayList;

import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;

public class Conditional extends Sentence{
    
    private Expression condition;
    private ArrayList<Sentence> thenBlock; 
    private ArrayList<Sentence> elseBlock;


    public Conditional(Expression condition, ArrayList<Sentence> thenBlock,ArrayList<Sentence> elseBlock,String struct, String method  ) {
        super(struct, method);
        this.condition=condition;
        this.thenBlock=thenBlock;
        this.elseBlock=elseBlock;
    }

    @Override
    public String toJSON(String tabs){
        String thenJSON="[", elseJSON="[";

        //Genero el json del bloque then
        int countThen = thenBlock.size();
        for (Sentence sentence : thenBlock) {
            thenJSON+= tabs + sentence.toJSON(tabs) +  (countThen > 1 ? ",\n" : "\n" );
            countThen--;
        }
        thenJSON+=tabs+"]";

        int countElse = elseBlock.size();
        //Genero el json del bloque else
        for (Sentence sentence : elseBlock) {
            elseJSON += tabs + sentence.toJSON(tabs) +  (countElse > 1 ? ",\n" : "\n" );
            countElse--;
        }
        elseJSON+=tabs+"]";

        return tabs + "{\n" +
            tabs + "    \"nombre\": \"" + "Conditional" + "\",\n" + 
            tabs + "    \"struct\": \"" + this.getNameStruct() + "\",\n" +
            tabs + "    \"method\": \"" + this.getNameMethod() + "\",\n" +
            tabs + "    \"condition\": " + condition.toJSON(tabs) + ",\n" +
            tabs + "    \"thenBlock\": " + thenJSON + ",\n" +
            tabs + "    \"elseBlock\": " + (elseJSON == "" ? ("\"\"") : elseJSON ) + "\n" +
        tabs + "}";
    }
}
