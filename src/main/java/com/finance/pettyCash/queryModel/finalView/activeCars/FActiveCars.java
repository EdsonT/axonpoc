package com.finance.pettyCash.queryModel.finalView.activeCars;

import com.finance.pettyCash.queryModel.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(
        collection = "F_activeCars"
)
public class FActiveCars extends BaseEntity {
    private String code;

    private String name;

    private String houseCode;

    private String houseName;

    public FActiveCars() {
    }

    public FActiveCars(String code, String name, String houseCode, String houseName) {
        this.code = code;
                this.name = name;
                this.houseCode = houseCode;
                this.houseName = houseName;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public String getHouseName() {
        return houseName;
    }
}
