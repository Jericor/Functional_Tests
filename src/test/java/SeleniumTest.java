import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class SeleniumTest {

    /*
    private static WebDriver driver;

    @Before
    public void setupDriver() {
        driver = new FirefoxDriver();
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
*/
    // Testeo de ingreso exitoso al sistema
    @Test
    public void signIn() {
        WebDriver driver = new FirefoxDriver();
        try{
            driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
            WebElement email = driver.findElement(By.id("email"));
            email.sendKeys("test@mail.cl");
            WebElement password = driver.findElement(By.id("passwd"));
            password.sendKeys("fp30t");
            WebElement button = driver.findElement(By.id("SubmitLogin"));
            button.click();
        }
        finally {
            //driver.quit();
        }
    }

    // Testeo de ingreso fallido al sistema
    // Razón: Campos Vacíos
    @Test
    public void blankSignIn() {
        WebDriver driver = new FirefoxDriver();
        try{
            driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
            WebElement button = driver.findElement(By.id("SubmitLogin"));
            button.click();
        }
        finally {
            //driver.quit();
        }
    }

    // Testeo de verificación fallida para creación de cuenta
    // Razón: Correo no válido
    @Test
    public void failedVerification() {
        WebDriver driver = new FirefoxDriver();
        try{
            driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
            WebElement email = driver.findElement(By.id("email_create"));
            email.sendKeys("mail.test.cl");
            WebElement button = driver.findElement(By.id("SubmitCreate"));
            button.click();
        }
        finally {
            //driver.quit();
        }
    }

    // Testeo de verificación exitosa para creación de cuenta
    @Test
    public void verification() {
        WebDriver driver = new FirefoxDriver();
        try{
            driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
            WebElement email = driver.findElement(By.id("email_create"));
            email.sendKeys("mail@test.cl");
            WebElement button = driver.findElement(By.id("SubmitCreate"));
            button.click();
        }
        finally {
            //driver.quit();
        }
    }

    // Testeo de añadir un objeto al carrito de compras
    @Test
    public void addToCart() {
        WebDriver driver = new FirefoxDriver();
        try{
            driver.get("http://automationpractice.com/index.php");
            WebElement searchbox = driver.findElement(By.id("search_query_top"));
            searchbox.sendKeys("Blouse");
            searchbox.sendKeys(Keys.ENTER);
            driver.get("http://automationpractice.com/index.php?id_product=2&controller=product&search_query=blouse&results=1");
            WebElement add = driver.findElement(By.id("add_to_cart"));
            add.click();

        }
        finally {
            //driver.quit();
        }
    }

    // Testeo de eliminar un objeto del carrito de compras
    @Test
    public void deleteFromCart() {
        WebDriver driver = new FirefoxDriver();
        try{
            driver.get("http://automationpractice.com/index.php");
            WebElement searchbox = driver.findElement(By.id("search_query_top"));
            searchbox.sendKeys("Blouse");
            searchbox.sendKeys(Keys.ENTER);
            driver.get("http://automationpractice.com/index.php?id_product=2&controller=product&search_query=blouse&results=1");
            WebElement add = driver.findElement(By.id("add_to_cart"));
            add.click();
            driver.get("http://automationpractice.com/index.php?controller=order");
            WebElement delete = driver.findElement(By.id("2_7_0_598222"));
            delete.click();

        }
        finally {
            //driver.quit();
        }
    }

    // Testeo para cambiar la talla a M 
    @Test
    public void changeSize() {
        WebDriver driver = new FirefoxDriver();
        try{
            driver.get("http://automationpractice.com/index.php");
            WebElement searchbox = driver.findElement(By.id("search_query_top"));
            searchbox.sendKeys("Blouse");
            searchbox.sendKeys(Keys.ENTER);
            driver.get("http://automationpractice.com/index.php?id_product=2&controller=product&search_query=blouse+&results=1");
            Select change = new Select(driver.findElement(By.id("group_1")));
            change.selectByValue("2");

        }
        finally {
            //driver.quit();
        }
    }

    // Testeo de envío del producto y aceptación de los terminos y condiciones
    @Test
    public void sendProduct() {
        WebDriver driver = new FirefoxDriver();
        try{
            driver.get("http://automationpractice.com/index.php?controller=order");
            WebElement terms_license = driver.findElement(By.id("uniform.cgv"));
            terms_license.click();
            WebElement send = driver.findElement(By.name("processCarrier"));
            send.click();

        }
        finally {
            //driver.quit();
        }
    }


    // Testeo de una busqueda vacía
    @Test
    public void blankSearch() {
        WebDriver driver = new FirefoxDriver();
        try{
            driver.get("http://automationpractice.com/index.php");
            WebElement searchbox = driver.findElement(By.id("search_query_top"));
            searchbox.sendKeys(Keys.ENTER);
        }
        finally {
            //driver.quit();
        }
    }

    // Testeo de busqueda con una palabra clave no relacionada
    @Test
    public void nonsenseSearch() {
        WebDriver driver = new FirefoxDriver();
        try{
            driver.get("http://automationpractice.com/index.php");
            WebElement searchbox = driver.findElement(By.id("search_query_top"));
            searchbox.sendKeys("Pelota");
            searchbox.sendKeys(Keys.ENTER);
        }
        finally {
            //driver.quit();
        }
    }

    // Testeo de envio de mensaje vacío a atención al cliente
    @Test
    public void blankCustomerMessage() {
        WebDriver driver = new FirefoxDriver();
        try{
            driver.get("http://automationpractice.com/index.php?controller=contact");
            Select subject = new Select(driver.findElement(By.id("id_contact")));
            subject.selectByValue("2");
            WebElement email = driver.findElement(By.id("email"));
            email.sendKeys("test@mail.cl");
            WebElement button = driver.findElement(By.id("submitMessage"));
            button.click();
        }
        finally {
            //driver.quit();
        }
    }

    // Testeo de mensaje de atención al cliente
    @Test
    public void customerMessage() {
        WebDriver driver = new FirefoxDriver();
        try{
            driver.get("http://automationpractice.com/index.php?controller=contact");
            Select subject = new Select(driver.findElement(By.id("id_contact")));
            subject.selectByValue("2");
            WebElement email = driver.findElement(By.id("email"));
            email.sendKeys("test@mail.cl");
            WebElement message = driver.findElement(By.id("message"));
            message.sendKeys("test message");
            WebElement button = driver.findElement(By.id("submitMessage"));
            button.click();
        }
        finally {
            //driver.quit();
        }
    } 
  

   


}
