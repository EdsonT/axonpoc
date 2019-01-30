package com.finance.pettyCash.queryModel.finalView.dailyBooks;

import java.util.Date;

public class DailyBookQuery extends DailyBook {
    public DailyBookQuery(String aggregateCode, String financialCode, String accountCode, String accountName,
                     String accountType, String description, String houseCode, String houseName,
                     Date requestDate, Date registerDate, Double amount, Double realBalance,
                     Double systemBalance, Double difference, String currencyCode, String currencyName,
                     String currencySymbol, String movementCode, String movementName, String entityType,
                     String entityCode, String entityName, String responsibleCode,
                     String responsibleName, Long requestDatetime, Long registerDatetime) {
        super(aggregateCode, financialCode, accountCode, accountName, accountType, description, houseCode,
                houseName, requestDate, registerDate, amount, realBalance, systemBalance, difference,
                currencyCode, currencyName, currencySymbol, movementCode, movementName, entityType,
                entityCode, entityName, responsibleCode, responsibleName, requestDatetime, registerDatetime);
    }
}
