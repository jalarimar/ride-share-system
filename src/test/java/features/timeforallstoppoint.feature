
Feature: Time For All Stop Points
  Scenario: A trip must have a stop-time for all of its stop-points.
    Given I have defined a route with 2 stop points
    And I have defined a list of 1 time
    When I try to map these times to the route
    Then The mapping will not succeed
