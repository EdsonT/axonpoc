package pettyCash

import com.finance.pettyCash.PettyCashApplication
import com.finance.pettyCash.commons.dtos.request.PettyCashRequest
import com.finance.pettyCash.commons.dtos.response.Response
import com.finance.pettyCash.commons.enums.AccountType
import com.finance.pettyCash.commons.enums.EntityType
import com.finance.pettyCash.commons.enums.OutType
import com.finance.pettyCash.commons.helpers.Statics
import com.finance.pettyCash.commons.tool.JsonTool
import com.finance.pettyCash.comunicator.JsonSchemaService
import com.finance.pettyCash.controllers.DailyBookController
import com.finance.pettyCash.queryModel.finalView.accounts.Account
import com.finance.pettyCash.queryModel.finalView.accounts.AccountCmd
import com.finance.pettyCash.queryModel.finalView.accounts.AccountRepository
import com.finance.pettyCash.queryModel.finalView.activeCars.FActiveCars
import com.finance.pettyCash.queryModel.finalView.activeCars.FActiveCarsRepository
import com.finance.pettyCash.queryModel.finalView.activeHouses.FActiveHouses
import com.finance.pettyCash.queryModel.finalView.activeHouses.FActiveHousesRepository
import com.finance.pettyCash.queryModel.finalView.activeYoungPersons.FActiveYoungPersons
import com.finance.pettyCash.queryModel.finalView.activeYoungPersons.FActiveYoungPersonsRepository
import com.finance.pettyCash.queryModel.finalView.currencies.Currency
import com.finance.pettyCash.queryModel.finalView.currencies.CurrencyRepository
import com.finance.pettyCash.queryModel.finalView.dailyBooks.DailyBook
import com.finance.pettyCash.queryModel.finalView.dailyBooks.DailyBookRepository
import com.finance.pettyCash.queryModel.finalView.expenses.ExpenseCmd
import com.finance.pettyCash.queryModel.finalView.expenses.ExpenseRepository
import com.finance.pettyCash.queryModel.finalView.income.Income
import com.finance.pettyCash.queryModel.finalView.income.IncomeRepository
import com.finance.pettyCash.queryModel.operationalView.balance.BalancePerHouse
import com.finance.pettyCash.queryModel.operationalView.balance.BalancePerHouseRepository
import com.finance.pettyCash.queryModel.operationalView.openPettyCash.PettyCashCmd
import com.finance.pettyCash.queryModel.operationalView.openPettyCash.PettyCashRepository
import com.finance.pettyCash.services.QueryService
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId

@SpringBootTest(classes = PettyCashApplication)
@ActiveProfiles("test")
class BaseTest extends Specification {

    @Autowired
    CurrencyRepository currencyRepository

    @Autowired
    DailyBookRepository dailyBookRepository

    @Autowired
    PettyCashRepository pettyCashRepository

    @Autowired
    IncomeRepository incomeRepository

    @Autowired
    ExpenseRepository expenseRepository

    @Autowired
    AccountRepository accountRepository

    @Autowired
    FActiveHousesRepository housesRepository

    @Autowired
    FActiveCarsRepository carsRepository

    @Autowired
    FActiveYoungPersonsRepository youngPersonsRepository;

    @Autowired
    DailyBookController pettyCashController

    @Autowired
    QueryService queryService

    @Autowired
    BalancePerHouseRepository balancePerHouseRepository

    @Autowired
    JsonSchemaService jsonSchemaService

    def setup() {
        currencyRepository.deleteAll()
        pettyCashRepository.deleteAll()
        dailyBookRepository.deleteAll()
        incomeRepository.deleteAll()
        expenseRepository.deleteAll()
        accountRepository.deleteAll()
        balancePerHouseRepository.deleteAll()
        housesRepository.deleteAll()
        carsRepository.deleteAll()
        youngPersonsRepository.deleteAll()
    }

    def createHouse(String code, String name) {
        if (code != null) {
            def oldHouse = queryService.getActiveHouseByCode(code)
            if (oldHouse == null) {
                def house = new FActiveHouses(code, name)
                housesRepository.save(house)
            }
        }
    }

