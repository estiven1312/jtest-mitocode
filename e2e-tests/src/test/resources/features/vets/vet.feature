@vetManagement
Feature: List Vets

  @listVets
  Scenario: List all vets
    When The client click on the vets menu
    Then the page should show a list of vets with table id called "vetsTable"
    And the response should contain the following vets:
      | name          | specialties       |
      | James Carter  | none              |
      | Linda Douglas | dentistry surgery |