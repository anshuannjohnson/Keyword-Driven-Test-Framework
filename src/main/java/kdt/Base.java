package kdt;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Base {
	
	public WebDriver driver;
	
	public WebDriver init_driver(String browserName){
		
		if(browserName.equals("chrome")){
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\anshu\\Downloads\\Sw-Engr\\Project\\selenium\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		
		if(browserName.equals("firefox")){
			System.setProperty("webdriver.gecko.driver", "C:\\Users\\anshu\\Downloads\\Sw-Engr\\Project\\selenium\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		
		return driver;
		
	}

}
