package testmovies;


import movies.Movies;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class TestMovies {
    WebDriver driver;
    Movies movieName;

    //@Test(priority = 0)
    public void setup(){

        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
        System.out.println("Launching Browser");
        driver.get("http://www.imdb.com/chart/top");

    }
    @Test(priority = 1)
    public void count_in_db(){

        List<WebElement> optionCount;
        optionCount = driver.findElements(By.xpath("//table//tbody[@class='lister-list']//tr"));
        System.out.println(optionCount.size());

    }

}
