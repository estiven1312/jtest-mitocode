package com.mycompany.steps;

import com.mycompany.pages.owners.OwnerPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OwnersDefinitions {

    OwnerPage ownerPage;

    @Given("el cliente navega al menú propietarios")
    public void elClienteNavegaAlMenuPropietarios() {
        ownerPage.open();
        ownerPage.clickOnOwnerMenu();
    }

    @When("el cliente selecciona la opción listar de propietarios")
    public void el_cliente_selecciona_la_opcion_listar_de_propietarios() {
        ownerPage.clickOnOwnerSearchOptionMenu();
    }

    @Then("la página debe mostrar una lista de propietarios")
    public void laPaginaDebeMostrarUnaListaDePropietarios() {
        ownerPage.scrollToBottom();
//        int rows = ownerPage.getOwnersTableRowsCount();
//        assertEquals(10, rows);
        assertThat(ownerPage.getOwnersTableRowsCount()).isPositive();
    }

    @Given("el cliente tiene los siguientes datos del propietario:")
    public void elClienteTieneLosSiguientesDatosDelPropietario(DataTable dataTable) {
        dataTable.asMaps(String.class, String.class)
                .forEach(row -> row.forEach((header, cell) -> Serenity.setSessionVariable(header).to(cell)));
    }

    @When("el cliente selecciona la opción agregar nuevo")
    public void elClienteSeleccionaLaOpcionAgregarNuevo() {
        ownerPage.clickAddOwnerButtonOption();
    }

    @And("el cliente ingresa los datos del propietario")
    public void elClienteIngresaLosDatosDelPropietario() {
        ownerPage.enterOwnerData();
    }

    @And("el cliente guarda el propietario")
    public void elClienteGuardaElPropietario() {
        ownerPage.clickAddOwnerButton();
    }

    @Then("la página debe mostrar la información del propietario registrado")
    public void laPaginaDebeMostrarLaInformacionDelPropietarioRegistrado() {
        ownerPage.scrollToBottom();
        String firstName = Serenity.sessionVariableCalled("firstName");
        String lastName = Serenity.sessionVariableCalled("lastName");
        String fullName = firstName + " " + lastName;
        assertEquals(fullName, ownerPage.getFullName());
        assertThat(ownerPage.getFullName()).isEqualTo(fullName);
    }
}
