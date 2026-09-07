package lms;

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
 * Alchemy LMS (WordPress + LearnDash)
 * URL: https://alchemy.hguy.co/lms
 * Username: root | Password: pa$$w0rd
 *
 * NOTE: selectors are best-effort based on your screenshots and typical
 * LearnDash markup. Verify with browser Inspect if a locator fails
 * (flagged with "// VERIFY").
 */
public class LmsTest {

    WebDriver driver;
    private static final String BASE_URL = "https://alchemy.hguy.co/lms";
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
        Assert.assertEquals(actualTitle, "Alchemy LMS – An LMS Application");
    }

    // 2. Verify the website heading
    @Test
    public void verifyWebsiteHeading() {
        driver.get(BASE_URL);
        String heading = driver.findElement(By.tagName("h1")).getText();
        System.out.println("Heading found: " + heading);
        Assert.assertEquals(heading, "Learn from Industry Experts");
    }

    // 3. Verify the title of the first info box ("Actionable Training")
    @Test
    public void verifyFirstInfoBoxTitle() {
        driver.get(BASE_URL);
        // VERIFY: the three info boxes below the hero banner
        List<WebElement> infoBoxTitles = driver.findElements(By.cssSelector(".info-box h3, .feature-box h3"));
        String firstBoxTitle = infoBoxTitles.get(0).getText();
        System.out.println("First info box title: " + firstBoxTitle);
        Assert.assertEquals(firstBoxTitle, "Actionable Training");
    }

    // 4. Verify the title of the second most popular course ("Email Marketing Strategies")
    @Test
    public void verifySecondPopularCourseTitle() {
        driver.get(BASE_URL);
        // VERIFY: course cards under "Our Most Popular Courses"
        List<WebElement> courseTitles = driver.findElements(By.cssSelector(".course-card h3, .ld-course-list h3"));
        String secondCourseTitle = courseTitles.get(1).getText();
        System.out.println("Second popular course title: " + secondCourseTitle);
        Assert.assertEquals(secondCourseTitle, "Email Marketing Strategies");
    }

    // 5. Navigate to another page ("My Account")
    @Test
    public void navigateToMyAccountPage() {
        driver.get(BASE_URL);
        driver.findElement(By.linkText("My Account")).click();
        String title = driver.getTitle();
        System.out.println("Navigated page title: " + title);
        Assert.assertTrue(title.contains("My Account"));
    }

    // 6. Logging into the site
    @Test
    public void loginToSite() {
        driver.get(BASE_URL);
        driver.findElement(By.linkText("My Account")).click();

        // VERIFY: "Login" tab/button on the My Account page
        driver.findElement(By.linkText("Login")).click();

        // WooCommerce/WordPress default login field ids
        driver.findElement(By.id("username")).sendKeys(USERNAME);
        driver.findElement(By.id("password")).sendKeys(PASSWORD);
        driver.findElement(By.xpath("//button[normalize-space()='Log in'] | //input[@value='Log in']")).click();

        boolean loggedIn = driver.getPageSource().contains("Logout") || driver.getPageSource().contains("Log out");
        System.out.println("Logged in: " + loggedIn);
        Assert.assertTrue(loggedIn);
    }

    // 7. Count the number of courses
    @Test
    public void countNumberOfCourses() {
        driver.get(BASE_URL);
        driver.findElement(By.linkText("All Courses")).click();

        // VERIFY: course card container class from LearnDash course grid
        List<WebElement> courses = driver.findElements(By.cssSelector(".course-card, .ld-course-list-item"));
        System.out.println("Number of courses found: " + courses.size());
    }

    // 8. Contact the admin
    @Test
    public void contactAdmin() {
        driver.get(BASE_URL);
        driver.findElement(By.linkText("Contact")).click();

        driver.findElement(By.name("your-name")).sendKeys("John Abraham");
        driver.findElement(By.name("your-email")).sendKeys("john.abraham@example.com");
        driver.findElement(By.name("your-subject")).sendKeys("Test Inquiry");
        driver.findElement(By.name("your-message")).sendKeys("This is a test message sent via Selenium.");

        driver.findElement(By.xpath("//input[@value='Send Message'] | //button[normalize-space()='Send Message']")).click();

        // Contact Form 7 shows a status <div> after submission
        WebElement responseMessage = driver.findElement(By.cssSelector(".wpcf7-response-output"));
        String message = responseMessage.getText();
        System.out.println("Submission response: " + message);
    }

    // 9. Complete a simple lesson
    @Test
    public void completeSimpleLesson() {
        driver.get(BASE_URL);
        driver.findElement(By.linkText("All Courses")).click();

        // Open the first course listed
        driver.findElement(By.cssSelector(".course-card a, .ld-course-list-item a")).click();

        // Expand the first topic/lesson and open it
        driver.findElement(By.linkText("Developing Strategy")).click();

        String courseTitle = driver.findElement(By.tagName("h1")).getText();
        System.out.println("Lesson/course title: " + courseTitle);

        // Click "Mark Complete" if the button is present
        List<WebElement> markCompleteButtons = driver.findElements(By.xpath("//button[contains(., 'Mark Complete')]"));
        if (!markCompleteButtons.isEmpty()) {
            markCompleteButtons.get(0).click();
            System.out.println("Lesson marked complete.");
        } else {
            System.out.println("Mark Complete button not found on this page.");
        }
    }
}
