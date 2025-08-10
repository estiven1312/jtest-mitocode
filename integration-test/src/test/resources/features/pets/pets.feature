@petsManagement
Feature: Management of pets

  Scenario: 01 - Save a pet
    Given the client prepare the data
      """
        {
          "id": 10,
          "name": "test",
          "category": {
            "id": 1,
            "name": "Dogs"
          },
          "photoUrls": [
            "string"
          ],
          "tags": [
            {
              "id": 0,
              "name": "mio"
            }
          ],
          "status": "available"
        }
      """
    When the client saves the new pet to the endpoint "/pet"
    Then the server should respond with a status "200"
    And the response body should contain with the following properties and values
      | id              | 10         |
      | name            | test       |
      | status          | available  |
      | category.id     | 1          |
      | category.name   | Dogs       |
      | photoUrls[0]    | string     |
      | tags[0].id      | 0          |
      | tags[0].name    | mio        |

  Scenario Outline: 02 - Save a pet
    Given client omit the field "<field>" in the following JSON:
       """
          {
          "id": 10,
          "name": "test",
          "category": {
            "id": 1,
            "name": "Dogs"
          },
          "photoUrls": [
            "string"
          ],
          "tags": [
            {
              "id": 0,
              "name": "mio"
            }
          ],
          "status": "available"
        }
       """
    When the client saves the wrong body to the endpoint "/pet"
    Then the server should respond with a status "500"
    And the response body should contain partial values
      | code    | 500                                  |
      | message | There was an error processing your   |
    Examples:
      | field |
      | id    |
