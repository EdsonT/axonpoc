package features.pettyCash;

import com.finance.pettyCash.commons.enums.AccountType;
import com.finance.pettyCash.commons.helpers.Statics;
import com.finance.pettyCash.commons.helpers.ValidatorHelper;
import com.finance.pettyCash.commons.tool.JsonTool;
import com.finance.pettyCash.comunicator.JsonSchemaService;
import com.finance.pettyCash.queryModel.finalView.accounts.Account;
import com.finance.pettyCash.queryModel.finalView.accounts.AccountCmd;
import com.finance.pettyCash.queryModel.finalView.activeCars.FActiveCars;
import com.finance.pettyCash.queryModel.finalView.activeHouses.FActiveHouses;
import com.finance.pettyCash.queryModel.finalView.activeYoungPersons.FActiveYoungPersons;
import com.finance.pettyCash.queryModel.finalView.currencies.Currency;
import com.finance.pettyCash.queryModel.operationalView.balance.BalancePerHouse;
import com.finance.pettyCash.queryModel.operationalView.openPettyCash.PettyCashCmd;
import com.finance.pettyCash.services.pettyCash.DailyBookService;
import com.finance.pettyCash.services.pettyCash.DailyBookServiceImpl;
import com.finance.pettyCash.services.QueryService;
import org.axonframework.common.IdentifierFactory;
import org.json.JSONObject;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.when;

public class BasePettyCash {
    protected final IdentifierFactory identifierFactory = IdentifierFactory.getInstance();
    QueryService queryService;
    JsonSchemaService jsonSchemaService;
    DailyBookService dailyBookService;

    ValidatorHelper validatorHelper;
    String formCode = "8964-5646-7846-6565";
    String sectionCode = "5646-9869-4512-3223";

    void startMocks() {
        queryService = Mockito.mock(QueryService.class);
        jsonSchemaService = Mockito.mock(JsonSchemaService.class);
        dailyBookService = new DailyBookServiceImpl(queryService, jsonSchemaService);
    }

    void createHouse(String code, String name) {
        FActiveHouses house = new FActiveHouses(code, name);
        when(queryService.getActiveHouseByCode(code)).thenReturn(house);
    }

    void createYoungPerson(String code, String name) {
        if (code != null) {
            FActiveYoungPersons person = new FActiveYoungPersons(code, name, code, name);
            when(queryService.getActiveYoungPersonByCode(code)).thenReturn(person);
        } else {
            when(queryService.getActiveYoungPersonByCode(code)).thenReturn(null);
        }
    }

    void createCar(String code, String name) {
        if (code != null) {
            FActiveCars car = new FActiveCars(code, name, code, name);
            when(queryService.getActiveCarsByCode(code)).thenReturn(car);
        } else {
            when(queryService.getActiveCarsByCode(code)).thenReturn(null);
        }
    }

    void createCurrency(String currency) {
        if (currency != null) {
            Currency currencyView = new Currency(currency, currency, currency, true, "A", "ACTIVE");
            when(queryService.getCurrencyByCodeAndStatus(currency, "ACTIVE")).thenReturn(currencyView);
        } else {
            when(queryService.getCurrencyByCodeAndStatus(currency, "ACTIVE")).thenReturn(null);
        }
    }

    void createAccount(String code, String name, AccountType type) {
        Account account = new AccountCmd(code, name, "", type.toString());
        when(queryService.getAccountByCode(code)).thenReturn(account);
    }

    void openPettyCash(String house, String aggregateCode) {
        PettyCashCmd pettyCash = new PettyCashCmd(aggregateCode, Statics.openCode, "OPENING", house, house);
        when(queryService.getPettyCashByAccountCodeAndHouseCode(Statics.openCode, house)).thenReturn(pettyCash);
    }

    void newEntry(String currency, String house, double amount) {
        BalancePerHouse balancePerHouse = new BalancePerHouse(amount, amount, 0D, house, currency, currency, currency);
        when(queryService.getBalancePerHouse(house)).thenReturn(balancePerHouse);
    }

    void setJsonSchema(String formCode, String sectionCode, String file) throws IOException {
        JSONObject schema = JsonTool.loadJsonSchema(file);
        when(dailyBookService.getJsonSchema(formCode, sectionCode, file)).thenReturn(schema);
    }
}