    def createYoungPerson(String code, String name) {
        if (code != null) {
            def oldPerson = queryService.getActiveYoungPersonByCode(code)
            if (oldPerson == null) {
                def person = new FActiveYoungPersons(code, name, code, name)
                youngPersonsRepository.save(person)
            }
        }
    }

    def createCar(String code, String name) {
        if (code != null) {
            def oldCar = queryService.getActiveCarsByCode(code)
            if (oldCar == null) {
                def car = new FActiveCars(code, name, code, name)
                carsRepository.save(car)
            }
        }
    }

    def createCurrency(symbol) {
        if (symbol != null && symbol != "NOT" && queryService.getCurrencyByCodeAndStatus(symbol, "ACTIVE") == null) {
            def currency = new Currency(symbol, "European Money", symbol, true, "A", "ACTIVE")
            currencyRepository.save(currency)
        }
    }

    def createAccount(String code, String name, String type) {
        if (queryService.getAccountByCode(code) == null) {
            Account account = new AccountCmd(code, name, code + "-" + name, type)
            accountRepository.save(account)
        }
    }

    def open(String houseCode, String status) {
        if (status == "open") {
            def cashBox = new PettyCashCmd("123133", Statics.openCode, "OPENING", houseCode, houseCode)
            pettyCashRepository.save(cashBox)
        }
    }

    def entry(Double amount, Currency currency, String house) {
        DailyBook book = new DailyBook()
        book.financialCode = "2018OO-1"
        book.accountCode = Statics.openCode
        book.accountName = "OPENING"
        book.accountType = AccountType.INCOME.toString()
        book.description = "Open petty cash"
        book.houseCode = house
        book.houseName = house
        book.requestDate = new Date()
        book.registrationDate = new Date()
        book.amount = amount
        book.currencyCode = currency.getCode()
        book.currencyName = currency.getDescription()
        book.currencySymbol = currency.getSymbol()
        book.responsibleCode = "admin"
        book.responsibleName = "admin"

        dailyBookRepository.save(book)

        def balance = new BalancePerHouse(amount, amount, 0D, house, currency, currency, currency)
        balancePerHouseRepository.save(balance)
    }

    def executeOpen(String house, String currency, Double amount) {
        createAccount(Statics.openCode, "Openning", AccountType.INCOME.toString())
        def data = new JSONObject()
        data.put("houseCode", house)
        data.put("responsibleCode", "ADMIN")
        data.put("currencyCode", currency)
        data.put("amount", amount)
        data.put("description", "Open Petty Cash")
        def objectData = new JSONObject()
        objectData.put("56464", data)
        def request = new PettyCashRequest("2345", null, JsonTool.getJSONObjectToObject(objectData))
        try {
            def openResult = pettyCashController.openPettyCash(request)
            return (((Response) openResult.getBody()).getContent()).get("aggregateCode")
        } catch (Exception ve) {
            println "ERROR IN OPEN COMMAND EXECUTION:: " + ve.getMessage()
        }
        return null
    }

    def executeEntry(String aggregateId, String house, String currency, Double amount, String account, String mov, String assingTo) {
        def data = new JSONObject()
        data.put("houseCode", house)
        data.put("responsibleCode", "admin")
        data.put("currencyCode", currency)
        data.put("amount", amount)
        data.put("description", house + " " + account)
        data.put("movementCode", mov)
        data.put("accountCode", account)
        if (assingTo != null) {
            createYoungPerson(assingTo, assingTo)
            data.put("entityCode", assingTo)
        }
        def objectData = new JSONObject()
        objectData.put("4656", data)
        def request = new PettyCashRequest("5623-5654", aggregateId, JsonTool.getJSONObjectToObject(objectData))
        try {
            def entryResult = pettyCashController.entryPettyCash(request)
            return (((Response) entryResult.getBody()).getContent()).get("aggregateCode")
        } catch (Exception ve) {
            println "ERROR IN ENTRY COMMAND EXECUTION:: " + ve.getMessage()
        }
    }

