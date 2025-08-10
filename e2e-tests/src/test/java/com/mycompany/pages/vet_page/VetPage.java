package com.mycompany.pages.vet_page;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VetPage  extends PageObject {
    @FindBy(css = "a[href='/vets']")
    WebElementFacade vetOptionMenu;
    @FindBy(id = "vetsTable")
    WebElementFacade vetsTable;

    public void clickOnVetMenu() {
        vetOptionMenu.waitUntilVisible().waitUntilClickable().click();
    }
    public Map<String, List<String>> getTableData() {
        WebElementFacade table = waitFor(vetsTable);
        table.shouldBeVisible();

        // Obtener encabezados
        List<String> headers = table.thenFindAll("thead th").stream()
                .map(WebElementFacade::getText)
                .collect(Collectors.toList());

        // Obtener filas
        List<WebElementFacade> rows = table.thenFindAll("tbody tr");

        // Crear el mapa resultado
        Map<String, List<String>> tableData = new LinkedHashMap<>();

        // Inicializar listas para cada encabezado
        for (String header : headers) {
            tableData.put(header, new ArrayList<>());
        }

        // Rellenar datos por columna
        for (WebElementFacade row : rows) {
            List<WebElementFacade> cells = row.thenFindAll("td");
            for (int i = 0; i < headers.size(); i++) {
                tableData.get(headers.get(i)).add(cells.get(i).getText().trim());
            }
        }
        return tableData;
    }
    public List<WebElementFacade> getRows() {
        waitFor(vetsTable).shouldBeVisible();
        return vetsTable.thenFindAll("tbody tr");
    }
}
