package com.finance.pettyCash.queryModel.finalView.activeYoungPersons;


import com.finance.pettyCash.queryModel.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(
        collection = "F_activeYoungPersons"
)
public class FActiveYoungPersons extends BaseEntity {
    private String code;

    private String name;

    private String houseCode;

    private String Beltra;

    public FActiveYoungPersons() {
    }

    public FActiveYoungPersons(String code, String name, String houseCode, String Beltra) {
        this.code = code;
                this.name = name;
                this.houseCode = houseCode;
                this.Beltra = Beltra;
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

    public String getBeltra() {
        return Beltra;
    }
}
