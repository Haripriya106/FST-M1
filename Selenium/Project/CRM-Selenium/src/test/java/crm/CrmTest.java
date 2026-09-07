package crm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * SuiteCRM
 * URL: https://alchemy.hguy.co/crm/
 * Username: admin | Password: pa$$w0rd
 *
 * NOTE: selectors are best-effort based on your screenshots and standard
 * SuiteCRM 7.x markup. Verify with browser Inspect if a locator fails
 * (flagged "// VERIFY").
 */
public class CrmTest {

    WebDriver driver;
    private static final String BASE_URL = "https://alchemy.hguy.co/crm/";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "pa$$w0rd";

    @BeforeMethod
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.close();
        }
    }

    private void login() {
        driver.get(BASE_URL);
        driver.findElement(By.name("user_name")).sendKeys(USERNAME);
        driver.findElement(By.name("username_password")).sendKeys(PASSWORD);
        driver.findElement(By.xpath("//button[normalize-space()='LOG IN'] | //input[@value='LOG IN']")).click();
    }

    // 1. Verify the website title
    @Test
    public void verifyWebsiteTitle() {
        driver.get(BASE_URL);
        String actualTitle = driver.getTitle();
        System.out.println("Title found: " + actualTitle);
        Assert.assertEquals(actualTitle, "SuiteCRM");
    }

    // 2. Get the url of the header image
    @Test
    public void getHeaderImageUrl() {
        driver.get(BASE_URL);
        // VERIFY: the "SUITE CRM" logo image on the login page
        WebElement headerImage = driver.findElement(By.cssSelector(".suitecrm-logo img, #login img"));
        String imgUrl = headerImage.getAttribute("src");
        System.out.println("Header image URL: " + imgUrl);
    }

    // 3. Get the copyright text
    @Test
    public void getCopyrightText() {
        driver.get(BASE_URL);
        // VERIFY: footer copyright links
        List<WebElement> copyrightLinks = driver.findElements(By.cssSelector("#login-footer a, .copyright a"));
        String firstCopyright = copyrightLinks.get(0).getText();
        System.out.println("First copyright text: " + firstCopyright);
    }

    // 4. Logging into the site
    @Test
    public void loginToSite() {
        login();
        boolean homepageOpened = driver.getPageSource().contains("SUITECRM DASHBOARD")
                || driver.getPageSource().contains("Dashboard");
        System.out.println("Homepage opened: " + homepageOpened);
        Assert.assertTrue(homepageOpened);
    }

    // 5. Getting colors (of the navigation menu)
    @Test
    public void getNavigationMenuColor() {
        login();
        // VERIFY: main nav bar container selector
        WebElement navBar = driver.findElement(By.cssSelector("#navbar, .navbar, #module_navigation"));
        String color = navBar.getCssValue("background-color");
        System.out.println("Navigation menu background color: " + color);
    }

    // 6. Menu checking ("Activities" menu item)
    @Test
    public void verifyActivitiesMenuExists() {
        login();
        WebElement activitiesMenu = driver.findElement(By.linkText("Activities"));
        boolean exists = activitiesMenu.isDisplayed();
        System.out.println("Activities menu item exists and visible: " + exists);
        Assert.assertTrue(exists);
    }

    // 7. Reading additional information (popup on Leads list)
    @Test
    public void readAdditionalInformationPopup() {
        login();
        driver.findElement(By.linkText("Sales")).click();
        driver.findElement(By.linkText("Leads")).click();

        // VERIFY: the small "i" additional-info icon at the end of each row
        driver.findElement(By.cssSelector("table tbody tr:first-child .list-view-additional-info-icon, table tbody tr:first-child i")).click();

        // VERIFY: mobile number field inside the popup
        WebElement popup = driver.findElement(By.cssSelector(".additionalDetails, .popupBody"));
        String popupText = popup.getText();
        System.out.println("Additional details popup text: " + popupText);
    }

    // 8. Traversing tables (Accounts - odd-numbered rows)
    @Test
    public void traverseAccountsTable() {
        login();
        driver.findElement(By.linkText("Sales")).click();
        driver.findElement(By.linkText("Accounts")).click();

        // VERIFY: table row selector for the Accounts list view
        List<WebElement> nameCells = driver.findElements(By.cssSelector("table tbody tr td:nth-child(2)"));

        System.out.println("First 5 odd-numbered row names:");
        int printed = 0;
        for (int i = 0; i < nameCells.size() && printed < 5; i += 2) {
            System.out.println(nameCells.get(i).getText());
            printed++;
        }
    }

    // 9. Traversing tables 2 (Leads - Name & User columns)
    @Test
    public void traverseLeadsTable() {
        login();
        driver.findElement(By.linkText("Sales")).click();
        driver.findElement(By.linkText("Leads")).click();

        // VERIFY: column indices for Name and User in the Leads list view
        List<WebElement> nameCells = driver.findElements(By.cssSelector("table tbody tr td:nth-child(2)"));
        List<WebElement> userCells = driver.findElements(By.cssSelector("table tbody tr td:nth-child(6)"));

        System.out.println("First 10 Name / User values:");
        for (int i = 0; i < 10 && i < nameCells.size() && i < userCells.size(); i++) {
            System.out.println(nameCells.get(i).getText() + " | " + userCells.get(i).getText());
        }
    }
}
