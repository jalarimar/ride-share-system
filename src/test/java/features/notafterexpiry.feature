
Feature: Not After Expiry
  Scenario: The expiration date of a recurring trip cannot occur after the driver's
  licence expiry date or the car's WOF + registration.
    Given I have defined a route with 2 stop points
    And The expiry date is after the driver's licence expiry
    When I try to map these times to the route
    Then The mapping will not succeed
