Feature: Register old movement in petty cash
  As a staff member
  I want to register a old income/expense entry
  So that I have the control of all money movements in the house for the shift

  Scenario Outline: Register old income entry
    Given I need to register a income entry for a old date <date> in the house <house> in <currency>
    And I have old entries <oldBefore> and old entries <oldAfter> before and after <requestDate> respectively
    When I register the income entry with information of <currency> <amount>, <description> and old date <requestDate>
    Then the money income entry  is <result>
    And the balance of all subsequence entries are updated and the new balance of each entries are <newBalance> after income entry is registered

    Examples:

      | house  | requestDate | currency | amount | description           | oldBefore                     | oldAfter                        | result  | newBalance |
      | house1 | 01/10/2018  | Euros    | 100    | request of buy a food | [[100, O], [50, MO]]          | [[100, MI], [40, MO], [20, MI]] | success | 230        |
      | house1 | 01/11/2018  | Euros    | 100    | request of buy a food | [[50, O], [40, MO], [20, MI]] | [[500, MI]]                     | success | 630        |
      | house1 | 05/12/2018  | null     | 100    | request of buy a food | [[58.50, O], [3, MO]]         | [[100, MI], [150, MO]]          | failed  | 5.50       |
      | null   | 08/12/2018  | Euros    | 100    | request of buy a food | [[58.50, O]]                  | [[50, MI], [40, MO], [20, MI]]  | failed  | 88.50      |
      | house1 | 01/12/2018  | Euros    | null   | request of buy a food | [[58.50, O]]                  | [[50, MI], [40, MO], [20, MI]]  | failed  | 88.50      |
      | house1 | 01/12/2018  | Euros    | 100    | null                  | [[58.50, O]]                  | [[50, MI], [40, MO], [20, MI]]  | failed  | 88.50      |
      | house1 | 01/12/2018  | Euros    | 100    | request of buy a food | [[58.50, O]]                  | [[50, MI], [40, MO], [20, MI]]  | success | 188.50     |


  Scenario Outline: Register old expense entry
    Given I need to register a expense entry for a old date <date> in the house <house>
    When I register the expense entry with information of <currency> <amount>, <description>, <category_expenditure>, <assign_to>, old date <date>
    Then the register of expense entry is <result>
    And the balance of all subsequence entries are updated and the new balance of each entries are <new_balance>  after expense entry is registered

    Examples:

      | house  | date      | currency | amount | description                | category_expenditure | assign_to  | result  | new_balance    |
      | house1 | 1/10/2018 | Euros    | 20     | the child yo to the cinema | Activity             | Juan Perez | success | [100,40,10,30] |
      | house2 | 1/10/2018 | Euros    | 100    | by some vegetables         | Food                 | -          | success | [100,40,10,30] |
      | house3 | 1/10/2018 | null     | 100    | by some vegetables         | Food                 |            | failed  |                |
      | null   | 1/10/2018 | Euros    | 100    | by some vegetables         | Food                 |            | failed  |                |
      | house3 | 1/10/2018 | Euros    | null   | by some vegetables         | Food                 |            | failed  |                |
      | house3 | 1/10/2018 | Euros    | null   | by some vegetables         | null                 |            | failed  |                |
