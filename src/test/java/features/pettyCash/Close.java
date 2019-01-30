package features.pettyCash;

import com.finance.pettyCash.commandModel.commands.CloseCommand;
import com.finance.pettyCash.commandModel.domains.Amount;
import com.finance.pettyCash.commandModel.domains.Balance;
import com.finance.pettyCash.commandModel.domains.CurrencyModel;
import com.finance.pettyCash.commons.dtos.request.PettyCashRequest;
import com.finance.pettyCash.commons.enums.AccountType;
import com.finance.pettyCash.commons.helpers.Statics;
import com.finance.pettyCash.commons.tool.JsonTool;
import com.finance.pettyCash.queryModel.finalView.dailyBooks.DailyBookCmd;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class Close extends BasePettyCash {
    private String house;
    private String currency;
    private String status;
    private Double balance;
    private Double diffClose;
    private String aggregateCode = "4564697";

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        startMocks();
    }

    @Given("^I have petty cash status in (.+) for the (.+)$")
    public void i_have_petty_cash_status_in_for_the(String state, String house) throws Throwable {
        this.house = house.equalsIgnoreCase("null") ? null : house;
        createAccount(Statics.closeCode, "Closing", AccountType.EXPENSE);
        this.status = state;
    }

    @And("^the system balance is (.+) (.+)$")
    public void the_system_balance_in(String sysb, String currency) throws Throwable {
        this.currency = currency.equalsIgnoreCase("null") ? null : currency;
        createCurrency(this.currency);
        Double sysB = sysb.equalsIgnoreCase("null") ? null : Double.parseDouble(sysb);
        if (this.status.equalsIgnoreCase("open")) {
            if (sysB != null && this.house != null) {
                createHouse(house, house);
                openPettyCash(this.house, aggregateCode);
                DailyBookCmd dailyBook = new DailyBookCmd();
                when(queryService.getDailyBookByAggregateAndHouseAndAccount(aggregateCode, house, Statics.openCode)).thenReturn(dailyBook);
                newEntry(this.currency, this.house, sysB);
                this.balance = queryService.getBalancePerHouse(house).getRealBalance();
            } else {
                when(queryService.getPettyCashByAccountCodeAndHouseCode(Statics.openCode, house)).thenReturn(null);
                when(queryService.getBalancePerHouse(house)).thenReturn(null);
            }
        } else {
            balance = sysB;
        }

    }

    @When("^I closing petty cash with the physical amount (.+) (.+)$")
    public void i_closing_petty_cash_with_the_physical_amount(String currency, Double amount) throws Throwable {
        JSONObject data = new JSONObject();
        data.put("houseCode", house);
        data.put("responsibleCode", "ADMIN");
        data.put("currencyCode", currency);
        data.put("amount", amount);
        data.put("description", "Close Petty Cash");
        JSONObject objectData = new JSONObject();
        objectData.put(sectionCode, data);
        PettyCashRequest request = new PettyCashRequest(formCode, aggregateCode, JsonTool.getJSONObjectToObject(objectData));
        setJsonSchema(formCode, sectionCode, "/schemas/openClosePettyCash.json");
        validatorHelper = dailyBookService.validateClose(new CloseCommand(request, identifierFactory.generateIdentifier()));
        if (validatorHelper.isValid()) {
            Balance balanceObj = new Balance();
            Double oldBalance = queryService.getBalancePerHouse(house).getSystemBalance();
            balanceObj.openNormal(new Amount(new CurrencyModel(currency, currency, currency), amount), oldBalance);
            balanceObj.closeBalance(amount, oldBalance);
            balance = balanceObj.getRealBalance();
            diffClose = balanceObj.getDifference();
        }
    }

    @Then("^the closing of petty cash is (.+)$")
    public void the_closing_of_petty_cash_is(String result) throws Throwable {
        if (validatorHelper.isValid()) {
            Assert.assertEquals(result, "success");
        } else {
            Assert.assertEquals(result, "failed");
        }
    }

    @And("^the final balance of the house for the shift is (.+) and (.+)$")
    public void the_final_balance_of_the_house_for_the_shift_is_and(String balance, String diffclose) throws Throwable {
        Double newB = balance.equalsIgnoreCase("null") ? null : Double.parseDouble(balance);
        Double diff = diffclose.equalsIgnoreCase("null") ? null : Double.parseDouble(diffclose);
        Assert.assertEquals(newB, this.balance);
        Assert.assertEquals(diff, this.diffClose);
    }
}
