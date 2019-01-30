package com.finance.pettyCash.queryModel.finalView.activeHouses;

import com.finance.pettyCash.queryModel.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(
        collection = "F_activeHouses"
)
public class FActiveHouses extends BaseEntity {
    private String code;

    private String name;

    public FActiveHouses() {
    }

    public FActiveHouses(String code, String name) {
        this.code = code;
                this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
