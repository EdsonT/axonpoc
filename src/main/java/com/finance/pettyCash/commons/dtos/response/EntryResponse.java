package com.finance.pettyCash.commons.dtos.response;

import com.finance.pettyCash.commandModel.domains.*;

import java.util.Date;

public class EntryResponse extends PettyCashResponse {
    public EntryResponse(FinancialCode financialCode, House house, Amount amount, Staff responsible, String description,
                         Balance balance, IncomeModel incomeModel, Date requestDate, Date registrationDate) {
        super(financialCode, house, amount, responsible, description, balance, incomeModel, requestDate, registrationDate);
    }

    public EntryResponse(FinancialCode financialCode, House house, Amount amount, Staff responsible, String description,
                         Balance balance, ExpenseModel expenseModel, Date requestDate, Date registrationDate) {
        super(financialCode, house, amount, responsible, description, balance, expenseModel, requestDate, registrationDate);
    }
}
