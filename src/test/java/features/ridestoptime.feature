
Feature: Ride Stop Time
  Scenario: First stop-time for a ride must be at least 1 hour ahead from the system time.
    Given I have defined a route with 2 stop points
    And The first time in my list of 2 times is less than 1 hour ahead of my system time
    When I try to map these times to the route
    Then The mapping will not succeed
