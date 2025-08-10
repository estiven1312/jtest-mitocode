package com.mycompany.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;

import java.util.Map;
import static net.serenitybdd.rest.RestRequests.given;
import static net.serenitybdd.rest.SerenityRest.then;
import static net.serenitybdd.rest.SerenityRest.when;
import static org.hamcrest.Matchers.*;

public class PetDefinitions {
    @Given("the client prepare the data")
    public void theClientPrepareTheData(String body) {
        given()
                .contentType(ContentType.JSON)
                .body(body);
    }

    @When("the client saves the new pet to the endpoint {string}")
    public void theClientSavesTheNewPet(String endpoint) {
            when()
                .post(endpoint)
                .andReturn();
    }

    @Then("the server should respond with a status {string}")
    public void theServerShouldRespondWithAStatus(String statusCode) {
        then().statusCode(Integer.parseInt(statusCode));
    }

    @Then("the response body should contain with the following properties and values")
    public void theResponseBodyShouldContainWithPropertiesAndValues(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        data.forEach((campo, valor) -> {
            then().body(campo, is(parseValue(valor)));
        });
    }

    @Then("the response body should contain partial values")
    public void theResponseBodyShouldContainPartialValues(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        data.forEach((campo, valor) -> {
            if (isNumeric(valor)) {
                then().body(campo, equalTo(Integer.parseInt(valor)));
            } else {
                then().body(campo, containsStringIgnoringCase(valor.toLowerCase()));
            }
        });
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    @Given("client omit the field {string} in the following JSON:")
    public void clientOmitTheFieldInTheFollowingJSON(String campo, String docString) throws JsonProcessingException {
        Map<String, Object> jsonMap = new ObjectMapper().readValue(docString, new TypeReference<>() {
        });
        jsonMap.remove(campo);
        given()
                .contentType(ContentType.JSON)
                .body(jsonMap);
    }

    @When("the client saves the wrong body to the endpoint {string}")
    public void theClientSavesTheWrongBodyToTheEndpoint(String endpoint) {
            when()
                .post(endpoint)
            .andReturn();
    }

    private Object parseValue(String valor) {
        if (valor.startsWith("$str{") && valor.endsWith("}")) {
            return valor.substring(5, valor.length() - 1);
        } else if (valor.matches("-?\\d+")) {
            return Integer.parseInt(valor);
        } else if (valor.matches("-?\\d+\\.\\d+")) {
            return Double.parseDouble(valor);
        } else {
            return valor;
        }
    }
}
