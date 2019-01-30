package com.finance.pettyCash.commons.tool;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class JsonTool {
    private JsonTool() {
    }

    public static String getString(Object o, String microService, String type) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map json = mapper.convertValue(o, Map.class);
        json.put("className", o.getClass().getSimpleName());
        json.put("appName", microService);
        json.put("type", type);
        return mapper.writeValueAsString(json);
    }

    public static Object getJSONObjectToObject(JSONObject jsonObject) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(jsonObject.toMap(), Object.class);
    }

    public static JSONObject getJSONObjectFromObject(Object object) {
        Gson gson = new Gson();
        return new JSONObject(gson.toJson(object));
    }

    public static JSONObject loadJsonSchema(String file) throws IOException {
        InputStream inputStream = TypeReference.class.getResourceAsStream(file);
        ObjectMapper mapper = new ObjectMapper();
        return new JSONObject(mapper.readValue(inputStream, Map.class));
    }
}
