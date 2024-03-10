package src.lib.lexicalHelper.resultTypes;

public class ValidatorResult {
    private String descriptionError;
    private boolean currentRead;
    private boolean currentReadWithChar;

    public ValidatorResult (boolean currentRead, boolean currentReadWithChar, String descriptionError) {
        this.currentRead = currentRead;
        this.currentReadWithChar = currentReadWithChar;
        this.descriptionError = descriptionError;
    }
    public ValidatorResult () {
        descriptionError = null;
        currentRead = false;
        currentReadWithChar = false;
    }


    public void setCurrentRead(boolean currentRead) {
        this.currentRead = currentRead;
    }
    public void setCurrentReadWithChar(boolean currentReadWithChar) {
        this.currentReadWithChar = currentReadWithChar;
    }
    public void setDescriptionError(String descriptionError) {
        this.descriptionError = descriptionError;
    }

    public boolean isCurrentRead() {
        return currentRead;
    }
    public boolean isCurrentReadWithChar() {
        return currentReadWithChar;
    }
    public String getDescriptionError() {
        return descriptionError;
    }
}
