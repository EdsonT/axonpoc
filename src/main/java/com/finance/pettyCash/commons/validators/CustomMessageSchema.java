package com.finance.pettyCash.commons.validators;

/**
 * @author Mirko Flores
 */
public enum CustomMessageSchema {
    REQUIRED("is required"),
    ADDITIONAL("Character not allowed"),
    SIZE_MIN("must contain at least %d characters"),
    SIZE_MAX("must not exceed %d characters"),
    TYPE("Invalid type"),
    PROPERTY_DEPENDENCIE("Property not allowed"),
    PATTERN("does not comply with the established pattern"),
    MINIMUN_SIZE("is empty"),
    MINIMUN("is not higher or equal to %d"),
    MAXIMUM("is not lower or equal to %d"),
    DEFAULT("not message defined");
    private String message;

    CustomMessageSchema(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
