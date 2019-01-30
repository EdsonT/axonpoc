package pettyCash

import com.finance.pettyCash.commons.dtos.request.PettyCashRequest
import com.finance.pettyCash.commons.enums.AccountType
import com.finance.pettyCash.commons.helpers.Statics
import com.finance.pettyCash.commons.tool.JsonTool
import org.json.JSONObject

class CloseSpec extends BaseTest {
    def "Close Petty Cash"() {
        given: "I have a request to close petty cash"
        createHouse(house, house)
        createCurrency(currency)
        createAccount(Statics.closeCode, "Close petty cash", AccountType.CLOSING.toString())
        def openId = executeOpen(house, currency, oldB)
        def data = new JSONObject()
        data.put("houseCode", house)
        data.put("responsibleCode", "ADMIN")
        data.put("currencyCode", currency)
        data.put("amount", amount)
        data.put("description", "Close Petty Cash")
        def objectData = new JSONObject()
        objectData.put("56464", data)
        def request = new PettyCashRequest(formCode, openId == null ? "123456" : openId, JsonTool.getJSONObjectToObject(objectData))
        when:
        def response
        try {
            pettyCashController.closePettyCash(request).getBody()
            response = "SUCCESS"
        } catch (Exception ve) {
            response = getResponseError(ve.getMessage().toString(), result)
        }
        then:
        response == result.toString()
        def balance = getLastByHouseAndAccount(house, Statics.closeCode)
        if (balance != null) {
            balance.realBalance == newB
            balance.difference == diffClose
        }
        where:
        formCode  | house     | currency | oldB  | amount | newB | diffClose | result
        "1231331" | null      | "EUR"    | 200   | 200    | 200  | null      | ["houseCode"]
        "1231331" | "HOUSE-1" | "E"      | 200   | 200    | 200  | null      | ["currencyCode"]
        "1231331" | "HOUSE-1" | "EUR"    | null  | 200    | 200  | null      | ["ISNOT_OPEN_CASHBOX"]
        "1231331" | "HOUSE-1" | "EUR"    | 200   | null   | 200  | null      | ["amount"]
        "1231331" | "HOUSE-1" | "EUR"    | 200   | 200    | 200  | 0         | "SUCCESS"
        "1231331" | "HOUSE-1" | "EUR"    | 200   | 250    | 250  | 50        | "SUCCESS"
        "1231331" | "HOUSE-1" | "EUR"    | 300   | 100    | 100  | -200      | "SUCCESS"
        "1231331" | "HOUSE-1" | "EUR"    | 175.5 | 200    | 200  | 24.5      | "SUCCESS"
        "1231331" | "HOUSE-1" | "NOT"    | 20    | 20     | 20   | null      | ["NOTFOUND_CURRENCY_CODE", "ISNOT_OPEN_CASHBOX"]
    }
}
