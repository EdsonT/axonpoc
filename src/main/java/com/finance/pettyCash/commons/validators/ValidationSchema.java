package com.finance.pettyCash.commons.validators;

/**
 * @author Mirko Flores
 */
public enum ValidationSchema {
    REQUIRED("required key"),
    ADDITIONAL("extraneous key"),
    SIZE_MIN("expected minLength"),
    SIZE_MAX("expected maxLength"),
    TYPE("expected type"),
    //    DEPENDENCIE("property [%s] is required"),
    PROPERTY_DEPENDENCIE("property"),
    PATTERN("does not match pattern"),
    MINIMUM_SIZE(": minimum size:"),
    MINIMUM("is not higher or equal to"),
    MAXIMUM("is not lower or equal to");
    private String message;

    ValidationSchema(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ValidationSchema getTypeMessage(String message) {
        if (message.contains(REQUIRED.message)) {
            return REQUIRED;
        }
        if (message.contains(ADDITIONAL.message)) {
            return ADDITIONAL;
        }
        if (message.contains(SIZE_MAX.message)) {
            return SIZE_MAX;
        }
        if (message.contains(SIZE_MIN.message)) {
            return SIZE_MIN;
        }
        if (message.contains(PROPERTY_DEPENDENCIE.message)) {
            return PROPERTY_DEPENDENCIE;
        }
        if (message.contains(PATTERN.message)) {
            return PATTERN;
        }
        if (message.contains(TYPE.message)) {
            return TYPE;
        }
        if (message.contains(MINIMUM_SIZE.message)) {
            return MINIMUM_SIZE;
        }
        if (message.contains(MINIMUM.message)) {
            return MINIMUM;
        }
        if (message.contains(MAXIMUM.message)) {
            return MAXIMUM;
        }
        return null;
    }
}
