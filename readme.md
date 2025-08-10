# spring-petclinic-rest-unit-test)spring-petclinic-rest-unit-test

## Unit Tests

Este proyecto incluye pruebas unitarias para garantizar el correcto funcionamiento de los servicios principales de la aplicación. A continuación, se describen los archivos de prueba y lo que se implementó en cada uno de ellos.

### Archivos de Prueba

#### `ClinicServiceUnitTest`
Este archivo contiene pruebas unitarias para el servicio `ClinicServiceImpl`. Las pruebas verifican el comportamiento de los métodos principales del servicio, utilizando mocks para simular las dependencias. Entre las pruebas implementadas se encuentran:

- **`shouldFindAllPets`**: Verifica que el método `findAllPets` devuelva la lista completa de mascotas.
- **`shouldDeletePet`**: Comprueba que el método `deletePet` elimine correctamente una mascota.
- **`shouldThrowsDataAccessExceptionWhenTryDeletePet`**: Valida que se lance una excepción `DataAccessException` al intentar eliminar una mascota en caso de error.
- **`shouldFindOwnerById`**: Verifica que el método `findOwnerById` devuelva el propietario correcto según su ID.
- **`shouldReturnNullWhenNoOwnerWithGivenId`**: Comprueba que se devuelva `null` cuando no se encuentra un propietario con el ID proporcionado.
- **`shouldReturnVisitsByPetId`**: Valida que el método `findVisitsByPetId` devuelva las visitas asociadas a un ID de mascota.

#### `UserServiceUnitTest`
Este archivo contiene pruebas unitarias para el servicio `UserServiceImpl`. Las pruebas aseguran que los usuarios y sus roles se manejen correctamente. Entre las pruebas implementadas se encuentran:

- **`shouldSaveUser`**: Verifica que un usuario con roles se guarde correctamente en el repositorio.
- **`shouldThrowExceptionWhenUserHasNoRoles`**: Comprueba que se lance una excepción `IllegalArgumentException` si un usuario no tiene roles asignados.
- **`shouldAddRolePrefixIfMissing`**: Valida que se agregue el prefijo `ROLE_` a los nombres de roles que no lo tienen.
- **`shouldThrowExceptionWhenRoleIsNull`**: Verifica que se lance una excepción `IllegalArgumentException` si los roles de un usuario son `null`.

### Tecnologías Utilizadas
- **Java**: Lenguaje principal del proyecto.
- **Spring Boot**: Framework utilizado para la implementación de los servicios.
- **Mockito**: Biblioteca para la creación de mocks en las pruebas unitarias.
- **JUnit 5**: Framework de pruebas utilizado para ejecutar las pruebas unitarias.

Estas pruebas aseguran que los servicios funcionen correctamente y manejen los casos esperados y excepcionales de manera adecuada.

# integration-test
## Pruebas de Integración - Gestión de Mascotas

### 📌 Contexto General
Este proyecto implementa **pruebas de integración** para verificar el correcto funcionamiento de una **API REST** dedicada a la gestión de mascotas (*pets*).  
Se utiliza un enfoque **BDD** (*Behavior-Driven Development*) con **Cucumber** y **Serenity** para validar la funcionalidad de la API de manera clara y mantenible.

---

### 🛠 Framework y Herramientas
- **Cucumber** → Definición de escenarios de prueba en lenguaje Gherkin.
- **Serenity BDD** → Ejecución de pruebas y generación de reportes detallados.
- **RestAssured** → Realización de solicitudes HTTP y validación de respuestas.
- **Jackson** → Manipulación y serialización/deserialización de datos JSON.
- **Java** → Lenguaje de programación base.

---

### 📋 Escenarios Implementados

#### ✅ Guardar una mascota exitosamente
1. Preparar un JSON con datos completos de una mascota (`id`, `nombre`, `categoría`, etc.).
2. Enviar una solicitud **POST** al endpoint `/pet`.
3. Verificar respuesta con código **200**.
4. Validar que la respuesta contenga todos los campos esperados con valores correctos.

#### ⚠️ Validación de campos obligatorios
1. Escenario parametrizado para probar omisión de campos.
2. Omitir el campo **ID** del JSON de mascota.
3. Verificar fallo con código **500**.
4. Validar que el mensaje de error contenga texto específico.

---

#### ⚙️ Estructura Técnica

- **Step Definitions:** Implementados en `PetDefinitions.java`, encargados de:
  - Preparar datos de prueba en formato JSON.
  - Configurar solicitudes HTTP usando RestAssured.
  - Ejecutar las llamadas a la API.
  - Implementar validaciones para diferentes tipos de datos.
  - Manejar valores especiales con formatos como `$str{}`.



# e2e-test
## 🔍 Aspectos Técnicos

La implementación incluye los siguientes aspectos técnicos:

### 1️⃣ Step Definitions para escenarios de Cucumber
- `VetDefinitions.java` y `ErrorDefinitions.java` definen las implementaciones de pasos para los escenarios **Gherkin** en los archivos `vet.feature` y `error.feature`, respectivamente.
- Estos pasos interactúan con los correspondientes **Page Objects** para realizar acciones y aserciones, garantizando la separación entre la lógica de prueba y las interacciones con la interfaz.

### 2️⃣ Page Object Model (POM)
- `VetPage` y `ErrorPage` encapsulan los **elementos de la interfaz** y las **acciones del usuario** para las páginas *"Vets"* y *"Error"*.
- Esta abstracción mejora la **mantenibilidad**, **legibilidad** y **reutilización** del código, al centralizar los localizadores y métodos de interacción.

### 3️⃣ Aserciones
- Las aserciones validan el comportamiento esperado de la aplicación:
  - En `VetDefinitions.java`, se verifica que la lista de veterinarios se muestre y coincida con los datos esperados en la tabla.
  - En `ErrorDefinitions.java`, se asegura que el mensaje de error se muestre y contenga el texto esperado.
- Estas validaciones garantizan que tanto los aspectos funcionales como visuales cumplan con los requisitos.

### 4️⃣ Archivos Gherkin
- `vet.feature` y `error.feature` definen **escenarios de prueba de alto nivel** en un formato **legible para humanos**, describiendo el comportamiento esperado de la aplicación.

### 5️⃣ Integración con Serenity BDD
- **Serenity BDD** se utiliza para:
  - Gestionar el ciclo de vida de las pruebas.
  - Interactuar con elementos web.
  - Generar reportes detallados con evidencia de ejecución.