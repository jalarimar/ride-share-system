
Feature: Register Car
  Scenario: A driver registers a car
    Given a driver named "Jessica" "Robertson"
    When I register a green car: Honda Accord 2004, with license JRQ930 and 5 seats
    And I register a purple car: Ford Taurus 1933, with license ABC123 and 23 seats
    And I look at the car with license ABC123
    Then it is the colour purple
    And it has make and model Ford Taurus