    def executeClose(String house, String currency, Double amount, String aggregateCode) {
        createAccount("C", "Closing", AccountType.EXPENSE.toString())
        def data = new JSONObject()
        data.put("houseCode", house)
        data.put("responsibleCode", "ADMIN")
        data.put("currencyCode", currency)
        data.put("amount", amount)
        data.put("description", "Close Petty Cash")
        def objectData = new JSONObject()
        objectData.put("56464", data)
        def request = new PettyCashRequest("2345", aggregateCode, JsonTool.getJSONObjectToObject(objectData))
        try {
            def openResult = pettyCashController.closePettyCash(request)
            return (((Response) openResult.getBody()).getContent()).get("aggregateCode")
        } catch (Exception ve) {
            println "ERROR IN CLOSE COMMAND EXECUTION:: " + ve.getMessage()
        }
        return null
    }

    def createIncome(String code) {
        if (code != null && code != "NOT") {
            if (queryService.getIncomeByCode(code) == null) {
                def income = new Income(code, code, "A", "ACTIVE")
                incomeRepository.save(income)
            }
        }
    }

    def createExpense(String code, String type, String entity) {
        if (code != null && code != "NOT" && queryService.getExpenseByCode(code) == null) {
            def expense = new ExpenseCmd(code, code, code, type, type, entity)
            expenseRepository.save(expense)
        }
    }

    def getLastBalance(String house) {
        def record = queryService.getBalancePerHouse(house)
        if (record != null)
            return record.getRealBalance()
        return null
    }

    def getLastByHouseAndAccount(String house, String account) {
        def record = queryService.getLastByHouseCodeAndAccountCode(house, account)
        if (record != null)
            return record
        return null
    }

    def getResponseError(String aux, List result) {
        def errorList = new ArrayList()
        result.each {
            if (aux.contains(it))
                errorList.add(it)
        }
        return errorList.toString()
    }

    def registerEntry(List records, String requestDate, String house, String currency, Number days) {
        def aggregateId = null
        records.each {
            def accountCode = it[1]
            def amount = it[0]
            if (accountCode.equalsIgnoreCase(Statics.openCode)) {
                aggregateId = executeOpen(house, currency, amount)
            }
            if (accountCode.equalsIgnoreCase("MI")) {
                createAccount(accountCode, accountCode, AccountType.INCOME.toString())
                createIncome("TUFB")
                executeEntry(aggregateId, house, currency, amount, accountCode, "TUFB", null)
            } else if (accountCode.equalsIgnoreCase("MO")) {
                createAccount(accountCode, accountCode, AccountType.EXPENSE.toString())
                createYoungPerson("JPT", "Juan Peres")
                createExpense("F", OutType.Specific.toString(), EntityType.YoungPerson.toString())
                executeEntry(aggregateId, house, currency, amount, accountCode, "F", "JPT")
            }
            if (accountCode.equalsIgnoreCase(Statics.closeCode)) {
                executeClose(house, currency, amount, aggregateId)
            }
            modifyDateDailyBook(house, aggregateId, getDate(requestDate, days))
        }

        return aggregateId
    }

    def modifyDateDailyBook(String house, String aggregateId, Date date) {
        def dailyBooks = queryService.getByAggregateCode(aggregateId, house)
        dailyBooks.each {
            it.requestDate = date
            it.requestDatetime = date.getTime()
            dailyBookRepository.save(it)
        }
    }

    def getDate(String date, Number number) {
        DateFormat dateFormat = new SimpleDateFormat(Statics.dateFormat)
        Date dateFormatted = dateFormat.parse(date)
        LocalDate localDate = (dateFormatted.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).plusDays(number)
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
    }

    def cleanup() {
        currencyRepository.deleteAll()
        pettyCashRepository.deleteAll()
        dailyBookRepository.deleteAll()
        incomeRepository.deleteAll()
        expenseRepository.deleteAll()
        accountRepository.deleteAll()
        balancePerHouseRepository.deleteAll()
        housesRepository.deleteAll()
        carsRepository.deleteAll()
        youngPersonsRepository.deleteAll()
    }
}