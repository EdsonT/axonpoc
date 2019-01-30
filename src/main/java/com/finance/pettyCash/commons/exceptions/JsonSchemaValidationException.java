package com.finance.pettyCash.commons.exceptions;

import com.finance.pettyCash.commons.enums.Errors;
import com.finance.pettyCash.commons.validators.CustomMessageSchema;
import com.finance.pettyCash.commons.validators.ValidationSchema;
import org.everit.json.schema.NumberSchema;
import org.everit.json.schema.StringSchema;
import org.everit.json.schema.ValidationException;
import org.json.JSONObject;

import java.util.Arrays;


/**
 * @author Mirko Flores
 */
public class JsonSchemaValidationException extends RuntimeException {

    private final JSONObject fields;
    private final JSONObject properties;

    public JsonSchemaValidationException(ValidationException v,
                                         JSONObject schema) {
        this.properties = getProperties(schema, "properties");
        this.fields = addError(v, null);
    }

    private JSONObject getProperties(JSONObject jsonObject, String key) {
        if (jsonObject.has(key)) {
            return jsonObject.getJSONObject(key);
        }
        throw new BaseException(Arrays.asList(Errors.JSON_KEY_NOT_FOUND));
    }

    private JSONObject addError(ValidationException v, JSONObject jsonObject1) {
        JSONObject jsonObject;
        if (jsonObject1 != null) {
            jsonObject = jsonObject1;
        } else {
            jsonObject = new JSONObject();
        }
        int size = v.getCausingExceptions().size();
        String messageKey = "message";
        if (size == 0) {
            String key = getKey(v, v.getPointerToViolation());
            jsonObject.put(key, setMessage(getMessage(v.getMessage(), v.getViolatedSchema(), true, key) + ".",
                    messageKey));
        } else {
            for (ValidationException ve : v.getCausingExceptions()
                    ) {
                if (ve.getCausingExceptions().isEmpty()) {
                    if (ve.getMessage() != null) {
                        String key = getKey(ve, ve.getPointerToViolation());
                        String message = ve.getMessage();
                        if (jsonObject.has(key)) {
                            JSONObject beforeJsonMessage = jsonObject.getJSONObject(key);
                            String beforeMessage = verifyDot(beforeJsonMessage.getString(messageKey));
                            message = beforeMessage + ", " + getMessage(message,
                                    ve.getViolatedSchema(), false, key) + ".";
                        } else {
                            message = getMessage(message, ve.getViolatedSchema(), true, key) + ".";
                        }
                        jsonObject.put(key, setMessage(message, messageKey));
                    }
                } else {
                    addError(ve, jsonObject);
                }
            }
        }
        return jsonObject;
    }

    private String verifyDot(String message) {
        if (message.charAt(message.length() - 1) == '.') {
            return message.substring(0, message.length() - 1);
        }
        return message;
    }

    private String getKey(ValidationException v, String key) {
        if (key.length() == 1) {
            String message = v.getMessage();
            int open = message.indexOf('[');
            int close = message.indexOf(']');
            return message.substring(open + 1, close);
        } else {
            if (key.contains("/")) {
                String[] keys = key.split("/");
                return keys[1];
            } else {
                return key.substring(2);
            }
        }
    }


    public JSONObject getFields() {
        return fields;
    }

    private String getMessage(String message, Object objectSchema, boolean isFirst, String key) {
        StringSchema stringSchema = null;
        NumberSchema numberSchema = null;
        if (objectSchema instanceof StringSchema) {
            stringSchema = (StringSchema) objectSchema;
        } else {
            if (objectSchema instanceof NumberSchema) {
                numberSchema = (NumberSchema) objectSchema;
            }
        }
        ValidationSchema validationSchema = ValidationSchema.getTypeMessage(message);
        String typeMessage = null;
        if (validationSchema != null) {
            switch (validationSchema) {
                case REQUIRED:
                    typeMessage = CustomMessageSchema.REQUIRED.getMessage();
                    break;
                case TYPE:
                    typeMessage = CustomMessageSchema.TYPE.getMessage();
                    break;
                case PATTERN:
                    typeMessage = CustomMessageSchema.PATTERN.getMessage();
                    break;
                case SIZE_MAX:
                    typeMessage = String.format(CustomMessageSchema.SIZE_MAX.getMessage(),
                            stringSchema.getMaxLength());
                    break;
                case SIZE_MIN:
                    typeMessage = String.format(CustomMessageSchema.SIZE_MIN.getMessage(),
                            stringSchema.getMinLength());
                    break;
                case ADDITIONAL:
                    typeMessage = CustomMessageSchema.ADDITIONAL.getMessage();
                    break;
                case PROPERTY_DEPENDENCIE:
                    typeMessage = CustomMessageSchema.PROPERTY_DEPENDENCIE.getMessage();
                    break;
                case MINIMUM_SIZE:
                    typeMessage = CustomMessageSchema.MINIMUN_SIZE.getMessage();
                    break;
                case MAXIMUM:
                    typeMessage = String.format(CustomMessageSchema.MAXIMUM.getMessage(), numberSchema.getMaximum());
                    break;
                case MINIMUM:
                    typeMessage = String.format(CustomMessageSchema.MINIMUN.getMessage(), numberSchema.getMinimum());
                    break;
            }
        } else {
            typeMessage = CustomMessageSchema.DEFAULT.getMessage();
        }
        StringBuilder init = new StringBuilder();
        if (isFirst) {
            JSONObject element = getProperties(properties, key);
            String title = element.getString("title");
            init.append(cleanTitle(title).trim() + " ");
        }
        return init.toString() + typeMessage.toLowerCase();
    }

    private JSONObject setMessage(String message, String key) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key, message);
        return jsonObject;
    }

    private String cleanTitle(String title) {
        if (title.charAt(title.length() - 1) == '*') {
            return title.substring(0, title.length() - 1);
        }
        return title;
    }
}
