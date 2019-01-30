package features.pettyCash;

import com.finance.pettyCash.commandModel.commands.EntryCommand;
import com.finance.pettyCash.commandModel.domains.*;
import com.finance.pettyCash.commons.dtos.request.PettyCashRequest;
import com.finance.pettyCash.commons.enums.AccountType;
import com.finance.pettyCash.commons.helpers.Statics;
import com.finance.pettyCash.commons.tool.JsonTool;
import com.finance.pettyCash.queryModel.finalView.dailyBooks.DailyBookCmd;
import com.finance.pettyCash.queryModel.finalView.expenses.Expense;
import com.finance.pettyCash.queryModel.finalView.expenses.ExpenseCmd;
import com.finance.pettyCash.queryModel.operationalView.balance.BalancePerHouse;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class OutcomeEntry extends BasePettyCash {
    private String house;
    private String currency;
    private Double balance;
    private Double oldBalance;
    private String aggregateCode = "1231346";
    private String expenseType;
    private String entityType;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        startMocks();
    }

    @Given("^I need to register a new expense of type (.+) with code (.+) in the (.+) in (.+) for (.+)$")
    public void i_need_to_register_a_new_expense_of_type_with_code_in_the_in_for(String etype, String ecode, String house,
                                                                                 String currency, String entity) throws Throwable {
        createHouse(house, house);
        this.house = house.equalsIgnoreCase("null") ? null : house;
        this.currency = currency.equalsIgnoreCase("null") ? null : currency;

        createCurrency(this.currency);
        createAccount(Statics.openCode, "Opening", AccountType.INCOME);
        createAccount("MO", "Money Out", AccountType.EXPENSE);

        Expense expenseView = new ExpenseCmd(ecode, ecode, ecode, etype, etype, entity);
        when(queryService.getExpenseByCode(ecode)).thenReturn(expenseView);
        this.expenseType = etype;
        this.entityType = entity;
    }

    @And("^the cash box have state is (.+) with the initial balance is (.+)$")
    public void the_cash_box_have_state_is_with_the_initial_balance_is(String status, String oldb) throws Throwable {
        this.oldBalance = oldb.equalsIgnoreCase("null") ? null : Double.parseDouble(oldb);
        if (status.equalsIgnoreCase("open")) {
            if (oldBalance != null && house != null) {
                openPettyCash(house, aggregateCode);
                newEntry(this.currency, this.house, oldBalance);
                balance = queryService.getBalancePerHouse(house).getRealBalance();
                DailyBookCmd dailyBook = new DailyBookCmd();
                when(queryService.getDailyBookByAggregateAndHouseAndAccount(aggregateCode, house, Statics.openCode)).thenReturn(dailyBook);
            } else {
                when(queryService.getPettyCashByAccountCodeAndHouseCode(Statics.openCode, house)).thenReturn(null);
                when(queryService.getBalancePerHouse(house)).thenReturn(null);
            }
        } else {
            balance = oldBalance;
        }
    }

    @When("^I register the new expense entry with information of (.+) (.+), (.+), (.+), (.+)$")
    public void i_register_the_new_expense_entry_with_information_of_(String currency, Double amount, String description,
                                                                      String assignto, String ecode) throws Throwable {
        if (!entityType.equalsIgnoreCase("General")) {
            if (entityType.equalsIgnoreCase("Car"))
                createCar(assignto, assignto);
            else if (entityType.equalsIgnoreCase("YoungPerson"))
                createYoungPerson(assignto, assignto);
        }
        JSONObject data = new JSONObject();
        data.put("houseCode", house);
        data.put("responsibleCode", "ADMIN");
        data.put("currencyCode", this.currency);
        data.put("amount", amount);
        data.put("description", description);
        data.put("accountCode", "MO");
        data.put("movementCode", ecode);
        data.put("entityCode", assignto);
        JSONObject objectData = new JSONObject();
        setJsonSchema(formCode, sectionCode, "/schemas/newEntryPettyCash.json");
        objectData.put(sectionCode, data);
        PettyCashRequest request = new PettyCashRequest(formCode, aggregateCode, JsonTool.getJSONObjectToObject(objectData));
        EntryCommand command = new EntryCommand(identifierFactory.generateIdentifier(), request);
        validatorHelper = dailyBookService.validateEntry(command);
        if (validatorHelper.isValid()) {
            BalancePerHouse balancePerHouse = queryService.getBalancePerHouse(house);
            Balance balanceObj = new Balance();
            balanceObj.openInit(new Amount(new CurrencyModel(currency, currency, currency), amount));
            balanceObj.updateBalance(amount, AccountType.EXPENSE.toString(), balancePerHouse.getSystemBalance());
            balance = balanceObj.getSystemBalance();
        }
    }

    @Then("^the register of new expense entry is (.+)$")
    public void the_register_of_new_expense_entry_is(String result) throws Throwable {
        if (validatorHelper.isValid()) {
            Assert.assertEquals(result, "success");
        } else {
            Assert.assertEquals(result, "failed");
        }
    }

    @And("^the balance is updated with a new (.+) of balance for new expense entry$")
    public void the_balance_is_updated_with_a_new_of_balance_for_new_expense_entry(String newb) throws Throwable {
        Double newB = newb.equalsIgnoreCase("null") ? null : Double.parseDouble(newb);
        Assert.assertEquals(newB, balance);
    }
}
