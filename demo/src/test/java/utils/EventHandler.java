package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.events.WebDriverListener;

public class EventHandler implements WebDriverListener {

	private static void logger(String logtxt) {
		if(logtxt != null) {
			try {
				File file = new File("log.log");
				BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
				writer.write(logtxt);
				writer.write("\n");
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void beforeClick(WebElement element) {
		WebDriverListener.super.beforeClick(element);
		logger("Clicked "+element.getText());
		logger(element.getAttribute("value"));
	}

	@Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        WebDriverListener.super.afterFindElement(driver, locator, result);
		logger("Found "+result.getText());
    }

	@Override
	public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
		WebDriverListener.super.beforeSendKeys(element, keysToSend);
		logger(keysToSend[0].toString());
	}

	@Override
	public void beforePerform(WebDriver driver, Collection<Sequence> actions) {
		WebDriverListener.super.beforePerform(driver, actions);

        String regex = "xpath: (.*?)(?=]})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(actions.iterator().next().toJson().toString());

        if (matcher.find()) {
            String xpath = matcher.group(1);
			logger("Hovered "+ driver.findElement(By.xpath(xpath)).getText());
        } else {
            System.out.println("XPath not found.");
        }
	}
}