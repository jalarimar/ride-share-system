
Feature: Timezone
  Scenario: Time must be set to Wellington time zone using AM/PM format
    Given I have a stop point with a time
    When I get the stop point's zoned time
    Then the timezone is Pacific/Auckland
    When I get the stop point's readable time
    Then it is in the AM/PM format
