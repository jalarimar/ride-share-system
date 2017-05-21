
Feature: Driver Not Passenger
  Scenario: The driver cannot be a passenger of their own ride
    Given I am a driver named "Jessica Robertson" with university id 1234
    And I am offering a ride which has 0 passengers
    When I try to book the ride
    Then I am not allowed to book the ride
    And the ride still has 0 passengers
