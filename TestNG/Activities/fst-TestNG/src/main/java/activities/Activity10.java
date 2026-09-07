package activities;



import static org.testng.Assert.assertEquals;
import java.io.FileReader;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.opencsv.CSVReader;

public class Activity10 {
	// Declare WebDriver
	WebDriver driver;
	WebDriverWait wait;

	@BeforeClass
	public void setUp() {
		// Initialize the driver object
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Open the page
		driver.get("https://training-support.net/webelements/simple-form");
		// Implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}

	@DataProvider(name = "csvDataProvider")
	public static Object[][] readCsv() throws Exception {
		// Open a CSV file with the CSVReader
		CSVReader reader = new CSVReader(new FileReader("src/test/resources/input.csv"));
		// Skip the first line (column headings)
		reader.skip(1);
		// Read all the lines from the CSV file
		List<String[]> allRows = reader.readAll();

		// Create the output dataset
		Object[][] data = new Object[allRows.size()][];
		for (int i = 0; i < allRows.size(); i++) {
			data[i] = allRows.get(i);
		}
		reader.close();

		// Send the data to the test function
		return data;
	}

	@Test(dataProvider = "csvDataProvider")
	public void testForm(String[] rows) {
		// Find the input fields and enter text
		WebElement fullName = driver.findElement(By.id("full-name"));
		fullName.sendKeys(rows[0]);
		// Enter the email
		driver.findElement(By.id("email")).sendKeys(rows[1]);
		// Enter the Date of the event
		driver.findElement(By.name("event-date")).sendKeys(rows[2]);
		// Enter additional details
		driver.findElement(By.id("additional-details")).sendKeys(rows[3]);

		// Click Submit
		driver.findElement(By.xpath("//button[text()='Submit']")).click();

		// Confirm booking
		String message = driver.findElement(By.id("action-confirmation")).getText();
		assertEquals(message, "Your event has been scheduled!");

		// Refresh the page
		driver.navigate().refresh();
	}

	@AfterClass
	public void tearDown() {
		// Close the browser
		driver.quit();
	}

}