package com.finance.pettyCash.services;

import com.finance.pettyCash.commandModel.domains.Car;
import com.finance.pettyCash.commandModel.domains.House;
import com.finance.pettyCash.commandModel.domains.Person;
import com.finance.pettyCash.commons.helpers.Statics;
import com.finance.pettyCash.queryModel.finalView.accounts.Account;
import com.finance.pettyCash.queryModel.finalView.activeCars.FActiveCars;
import com.finance.pettyCash.queryModel.finalView.activeHouses.FActiveHouses;
import com.finance.pettyCash.queryModel.finalView.activeYoungPersons.FActiveYoungPersons;
import com.finance.pettyCash.queryModel.finalView.currencies.Currency;
import com.finance.pettyCash.queryModel.finalView.dailyBooks.DailyBook;
import com.finance.pettyCash.queryModel.finalView.dailyBooks.DailyBookCmd;
import com.finance.pettyCash.queryModel.finalView.dailyBooks.DailyBookRepository;
import com.finance.pettyCash.queryModel.finalView.expenses.Expense;
import com.finance.pettyCash.queryModel.finalView.income.Income;
import com.finance.pettyCash.queryModel.operationalView.balance.BalancePerHouse;
import com.finance.pettyCash.queryModel.operationalView.openPettyCash.PettyCash;
import com.finance.pettyCash.queryModel.operationalView.openPettyCash.PettyCashCmd;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.responsetypes.MultipleInstancesResponseType;
import org.axonframework.queryhandling.responsetypes.ResponseTypes;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class QueryService {
    @Autowired
    QueryGateway queryGateway;

    @Autowired
    DailyBookRepository dailyBookRepository;

    public PettyCash getPettyCashByAccountCodeAndHouseCode(String accountCode, String houseCode) {
        try {
            PettyCashCmd pettyCash = new PettyCashCmd(null, accountCode, null, houseCode, null);
            Future<PettyCash> future = queryGateway.query(new JSONObject(pettyCash), ResponseTypes.instanceOf(PettyCash.class));
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BalancePerHouse getBalancePerHouse(String house) {
        try {
            BalancePerHouse balancePerHouse = new BalancePerHouse(null, null, null, house, null, null, null);
            Future<BalancePerHouse> future = queryGateway.query(new JSONObject(balancePerHouse), ResponseTypes.instanceOf(BalancePerHouse.class));
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DailyBook getLastByHouseCodeAndAccountCode(String house, String account) {
        try {
            DailyBookCmd dailyBook = new DailyBookCmd(account, house, "1", "DESC", "registerDate");
            Future<DailyBook> future = queryGateway.query(new JSONObject(dailyBook), ResponseTypes.instanceOf(DailyBook.class));
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<DailyBook> getByAggregateCode(String aggregateCode, String house) {
        try {
            DailyBook dailyBook = new DailyBook(aggregateCode, null, null, null,
                    null, null, house, null, null, null,
                    null, null, null, null, null, null,
                    null, null, null, null, null,
                    null, null, null, null, null);
            Future<List<DailyBook>> future = queryGateway.query(new JSONObject(dailyBook), new MultipleInstancesResponseType<>(DailyBook.class));
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Currency getCurrencyByCodeAndStatus(String code, String status) {
        try {
            Currency currency = new Currency(code, null, null, null, null, status);
            Future<Currency> future = queryGateway.query(new JSONObject(currency), ResponseTypes.instanceOf(Currency.class));
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account getAccountByCode(String code) {
        try {
            Future<Account> future = queryGateway.query(code, ResponseTypes.instanceOf(Account.class));
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Income getIncomeByCode(String code) {
        try {
            Future<Income> future = queryGateway.query(code, ResponseTypes.instanceOf(Income.class));
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Expense getExpenseByCode(String code) {
        try {
            Future<Expense> future = queryGateway.query(code, ResponseTypes.instanceOf(Expense.class));
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DailyBook getDailyBookByFinancialCode(String code) {
        try {
            Future<DailyBook> future = queryGateway.query(code, ResponseTypes.instanceOf(DailyBook.class));
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DailyBook getDailyBookBeforeCloseByAggregateCodeAndHouseCode(String aggregateCode, String houseCode) {
        List<String> codes = Arrays.asList(Statics.openCode, Statics.closeCode);
        List<DailyBook> dailyBooks = dailyBookRepository.findByAggregateCodeAndHouseCode(aggregateCode, houseCode, codes);
        if (dailyBooks.size() > 0)
            return dailyBooks.get(0);
        return null;
    }

    public DailyBook getDailyBookByAggregateAndHouseAndAccount(String aggregate, String house, String account) {
        try {
            DailyBook dailyBook = new DailyBook(aggregate, null, account, null,
                    null, null, house, null, null, null,
                    null, null, null, null, null, null,
                    null, null, null, null, null,
                    null, null, null, null, null);
            Future<DailyBook> future = queryGateway.query(new JSONObject(dailyBook), ResponseTypes.instanceOf(DailyBook.class));
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<DailyBook> getLastRecordsByHouseCodeAndRegisterDate(String houseCode, Date date) {
        return dailyBookRepository.findByHouseCodeAndRegisterDateGreaterThan(houseCode, date);
    }

    public List<DailyBook> getLastRecordsByAggregateCodeHouseCodeAndRegisterDate(String aggregateCode, String houseCode, Date date) {
        return dailyBookRepository.findByAggregateCodeAndHouseCodeAndRegisterDateGreaterThan(aggregateCode, houseCode, date);
    }


    public FActiveCars getActiveCarsByCode(String code) {
        try {
            Future<FActiveCars> future = queryGateway.query(code, ResponseTypes.instanceOf(FActiveCars.class));
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FActiveHouses getActiveHouseByCode(String code) {
        try {
            Future<FActiveHouses> future = queryGateway.query(code, ResponseTypes.instanceOf(FActiveHouses.class));
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    public FActiveYoungPersons getActiveYoungPersonByCode(String code) {
        try {
            Future<FActiveYoungPersons> future = queryGateway.query(code, ResponseTypes.instanceOf(FActiveYoungPersons.class));
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
