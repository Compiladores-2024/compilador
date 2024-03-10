package src.lib.lexicalHelper.resultTypes;

import src.lib.tokenHelper.Token;

public class TokenCheckerResult {
    private Token token;
    private boolean endRead;
    private String descriptionError;

    public TokenCheckerResult (Token token, boolean endRead, String descriptionError) {
        this.token = token;
        this.endRead = endRead;
        this.descriptionError = descriptionError;
    }
    public TokenCheckerResult () {
        token = null;
        endRead = false;
        descriptionError = null;
    }

    public void setDescriptionError(String descriptionError) {
        this.descriptionError = descriptionError;
    }
    public void setEndRead(boolean endRead) {
        this.endRead = endRead;
    }
    public void setToken(Token token) {
        this.token = token;
    }

    public String getDescriptionError() {
        return descriptionError;
    }
    public Token getToken() {
        return token;
    }
    public boolean isEndRead() {
        return endRead;
    }

}
