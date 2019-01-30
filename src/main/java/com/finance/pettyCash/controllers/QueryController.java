package com.finance.pettyCash.controllers;

import com.finance.pettyCash.commons.exceptions.BaseException;
import com.finance.pettyCash.controllers.dto.Response;
import com.finance.pettyCash.queryModel.SearchQuery;
import com.finance.pettyCash.queryModel.finalView.accounts.Account;
import com.finance.pettyCash.queryModel.finalView.accounts.AccountQuery;
import com.finance.pettyCash.queryModel.finalView.activeCars.FActiveCars;
import com.finance.pettyCash.queryModel.finalView.activeCars.FActiveCarsQuery;
import com.finance.pettyCash.queryModel.finalView.activeHouses.FActiveHouses;
import com.finance.pettyCash.queryModel.finalView.activeHouses.FActiveHousesQuery;
import com.finance.pettyCash.queryModel.finalView.activeYoungPersons.FActiveYoungPersons;
import com.finance.pettyCash.queryModel.finalView.activeYoungPersons.FActiveYoungPersonsQuery;
import com.finance.pettyCash.queryModel.finalView.currencies.Currency;
import com.finance.pettyCash.queryModel.finalView.currencies.CurrencyQuery;
import com.finance.pettyCash.queryModel.finalView.dailyBooks.DailyBook;
import com.finance.pettyCash.queryModel.finalView.dailyBooks.DailyBookQuery;
import com.finance.pettyCash.queryModel.finalView.entityPerExpenses.FEntityPerExpenses;
import com.finance.pettyCash.queryModel.finalView.entityPerExpenses.FEntityPerExpensesQuery;
import com.finance.pettyCash.queryModel.finalView.expenses.Expense;
import com.finance.pettyCash.queryModel.finalView.expenses.ExpenseQuery;
import com.finance.pettyCash.queryModel.finalView.houses.FHouses;
import com.finance.pettyCash.queryModel.finalView.houses.FHousesQuery;
import com.finance.pettyCash.queryModel.finalView.income.Income;
import com.finance.pettyCash.queryModel.finalView.income.IncomeQuery;
import com.finance.pettyCash.queryModel.operationalView.balance.BalancePerHouse;
import com.finance.pettyCash.queryModel.operationalView.balance.BalancePerHouseQuery;
import com.finance.pettyCash.queryModel.operationalView.openPettyCash.PettyCash;
import com.finance.pettyCash.queryModel.operationalView.openPettyCash.PettyCashQuery;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.responsetypes.MultipleInstancesResponseType;
import org.axonframework.queryhandling.responsetypes.ResponseTypes;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
//@RequestMapping(value = "petty-cash")
public class QueryController {

    @Autowired
    QueryGateway gateway;

    @GetMapping("currencies")
    public Response listCurrencies(CurrencyQuery params) throws ExecutionException, InterruptedException {
        Response response = new Response();
        Future<List<Currency>> future = gateway.query(new JSONObject(params), new MultipleInstancesResponseType<>(Currency.class));
        response.setContent(future.get());
        return response;
    }

    @GetMapping("currencies/{code}")
    public Response getCurrencies(@PathVariable String code) throws ExecutionException, InterruptedException {
        Response response = new Response();
        Future<Currency> future = gateway.query(code, ResponseTypes.instanceOf(Currency.class));
        response.setContent(future.get());

        return response;
    }

    @GetMapping("accounts")
    public Response listAccount(AccountQuery params) throws ExecutionException, InterruptedException {
        Response response = new Response();
        Future<List<Account>> future = gateway.query(new JSONObject(params), new MultipleInstancesResponseType<>(Account.class));
        response.setContent(future.get());
        return response;
    }

    @GetMapping("accounts/{code}")
    public Response getAccount(@PathVariable String code) throws ExecutionException, InterruptedException {
        Response response = new Response();
        Future<Account> future = gateway.query(code, ResponseTypes.instanceOf(Account.class));
        response.setContent(future.get());
        return response;
    }

