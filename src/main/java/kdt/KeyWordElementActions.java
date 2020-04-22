package kdt;

import javax.validation.constraints.NotNull;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.TestException;

public class KeyWordElementActions extends Base {
	
	public WebDriver driver;
	public WebDriverWait wait;
	public Actions actions;
	public Select select;

	public KeyWordElementActions(WebDriver driver){
		this.driver = driver;
	}
	
	public By getId(@NotNull String Selector) {
		return By.id(Selector);
	}


	public By getLinkText(@NotNull String Selector) {
		return By.linkText(Selector);
	}
	
	public By getClassName(@NotNull String Selector) {
		return By.className(Selector);
	}

	public void launchUrl(@NotNull String URL) {
		try {
			driver.get(URL);
		} catch (Exception e) {
			throw new TestException("URL did not load");
		}
	}

	public void navigateToURL(@NotNull String URL) {
		try {
			driver.navigate().to(URL);
		} catch (Exception e) {
			throw new TestException("URL did not load");
		}
	}

	public String getPageTitle() {
		try {
			return driver.getTitle();
		} catch (Exception e) {
			throw new TestException(String.format("Current page title is: %s", driver.getTitle()));
		}
	}

	public WebElement getElement(@NotNull By selector) {
		try {
			 WebElement ele = driver.findElement(selector);
			 return ele;
		} catch (Exception e) {
			throw new TestException(String.format("Element not found"));
		}
	}

	public String getElementText(@NotNull By selector) {
		waitForElementToBeVisible(selector);
		try {
			return getElement(selector).getText().trim();
		} catch (Exception e) {
			throw new TestException(String.format("Element not found"));
		}
	}

	public void sendKeys(@NotNull By selector, @NotNull String value) {
		WebElement element = getElement(selector);
		clearField(element);
		try {
			element.sendKeys(value);
			waitFor2Sec(selector);
		} catch (Exception e) {
			throw new TestException(
					String.format("Error in sending [%s] to the following element: [%s]", value, selector.toString()));
		}
	}


	public void clearField(@NotNull WebElement element) {
		try {
			element.clear();
		} catch (Exception e) {
		}
	}


	public void clearField(@NotNull By selector) {
		try {
			getElement(selector).clear();
		} catch (Exception e) {
		}
	}

	public void click(@NotNull By selector) {
		WebElement element = getElement(selector);
		waitForElementToBeClickable(selector);
		try {
			if (element != null) {
				element.click();
				waitFor2Sec(selector);
			}
		} catch (Exception e) {
			throw new TestException(String.format("The following element is not clickable: [%s]", selector));
		}
	}
	
	public void waitForElementToBeClickable(@NotNull By selector) {
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(selector));
		} catch (Exception e) {
			throw new TestException("The following element is not clickable: " + selector);
		}
	}
	
	public void waitForElementToBeVisible(@NotNull By selector) {
		try {
			wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(selector));
		} catch (Exception e) {
		}
	}
	
	public void waitFor2Sec(@NotNull By selector) {
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}
	}
	
	public void quitBrowser(){
		try{
			driver.quit();
		}catch(Exception e){
			
		}
		
	}

	
}
