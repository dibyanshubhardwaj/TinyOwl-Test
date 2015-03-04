package movies;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Movies {




    public static List<String> parse_string(String value) {
        String[] sub = value.split("\\(", 2);
        String rating = value.substring(value.length() - 3);
        String year = sub[1].split("\\)", 2)[0];
        String name = sub[0].split(" ", 2)[1];
        return Arrays.asList(year, name, rating);
    }

    public static List<WebElement> entryList(WebDriver driver) {
        return driver.findElements(By.xpath("//table//tbody[@class='lister-list']//tr"));

    }


    public static void insert_in_db(List<WebElement> entries) {
        SqliteHandler sql = null;
        System.out.println("Inserting Movies in database");
        try {
            sql = new SqliteHandler(false);
            for (WebElement entry : entries) {
                String text = entry.getText();
                List<String> d = parse_string(text);
                try {
                    sql.insert_values(d);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert sql != null;
        sql.close_connection();
    }

    public static int print_movies(){
        try {
            System.out.println("Getting DB Connection");
            SqliteHandler sql = new SqliteHandler(true);
            sql.print_db_values();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    static void waitForLoad(WebDriver driver) {
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(pageLoadCondition);
    }

        public static void main(String[] args) {
        if (args.length > 0){
            if (args[0].equals("print")){
                print_movies();
            }
        }
        else {
            WebDriver driver = new FirefoxDriver();
            driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
            System.out.println("Launching Browser");
            driver.get("http://www.imdb.com/chart/top");
            waitForLoad(driver);
            insert_in_db(entryList(driver));
            driver.close();
        }

        System.exit(0);
    }



}
