package testmovies;


import movies.Movies;
import movies.SqliteHandler;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class TestMovies {
    WebDriver driver;
    Movies movieName;


    @Test
    public List<WebElement> setup(){
    //find the element

            return driver.findElements(By.xpath("//table//tbody[@class='lister-list']//tr"));

    }
      @BeforeMethod
    public void beforeMethod(){
        //initialize the firebox browser
        driver = new FirefoxDriver();
        //declare the implicit wait time
        driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
        //launching the browser
        System.out.println("Launching Browser");
        driver.get("http://www.imdb.com/chart/top");

    }
    @AfterMethod
    public void afterMethod(){
        driver.quit();
    }
}
