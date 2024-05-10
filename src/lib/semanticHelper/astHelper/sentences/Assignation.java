package src.lib.semanticHelper.astHelper.sentences;

import java.beans.Expression;

import src.lib.semanticHelper.astHelper.sentences.expressions.primaries.SimpleAccess;

public class Assignation extends Sentence{
    
    private SimpleAccess leftSide;
    private Expression rightSide;
}
