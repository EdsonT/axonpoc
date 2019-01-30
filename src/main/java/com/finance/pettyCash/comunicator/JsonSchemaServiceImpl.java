package com.finance.pettyCash.comunicator;

import com.finance.pettyCash.commons.Constants;
import com.finance.pettyCash.commons.enums.Errors;
import com.finance.pettyCash.commons.exceptions.BaseException;
import com.finance.pettyCash.commons.exceptions.ErrorInformation;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.util.JSONParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
public class JsonSchemaServiceImpl implements JsonSchemaService {
    private RestTemplate restTemplate;

    public JsonSchemaServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public JSONObject getSchemmaData(String formCode, String sectionCode) {
        JSONObject info = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response;
        List<Errors> errors = new ArrayList<>();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        try {
            //todo call to discovery to obtain Link Schemma
            String formatted_URL = MessageFormat.format(Constants.LINK_SCHEMMA, formCode, sectionCode);
            response = restTemplate.exchange(formatted_URL, HttpMethod.GET, entity, String.class);
        } catch (HttpServerErrorException e) {
            throw new BaseException(Arrays.asList(Errors.HTTP_SERVER_ERROR_EXCEPTION));
        }
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                JsonParser parser = new JsonParser();
                parser.parse(response.getBody());
            } catch (JSONParseException e) {
                throw new BaseException(Arrays.asList(Errors.JSON_FORMAT_NOT_VALID));
            }
            try {
                JSONObject jsonObject = new JSONObject(response.getBody());
                if (!jsonObject.isNull("content")) {
                    if (jsonObject.getJSONObject("content") instanceof JSONObject){
                        info = new JSONObject(response.getBody()).getJSONObject("content");
                        return info;
                    }
                } else if (!jsonObject.isNull("errors")) {
                    if ((new JSONObject(response.getBody())).get("errors") instanceof JSONArray) {
                        JSONArray array = (new JSONObject(response.getBody())).getJSONArray("errors");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject row = array.getJSONObject(i);
                            String errorSchemma = row.getString("alias");
                            if (errorSchemma.equalsIgnoreCase("INVALID_FORM_SECTION")) {
                                errors.add(Errors.INVALID_FORM_SECTION);
                            }
                            if (errorSchemma.equalsIgnoreCase("SCHEMA_NOT_FOUND")) {
                                errors.add(Errors.SCHEMA_NOT_FOUND);
                            }
                            if (errorSchemma.equalsIgnoreCase("INVALID_FORM_DEFINITION_CODE")) {
                                errors.add(Errors.INVALID_FORM_DEFINITION_CODE);
                            }
                        }
                        if (!errors.isEmpty()){
                            throw new BaseException(errors);
                        }
                    }
                }
            } catch (JSONParseException e) {
                throw new BaseException(Arrays.asList(Errors.JSON_FORMAT_NOT_VALID));
            } catch (JSONException e) {
                throw new BaseException(Arrays.asList(Errors.JSON_FORMAT_NOT_VALID));
            }
        } else {
            throw new BaseException(Arrays.asList(Errors.HTTP_SERVER_ERROR_EXCEPTION));
        }
        return null;
    }
}
