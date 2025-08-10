package com.mycompany.steps;

import com.mycompany.pages.error.ErrorPage;
import com.mycompany.pages.pettypes.PetTypesPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static net.serenitybdd.core.Serenity.getDriver;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ErrorDefinitions {
    ErrorPage errorPage;
    @When("the client navigates to the error page")
    public void the_client_navigates_to_the_error_page() {
        errorPage.open();
        // Ensure the page is full size
        getDriver().manage().window().maximize();
        errorPage.clickOnErrorMenu();
    }
    @Then("the page should display an error message")
    public void the_page_should_display_an_error_message() {
        String errorMessage = errorPage.getErrorMessage();
        assertFalse(errorMessage.isEmpty(), "Error message should not be empty");
    }
    @Then("the error message should contain {string}")
    public void the_error_message_should_contain(String string) {
        String errorMessage = errorPage.getErrorMessage();
        assertTrue(errorMessage.contains(string), "Error message should contain: " + string);
    }
}
