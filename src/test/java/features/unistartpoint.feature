
Feature: Uni Start Point
  Scenario: The University of Canterbury must be either the departure point or the destination of all trips
    Given I am a driver
    When I attempt to create a route which has UC as the departure point
    Then it will pass validation
