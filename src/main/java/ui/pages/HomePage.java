package ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    @FindBy(className = "global-header__utility")
    private WebElement utilityHeader;

    public HomePage(WebDriver driver) {
        super(driver);
    }
}