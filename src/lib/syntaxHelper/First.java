package src.lib.syntaxHelper;

import java.util.ArrayList;

import src.lib.tokenHelper.IDToken;


public class First {
    final ArrayList<IDToken> firstProgram;
    public First(){
        firstProgram = new ArrayList<IDToken>(){{
            add(IDToken.idSTART);
            add(IDToken.pSTRUCT);
            add(IDToken.pIMPL);

        }};

    }
    public boolean program(IDToken idToken){
        
        return compare(firstProgram, idToken);
    }
    private boolean compare(ArrayList<IDToken> firsts, IDToken idToken){
        return firsts.contains(idToken);
    }
}
