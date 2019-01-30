package com.finance.pettyCash.commons.dtos.response;

import com.finance.pettyCash.commandModel.domains.*;

import java.util.Date;

public class OpenCloseResponse extends PettyCashResponse {
    public OpenCloseResponse(FinancialCode financialCode, House house, Amount amount, Staff responsible, String description,
                             Balance balance, AccountModel accountModel, Date requestDate, Date registrationDate) {
        super(financialCode, house, amount, responsible, description, balance, accountModel, requestDate, registrationDate);
    }
}
