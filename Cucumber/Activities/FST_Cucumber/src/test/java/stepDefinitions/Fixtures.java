package stepDefinitions;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;

/**
 * NOTE: these @BeforeAll / @AfterAll annotations are Cucumber's own
 * (io.cucumber.java.BeforeAll / AfterAll) -- NOT JUnit 5's
 * (org.junit.jupiter.api.BeforeAll). Cucumber added its own global hooks
 * in v6+, which run once before/after the ENTIRE suite rather than once
 * per test class like JUnit's version. The methods must be static, which
 * is why BaseClass's driver/wait fields are also static.
 */
public class Fixtures extends BaseClass {

    @BeforeAll
    public static void setUp() {
        // Initialize Firefox Driver
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterAll
    public static void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}
