Feature: Register new movement in petty cash
  As a staff member
  I want to register a new income entry
  So that I have the control of all money movements in the house for the shift

  Scenario Outline: Register new income entry for the same shift
    Given I need to register a new entry income in the <house> with <currency> and <moneySource>
    And the cash box have status is <status> with the initial balance is <old_balance>
    When I register the new income entry with information of <currency> <amount>, <description>, <moneySource>
    Then the register of new income entry is <result>
    And the balance is updated with a new <new_balance> of balance for new income entry

    Examples:

      | house  | status | old_balance | currency | amount | description           | moneySource | result  | new_balance |
      | house1 | open   | 50          | EUR      | 100    | request of buy a food | TUFB        | success | 150         |
      | house1 | open   | 150         | EUR      | 100    | request of buy a food | PO          | success | 250         |
      | house1 | open   | 180         | null     | 100    | request of buy a food | TUFB        | failed  | 180         |
      | null   | open   | 180         | EUR      | 100    | request of buy a food | TUFB        | failed  | null        |
      | house1 | open   | null        | EUR      | 100    | request of buy a food | TUFB        | failed  | null        |
      | house1 | open   | 180         | EUR      | 100    | null                  | TUFB        | success | 280         |
      | house1 | close  | 50          | EUR      | 100    | request of buy a food | TUFB        | failed  | 50          |

  Scenario Outline: Register new expense entry
    Given I need to register a new expense of type <eType> with code <eCode> in the <house> in <currency> for <entity>
    And the cash box have state is <status> with the initial balance is <oldB>
    When I register the new expense entry with information of <currency> <amount>, <description>, <assign_to>, <eCode>
    Then the register of new expense entry is <result>
    And the balance is updated with a new <newB> of balance for new expense entry

    Examples:
      | house  | status | oldB  | currency | amount | description        | eCode | eType    | entity      | assign_to  | result  | newB |
      | house1 | open   | 50.0  | EUR      | 20     | Go to the cinema   | A     | Specific | YoungPerson | Juan Perez | success | 30   |
      | house2 | open   | 150.0 | EUR      | 100    | by some vegetables | F     | General  | null        | all        | success | 50   |
      | house3 | open   | 180.0 | null     | 100    | by some vegetables | F     | General  | null        | all        | failed  | 180  |
      | house4 | close  | null  | EUR      | 100    | by some vegetables | F     | General  | null        | null       | failed  | null |
      | house1 | open   | 50.0  | EUR      | 20     | Go to the cinema   | G     | Specific | Car         | PUP90U     | success | 30   |