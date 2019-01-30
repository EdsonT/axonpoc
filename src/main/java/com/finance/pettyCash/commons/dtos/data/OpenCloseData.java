package com.finance.pettyCash.commons.dtos.data;

public class OpenCloseData {
    private String houseCode;
    private String responsibleCode;
    private String currencyCode;
    private Double amount;
    private String description;

    public OpenCloseData(String houseCode, String responsibleCode, String currencyCode, Double amount, String description) {
        this.houseCode = houseCode;
        this.responsibleCode = responsibleCode;
        this.currencyCode = currencyCode;
        this.amount = amount;
        this.description = description;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public String getResponsibleCode() {
        return responsibleCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "OpenData{" +
                "houseCode='" + houseCode + '\'' +
                ", responsibleCode='" + responsibleCode + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
