package pettyCash

import com.finance.pettyCash.commons.dtos.request.PettyCashRequest
import com.finance.pettyCash.commons.tool.JsonTool
import org.json.JSONObject

class CorrectEntrySpec extends BaseTest {

    def "Register old income entry"() {
        given: "I have a request to correct old entry"
        createHouse(house, house)
        createCurrency(coin)
        def firstDayRecords = [[100, "O"], [40, "MI"], [200, "MO"], [80, "MI"], [20, "C"]]
        def firstId = registerEntry(firstDayRecords, requestDate, house, coin, -1)
        def secondId = registerEntry(secondDayRecords, requestDate, house, coin, 1)
        def requestList = new ArrayList()
        correctAmounts.each {
            def data = new JSONObject()
            data.put("houseCode", house)
            data.put("responsibleCode", "admin")
            data.put("currencyCode", coin)
            data.put("amount", it[1])
            data.put("description", description)
            def aggregateCode
            if (it[0].equals("MO")) {
                data.put("movementCode", "F")
                data.put("entityCode", "JPT")
            } else
                data.put("movementCode", "TUFB")
            if (it[2].equals("SECOND"))
                aggregateCode = secondId
            else
                aggregateCode = firstId

            data.put("requestDate", requestDate)
            data.put("accountCode", it[0])
            def objectData = new JSONObject()
            objectData.put("4656898913", data)
            def request = new PettyCashRequest("131456", aggregateCode, JsonTool.getJSONObjectToObject(objectData))
            requestList.add(request)
        }
        when: "I register correct entry"
        def response
        try {
            requestList.each {
                pettyCashController.correctEntryPettyCash(it).getBody()
            }
            response = "SUCCESS"
        } catch (Exception e) {
            response = getResponseError(e.getMessage().toString(), result)
        }
        def dailyBook = queryService.getDailyBookByAggregateAndHouseAndAccount(firstId, house, "C")
        then: "I have a result"
        response == result.toString()
        dailyBook.difference == diffF
        where:
        house  | requestDate           | coin  | description  | correctAmounts                              | secondDayRecords                                            | result    | diffF
        "HO-1" | "01-10-2018 01:25:20" | "EUR" | "Buy a food" | [["MO", 35, "SECOND"]]                      | [[20, "O"], [10, "MI"], [50, "MO"], [60, "MI"], [5, "C"]]   | "SUCCESS" | 0
        "HO-2" | "01-11-2018 01:25:20" | "EUR" | "Buy a food" | [["MI", 60, "SECOND"]]                      | [[20, "O"], [10, "MI"], [50, "MO"], [60, "MI"], [100, "C"]] | "SUCCESS" | 0
        "HO-3" | "05-12-2018 01:25:20" | "EUR" | "Buy a food" | [["MI", 20, "FIRST"]]                       | [[40, "O"], [10, "MI"], [50, "MO"], [60, "MI"], [60, "C"]]  | "SUCCESS" | -20
        "HO-4" | "08-12-2018 01:25:20" | "EUR" | "Buy a food" | [["MI", 20, "FIRST"], ["MI", 30, "SECOND"]] | [[40, "O"], [10, "MI"], [50, "MO"], [60, "MI"], [90, "C"]]  | "SUCCESS" | -20
        "HO-5" | "08-12-2018 01:25:20" | "EUR" | "Buy a food" | [["MI", 20, "FIRST"], ["MO", 55, "SECOND"]] | [[40, "O"], [10, "MI"], [50, "MO"], [60, "MI"], [5, "C"]]   | "SUCCESS" | -20
        "HO-6" | "04-12-2018 05:15:58" | "EUR" | "Buy a food" | [["MO", 15, "FIRST"], ["MO", 10, "SECOND"]] | [[5, "O"], [10, "MI"], [50, "MO"], [60, "MI"], [15, "C"]]  | "SUCCESS" | 15
    }
}