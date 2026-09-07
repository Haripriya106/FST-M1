package jobboard;

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
 * Alchemy Jobs (WordPress + Job Board plugin)
 * URL: https://alchemy.hguy.co/jobs/
 * Username: root | Password: pa$$w0rd
 *
 * NOTE: I could not browse the live site directly (robots.txt blocks
 * automated fetching), so the CSS/XPath selectors below are my best-effort
 * guesses based on the screenshots you shared and common WP Job Manager
 * conventions. Run each test, and if a NoSuchElementException is thrown,
 * right-click the element in your browser -> Inspect, and swap in the
 * real id/class/name. I've flagged the riskiest guesses with "// VERIFY".
 */
public class JobBoardTest {

    WebDriver driver;
    private static final String BASE_URL = "https://alchemy.hguy.co/jobs/";
    private static final String ADMIN_URL = "https://alchemy.hguy.co/jobs/wp-admin";
    private static final String USERNAME = "root";
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

    // 1. Verify the website title
    @Test
    public void verifyWebsiteTitle() {
        driver.get(BASE_URL);
        String actualTitle = driver.getTitle();
        System.out.println("Title found: " + actualTitle);
        Assert.assertEquals(actualTitle, "Alchemy Jobs – Job Board Application");
    }

    // 2. Verify the website heading
    @Test
    public void verifyWebsiteHeading() {
        driver.get(BASE_URL);
        // VERIFY: main heading is assumed to be an <h1>
        String heading = driver.findElement(By.tagName("h1")).getText();
        System.out.println("Heading found: " + heading);
        Assert.assertEquals(heading, "Welcome to Alchemy Jobs");
    }

    // 3. Get the url of the header image
    @Test
    public void getHeaderImageUrl() {
        driver.get(BASE_URL);
        // VERIFY: header image assumed to be the first <img> inside <header>
        WebElement headerImage = driver.findElement(By.cssSelector("header img"));
        String imgUrl = headerImage.getAttribute("src");
        System.out.println("Header image URL: " + imgUrl);
    }

    // 4. Verify the website's second heading
    @Test
    public void verifySecondHeading() {
        driver.get(BASE_URL);
        // VERIFY: grabs every <h2> on the page and reads the second one
        List<WebElement> headings = driver.findElements(By.tagName("h2"));
        String secondHeading = headings.get(1).getText();
        System.out.println("Second heading found: " + secondHeading);
        Assert.assertEquals(secondHeading, "Quia quis non");
    }

    // 5. Navigate to another page ("Jobs")
    @Test
    public void navigateToJobsPage() {
        driver.get(BASE_URL);
        driver.findElement(By.linkText("Jobs")).click();
        String title = driver.getTitle();
        System.out.println("Navigated page title: " + title);
        Assert.assertTrue(title.toLowerCase().contains("jobs"));
    }

    // 6. Apply to a job
    @Test
    public void applyToJob() {
        driver.get(BASE_URL);
        driver.findElement(By.linkText("Jobs")).click();

        // VERIFY: WP Job Manager typically names these fields search_keywords / search_location
        WebElement keywordField = driver.findElement(By.id("search_keywords"));
        keywordField.sendKeys("Banking");
        driver.findElement(By.cssSelector("input[type='submit'], button[type='submit']")).click();

        // Click the first job listing result
        driver.findElement(By.cssSelector("ul.job_listings li.job_listing a")).click();

        // Click "Apply for job" and read the email shown
        driver.findElement(By.linkText("Apply for job")).click();
        WebElement emailElement = driver.findElement(By.cssSelector("a[href^='mailto:']"));
        String email = emailElement.getText();
        System.out.println("Application email: " + email);
    }

    // 7. Create a new job listing (frontend "Post a Job" form)
    @Test
    public void createJobListingFrontend() {
        driver.get(BASE_URL);
        driver.findElement(By.linkText("Post a Job")).click();

        // Fill in only the required "Company name" field, then Preview -> Submit
        // VERIFY: field id/name for company name
        driver.findElement(By.id("company_name")).sendKeys("Example");

        driver.findElement(By.xpath("//button[normalize-space()='Preview'] | //input[@value='Preview']")).click();
        driver.findElement(By.xpath("//button[normalize-space()='Submit Listing'] | //input[@value='Submit Listing']")).click();

        // Verify the listing was posted by revisiting Jobs
        driver.findElement(By.linkText("Jobs")).click();
        boolean listingFound = driver.getPageSource().contains("Test Job");
        System.out.println("New listing visible on Jobs page: " + listingFound);
        Assert.assertTrue(listingFound);
    }

    // 8. Login into the website's backend
    @Test
    public void loginToBackend() {
        driver.get(ADMIN_URL);
        driver.findElement(By.id("user_login")).sendKeys(USERNAME);
        driver.findElement(By.id("user_pass")).sendKeys(PASSWORD);
        driver.findElement(By.id("wp-submit")).click();

        boolean loggedIn = driver.getPageSource().contains("Dashboard");
        System.out.println("Logged in successfully: " + loggedIn);
        Assert.assertTrue(loggedIn);
    }

    // 9. Create a job listing using the backend
    @Test
    public void createJobListingBackend() {
        driver.get(ADMIN_URL);
        driver.findElement(By.id("user_login")).sendKeys(USERNAME);
        driver.findElement(By.id("user_pass")).sendKeys(PASSWORD);
        driver.findElement(By.id("wp-submit")).click();

        driver.findElement(By.linkText("Job Listings")).click();
        driver.findElement(By.linkText("Add New")).click();

        // Gutenberg / classic title field
        // VERIFY: id may be "title" (classic editor) or aria-label based (block editor)
        WebElement titleField = driver.findElement(By.id("title"));
        titleField.sendKeys("Data Analyst");

        driver.findElement(By.xpath("//button[normalize-space()='Publish']")).click();
        // Confirm publish in the popover that appears
        driver.findElement(By.xpath("//button[normalize-space()='Publish']")).click();

        boolean published = driver.getPageSource().contains("Post published");
        System.out.println("Job listing published: " + published);
        Assert.assertTrue(published);
    }
}
