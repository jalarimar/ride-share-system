
Feature: Not After WOF Expiry
  Scenario: The expiration date of a recurring trip cannot occur after the vehicle's
  WOF expiry date.
    Given I have defined a route with 2 stop points
    And The expiry date is after the vehicle's WOF expiry
    When I try to map these times to the route
    Then The mapping will not succeed