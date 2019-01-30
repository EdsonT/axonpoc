package com.finance.pettyCash.queryModel.operationalView.openPettyCash;

import com.finance.pettyCash.queryModel.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "O_pettyCashOpened")
public class PettyCash extends BaseEntity {

    protected String aggregateCode;
    protected String accountCode;
    protected String accountName;
    protected String houseCode;
    protected String houseName;

    public PettyCash(){}

    public String getAccountCode() {
        return accountCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public String getHouseName() {
        return houseName;
    }

    public String getAggregateCode() {
        return aggregateCode;
    }

}
