package src.lib.semanticHelper.astHelper.sentences;

import java.beans.Expression;
import java.util.ArrayList;

public class Loop extends Sentence{
    
    Expression condition;
    ArrayList<Sentence> loopBlock;
}
