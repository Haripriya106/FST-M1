package Example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class HelloSelenium {
public static void main(String[] args) {
	//Declare the driver instance
	WebDriver driver = new FirefoxDriver();
	//Go to the webpage
	driver.get("https://training-support.net");
	//Do things on the webpage
	System.out.println("Page title is: " + driver.getTitle());
	//close the browser
	driver.quit();
}
}
