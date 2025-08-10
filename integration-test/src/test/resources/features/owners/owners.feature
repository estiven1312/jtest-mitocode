@gestionarPropietarios
Feature: Gestionar propietarios

  @listarPropietarios
  Scenario: Listar propietarios
    When el cliente realiza una peticion GET a "api/owners"
    Then el servidor debe de responder con un status 200
    And el cuerpo de la respuesta debe ser una lista de propietarios valida

  @obtenerPropietarioPorId
  Scenario: Obtener propietario por id
    When el cliente realiza una peticion GET a "api/owners/6"
    Then el servidor debe de responder con un status 200
    And el cliente comprueba el valor del campo "firstName" es igual a "Jean"
    And el cliente comprueba el valor del campo "lastName" es igual a "Coleman"
    And el cliente comprueba el valor del campo "address" es igual a "105 N. Lake St."
    And el cliente comprueba el valor del campo "city" es igual a "Monona"
    And el cliente comprueba el valor del campo "telephone" es igual a "6085552654"
    And el cliente comprueba el valor del campo "id" es igual a 6
    #    Forma 1: Data Table
    And el cuerpo de la respuesta contiene las propiedades en una sola columna
      | firstName |
      | lastName  |
      | address   |
      | city      |
      | telephone |
 #   Forma 2: Data Table
    And el cuerpo de la respuesta contiene las propiedades en una sola fila
      | firstName | lastName | address | city | telephone |
 #   Forma 3: Data Table
    And el cuerpo de la respuesta contiene con las siguientes propiedades y valores
      | firstName         | Jean             |
      | lastName          | Coleman          |
      | address           | 105 N. Lake St.  |
      | city              | Monona           |
      | telephone         | $str{6085552654} |
      | id                | 6                |
      | pets.name[0]      | Max              |
      | pets[0].type.name | cat              |

  @registrarPropietario
  Scenario: Registrar propietario
    Given el cliente tiene los datos de un nuevo propietario
       """
          {
              "firstName": "Dany",
              "lastName": "Cenas",
              "address": "Av. Javier Prado",
              "city": "Lima",
              "telephone": "123456789"
          }
       """
    When el cliente realiza una peticion POST a "api/owners" con los datos del nuevo propietario
    Then el servidor debe de responder con un status 201
    And el cliente comprueba el valor del campo "firstName" es igual a "Dany"
    And el cliente comprueba el valor del campo "lastName" es igual a "Cenas"
    And el cliente comprueba el valor del campo "address" es igual a "Av. Javier Prado"
    And el cliente comprueba el valor del campo "city" es igual a "Lima"
    And el cliente comprueba el valor del campo "telephone" es igual a "123456789"

  @registrarPropietarioConCamposRequeridos
  Scenario Outline: Registrar propietario con campos requeridos
    Given el cliente omite el campo requerido "<campo>" en el siguiente JSON:
       """
          {
              "firstName": "Dany",
              "lastName": "Cenas",
              "address": "Av. Javier Prado",
              "city": "Lima",
              "telephone": "123456789"
          }
       """
    When el cliente realiza una peticion POST a "api/owners"
    Then el servidor debe de responder con un status 400
    And la cabecera de la respuesta contiene la propiedad "errors" con valor "<campo>"
    Examples:
      | campo     |
      | firstName |
      | lastName  |
      | address   |
      | city      |
      | telephone |

  @registrarPropietarioConCamposVacios
#    @skip
  Scenario Outline: Registrar propietario con campos vacios
    Given el cliente proporciona los campos <firstName> <lastName> <address> <city> <telephone>
    When el cliente realiza una peticion POST a "api/owners"
    Then el servidor debe de responder con un status 400
    Examples:
      | firstName | lastName | address            | city   | telephone   |
      | ""        | "Cenas"  | "Av. Javier Prado" | "Lima" | "123456789" |
      | "Dany"    | ""       | "Av. Javier Prado" | "Lima" | "123456789" |
      | "Dany"    | "Cenas"  | ""                 | "Lima" | "123456789" |
      | "Dany"    | "Cenas"  | "Av. Javier Prado" | ""     | "123456789" |
      | "Dany"    | "Cenas"  | "Av. Javier Prado" | "Lima" | ""          |

  @registrarPropietarioConCamposNulos
#    @skip
  Scenario Outline: Registrar propietario con campos nulos
    Given el cliente omite el campo requerido "<campo>" con valor null en el siguiente JSON:
       """
          {
              "firstName": "Dany",
              "lastName": "Cenas",
              "address": "Av. Javier Prado",
              "city": "Lima",
              "telephone": "123456789"
          }
       """
    When el cliente realiza una peticion POST a "api/owners"
#    Then el servidor debe de responder con un status 400
    Examples:
      | campo     |
      | firstName |
      | lastName  |
      | address   |
      | city      |
      | telephone |