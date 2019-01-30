package com.finance.pettyCash.queryModel.operationalView.openPettyCash;

public class PettyCashQuery extends PettyCash {

    public PettyCashQuery(String aggregateCode, String accountCode, String accountName,
                          String houseCode, String houseName) {
        this.aggregateCode = aggregateCode;
        this.accountCode = accountCode;
        this.accountName = accountName;
        this.houseCode = houseCode;
        this.houseName = houseName;
    }


}
