package hrm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * OrangeHRM (classic OrangeHRM 3.x UI, matches your screenshots)
 * URL: http://alchemy.hguy.co/orangehrm
 * Username: orange | Password: orangepassword123
 *
 * NOTE: OrangeHRM's classic UI (the "LOGIN Panel" screenshots you shared)
 * uses fairly stable name attributes (txtUsername/txtPassword/btnLogin).
 * The rest are best-effort selectors — verify with Inspect if needed
 * (flagged "// VERIFY").
 */
public class HrmTest {

    WebDriver driver;
    private static final String BASE_URL = "http://alchemy.hguy.co/orangehrm";
    private static final String USERNAME = "orange";
    private static final String PASSWORD = "orangepassword123";

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
        driver.findElement(By.name("txtUsername")).sendKeys(USERNAME);
        driver.findElement(By.name("txtPassword")).sendKeys(PASSWORD);
        driver.findElement(By.id("btnLogin")).click();
    }

    // 1. Verify the website title
    @Test
    public void verifyWebsiteTitle() {
        driver.get(BASE_URL);
        String actualTitle = driver.getTitle();
        System.out.println("Title found: " + actualTitle);
        Assert.assertEquals(actualTitle, "OrangeHRM");
    }

    // 2. Get the url of the header image
    @Test
    public void getHeaderImageUrl() {
        driver.get(BASE_URL);
        // VERIFY: the OrangeHRM logo image on the login page
        WebElement headerImage = driver.findElement(By.cssSelector("img[alt='OrangeHRM'], .orangehrm-login-branding img, #logo img"));
        String imgUrl = headerImage.getAttribute("src");
        System.out.println("Header image URL: " + imgUrl);
    }

    // 3. Logging into the site
    @Test
    public void loginToSite() {
        login();
        boolean homepageOpened = driver.getPageSource().contains("Dashboard");
        System.out.println("Homepage opened: " + homepageOpened);
        Assert.assertTrue(homepageOpened);
    }

    // 4. Add a new employee
    @Test
    public void addNewEmployee() {
        login();
        driver.findElement(By.linkText("PIM")).click();
        driver.findElement(By.xpath("//input[@value='Add'] | //button[normalize-space()='Add']")).click();

        driver.findElement(By.name("firstName")).sendKeys("John");
        driver.findElement(By.name("lastName")).sendKeys("Doe");
        driver.findElement(By.xpath("//input[@value='Save'] | //button[normalize-space()='Save']")).click();

        driver.findElement(By.linkText("Employee List")).click();
        driver.findElement(By.id("employee_name_empName")).sendKeys("John Doe");
        driver.findElement(By.xpath("//input[@value='Search'] | //button[normalize-space()='Search']")).click();

        boolean employeeFound = driver.getPageSource().contains("John Doe");
        System.out.println("New employee found in list: " + employeeFound);
        Assert.assertTrue(employeeFound);
    }

    // 5. Edit user information
    @Test
    public void editUserInformation() {
        login();
        driver.findElement(By.linkText("My Info")).click();
        driver.findElement(By.xpath("//input[@value='Edit'] | //button[normalize-space()='Edit']")).click();

        WebElement firstName = driver.findElement(By.name("firstName"));
        firstName.clear();
        firstName.sendKeys("John");

        WebElement lastName = driver.findElement(By.name("lastName"));
        lastName.clear();
        lastName.sendKeys("Doe");

        // VERIFY: gender radio button name/value
        driver.findElement(By.xpath("//input[@type='radio' and @value='1']")).click(); // Male

        // VERIFY: Nationality dropdown name
        Select nationality = new Select(driver.findElement(By.name("nationality")));
        nationality.selectByVisibleText("Indian");

        driver.findElement(By.xpath("//input[@value='Save'] | //button[normalize-space()='Save']")).click();

        boolean saved = driver.getPageSource().contains("Doe");
        System.out.println("User info saved: " + saved);
        Assert.assertTrue(saved);
    }

    // 6. Verify that the "Directory" menu item is visible and clickable
    @Test
    public void verifyDirectoryMenuItem() {
        login();
        WebElement directoryLink = driver.findElement(By.linkText("Directory"));
        boolean isDisplayed = directoryLink.isDisplayed();
        boolean isEnabled = directoryLink.isEnabled();
        System.out.println("Directory link visible: " + isDisplayed + ", clickable: " + isEnabled);
        Assert.assertTrue(isDisplayed && isEnabled);

        directoryLink.click();
        String heading = driver.findElement(By.tagName("h1")).getText();
        System.out.println("Heading after navigating: " + heading);
        Assert.assertEquals(heading, "Search Directory");
    }

    // 7. Adding qualifications (Work Experience)
    @Test
    public void addQualifications() {
        login();
        driver.findElement(By.linkText("My Info")).click();
        driver.findElement(By.linkText("Qualifications")).click();

        // VERIFY: "Add" button inside the Work Experience section
        driver.findElement(By.xpath("(//input[@value='Add'] | //button[normalize-space()='Add'])[1]")).click();

        driver.findElement(By.name("company")).sendKeys("Acme Corp");
        driver.findElement(By.name("jobTitle")).sendKeys("Software Tester");

        driver.findElement(By.xpath("//input[@value='Save'] | //button[normalize-space()='Save']")).click();

        boolean saved = driver.getPageSource().contains("Acme Corp");
        System.out.println("Work experience saved: " + saved);
        Assert.assertTrue(saved);
    }

    // 8. Applying for a leave
    @Test
    public void applyForLeave() {
        login();
        driver.findElement(By.linkText("Apply Leave")).click();

        // VERIFY: Leave Type dropdown name
        Select leaveType = new Select(driver.findElement(By.name("leaveType")));
        leaveType.selectByIndex(1);

        driver.findElement(By.name("fromDate")).sendKeys("2026-12-20");
        driver.findElement(By.name("toDate")).sendKeys("2026-12-21");

        driver.findElement(By.xpath("//input[@value='Apply'] | //button[normalize-space()='Apply']")).click();

        driver.findElement(By.linkText("My Leave")).click();
        boolean leaveListed = driver.getPageSource().contains("Pending Approval");
        System.out.println("Leave application visible in My Leave: " + leaveListed);
        Assert.assertTrue(leaveListed);
    }

    // 9. Retrieve emergency contacts
    @Test
    public void retrieveEmergencyContacts() {
        login();
        driver.findElement(By.linkText("My Info")).click();
        driver.findElement(By.linkText("Emergency Contacts")).click();

        // VERIFY: emergency contacts table rows
        List<WebElement> rows = driver.findElements(By.cssSelector("table tr"));
        for (WebElement row : rows) {
            System.out.println(row.getText());
        }
    }
}
