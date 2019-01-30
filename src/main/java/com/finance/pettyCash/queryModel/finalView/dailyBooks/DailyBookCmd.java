package com.finance.pettyCash.queryModel.finalView.dailyBooks;

import com.finance.pettyCash.commandModel.domains.*;
import com.finance.pettyCash.commons.dtos.response.PettyCashResponse;
import com.finance.pettyCash.commons.enums.AccountType;
import com.finance.pettyCash.commons.enums.EntityType;
import com.finance.pettyCash.commons.helpers.MathHelper;

public class DailyBookCmd extends DailyBook {

    public DailyBookCmd(String accountCode, String houseCode, String limit, String orderCriteria, String orderBy) {
        this.accountCode = accountCode;
        this.houseCode = houseCode;
        this.limit = limit;
        this.orderBy = orderBy;
        this.orderCriteria = orderCriteria;
    }

    public DailyBookCmd() {
        super();
    }

    public void basicRecord(String eventId, PettyCashResponse response) {
        House house = response.getHouse();
        Amount amount = response.getAmount();
        CurrencyModel currency = amount.getCurrencyModel();
        Staff responsible = response.getResponsible();
        AccountModel account = response.getAccountModel();
        Balance balance = response.getBalance();

        this.aggregateCode = eventId;
        this.financialCode = response.getFinancialCode().getCode();
        this.accountCode = account.getCode().toString();
        this.accountName = account.getName();
        this.accountType = account.getType().toString();
        this.description = response.getDescription();
        this.houseCode = house.getCode();
        this.houseName = house.getName();
        this.requestDate = response.getRequestDate();
        this.requestDatetime = this.requestDate.getTime();
        this.registerDate = response.getRegistrationDate();
        this.registerDatetime = this.registerDate.getTime();
        this.amount = amount.getAmount();
        this.realBalance = MathHelper.round(balance.getRealBalance());
        this.systemBalance = MathHelper.round(balance.getSystemBalance());
        this.difference = MathHelper.round(balance.getDifference());
        this.currencyCode = currency.getCode();
        this.currencyName = currency.getName();
        this.currencySymbol = currency.getSymbol();
        this.responsibleCode = responsible.getCode();
        this.responsibleName = responsible.getName();
    }

    public void entryRecord(AccountModel account) {
        if (account.getType() == AccountType.EXPENSE) {
            ExpenseModel expense = (ExpenseModel) account;
            this.movementCode = expense.getExpenseCode();
            this.movementName = expense.getExpenseName();
            if (account instanceof SpecificExpenseModel) {
                SpecificExpenseModel specificExpense = (SpecificExpenseModel) expense;
                Entity entity = specificExpense.getAssignedTo();
                this.entityCode = entity.getCode();
                if (entity instanceof Person) {
                    this.entityType = EntityType.YoungPerson.toString();
                    Person person = (Person) entity;
                    this.entityName = person.getName();
                } else if (entity instanceof Car) {
                    this.entityType = EntityType.Car.toString();
                    Car car = (Car) entity;
                    this.entityName = car.getBrand();
                }
            }
        } else {
            IncomeModel income = (IncomeModel) account;
            this.movementCode = income.getSourceCode();
            this.movementName = income.getSourceMoney();
        }
    }

    public void correctEntry(Double realBalance, Double systemBalance, Double difference) {
        this.realBalance = realBalance;
        this.systemBalance = systemBalance;
        this.difference = difference;
    }

}
