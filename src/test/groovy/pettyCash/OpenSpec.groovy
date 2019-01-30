package pettyCash

import com.finance.pettyCash.commandModel.domains.FinancialCode
import com.finance.pettyCash.commons.dtos.request.PettyCashRequest
import com.finance.pettyCash.commons.enums.AccountType
import com.finance.pettyCash.commons.helpers.Statics
import com.finance.pettyCash.commons.tool.JsonTool
import org.json.JSONObject

class OpenSpec extends BaseTest {

    def "Verify generate finantial code"() {
        given: "I have a new Instance of Financial Code"
        def financialCode = new FinancialCode()
        when: "I generate Financial Code"
        financialCode.generateCode(code, auto)
        then: "Financial code is"
        financialCode.code == resutl
        where:
        code              | auto     | resutl
        Statics.openCode  | "1345AD" | "20190O-1345AD"
        "MI"              | "1"      | "20190MI-1"
        "MO"              | "12323"  | "20190MO-12323"
        Statics.closeCode | "0adsa"  | "20190C-0ADSA"
    }

    def "Open Petty Cash"() {
        given: "I have a request open petty cash for house"
        createHouse(houseCode, houseCode)
        createCurrency(currencySymbol)
        open(houseCode, status)
        createAccount(Statics.openCode, "Opening", AccountType.OPENING.toString())
        when: "I open petty cash"
        def data = new JSONObject()
        data.put("houseCode", houseCode)
        data.put("responsibleCode", resCode)
        data.put("currencyCode", currencySymbol)
        data.put("amount", amount)
        data.put("description", "Open Petty Cash")
        def objectData = new JSONObject()
        objectData.put("56464", data)
        def request = new PettyCashRequest(formCode, null, JsonTool.getJSONObjectToObject(objectData))
        def response
        try {
            pettyCashController.openPettyCash(request).getBody()
            response = "SUCCESS"
        } catch (Exception ve) {
            response = getResponseError(ve.getMessage().toString(), result)
        }
        then: "I have a result"
        response == result.toString()
        where:
        formCode | houseCode | currencySymbol | amount      | resCode | status  | result
        "5466"   | null      | "EUR"          | 100         | "ADMIN" | "close" | ["houseCode"]
        "5466"   | "H1-ONE"  | null           | 100         | "ADMIN" | "close" | ["currencyCode"]
        "5466"   | "H1"      | null           | 100         | "ADMIN" | "close" | ["houseCode", "currencyCode"]
        "5466"   | "H1"      | "EU"           | null        | "ADMIN" | "close" | ["houseCode", "currencyCode", "amount"]
        "5466"   | "H1-ONE"  | "EUR"          | 0.000000125 | "ADMIN" | "close" | ["amount"]
        "5466"   | "H1-ONE"  | "EUR"          | 100         | "ADMIN" | "close" | "SUCCESS"
        "5466"   | "H1-ONE"  | "EUR"          | 1000        | "ADMIN" | "open"  | ["ALREADY_OPEN_CASH"]
        "5466"   | "H1-ONE"  | "NOT"          | 1000        | "ADMIN" | "open"  | ["NOTFOUND_CURRENCY_CODE", "ALREADY_OPEN_CASH"]
    }
}
