@placing_order @payment_methods
Feature: As a user I want to be able to pay using different payment methods and place the order

  Background: User is ready to place the order
    Given user adds items in the cart
    And user gets to card page and clicks Proceed to Checkout
    And Shipping Info is filled out

@gift_card_payment
  Scenario Outline: paying with Gift Card using <gift card number> and <pin>
    And user chooses to Redeem Gift Card
    When user enters "<gift card number>" and "<pin>" and clicks Apply
    Then error "<message>" for gift card should be displayed

    Examples: data set for gift card
      | gift card number     | pin   |  message                                                           |
      | 12345                | 1234  | Please enter a valid gift card number, using at least 19 digits.   |
      | qazxwedwveshrthyw34  | 1234  | Please enter a valid gift card number, using numerical digits only.|
      |                      | 1234  | Please enter a gift card number.                                   |
      | 1234567890123456789  |       | Please enter a PIN.                                                |
      | 1234567890123456789  | 12    | Please enter a valid PIN, using four digits only.                  |
      | 1234567890123456789  | test  | Please enter a valid PIN, using numerical digits only.             |
      | 0987654321098765432  | 0000  | Gift card number doesn't exist, can't be redeemed                  |

@credit_card_payment
  Scenario Outline: paying with Credit or Debit Card
    When user enters "<credit card number>" and "<expiration>" and "<cvv>" and "<phone number>"
    Then error "<message>" for credit card should be displayed

    Examples: data set for credit or debit card
      | credit card number  | expiration | cvv | phone number | message                                                      |
      |                     | 1025       | 111 | 12345678901  | Please enter credit card number, this field can't be empty   |
      | 4111111111111111    |            | 111 | 12345678901  | Please enter an expiration date.                             |
      | 4111111111111111    | 1025       |     | 12345678901  | Please enter a CVV number.                                   |
      | 4111111111111111    | 1025       | 111 |              | Please enter a phone number.                                 |
