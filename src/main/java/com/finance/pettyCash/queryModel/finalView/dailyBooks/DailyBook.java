package com.finance.pettyCash.queryModel.finalView.dailyBooks;

import com.finance.pettyCash.queryModel.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "F_dailyBook")
public class DailyBook extends BaseEntity {

    protected String aggregateCode;
    protected String financialCode;
    protected String accountCode;
    protected String accountName;
    protected String accountType;
    protected String description;
    protected String houseCode;
    protected String houseName;
    protected Date requestDate;
    protected Date registerDate;
    protected Double amount;
    protected Double realBalance;
    protected Double systemBalance;
    protected Double difference;
    protected String currencyCode;
    protected String currencyName;
    protected String currencySymbol;
    protected String movementCode;
    protected String movementName;
    protected String entityType;
    protected String entityCode;
    protected String entityName;
    protected String responsibleCode;
    protected String responsibleName;
    protected Long requestDatetime;
    protected Long registerDatetime;

    public DailyBook(){}

    public DailyBook(String aggregateCode, String financialCode, String accountCode, String accountName,
                     String accountType, String description, String houseCode, String houseName,
                     Date requestDate, Date registerDate, Double amount, Double realBalance,
                     Double systemBalance, Double difference, String currencyCode, String currencyName,
                     String currencySymbol, String movementCode, String movementName, String entityType,
                     String entityCode, String entityName, String responsibleCode,
                     String responsibleName, Long requestDatetime, Long registerDatetime) {
        this.aggregateCode = aggregateCode;
        this.financialCode = financialCode;
        this.accountCode = accountCode;
        this.accountName = accountName;
        this.accountType = accountType;
        this.description = description;
        this.houseCode = houseCode;
        this.houseName = houseName;
        this.requestDate = requestDate;
        this.registerDate = registerDate;
        this.amount = amount;
        this.realBalance = realBalance;
        this.systemBalance = systemBalance;
        this.difference = difference;
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.currencySymbol = currencySymbol;
        this.movementCode = movementCode;
        this.movementName = movementName;
        this.entityType = entityType;
        this.entityCode = entityCode;
        this.entityName = entityName;
        this.responsibleCode = responsibleCode;
        this.responsibleName = responsibleName;
        this.requestDatetime = requestDatetime;
        this.registerDatetime = registerDatetime;
    }

    public String getAggregateCode() {
        return aggregateCode;
    }

    public String getFinancialCode() {
        return financialCode;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getDescription() {
        return description;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public String getHouseName() {
        return houseName;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public Double getAmount() {
        return amount;
    }

    public Double getRealBalance() {
        return realBalance;
    }

    public Double getSystemBalance() {
        return systemBalance;
    }

    public Double getDifference() {
        return difference;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public String getMovementCode() {
        return movementCode;
    }

    public String getMovementName() {
        return movementName;
    }

    public String getEntityType() {
        return entityType;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getResponsibleCode() {
        return responsibleCode;
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public Long getRequestDatetime() {
        return requestDatetime;
    }

    public Long getRegisterDatetime() {
        return registerDatetime;
    }
}
