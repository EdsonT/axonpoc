package com.finance.pettyCash.base.BaseController;

import com.finance.pettyCash.commons.dtos.request.PettyCashRequest;
import com.finance.pettyCash.commons.dtos.response.Response;
import com.finance.pettyCash.commons.helpers.ValidatorHelper;
import com.finance.pettyCash.commons.tool.JsonTool;
import com.finance.pettyCash.commons.validators.JsonValidator;
import com.finance.pettyCash.services.BusinessErrorService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class BaseController {
    @Autowired
    BusinessErrorService businessErrorService;

    protected ValidatorHelper validateRequest(PettyCashRequest pettyCashRequest, String path) throws IOException {
        JSONObject schema = JsonTool.loadJsonSchema(path);
        ValidatorHelper validator = JsonValidator.validate(schema, JsonTool.getJSONObjectFromObject(pettyCashRequest));
        return validator;
    }

    protected ResponseEntity getErrors(JSONObject error) {
        Response response = new Response();
        response.setErrors(businessErrorService.getResponseErrors(error));
        return ResponseEntity.ok(response);
    }
}
