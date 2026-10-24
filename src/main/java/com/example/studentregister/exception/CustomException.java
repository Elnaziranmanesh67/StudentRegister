package com.example.studentregister.exception;
import org.antlr.v4.runtime.misc.NotNull;

public class CustomException extends RuntimeException {
    @NotNull
    private CustomExceptionTypeEnum exceptionType;

    @NotNull
    private Class exceptionOccurredClass;
    @NotNull
    private String exceptionMessage;
    private String exceptionDescription;

    public CustomException(@NotNull CustomExceptionTypeEnum exceptionType, @NotNull Class exceptionOccurredClass, @NotNull CustomExceptionKey customExceptionKey, String exceptionDescription) {
        this.exceptionType = exceptionType;
        this.exceptionOccurredClass = exceptionOccurredClass;
        this.exceptionMessage = exceptionType.getValue().toUpperCase() + "." + exceptionOccurredClass.getSimpleName().toUpperCase() + "." + customExceptionKey.getValue().toUpperCase();
        this.exceptionDescription = exceptionDescription;
    }

    @Override
    public String toString() {
        return "CustomException{" +
                "exceptionType=" + exceptionType +
                ", exceptionOccurredClass=" + exceptionOccurredClass +
                ", exceptionMessage='" + exceptionMessage + '\'' +
                ", exceptionDescription='" + exceptionDescription + '\'' +
                '}';
    }

    @NotNull
    public CustomExceptionTypeEnum getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(@NotNull CustomExceptionTypeEnum exceptionType) {
        this.exceptionType = exceptionType;
    }

    @NotNull
    public Class getExceptionOccurredClass() {
        return exceptionOccurredClass;
    }

    public void setExceptionOccurredClass(@NotNull Class exceptionOccurredClass) {
        this.exceptionOccurredClass = exceptionOccurredClass;
    }

    @NotNull
    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(@NotNull String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionDescription() {
        return exceptionDescription;
    }

    public void setExceptionDescription(String exceptionDescription) {
        this.exceptionDescription = exceptionDescription;
    }
}
