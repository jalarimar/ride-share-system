
Feature: StopPoint
  Scenario: Create Stop Point
    Given a driver called "Jessica" "Robertson"
    When I create a stop point at 15 Clonbern Place, Ilam
    And I look at the driver's list of stop points
    Then the list of stop points contains 15 Clonbern Place, Ilam
