package com.finance.pettyCash.commons.exceptions;

import com.finance.pettyCash.commons.enums.Errors;

public class ErrorInformation {
    private String alias;
    private String field;
    private String message;

    public ErrorInformation() {
    }

    public ErrorInformation(String alias, String field, String message) {
        this.alias = alias;
        this.field = field;
        this.message = message;
    }

    public void create(String alias, String description, String field) {
        this.alias = alias;
        this.message = description;
        this.field = field;
    }

    public void updateMessage(String message) {
        this.message = this.message + ", " + message;
    }

    public String getAlias() {
        return alias;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    public void defaultError(Enum error, String message) {
        create(Errors.NOTFOUND_ERROR_IN_DATABASE.toString(), error + " " + message, "database");
    }

    @Override
    public String toString() {
        return "ErrorInformation{" +
                "alias='" + alias + '\'' +
                ", field='" + field + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
