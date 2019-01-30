package features.pettyCash;

import com.finance.pettyCash.commandModel.commands.OpenCommand;
import com.finance.pettyCash.commons.dtos.request.PettyCashRequest;
import com.finance.pettyCash.commons.enums.AccountType;
import com.finance.pettyCash.commons.helpers.Statics;
import com.finance.pettyCash.commons.tool.JsonTool;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import org.json.JSONObject;
import org.junit.Assert;
import org.mockito.MockitoAnnotations;

public class Open extends BasePettyCash {

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        startMocks();
    }

    @Given("^I have cash box status in (.+) for the (.+) in (.+)$")
    public void i_have_cash_box_status_in_for_the_in(String state, String house, String currency) throws Throwable {
        createHouse(house, house);
        if (state.equalsIgnoreCase("open"))
            openPettyCash(house, null);

        createCurrency(currency);
        createAccount(Statics.openCode, "Opening", AccountType.INCOME);
    }

    @When("^I open petty cash with an initial balance of (.+) and (.+) for house (.+)$")
    public void i_open_petty_cash_with_an_initial_balance_of_and_for_house(String amount, String currency, String house) throws Throwable {
        JSONObject data = new JSONObject();
        data.put("houseCode", house);
        data.put("responsibleCode", "ADMIN");
        data.put("currencyCode", currency);
        data.put("amount", Double.parseDouble(amount));
        data.put("description", "Open Petty Cash");
        JSONObject objectData = new JSONObject();
        setJsonSchema(formCode, sectionCode, "/schemas/openClosePettyCash.json");
        objectData.put(sectionCode, data);
        PettyCashRequest request = new PettyCashRequest(formCode, null, JsonTool.getJSONObjectToObject(objectData));
        validatorHelper = dailyBookService.validateOpen(new OpenCommand(request, identifierFactory.generateIdentifier()));
    }

    @Then("^The open petty cash operation is (.+)$")
    public void the_open_petty_cash_operation_is(String result) throws Throwable {
        if (validatorHelper.isValid()) {
            Assert.assertEquals(validatorHelper.getErrors().size(), 0);
            Assert.assertEquals("success", result);
        } else {
            Assert.assertEquals(validatorHelper.getErrors().size(), 1);
            Assert.assertEquals("failed", result);
        }
    }
}