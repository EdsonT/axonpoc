package com.finance.pettyCash.commons.dtos.response;


import com.finance.pettyCash.commandModel.domains.*;

import java.util.Date;
import java.util.List;

public class CorrectResponse extends EntryResponse {
    List<Balance> balanceAffected;

    public CorrectResponse(FinancialCode financialCode, House house, Amount amount, Staff responsible, String description, Balance balance,
                           IncomeModel incomeModel, Date requestDate, Date registrationDate, List<Balance> balanceAffected) {
        super(financialCode, house, amount, responsible, description, balance, incomeModel, requestDate, registrationDate);
        this.balanceAffected = balanceAffected;
    }

    public CorrectResponse(FinancialCode financialCode, House house, Amount amount, Staff responsible, String description, Balance balance,
                           ExpenseModel expenseModel, Date requestDate, Date registrationDate, List<Balance> balanceAffected) {
        super(financialCode, house, amount, responsible, description, balance, expenseModel, requestDate, registrationDate);
        this.balanceAffected = balanceAffected;
    }

    public List<Balance> getBalanceAffected() {
        return balanceAffected;
    }
}
