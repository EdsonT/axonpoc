package features.pettyCash;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class IncomeOldEntry {
    @Given("^I need to register a income entry for a old date (.+) in the house (.+) in (.+)$")
    public void i_need_to_register_a_income_entry_for_a_old_date_in_the_house_in(String date, String house, String currency) throws Throwable {
        throw new PendingException();
    }

    @When("^I register the income entry with information of (.+) (.+), (.+) and old date (.+)$")
    public void i_register_the_income_entry_with_information_of_and_old_date(String currency, String amount, String description, String requestdate) throws Throwable {
        throw new PendingException();
    }

    @Then("^the money income entry  is (.+)$")
    public void the_money_income_entry_is(String result) throws Throwable {
        throw new PendingException();
    }

    @And("^I have old entries (.+) and old entries (.+) before and after (.+) respectively$")
    public void i_have_old_entries_and_old_entries_before_and_after_respectively(String oldbefore, String oldafter, String requestdate) throws Throwable {
        throw new PendingException();
    }

    @And("^the balance of all subsequence entries are updated and the new balance of each entries are (.+) after income entry is registered$")
    public void the_balance_of_all_subsequence_entries_are_updated_and_the_new_balance_of_each_entries_are_after_income_entry_is_registered(String newbalance) throws Throwable {
        throw new PendingException();
    }
}