    @GetMapping("accounts/findOne")
    public Response getAccount(AccountQuery params) throws ExecutionException, InterruptedException {
        Response response = new Response();
        Future<Account> future = gateway.query(new JSONObject(params), ResponseTypes.instanceOf(Account.class));
        response.setContent(future.get());
        return response;
    }


//    @GetMapping("accounts/findCodeIn")
//    public Response getFindCodeIn(Account accountQuery) throws ExecutionException, InterruptedException {
//        Response response = new Response();
//
//        Future<List<Account>> future = gateway.query(accountQuery, new MultipleInstancesResponseType<>(Account.class));
//        response.setContent(future.get());
//        return response;
//    }


    @GetMapping("daily-book")
    public Response listDailyBook(DailyBookQuery params) throws ExecutionException, InterruptedException{
        System.out.println(params);
        Response response = new Response();
        Future<List<DailyBook>> future = gateway.query(new JSONObject(params), new MultipleInstancesResponseType<>(DailyBook.class));
        response.setContent(future.get());
        return response;
    }

    @GetMapping("daily-book/findOne")
    public Response getDailyBook(DailyBookQuery params) throws ExecutionException, InterruptedException {
        Response response = new Response();
        //DailyBook param1 = new DailyBook("201801MI2", "MI", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        Future<DailyBook> future = gateway.query(new JSONObject(params), ResponseTypes.instanceOf(DailyBook.class));
        response.setContent(future.get());
        return response;
    }

    @GetMapping("expenses")
    public Response listExpense(ExpenseQuery params) throws ExecutionException, InterruptedException {
        Response response = new Response();
        Future<List<Expense>> future = gateway.query(new JSONObject(params), new MultipleInstancesResponseType<>(Expense.class));
        response.setContent(future.get());
        return response;
    }

    @GetMapping("expenses/{code}")
    public Response getExpense(@PathVariable String code) throws ExecutionException, InterruptedException {
        Response response = new Response();
        Future<Expense> future = gateway.query(code, ResponseTypes.instanceOf(Expense.class));
        response.setContent(future.get());

        return response;
    }

    @GetMapping("incomes")
    public Response listIncome(IncomeQuery params) throws ExecutionException, InterruptedException {
        Response response = new Response();
        Future<List<Income>> future = gateway.query(new JSONObject(params), new MultipleInstancesResponseType<>(Income.class));
        response.setContent(future.get());
        return response;
    }

    @GetMapping("incomes/{code}")
    public Response getIncome(@PathVariable String code) throws ExecutionException, InterruptedException {
        Response response = new Response();
        Future<Income> future = gateway.query(code, ResponseTypes.instanceOf(Income.class));
        response.setContent(future.get());
        return response;
    }

    @GetMapping("petty-cash-opened")
    public Response listOpenPettyCash(PettyCashQuery params) throws ExecutionException, InterruptedException {
        Response response = new Response();
        Future<List<PettyCash>> future = gateway.query(new JSONObject(params), new MultipleInstancesResponseType<>(PettyCash.class));
        response.setContent(future.get());
        return response;
    }

    @GetMapping("petty-cash-opened/findOne")
    public Response getPettyCashOpened(PettyCashQuery params) throws ExecutionException, InterruptedException {
        Response response = new Response();
        Future<PettyCash> future = gateway.query(new JSONObject(params), ResponseTypes.instanceOf(PettyCash.class));
        response.setContent(future.get());
        return response;
    }



