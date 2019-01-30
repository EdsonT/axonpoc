package features.pettyCash;

import com.finance.pettyCash.commandModel.commands.EntryCommand;
import com.finance.pettyCash.commandModel.domains.*;
import com.finance.pettyCash.commons.dtos.request.PettyCashRequest;
import com.finance.pettyCash.commons.enums.AccountType;
import com.finance.pettyCash.commons.helpers.Statics;
import com.finance.pettyCash.commons.tool.JsonTool;
import com.finance.pettyCash.queryModel.finalView.dailyBooks.DailyBookCmd;
import com.finance.pettyCash.queryModel.finalView.income.Income;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class IncomeNewEntry extends BasePettyCash {
    private String house;
    private String currency;
    private Double balance;
    private Double oldBalance;
    private String aggregateCode = "987654";

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        startMocks();
    }

    @Given("^I need to register a new entry income in the (.+) with (.+) and (.+)$")
    public void i_need_to_register_a_new_entry_income_in_the_with_and(String house, String currency, String moneysource) throws Throwable {
        createHouse(house, house);
        this.house = house.equalsIgnoreCase("null") ? null : house;
        this.currency = currency.equalsIgnoreCase("null") ? null : currency;
        createCurrency(this.currency);
        createAccount(Statics.openCode, "Opening", AccountType.INCOME);
        createAccount("MI", "Money In", AccountType.INCOME);

        Income incomeView = new Income(moneysource, moneysource, "A", "ACTIVE");
        when(queryService.getIncomeByCode(moneysource)).thenReturn(incomeView);
    }

    @And("^the cash box have status is (.+) with the initial balance is (.+)$")
    public void the_cash_box_have_status_is_with_the_initial_balance_is(String status, String oldbalance) throws Throwable {
        this.oldBalance = oldbalance.equalsIgnoreCase("null") ? null : Double.parseDouble(oldbalance);
        if (status.equalsIgnoreCase("open")) {
            if (this.oldBalance != null && house != null) {
                openPettyCash(this.house, aggregateCode);
                DailyBookCmd dailyBook = new DailyBookCmd();
                when(queryService.getDailyBookByAggregateAndHouseAndAccount(aggregateCode, house, Statics.openCode)).thenReturn(dailyBook);
                newEntry(this.currency, this.house, this.oldBalance);
                balance = queryService.getBalancePerHouse(house).getRealBalance();
            } else {
                when(queryService.getPettyCashByAccountCodeAndHouseCode(Statics.openCode, house)).thenReturn(null);
                when(queryService.getBalancePerHouse(house)).thenReturn(null);
            }
        } else {
            balance = this.oldBalance;
        }
    }

    @When("^I register the new income entry with information of (.+) (.+), (.+), (.+)$")
    public void i_register_the_new_income_entry_with_information_of_(String currency, Double amount, String description, String moneysource) throws Throwable {
        JSONObject data = new JSONObject();
        data.put("houseCode", house);
        data.put("responsibleCode", "ADMIN");
        data.put("currencyCode", this.currency);
        data.put("amount", amount);
        data.put("description", description);
        data.put("accountCode", "MI");
        data.put("movementCode", moneysource);
        JSONObject objectData = new JSONObject();
        setJsonSchema(formCode, sectionCode, "/schemas/newEntryPettyCash.json");
        objectData.put(sectionCode, data);
        PettyCashRequest request = new PettyCashRequest(formCode, aggregateCode, JsonTool.getJSONObjectToObject(objectData));
        EntryCommand command = new EntryCommand(identifierFactory.generateIdentifier(), request);
        validatorHelper = dailyBookService.validateEntry(command);
        if (validatorHelper.isValid()) {
            Balance balanceObj = new Balance();
            balanceObj.openInit(new Amount(new CurrencyModel(currency, currency, currency), amount));
            balanceObj.updateBalance(amount, AccountType.INCOME.toString(), oldBalance);
            balance = balanceObj.getSystemBalance();
        } else {
            System.out.println("ERRORORS::" + validatorHelper.getErrors());
        }
    }

    @Then("^the register of new income entry is (.+)$")
    public void the_register_of_new_income_entry_is(String result) throws Throwable {
        if (validatorHelper.isValid()) {
            Assert.assertEquals(result, "success");
        } else {
            Assert.assertEquals(result, "failed");
        }
    }

    @And("^the balance is updated with a new (.+) of balance for new income entry$")
    public void the_balance_is_updated_with_a_new_of_balance_for_new_income_entry(String newbalance) throws Throwable {
        Double newB = newbalance.equalsIgnoreCase("null") ? null : Double.parseDouble(newbalance);
        Assert.assertEquals(newB, balance);
    }
}
