package com.finance.pettyCash.queryModel.handlers;

import com.finance.pettyCash.commandModel.domains.AccountModel;
import com.finance.pettyCash.commandModel.domains.Balance;
import com.finance.pettyCash.commandModel.domains.CurrencyModel;
import com.finance.pettyCash.commandModel.domains.House;
import com.finance.pettyCash.commandModel.events.CloseEvent;
import com.finance.pettyCash.commandModel.events.CorrectEvent;
import com.finance.pettyCash.commandModel.events.EntryEvent;
import com.finance.pettyCash.commandModel.events.OpenEvent;
import com.finance.pettyCash.commons.dtos.response.CorrectResponse;
import com.finance.pettyCash.commons.dtos.response.EntryResponse;
import com.finance.pettyCash.commons.dtos.response.OpenCloseResponse;
import com.finance.pettyCash.commons.helpers.Statics;
import com.finance.pettyCash.queryModel.finalView.dailyBooks.DailyBookCmd;
import com.finance.pettyCash.queryModel.finalView.dailyBooks.DailyBookRepository;
import com.finance.pettyCash.queryModel.operationalView.balance.BalancePerHouseCmd;
import com.finance.pettyCash.queryModel.operationalView.balance.BalancePerHouseRepository;
import com.finance.pettyCash.queryModel.operationalView.openPettyCash.PettyCashCmd;
import com.finance.pettyCash.queryModel.operationalView.openPettyCash.PettyCashRepository;
import com.finance.pettyCash.services.QueryService;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DailyBookHandler {

    private final DailyBookRepository dailyBookRepository;
    private final PettyCashRepository pettyCashRepository;
    private final BalancePerHouseRepository balancePerHouseRepository;
    private final QueryService queryService;

    @Autowired
    public DailyBookHandler(DailyBookRepository dailyBookRepository, PettyCashRepository pettyCashRepository,
                            BalancePerHouseRepository balancePerHouseRepository, QueryService queryService) {
        this.dailyBookRepository = dailyBookRepository;
        this.pettyCashRepository = pettyCashRepository;
        this.balancePerHouseRepository = balancePerHouseRepository;
        this.queryService = queryService;
    }

    @EventHandler
    public void on(OpenEvent event) {
        OpenCloseResponse response = (OpenCloseResponse) event.getProcessedData();
        DailyBookCmd dailyBookView = new DailyBookCmd();
        dailyBookView.basicRecord(event.getId(), response);
        dailyBookRepository.save(dailyBookView);

        AccountModel account = response.getAccountModel();
        House house = response.getHouse();
        PettyCashCmd pettyCashView = (PettyCashCmd) queryService.getPettyCashByAccountCodeAndHouseCode(null, house.getCode());
        if (pettyCashView == null)
            pettyCashView = new PettyCashCmd(event.getId(), account.getCode().toString(), account.getName(), house.getCode(), house.getName());
        else
            pettyCashView.reopen(account, event.getId());

        pettyCashRepository.save(pettyCashView);

        Double realBalance = response.getBalance().getRealBalance();
        Double systemBalance = response.getBalance().getSystemBalance();
        Double difference = response.getBalance().getDifference();
        CurrencyModel currency = response.getAmount().getCurrencyModel();
        BalancePerHouseCmd balancePerHouse = (BalancePerHouseCmd) queryService.getBalancePerHouse(house.getCode());
        if (balancePerHouse == null)
            balancePerHouse = new BalancePerHouseCmd(realBalance, systemBalance, difference, house.getCode(),
                    currency.getCode(), currency.getName(), currency.getSymbol());
        else
            balancePerHouse.updateBalance(realBalance, systemBalance, difference);
        balancePerHouseRepository.save(balancePerHouse);
    }

    @EventHandler
    public void on(EntryEvent event) {
        EntryResponse response = (EntryResponse) event.getProcessedData();
        DailyBookCmd dailyBookView = new DailyBookCmd();
        dailyBookView.basicRecord(event.getId(), response);
        dailyBookView.entryRecord(response.getAccountModel());
        dailyBookRepository.save(dailyBookView);

        House house = response.getHouse();
        BalancePerHouseCmd balance = (BalancePerHouseCmd) queryService.getBalancePerHouse(house.getCode());
        balance.updateBalance(response.getBalance().getRealBalance(), response.getBalance().getSystemBalance(), response.getBalance().getDifference());
        balancePerHouseRepository.save(balance);
    }

    @EventHandler
    public void on(CorrectEvent event) {
        CorrectResponse response = (CorrectResponse) event.getProcessedData();
        DailyBookCmd dailyBookView = new DailyBookCmd();
        dailyBookView.basicRecord(event.getId(), response);
        dailyBookView.entryRecord(response.getAccountModel());
        dailyBookRepository.save(dailyBookView);

        for (Balance balance : response.getBalanceAffected()) {
            DailyBookCmd dailyBook = (DailyBookCmd) queryService.getDailyBookByFinancialCode(balance.getFinancialCode());
            dailyBook.correctEntry(balance.getRealBalance(), balance.getSystemBalance(), balance.getDifference());
            dailyBookRepository.save(dailyBook);

            BalancePerHouseCmd balancePerHouse = (BalancePerHouseCmd) queryService.getBalancePerHouse(response.getHouse().getCode());
            balancePerHouse.updateBalance(balance.getRealBalance(), balance.getSystemBalance(), balance.getDifference());
            balancePerHouseRepository.save(balancePerHouse);
        }
    }

    @EventHandler
    public void on(CloseEvent event) {
        OpenCloseResponse response = (OpenCloseResponse) event.getProcessedData();
        DailyBookCmd dailyBookView = new DailyBookCmd();
        dailyBookView.basicRecord(event.getId(), response);
        dailyBookRepository.save(dailyBookView);

        House house = response.getHouse();
        AccountModel account = response.getAccountModel();
        PettyCashCmd pettyCashView = (PettyCashCmd) queryService.getPettyCashByAccountCodeAndHouseCode(Statics.openCode, house.getCode());
        pettyCashView.close(account);
        pettyCashRepository.save(pettyCashView);

        BalancePerHouseCmd balance = (BalancePerHouseCmd) queryService.getBalancePerHouse(house.getCode());
        balance.updateBalance(response.getBalance().getRealBalance(), response.getBalance().getSystemBalance(), response.getBalance().getDifference());
        balancePerHouseRepository.save(balance);
    }
}
