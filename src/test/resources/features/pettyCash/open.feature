Feature: open cash box
  As a staff member
  I want to open petty cash
  So that I can setup an initial balance in the House for the shift

  Scenario Outline: Cash box opening
    Given I have cash box status in <state> for the <house> in <currency>
    When I open petty cash with an initial balance of <amount> and <currency> for house <house>
    Then The open petty cash operation is <result>

    Examples:

      | house  | state | amount | currency | balance   | result  |
      | H1-ONE | close | 101.50 | EUROS    | 101.50000 | success |
      | H1-TWO | open  | 50     | RUPIA    | 101.50000 | failed  |