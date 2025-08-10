package com.mycompany.steps;

import com.mycompany.pages.vet_page.VetPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static net.serenitybdd.core.Serenity.getDriver;
import static org.assertj.core.api.Assertions.assertThat;


public class VetDefinitions {

    VetPage vetPage;

    @When("The client click on the vets menu")
    public void the_client_click_on_the_vets_menu() {
        vetPage.open();
        // Ensure the page is full size
        getDriver().manage().window().maximize();
        vetPage.clickOnVetMenu();
    }
    @Then("the page should show a list of vets with table id called {string}")
    public void the_page_should_show_a_list_of_vets_with_table_id_called(String string) {
        assertThat(vetPage.getRows().size()).isPositive();

    }
    @Then("the response should contain the following vets:")
    public void the_response_should_contain_the_following_vets(io.cucumber.datatable.DataTable dataTable) {
        var tableData = vetPage.getTableData();

        dataTable.asMaps(String.class, String.class).forEach(row -> {
            String vetName = row.get("name");
            String vetSpecialty = row.get("specialties");
            assertThat(tableData.get("Name")).contains(vetName);
            assertThat(tableData.get("Specialties")).contains(vetSpecialty);
        });
    }


}
