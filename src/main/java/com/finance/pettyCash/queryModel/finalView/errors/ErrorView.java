package com.finance.pettyCash.queryModel.finalView.errors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "O_errors")
public class ErrorView {
    @Id
    private String Id;
    private Integer code;
    private String type;
    private String alias;
    private String field;
    private String message;
    private String language;

    public String getId() {
        return Id;
    }

    public Integer getCode() {
        return code;
    }

    public String getType() {
        return type;
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

    public String getLanguage() {
        return language;
    }
}
