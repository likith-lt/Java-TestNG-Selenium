package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNGTest3 {

    private RemoteWebDriver driver;
    private String Status = "failed";

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = System.getenv("LT_USERNAME") == null ? "Your LT Username" : System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESS_KEY") == null ? "Your LT AccessKey" : System.getenv("LT_ACCESS_KEY");
        ;
        String hub = "@hub.lambdatest.com/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("browserVersion", "15.0");
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("build", "TestNG With Java");
        ltOptions.put("name", "TestNG Sample Test 3");
        ltOptions.put("platformName", "MacOS Monterey");
        ltOptions.put("selenium_version","4.0.0");
        ltOptions.put("plugin", "git-testng");
        capabilities.setCapability("LT:Options", ltOptions);

        String[] Tags = new String[] { "Feature", "Falcon", "Severe" };
        ltOptions.put("tags", Tags);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), capabilities);
    }

    @Test
    public void basicTest() throws InterruptedException {
        System.out.println("Loading Url");
        driver.get("https://stage-demo.lambdatest.com/");

            // Let's select the location
            driver.findElement(By.id("headlessui-listbox-button-1")).click();
            driver.findElement(By.id("Bali")).click();
            System.out.println("Location is selected as Bali.");
            // Let's select the number of guests
            driver.findElement(By.id("headlessui-listbox-button-5")).click();
            driver.findElement(By.id("2")).click();
            System.out.println("Number of guests are selected.");
            driver.findElement(By.xpath("//*[@id='search']")).click();
            Thread.sleep(3000);
            // Let's select one of the hotels for booking
            driver.findElement(By.id("reserve-now")).click();
            Thread.sleep(3000);
            driver.findElement(By.id("proceed")).click();
            Thread.sleep(3000);
            System.out.println("Booking is confirmed.");
            // Let's download the invoice
            boolean exec = driver.findElement(By.id("invoice")).isDisplayed();
            if(exec){
                Status = "passed";
                driver.findElement(By.id("invoice")).click();
                System.out.println("Tests are run successfully!");
            }
            else
                Status="failed";

    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }

}