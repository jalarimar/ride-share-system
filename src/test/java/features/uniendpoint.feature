
Feature: Uni End Point
  Scenario: The University of Canterbury must be either the departure point or the destination of all trips
    Given I am a driver
    When I attempt to create a route which does not have UC as the departure point or destination
    Then it will not pass validation
