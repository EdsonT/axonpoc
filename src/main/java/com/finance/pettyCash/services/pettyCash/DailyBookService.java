package com.finance.pettyCash.services.pettyCash;

import com.finance.pettyCash.commandModel.commands.CloseCommand;
import com.finance.pettyCash.commandModel.commands.CorrectCommand;
import com.finance.pettyCash.commandModel.commands.EntryCommand;
import com.finance.pettyCash.commandModel.commands.OpenCommand;
import com.finance.pettyCash.commandModel.domains.*;
import com.finance.pettyCash.commons.helpers.ValidatorHelper;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public interface DailyBookService {
    ValidatorHelper validateOpen(OpenCommand command) throws IOException;
    ValidatorHelper validateEntry(EntryCommand command) throws IOException;
    ValidatorHelper validateClose(CloseCommand command) throws IOException;
    ValidatorHelper validateCorrect(CorrectCommand command) throws IOException;
    House getHouse(String houseCode);
    Amount getAmount(String currencyCode, Double amount);
    Balance getBalance(Balance balance, Amount amount, AccountModel accountCode, String house);
    Balance getCorrectBalance(String aggregateCode, Amount amount, String accountCode, String house);
    List<Balance> getAffectedBalance(String aggregateCode, String house, Balance correctedBalance);
    Staff getResponsible(String responsibleCode);
    AccountModel getAccount(String accountCode);
    IncomeModel getIncome(String accountCode, String incomeCode);
    Entity getEntity(String entityCode, String type);
    ExpenseModel getExpense(String accountCode, String expenseCode, String entityCode);
    JSONObject getJsonSchema(Object data);
    JSONObject getJsonSchema(String formCode, String sectionCode, String file) throws IOException;
}
