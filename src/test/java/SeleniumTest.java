import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumTest {
    @Test
    public static void main(String[] args) {
        WebDriver driver = new FirefoxDriver();
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("test@mail.cl");
        WebElement password = driver.findElement(By.id("passwd"));
        password.sendKeys("");
    }
}
