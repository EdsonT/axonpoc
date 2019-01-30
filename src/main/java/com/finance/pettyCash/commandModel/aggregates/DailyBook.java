package com.finance.pettyCash.commandModel.aggregates;

import com.finance.pettyCash.base.aggregate.BaseAggregate;
import com.finance.pettyCash.base.config.GetBeanService;
import com.finance.pettyCash.base.process.BaseProcess;
import com.finance.pettyCash.commandModel.commands.CloseCommand;
import com.finance.pettyCash.commandModel.commands.CorrectCommand;
import com.finance.pettyCash.commandModel.commands.EntryCommand;
import com.finance.pettyCash.commandModel.commands.OpenCommand;
import com.finance.pettyCash.commandModel.domains.*;
import com.finance.pettyCash.commandModel.events.CloseEvent;
import com.finance.pettyCash.commandModel.events.CorrectEvent;
import com.finance.pettyCash.commandModel.events.EntryEvent;
import com.finance.pettyCash.commandModel.events.OpenEvent;
import com.finance.pettyCash.commons.dtos.data.EntryData;
import com.finance.pettyCash.commons.dtos.data.OpenCloseData;
import com.finance.pettyCash.commons.dtos.request.PettyCashRequest;
import com.finance.pettyCash.commons.dtos.response.CorrectResponse;
import com.finance.pettyCash.commons.dtos.response.EntryResponse;
import com.finance.pettyCash.commons.dtos.response.OpenCloseResponse;
import com.finance.pettyCash.commons.enums.AccountType;
import com.finance.pettyCash.commons.helpers.Statics;
import com.finance.pettyCash.services.pettyCash.DailyBookService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class DailyBook extends BaseAggregate {

    private FinancialCode financialCode;
    private Amount amount;
    private House assignTo;
    private Staff responsible;
    private AccountModel accountModel;
    private Balance balance;
    private String description;
    private Date requestDate;
    private Date registrationDate;

    public DailyBook() {
    }

    @CommandHandler()
    public DailyBook(OpenCommand command, GetBeanService service) {
        BaseProcess baseProcess = processData(command.getReceivedData(), service, "OPEN");
        apply(new OpenEvent(command.getReceivedData(), baseProcess.getProcessData(), command.getInitDate(),
                baseProcess.getBaseException(), null, null, command.getId()));
    }

    @EventSourcingHandler
    public void on(OpenEvent event) {
        this.id = event.getId();
        OpenCloseResponse response = (OpenCloseResponse) event.getProcessedData();
        this.financialCode = response.getFinancialCode();
        this.amount = response.getAmount();
        this.assignTo = response.getHouse();
        this.responsible = response.getResponsible();
        this.description = response.getDescription();
        this.requestDate = response.getRequestDate();
        this.registrationDate = response.getRegistrationDate();
        this.accountModel = response.getAccountModel();
        this.balance = response.getBalance();
    }

    @CommandHandler
    public void on(EntryCommand command, GetBeanService service) {
        BaseProcess baseProcess = processData(command.getReceivedData(), service, "ENTRY");
        apply(new EntryEvent(command.getReceivedData(), baseProcess.getProcessData(), command.getInitDate(),
                baseProcess.getBaseException(), null, null, command.getId()));
    }

    @EventSourcingHandler
    public void on(EntryEvent event) {
        EntryResponse response = (EntryResponse) event.getProcessedData();
        this.financialCode = response.getFinancialCode();
        this.amount = response.getAmount();
        this.assignTo = response.getHouse();
        this.responsible = response.getResponsible();
        this.description = response.getDescription();
        this.requestDate = response.getRequestDate();
        this.registrationDate = response.getRegistrationDate();
        this.accountModel = response.getAccountModel();
        this.balance = response.getBalance();
    }

    @CommandHandler
    public void on(CorrectCommand command, GetBeanService service) {
        BaseProcess baseProcess = processData(command.getReceivedData(), service, "CORRECT");
        apply(new CorrectEvent(command.getReceivedData(), baseProcess.getProcessData(), command.getInitDate(),
                baseProcess.getBaseException(), null, null, command.getId()));
    }

    @CommandHandler
    public void on(CloseCommand command, GetBeanService service) {
        BaseProcess baseProcess = processData(command.getReceivedData(), service, "CLOSE");
        apply(new CloseEvent(command.getReceivedData(), baseProcess.getProcessData(), command.getInitDate(),
                baseProcess.getBaseException(), null, null, command.getId()));
    }

    @EventSourcingHandler
    public void on(CloseEvent event) {
        OpenCloseResponse response = (OpenCloseResponse) event.getProcessedData();
        this.financialCode = response.getFinancialCode();
        this.amount = response.getAmount();
        this.assignTo = response.getHouse();
        this.responsible = response.getResponsible();
        this.description = response.getDescription();
        this.requestDate = response.getRequestDate();
        this.registrationDate = response.getRegistrationDate();
        this.accountModel = response.getAccountModel();
        this.balance = response.getBalance();
    }

    @Override
    public Object process(Object data, GetBeanService beanService, String commandType) {
        DailyBookService dailyBookService = beanService.getDailyBookService();
        PettyCashRequest request = (PettyCashRequest) data;
        JSONObject requestData = dailyBookService.getJsonSchema(request.getContent());
        switch (commandType) {
            case "OPEN":
                return openClosePettyCash(dailyBookService, requestData.getJSONObject("content"), Statics.openCode);
            case "ENTRY":
                return entryPettyCash(dailyBookService, requestData.getJSONObject("content"));
            case "CORRECT":
                return correctEntryPettyCash(dailyBookService, requestData.getJSONObject("content"), request.getAggregateCode());
            case "CLOSE":
                return openClosePettyCash(dailyBookService, requestData.getJSONObject("content"), Statics.closeCode);
            default:
                return null;
        }
    }

    private Object openClosePettyCash(DailyBookService dailyBookServiceImpl, JSONObject requestData, String code) {
        OpenCloseData info = (new Gson()).fromJson(requestData.toString(), OpenCloseData.class);
        House house = dailyBookServiceImpl.getHouse(info.getHouseCode());
        Amount localAmount = dailyBookServiceImpl.getAmount(info.getCurrencyCode(), info.getAmount());
        Staff localResponsible = dailyBookServiceImpl.getResponsible(info.getResponsibleCode());
        FinancialCode localFC = new FinancialCode();
        localFC.generateCode(code, UUID.randomUUID().toString());
        AccountModel account = dailyBookServiceImpl.getAccount(code);
        Balance balance = dailyBookServiceImpl.getBalance(this.balance, localAmount, account, info.getHouseCode());
        Date localDate = new Date();
        return new OpenCloseResponse(localFC, house, localAmount, localResponsible, info.getDescription(),
                balance, account, localDate, localDate);
    }

    private Object entryPettyCash(DailyBookService dailyBookServiceImpl, JSONObject requestData) {
        EntryData info = (new Gson()).fromJson(requestData.toString(), EntryData.class);
        Amount localAmount = dailyBookServiceImpl.getAmount(info.getCurrencyCode(), info.getAmount());
        Staff localRes = dailyBookServiceImpl.getResponsible(info.getResponsibleCode());
        FinancialCode localFC = new FinancialCode();
        localFC.generateCode(info.getAccountCode(), UUID.randomUUID().toString());
        AccountModel account = dailyBookServiceImpl.getAccount(info.getAccountCode());
        Balance balance = dailyBookServiceImpl.getBalance(this.balance, localAmount, account, info.getHouseCode());
        Date localDate = new Date();
        if (account.getType() == AccountType.INCOME) {
            IncomeModel incomeModel = dailyBookServiceImpl.getIncome(info.getAccountCode(), info.getMovementCode());
            return new EntryResponse(localFC, this.assignTo, localAmount, localRes, info.getDescription(),
                    balance, incomeModel, localDate, localDate);
        } else {
            ExpenseModel expenseModel = dailyBookServiceImpl.getExpense(info.getAccountCode(), info.getMovementCode(), info.getEntityCode());
            return new EntryResponse(localFC, this.assignTo, localAmount, localRes, info.getDescription(),
                    balance, expenseModel, localDate, localDate);
        }
    }

    private Object correctEntryPettyCash(DailyBookService dailyBookService, JSONObject requestData, String aggregate) {
        Gson gson = new GsonBuilder().setDateFormat(Statics.dateFormat).create();
        EntryData info = gson.fromJson(requestData.toString(), EntryData.class);
        Amount localAmount = dailyBookService.getAmount(info.getCurrencyCode(), info.getAmount());
        Staff localRes = dailyBookService.getResponsible(info.getResponsibleCode());
        FinancialCode localFC = new FinancialCode();
        localFC.generateCode(info.getAccountCode(), UUID.randomUUID().toString());
        Date registerDate = new Date();
        Balance balance = dailyBookService.getCorrectBalance(aggregate, localAmount, info.getAccountCode(), info.getHouseCode());
        List<Balance> affectedBalance = dailyBookService.getAffectedBalance(aggregate, info.getHouseCode(), balance);
        requestDate = info.getRequestDate();
        AccountModel account = dailyBookService.getAccount(info.getAccountCode());
        if (account.getType() == AccountType.INCOME) {
            IncomeModel incomeModel = dailyBookService.getIncome(info.getAccountCode(), info.getMovementCode());
            return new CorrectResponse(localFC, this.assignTo, localAmount, localRes, info.getDescription(),
                    balance, incomeModel, info.getRequestDate(), registerDate, affectedBalance);
        } else {
            ExpenseModel expenseModel = dailyBookService.getExpense(info.getAccountCode(), info.getMovementCode(), info.getEntityCode());
            return new CorrectResponse(localFC, this.assignTo, localAmount, localRes, info.getDescription(),
                    balance, expenseModel, info.getRequestDate(), registerDate, affectedBalance);
        }
    }
}
