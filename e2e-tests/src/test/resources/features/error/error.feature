@error
Feature: Show error handling

  @errorHandling
  Scenario: Show error message when an error occurs
    When the client navigates to the error page
    Then the page should display an error message
    And the error message should contain "Something happened..."
