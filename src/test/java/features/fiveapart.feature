
Feature: Five Apart
  Scenario: The route’s stop-times must differ from each other by at least 5 minutes.
    Given I have defined a route with 2 stop points
    And The second time in my list of 2 times is less than 5 minutes ahead of the first time
    When I try to map these times to the route
    Then The mapping will not succeed
