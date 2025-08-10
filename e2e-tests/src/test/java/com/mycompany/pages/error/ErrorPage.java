package com.mycompany.pages.error;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

public class ErrorPage extends PageObject {
    @FindBy(css = "a[href='/oups']")
    WebElementFacade errorMenu;
    @FindBy(css = "h2")
    WebElementFacade errorMessage;

    public void clickOnErrorMenu() {
        errorMenu.waitUntilVisible().waitUntilClickable().click();
    }

    public String getErrorMessage() {
        errorMessage.waitUntilVisible();
        return errorMessage.getText().trim();
    }
}
