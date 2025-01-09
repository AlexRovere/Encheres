package fr.eni.encheres.exceptions;

public class EnchereException extends Exception{
    private String errorType;

    public EnchereException(String errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }
}