    @GetMapping("balance-per-house")
    public Response listBalancePerHouse(BalancePerHouseQuery params) throws ExecutionException, InterruptedException, BaseException {
        Response response = new Response();
        Future<List<BalancePerHouse>> future = gateway.query(new JSONObject(params), new MultipleInstancesResponseType<>(BalancePerHouse.class));
        response.setContent(future.get());

        return response;
    }
    @GetMapping("balance-per-house/findOne")
    public Response findOne(BalancePerHouseQuery params) throws ExecutionException, InterruptedException, BaseException {
        Response response = new Response();
        Future<BalancePerHouse> future = gateway.query(new JSONObject(params), ResponseTypes.instanceOf(BalancePerHouse.class));
        response.setContent(future.get());


        return response;
    }
    @GetMapping("balance-per-house/search")
    public Response search(@Valid SearchQuery query) throws ExecutionException, InterruptedException, BaseException {
        System.out.println(query);
        Response response = new Response();
        Future<List<BalancePerHouse>> future = gateway.query("searchQuery",query, new MultipleInstancesResponseType<>(BalancePerHouse.class));
        response.setContent(future.get());


        return response;
    }

    @GetMapping("entity-per-expenses")
    public com.finance.pettyCash.commons.dtos.response.Response listFEntityPerExpenses(FEntityPerExpensesQuery params) throws
            ExecutionException, InterruptedException {
        com.finance.pettyCash.commons.dtos.response.Response response = new com.finance.pettyCash.commons.dtos.response.Response();
        Future<List<FEntityPerExpenses>> future = gateway.query(new JSONObject(params), new MultipleInstancesResponseType<>(FEntityPerExpenses.class));
        response.setContent(future.get());
        return response;
    }

    @GetMapping("entity-per-expenses/findOne")
    public com.finance.pettyCash.commons.dtos.response.Response findOneFEntityPerExpenses(FEntityPerExpensesQuery params) throws
            ExecutionException, InterruptedException {
        com.finance.pettyCash.commons.dtos.response.Response response = new com.finance.pettyCash.commons.dtos.response.Response();
        Future<FEntityPerExpenses> future = gateway.query(new JSONObject(params), ResponseTypes.instanceOf(FEntityPerExpenses.class));
        response.setContent(future.get());
        return response;
    }


//    @GetMapping("houses")
//    public com.finance.pettyCash.commons.dtos.response.Response listFHouses(FHousesQuery params) throws ExecutionException,
//            InterruptedException {
//        com.finance.pettyCash.commons.dtos.response.Response response = new com.finance.pettyCash.commons.dtos.response.Response();
//        Future<List<FHouses>> future = gateway.query(new JSONObject(params), new MultipleInstancesResponseType<>(FHouses.class));
//        response.setContent(future.get());
//        return response;
//    }
//
//    @GetMapping("houses/{code}")
//    public com.finance.pettyCash.commons.dtos.response.Response getFHouses(@PathVariable String code) throws ExecutionException,
//            InterruptedException {
//        com.finance.pettyCash.commons.dtos.response.Response response = new com.finance.pettyCash.commons.dtos.response.Response();
//        Future<FHouses> future = gateway.query(code, ResponseTypes.instanceOf(FHouses.class));
//        response.setContent(future.get());
//        return response;
//    }
//
//    @GetMapping("houses/findOne")
//    public com.finance.pettyCash.commons.dtos.response.Response findOneFHouses(FHousesQuery params) throws ExecutionException,
//            InterruptedException {
//        com.finance.pettyCash.commons.dtos.response.Response response = new com.finance.pettyCash.commons.dtos.response.Response();
//        Future<FHouses> future = gateway.query(new JSONObject(params), ResponseTypes.instanceOf(FHouses.class));
//        response.setContent(future.get());
//        return response;
//    }

    @GetMapping("active-cars")
    public com.finance.pettyCash.commons.dtos.response.Response listFActiveCars(FActiveCarsQuery params) throws ExecutionException,
            InterruptedException {
        com.finance.pettyCash.commons.dtos.response.Response response = new com.finance.pettyCash.commons.dtos.response.Response();
        Future<List<FActiveCars>> future = gateway.query(new JSONObject(params), new MultipleInstancesResponseType<>(FActiveCars.class));
        response.setContent(future.get());
        return response;
    }

