package com.finance.pettyCash.comunicator;

import org.json.JSONObject;


public interface JsonSchemaService {
    JSONObject getSchemmaData (String formCode, String sectionCode);
}
