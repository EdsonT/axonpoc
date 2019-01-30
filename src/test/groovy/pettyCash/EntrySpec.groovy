package pettyCash

import com.finance.pettyCash.commons.dtos.request.PettyCashRequest
import com.finance.pettyCash.commons.enums.AccountType
import com.finance.pettyCash.commons.enums.EntityType
import com.finance.pettyCash.commons.enums.OutType
import com.finance.pettyCash.commons.tool.JsonTool
import org.json.JSONObject

class EntrySpec extends BaseTest {
    def "New income entry"() {
        given: "I need register a new entry for house in specific currency"
        createHouse(house, house)
        createCurrency(currency)
        createAccount("MI", "Money In", AccountType.INCOME.toString())
        def openId = executeOpen(house, currency, oldB)
        createIncome(type)
        def data = new JSONObject()
        data.put("houseCode", house)
        data.put("responsibleCode", "admin")
        data.put("currencyCode", currency)
        data.put("amount", amount)
        data.put("description", description)
        data.put("movementCode", type)
        data.put("accountCode", "MI")
        def objectData = new JSONObject()
        objectData.put("4656", data)
        def request = new PettyCashRequest(formCode, openId == null ? "123456" : openId, JsonTool.getJSONObjectToObject(objectData))
        when: "I register a new income entry"
        def response
        try {
            pettyCashController.entryPettyCash(request).getBody()
            response = "SUCCESS"
        } catch (Exception ve) {
            response = getResponseError(ve.getMessage().toString(), result)
        }
        then: "I recieved a result"
        response == result.toString()
        def balance = getLastBalance(house)
        if (balance != null)
            balance == newB
        where: "I have a new entry with data"
        formCode | house  | currency | amount | description   | type   | oldB | newB      | result
        "5466"   | null   | "EUR"    | 100    | "Buy food"    | "PO"   | 200  | 200.00000 | ["houseCode"]
        "5466"   | "HO-1" | "E"      | 100    | "Buy food"    | "PO"   | 200  | 200.00000 | ["currencyCode"]
        "5466"   | "HO-1" | "EUR"    | null   | "Buy food"    | "PO"   | 200  | 200.00000 | ["amount"]
        "5466"   | "HO-1" | "EUR"    | 100    | "Buy food"    | "PO"   | 200  | 300.00000 | "SUCCESS"
        "5466"   | "HO-1" | "EUR"    | 100    | "Buy food"    | null   | 200  | 200.00000 | ["movementCode"]
        "5466"   | "HO-1" | "EUR"    | 100    | "Buy food"    | "NOT"  | 200  | 200.00000 | ["NOTFOUND_INCOME_CODE"]
        "5466"   | "HO-1" | "EUR"    | 80     | "Celebration" | "TUFB" | 300  | 220.00000 | "SUCCESS"
        "5466"   | "HO-1" | "EUR"    | 80     | "Celebration" | "TUFB" | null | null      | ["ISNOT_OPEN_CASHBOX"]
        "5466"   | "HO-1" | "EUR"    | 0      | "Celebration" | "TUFB" | 300  | 300.00000 | ["amount"]
    }

    def "New expense entry"() {
        given: "I have a request new expense"
        createHouse(house, house)
        createCurrency(currency)
        createAccount("MO", "Money Out", AccountType.EXPENSE.toString())
        if (eEntity != null)
            if (eEntity.equals("YoungPerson"))
                createYoungPerson(assingTo, assingTo)
            else
                createCar(assingTo, assingTo)
        createExpense(eCode, eType, eEntity)
        def openId = executeOpen(house, currency, oldB)
        def data = new JSONObject()
        data.put("houseCode", house)
        data.put("responsibleCode", "admin")
        data.put("currencyCode", currency)
        data.put("amount", amount)
        data.put("description", description)
        data.put("movementCode", eCode)
        data.put("entityCode", assingTo)
        data.put("accountCode", "MO")
        def objectData = new JSONObject()
        objectData.put("4656", data)
        def request = new PettyCashRequest(formCode, openId == null ? "123456" : openId, JsonTool.getJSONObjectToObject(objectData))
        when:
        def response
        try {
            pettyCashController.entryPettyCash(request).getBody()
            response = "SUCCESS"
        } catch (Exception ve) {
            response = getResponseError(ve.getMessage().toString(), result)
        }
        then:
        response == result.toString()
        def balance = getLastBalance(house)
        if (balance != null)
            balance == newB
        where:
        formCode | house | currency | amount | description | eCode | eType      | eEntity       | assingTo | oldB | newB       | result
        "5466"   | null  | "EUR"    | 100    | "Cinema"    | "A"   | "Specific" | "YoungPerson" | "YPT"    | 200  | 200.00000  | ["houseCode"]
        "5466"   | "HO1" | "R"      | 100    | "Cinema"    | "A"   | "Specific" | "YoungPerson" | "YPT"    | 200  | 200.00000  | ["currencyCode"]
        "5466"   | "HO1" | "EUR"    | null   | "Cinema"    | "A"   | "Specific" | "YoungPerson" | "YPT"    | 200  | 200.00000  | ["amount"]
        "5466"   | "HO1" | "EUR"    | 100    | "Cinema"    | null  | "Specific" | "YoungPerson" | "YPT"    | 200  | 200.00000  | ["movementCode"]
        "9874"   | "HO1" | "EUR"    | 100    | "Cinema"    | "A"   | "Specific" | "YoungPerson" | "YPT"    | 200  | 100.00000  | "SUCCESS"
        "5466"   | "HO1" | "EUR"    | 500    | "Furniture" | "FO"  | "General"  | null          | null     | 200  | -300.00000 | "SUCCESS"
        "5466"   | "HO1" | "EUR"    | 500    | "Furniture" | "FO"  | "General"  | null          | null     | 500  | 0.00000    | "SUCCESS"
        "5466"   | "HO1" | "EUR"    | 20     | "Gas"       | "NOT" | "General"  | null          | null     | 100  | 100.00000  | ["NOTFOUND_EXPENSE_CODE"]
        "5466"   | "HO1" | "EUR"    | 20     | "Gas"       | "G"   | "General"  | null          | null     | 100  | 80.00000   | "SUCCESS"
        "5466"   | "HO1" | "EUR"    | 189.98 | "Petrol"    | "P"   | "General" | "Car"         | "PUP201" | 200  | 10.02000   | "SUCCESS"
    }
}