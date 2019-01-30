package com.finance.pettyCash.queryModel.operationalView.openPettyCash;

import com.finance.pettyCash.commandModel.domains.AccountModel;

public class PettyCashCmd extends PettyCash {


    public PettyCashCmd(String aggregateCode, String accountCode, String accountName, String houseCode, String houseName) {
        this.aggregateCode = aggregateCode;
        this.accountCode = accountCode;
        this.accountName = accountName;
        this.houseCode = houseCode;
        this.houseName = houseName;
    }

    public void close(AccountModel account) {
        this.accountCode = account.getCode().toString();
        this.accountName = account.getName();
        this.aggregateCode = null;
    }

    public void reopen(AccountModel account, String aggregateCode) {
        this.accountCode = account.getCode().toString();
        this.accountName = account.getName();
        this.aggregateCode = aggregateCode;
    }
}
