
Feature: Daylight Savings
  Scenario: All the time fields should be automatically updated to account for daylight savings time
    Given it was 1:50am the last time I looked at my clock and daylight savings ends at 2am
    And it has been 2 hours since then
    When I try to catch a 3:30am ride
    Then I haven't missed the ride
