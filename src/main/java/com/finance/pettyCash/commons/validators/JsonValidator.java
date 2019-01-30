package com.finance.pettyCash.commons.validators;

import com.finance.pettyCash.commons.exceptions.JsonSchemaValidationException;
import com.finance.pettyCash.commons.helpers.ValidatorHelper;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JsonValidator {

    public static ValidatorHelper validate(JSONObject object, JSONObject data) throws IOException {
        JSONObject error = new JSONObject();
        Schema schema = SchemaLoader.load(object);
        try {
            schema.validate(data);
        } catch (ValidationException ve) {
            JsonSchemaValidationException j = new JsonSchemaValidationException(ve, object);
            error = j.getFields();
        } catch (Exception e) {
            error.put("exceptionError", e.getMessage());
        }

        return new ValidatorHelper(error);
    }

}
