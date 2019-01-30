package com.finance.pettyCash.queryModel.finalView.entityPerExpenses;


import com.finance.pettyCash.queryModel.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(
        collection = "F_entityPerExpenses"
)
public class FEntityPerExpenses extends BaseEntity {
    private String entityCode;

    private String entityName;

    private String expenseCode;

    private String expenseName;

    private String statusCode;

    private String status;

    private String entityAssigned;

    private String houseCode;

    private String houseName;

    public FEntityPerExpenses() {
    }

    public FEntityPerExpenses(String entityCode, String entityName, String expenseCode,
            String expenseName, String statusCode, String status, String entityAssigned,
            String houseCode, String houseName) {
        this.entityCode = entityCode;
                this.entityName = entityName;
                this.expenseCode = expenseCode;
                this.expenseName = expenseName;
                this.statusCode = statusCode;
                this.status = status;
                this.entityAssigned = entityAssigned;
                this.houseCode = houseCode;
                this.houseName = houseName;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getExpenseCode() {
        return expenseCode;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return status;
    }

    public String getEntityAssigned() {
        return entityAssigned;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public String getHouseName() {
        return houseName;
    }
}
