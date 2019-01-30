Feature: close cash box
  As a staff member
  I want to close the petty cash
  So that I can close the money movements in the house for the shift

  Scenario Outline: Cash box closing
    Given I have petty cash status in <state> for the <house>
    And the system balance is <sysB> <currency>
    When I closing petty cash with the physical amount <currency> <amount>
    Then the closing of petty cash is <result>
    And the final balance of the house for the shift is <balance> and <diffClose>

    Examples:
      | house  | state | sysB | currency | amount | balance | diffClose | result  |
      | House1 | open  | 50   | euros    | 100    | 100     | 50        | success |
      | House2 | close | 50   | euros    | 100    | 50      | null      | failed  |
      | House3 | open  | 50   | euros    | 50     | 50      | 0         | success |
      | House4 | open  | 50   | euros    | 30     | 30      | -20       | success |