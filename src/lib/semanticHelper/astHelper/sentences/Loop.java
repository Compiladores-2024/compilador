package src.lib.semanticHelper.astHelper.sentences;

import src.lib.semanticHelper.astHelper.sentences.expressions.Expression;
import java.util.ArrayList;

public class Loop extends Sentence{
    
    Expression condition;
    ArrayList<Sentence> loopBlock;
}
