package com.finance.pettyCash.services.pettyCash;

import com.finance.pettyCash.commandModel.commands.CloseCommand;
import com.finance.pettyCash.commandModel.commands.CorrectCommand;
import com.finance.pettyCash.commandModel.commands.EntryCommand;
import com.finance.pettyCash.commandModel.commands.OpenCommand;
import com.finance.pettyCash.commandModel.domains.*;
import com.finance.pettyCash.commons.dtos.data.EntryData;
import com.finance.pettyCash.commons.dtos.data.OpenCloseData;
import com.finance.pettyCash.commons.dtos.request.PettyCashRequest;
import com.finance.pettyCash.commons.enums.*;
import com.finance.pettyCash.commons.exceptions.BusinessException;
import com.finance.pettyCash.commons.helpers.Statics;
import com.finance.pettyCash.commons.helpers.ValidatorHelper;
import com.finance.pettyCash.commons.tool.JsonTool;
import com.finance.pettyCash.commons.validators.JsonValidator;
import com.finance.pettyCash.comunicator.JsonSchemaService;
import com.finance.pettyCash.queryModel.finalView.accounts.Account;
import com.finance.pettyCash.queryModel.finalView.activeCars.FActiveCars;
import com.finance.pettyCash.queryModel.finalView.activeHouses.FActiveHouses;
import com.finance.pettyCash.queryModel.finalView.activeYoungPersons.FActiveYoungPersons;
import com.finance.pettyCash.queryModel.finalView.currencies.Currency;
import com.finance.pettyCash.queryModel.finalView.dailyBooks.DailyBook;
import com.finance.pettyCash.queryModel.finalView.expenses.Expense;
import com.finance.pettyCash.queryModel.finalView.income.Income;
import com.finance.pettyCash.queryModel.operationalView.balance.BalancePerHouse;
import com.finance.pettyCash.services.QueryService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DailyBookServiceImpl implements DailyBookService {
    @Autowired
    QueryService queryService;

    @Autowired
    JsonSchemaService jsonSchemaService;

    @Autowired
    Environment environment;


    public DailyBookServiceImpl(QueryService queryService, JsonSchemaService jsonSchemaService) {
        this.queryService = queryService;
        this.jsonSchemaService = jsonSchemaService;
    }

    @Override
    public ValidatorHelper validateOpen(OpenCommand command) throws IOException {
        List<BusinessException> errors = new ArrayList<>();
        PettyCashRequest request = (PettyCashRequest) command.getReceivedData();
        JSONObject data = getJsonSchema(request.getContent());
        JSONObject schema = getJsonSchema(request.getFormCode(), data.getString("sectionCode"), "/schemas/openClosePettyCash.json");
        ValidatorHelper validator = JsonValidator.validate(schema, data.getJSONObject("content"));
        if (validator.isValid()) {
            OpenCloseData info = (new Gson()).fromJson(data.getJSONObject("content").toString(), OpenCloseData.class);
            ValidatorHelper isValidPettyCash = validatePettyCash(info.getHouseCode(), info.getCurrencyCode(), Statics.openCode, null);
            if (!isValidPettyCash.isValid())
                errors.addAll(isValidPettyCash.getErrors());
            if (queryService.getPettyCashByAccountCodeAndHouseCode(Statics.openCode, info.getHouseCode()) != null)
                errors.add(new BusinessException(Errors.ALREADY_OPEN_CASH));
        } else {
            errors.add(new BusinessException(Errors.VALIDATION_JSON_SCHEMA, validator.getError()));
        }
        return new ValidatorHelper(errors);
    }

    @Override
    public ValidatorHelper validateEntry(EntryCommand command) throws IOException {
        List<BusinessException> errors = new ArrayList<>();
        PettyCashRequest request = (PettyCashRequest) command.getReceivedData();
        JSONObject data = getJsonSchema(request.getContent());
        JSONObject schema = getJsonSchema(request.getFormCode(), data.getString("sectionCode"), "/schemas/newEntryPettyCash.json");
        ValidatorHelper validator = JsonValidator.validate(schema, data.getJSONObject("content"));
        if (validator.isValid()) {
            EntryData info = (new Gson()).fromJson(data.getJSONObject("content").toString(), EntryData.class);

            ValidatorHelper isValidPettyCash = validatePettyCash(info.getHouseCode(), info.getCurrencyCode(),
                    info.getAccountCode(), request.getAggregateCode());
            if (!isValidPettyCash.isValid())
                errors.addAll(isValidPettyCash.getErrors());
            else {
                AccountModel account = getAccount(info.getAccountCode());
                if (account.getType() == AccountType.INCOME) {
                    if (queryService.getIncomeByCode(info.getMovementCode()) == null)
                        errors.add(new BusinessException(Errors.NOTFOUND_INCOME_CODE));
                } else if (account.getType() == AccountType.EXPENSE) {
                    Expense expense = queryService.getExpenseByCode(info.getMovementCode());
                    if (expense == null)
                        errors.add(new BusinessException(Errors.NOTFOUND_EXPENSE_CODE));
                    else if (expense.getTypeName().equalsIgnoreCase(OutType.Specific.toString()))
                        if (info.getEntityCode() == null)
                            errors.add(new BusinessException(Errors.REQUIRED_ENTITY_CODE));
                        else if (getEntity(info.getEntityCode(), expense.getEntityAssigned()) == null)
                            errors.add(new BusinessException(Errors.NOTFOUND_ENTITY_CODE));
                }
            }
            if (queryService.getPettyCashByAccountCodeAndHouseCode(Statics.openCode, info.getHouseCode()) == null)
                errors.add(new BusinessException(Errors.ISNOT_OPEN_CASHBOX));
            if (queryService.getDailyBookByAggregateAndHouseAndAccount(request.getAggregateCode(), info.getHouseCode(), Statics.closeCode) != null)
                errors.add(new BusinessException(Errors.CLOSED_CASHBOX_HOUSE));
        } else {
            errors.add(new BusinessException(Errors.VALIDATION_JSON_SCHEMA, validator.getError()));
        }
        return new ValidatorHelper(errors);
    }

    @Override
    public ValidatorHelper validateClose(CloseCommand command) throws IOException {
        List<BusinessException> errors = new ArrayList<>();
        PettyCashRequest request = (PettyCashRequest) command.getReceivedData();
        JSONObject data = getJsonSchema(request.getContent());
        JSONObject schema = getJsonSchema(request.getFormCode(), data.getString("sectionCode"), "/schemas/openClosePettyCash.json");

        ValidatorHelper validator = JsonValidator.validate(schema, data.getJSONObject("content"));
        if (validator.isValid()) {
            OpenCloseData info = (new Gson()).fromJson(data.getJSONObject("content").toString(), OpenCloseData.class);
            ValidatorHelper isValidPettyCash = validatePettyCash(info.getHouseCode(), info.getCurrencyCode(), Statics.closeCode, request.getAggregateCode());
            if (!isValidPettyCash.isValid())
                errors.addAll(isValidPettyCash.getErrors());
            if (queryService.getPettyCashByAccountCodeAndHouseCode(Statics.openCode, info.getHouseCode()) == null)
                errors.add(new BusinessException(Errors.ISNOT_OPEN_CASHBOX));
            if (queryService.getDailyBookByAggregateAndHouseAndAccount(request.getAggregateCode(), info.getHouseCode(), Statics.closeCode) != null)
                errors.add(new BusinessException(Errors.CLOSED_CASHBOX_HOUSE));
        } else {
            errors.add(new BusinessException(Errors.VALIDATION_JSON_SCHEMA, validator.getError()));
        }
        return new ValidatorHelper(errors);
    }

    @Override
    public ValidatorHelper validateCorrect(CorrectCommand command) throws IOException {
        List<BusinessException> errors = new ArrayList<>();
        PettyCashRequest request = (PettyCashRequest) command.getReceivedData();
        JSONObject data = getJsonSchema(request.getContent());
        JSONObject schema = getJsonSchema(request.getFormCode(), data.getString("sectionCode"), "/schemas/oldEntryPettyCash.json");
        ValidatorHelper validator = JsonValidator.validate(schema, data.getJSONObject("content"));
        if (validator.isValid()) {
            Gson gson = new GsonBuilder().setDateFormat(Statics.dateFormat).create();
            EntryData info = gson.fromJson(data.getJSONObject("content").toString(), EntryData.class);
            ValidatorHelper isValidPettyCash = validatePettyCash(info.getHouseCode(), info.getCurrencyCode(),
                    info.getAccountCode(), request.getAggregateCode());
            if (!isValidPettyCash.isValid())
                errors.addAll(isValidPettyCash.getErrors());
            if (queryService.getDailyBookByAggregateAndHouseAndAccount(request.getAggregateCode(), info.getHouseCode(), Statics.closeCode) == null)
                errors.add(new BusinessException(Errors.NOT_CLOSED_CASHBOX));
        } else {
            errors.add(new BusinessException(Errors.VALIDATION_JSON_SCHEMA, validator.getError()));
        }
        return new ValidatorHelper(errors);
    }

    private ValidatorHelper validatePettyCash(String house, String currency, String account, String aggregate) {
        List<BusinessException> errors = new ArrayList<>();
        if (queryService.getCurrencyByCodeAndStatus(currency, "ACTIVE") == null)
            errors.add(new BusinessException(Errors.NOTFOUND_CURRENCY_CODE));
        if (getHouse(house) == null)
            errors.add(new BusinessException(Errors.NOTFOUND_HOUSE_CODE));
        if (queryService.getAccountByCode(account.toString()) == null)
            errors.add(new BusinessException(Errors.NOTFOUND_ACCOUNT_CODE));

        if (aggregate != null)
            if (queryService.getDailyBookByAggregateAndHouseAndAccount(aggregate, house, Statics.openCode) == null)
                errors.add(new BusinessException(Errors.NOTFOUND_HOUSE_AGGREGATE));
        return new ValidatorHelper(errors);
    }

    @Override
    public House getHouse(String houseCode) {
        FActiveHouses house = queryService.getActiveHouseByCode(houseCode);
        if (house != null)
            return new House(house.getCode(), house.getName());
        return null;
    }

    @Override
    public Amount getAmount(String currencyCode, Double amount) {
        Currency mView = queryService.getCurrencyByCodeAndStatus(currencyCode, "ACTIVE");
        CurrencyModel currencyModel = new CurrencyModel(mView.getCode(), mView.getName(), mView.getSymbol());
        return new Amount(currencyModel, amount);
    }

    @Override
    public Balance getBalance(Balance balance, Amount amount, AccountModel account, String house) {
        BalancePerHouse balancePerHouse = queryService.getBalancePerHouse(house);
        if (balance == null && account.getCode().equalsIgnoreCase(Statics.openCode)) {
            Balance newBalance = new Balance();
            if (balancePerHouse == null) {
                newBalance.openInit(amount);
            } else {
                newBalance.openNormal(amount, balancePerHouse.getSystemBalance());
            }
            return newBalance;
        } else if (account.getCode().equalsIgnoreCase(Statics.closeCode)) {
            balance.closeBalance(amount.getAmount(), balancePerHouse.getSystemBalance());
        } else if (account.getType() == AccountType.INCOME || account.getType() == AccountType.EXPENSE) {
            Account accountView = queryService.getAccountByCode(account.getCode());
            balance.updateBalance(amount.getAmount(), accountView.getAccountType(), balancePerHouse.getSystemBalance());
        }
        return balance;
    }

    @Override
    public Balance getCorrectBalance(String aggregateCode, Amount amount, String accountCode, String house) {
        DailyBook dailyBook = queryService.getDailyBookBeforeCloseByAggregateCodeAndHouseCode(aggregateCode, house);
        Balance balance = new Balance();
        CurrencyModel currency = new CurrencyModel(dailyBook.getCurrencyCode(), dailyBook.getCurrencyName(), dailyBook.getCurrencySymbol());
        balance.redefineBalance(dailyBook.getRealBalance(), dailyBook.getSystemBalance(), dailyBook.getDifference(), currency, dailyBook.getFinancialCode());
        Account accountView = queryService.getAccountByCode(accountCode);
        balance.updateBalance(amount.getAmount(), accountView.getAccountType(), dailyBook.getSystemBalance());
        return balance;
    }

    @Override
    public List<Balance> getAffectedBalance(String aggregateCode, String house, Balance correctedBalance) {
        Balance localBalance = new Balance();
        localBalance.redefineBalance(correctedBalance.getRealBalance(), correctedBalance.getSystemBalance(),
                correctedBalance.getDifference(), correctedBalance.getCurrencyModel(), correctedBalance.getFinancialCode());
        List<Balance> balances = new ArrayList<>();
        DailyBook dBClose = queryService.getDailyBookByAggregateAndHouseAndAccount(aggregateCode, house, Statics.closeCode);
        if (dBClose != null) {
            localBalance.closeBalance(dBClose.getAmount(), localBalance.getSystemBalance());

            Balance closeBalance = new Balance();
            closeBalance.redefineBalance(localBalance.getRealBalance(), localBalance.getSystemBalance(),
                    localBalance.getDifference(), localBalance.getCurrencyModel(), dBClose.getFinancialCode());
            balances.add(closeBalance);

            List<DailyBook> dailyBooks = queryService.getLastRecordsByHouseCodeAndRegisterDate(house, dBClose.getRegisterDate());
            String previousAccount = dBClose.getAccountCode();
            boolean correct = false;
            for (DailyBook dB : dailyBooks) {
                if (dB.getAccountCode().equalsIgnoreCase(Statics.openCode)) {
                    CurrencyModel currency = new CurrencyModel(dB.getCurrencyCode(), dB.getCurrencyName(), dB.getCurrencySymbol());
                    Amount amount = new Amount(currency, dB.getAmount());
                    localBalance.openNormal(amount, localBalance.getSystemBalance());
                    correct = true;
                } else if (dB.getAccountCode().equalsIgnoreCase(Statics.closeCode)) {
                    correct = true;
                    localBalance.closeBalance(dB.getAmount(), localBalance.getSystemBalance());
                } else if (!previousAccount.equalsIgnoreCase(Statics.closeCode)) {
                    correct = true;
                    localBalance.updateBalance(dB.getAmount(), dB.getAccountType(), localBalance.getSystemBalance());
                }
                if (correct) {
                    Balance updatedBalance = new Balance();
                    updatedBalance.redefineBalance(localBalance.getRealBalance(), localBalance.getSystemBalance(),
                            localBalance.getDifference(), localBalance.getCurrencyModel(), dB.getFinancialCode());
                    balances.add(updatedBalance);
                }
                previousAccount = dB.getAccountCode();
                correct = false;
            }
        }
        return balances;
    }

    @Override
    public Staff getResponsible(String responsibleCode) {
        return new Staff(responsibleCode, responsibleCode);
    }

    @Override
    public AccountModel getAccount(String accountCode) {
        Account account = queryService.getAccountByCode(accountCode);
        if (account != null)
            return new AccountModel(accountCode, account.getName(), AccountType.valueOf(account.getAccountType().toUpperCase()));
        return null;
    }

    @Override
    public IncomeModel getIncome(String accountCode, String incomeCode) {
        Income income = queryService.getIncomeByCode(incomeCode);
        Account account = queryService.getAccountByCode(accountCode);

        return new IncomeModel(account.getCode(), account.getName(),
                AccountType.valueOf(account.getAccountType().toUpperCase()), income.getCode(), income.getName());
    }

    @Override
    public Entity getEntity(String entityCode, String type) {
        if (type.equalsIgnoreCase(EntityType.YoungPerson.toString())) {
            FActiveYoungPersons person = queryService.getActiveYoungPersonByCode(entityCode);
            if (person != null)
                return new Person(person.getCode(), person.getName());
        } else if (type.equalsIgnoreCase(EntityType.Car.toString())) {
            FActiveCars car = queryService.getActiveCarsByCode(entityCode);
            if (car != null)
                return new Car(car.getCode(), car.getName());
        }
        return null;
    }

    @Override
    public ExpenseModel getExpense(String accountCode, String expenseCode, String entityCode) {
        Account account = queryService.getAccountByCode(accountCode);
        String code = account.getCode();
        AccountType type = AccountType.valueOf(account.getAccountType().toUpperCase());
        Expense expenseView = queryService.getExpenseByCode(expenseCode);

        if (expenseView.getTypeName().equalsIgnoreCase(OutType.General.toString())) {
            return new GeneralExpenseModel(code, account.getName(), type, expenseView.getCode(),
                    expenseView.getName(), expenseView.getDescription());
        } else {
            return new SpecificExpenseModel(code, account.getName(), type, expenseView.getCode(),
                    expenseView.getName(), getEntity(entityCode, expenseView.getEntityAssigned()));
        }
    }

    public JSONObject getJsonSchema(Object data) {
        JSONObject content = JsonTool.getJSONObjectFromObject(data);
        JSONObject response = new JSONObject();
        for (String key : content.keySet()) {
            response.put("sectionCode", key);
            response.put("content", content.get(key));
        }
        return response;
    }

    public JSONObject getJsonSchema(String formCode, String sectionCode, String file) throws IOException {
        //Get JsonSchema to Operational Logging
        if (environment != null)
            if (Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> (env.equalsIgnoreCase("test")))) {
                return JsonTool.loadJsonSchema(file);
            }
        return jsonSchemaService.getSchemmaData(formCode, sectionCode);
    }
}
