package com.finance.pettyCash.queryModel.finalView.houses;

import com.finance.pettyCash.queryModel.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(
        collection = "F_houses"
)
public class FHouses extends BaseEntity {

    private String code;

    private String name;

    private String description;

    private String statusCode;

    private String status;

    public FHouses() {
    }

    public FHouses(String code, String name, String description, String statusCode, String status) {
        this.code = code;
                this.name = name;
                this.description = description;
                this.statusCode = statusCode;
                this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return status;
    }
}