    @GetMapping("active-cars/{code}")
    public com.finance.pettyCash.commons.dtos.response.Response getFActiveCars(@PathVariable String code) throws ExecutionException,
            InterruptedException {
        com.finance.pettyCash.commons.dtos.response.Response response = new com.finance.pettyCash.commons.dtos.response.Response();
        Future<FActiveCars> future = gateway.query(code, ResponseTypes.instanceOf(FActiveCars.class));
        response.setContent(future.get());
        return response;
    }

    @GetMapping("active-cars/findOne")
    public com.finance.pettyCash.commons.dtos.response.Response findOneFActiveCars(FActiveCarsQuery params) throws ExecutionException,
            InterruptedException {
        com.finance.pettyCash.commons.dtos.response.Response response = new com.finance.pettyCash.commons.dtos.response.Response();
        Future<FActiveCars> future = gateway.query(new JSONObject(params), ResponseTypes.instanceOf(FActiveCars.class));
        response.setContent(future.get());
        return response;
    }

    @GetMapping("active-houses")
    public com.finance.pettyCash.commons.dtos.response.Response listFActiveHouses(FActiveHousesQuery params) throws ExecutionException,
            InterruptedException {
        com.finance.pettyCash.commons.dtos.response.Response response = new com.finance.pettyCash.commons.dtos.response.Response();
        Future<List<FActiveHouses>> future = gateway.query(new JSONObject(params), new MultipleInstancesResponseType<>(FActiveHouses.class));
        response.setContent(future.get());
        return response;
    }

    @GetMapping("active-houses/{code}")
    public com.finance.pettyCash.commons.dtos.response.Response getFActiveHouses(@PathVariable String code) throws ExecutionException,
            InterruptedException {
        com.finance.pettyCash.commons.dtos.response.Response response = new com.finance.pettyCash.commons.dtos.response.Response();
        Future<FActiveHouses> future = gateway.query(code, ResponseTypes.instanceOf(FActiveHouses.class));
        response.setContent(future.get());
        return response;
    }

    @GetMapping("active-houses/findOne")
    public com.finance.pettyCash.commons.dtos.response.Response findOneFActiveHouses(FActiveHousesQuery params) throws ExecutionException,
            InterruptedException {
        com.finance.pettyCash.commons.dtos.response.Response response = new com.finance.pettyCash.commons.dtos.response.Response();
        Future<FActiveHouses> future = gateway.query(new JSONObject(params), ResponseTypes.instanceOf(FActiveHouses.class));
        response.setContent(future.get());
        return response;
    }
    @GetMapping("active-young-persons")
    public com.finance.pettyCash.commons.dtos.response.Response listFActiveYoungPersons(FActiveYoungPersonsQuery params) throws
            ExecutionException, InterruptedException {
        com.finance.pettyCash.commons.dtos.response.Response response = new com.finance.pettyCash.commons.dtos.response.Response();
        Future<List<FActiveYoungPersons>> future = gateway.query(new JSONObject(params), new MultipleInstancesResponseType<>(FActiveYoungPersons.class));
        response.setContent(future.get());
        return response;
    }

    @GetMapping("active-young-persons/{code}")
    public com.finance.pettyCash.commons.dtos.response.Response getFActiveYoungPersons(@PathVariable String code) throws ExecutionException,
            InterruptedException {
        com.finance.pettyCash.commons.dtos.response.Response response = new com.finance.pettyCash.commons.dtos.response.Response();
        Future<FActiveYoungPersons> future = gateway.query(code, ResponseTypes.instanceOf(FActiveYoungPersons.class));
        response.setContent(future.get());
        return response;
    }

    @GetMapping("active-young-persons/findOne")
    public com.finance.pettyCash.commons.dtos.response.Response findOneFActiveYoungPersons(FActiveYoungPersonsQuery params) throws
            ExecutionException, InterruptedException {
        com.finance.pettyCash.commons.dtos.response.Response response = new com.finance.pettyCash.commons.dtos.response.Response();
        Future<FActiveYoungPersons> future = gateway.query(new JSONObject(params), ResponseTypes.instanceOf(FActiveYoungPersons.class));
        response.setContent(future.get());
        return response;
    }
}
