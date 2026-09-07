package activities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Activity3 {

    WebDriver driver;

    @BeforeClass
    public void setUp() {
        // Initialize Firefox driver
        driver = new FirefoxDriver();
        // Open the login form page
        driver.get("https://training-support.net/webelements/login-form/");
    }

    @Test
    public void loginTest() {
        // Find the username and password fields
        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));

        // Enter credentials
        username.sendKeys("admin");
        password.sendKeys("password");

        // Click the "Log in" button
        driver.findElement(By.xpath("//button[text()='Log in']")).click();

        // Read the confirmation message
        String confirmationMessage = driver.findElement(By.tagName("h2")).getText();

        // Assert the correct message is displayed
        Assert.assertEquals(confirmationMessage, "Welcome Back!");
    }

    @AfterClass
    public void tearDown() {
        // Close the browser
        driver.close();
    }
}