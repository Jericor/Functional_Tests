import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeleniumTest {

    // Se declara el driver para el navegador Web
    private static WebDriver driver;

    // Antes de hace cualquier test se instancia el driver de firefox
    // Y se configura el wait implicito
    @BeforeAll
    public static void setupDriver(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    // Despues de ejecutar todos los test se cierra el driver del navegador Web
    @AfterAll
    public static void tearDownDriver(){
        if (driver != null) {
            driver.quit();
        }
    }

    // Despues de cada test se espera un tiempo de 5 segundos
    // Para ver el resultado y dejar que los datos se actualicen
    @AfterEach
    public void afterSleep(){
        try{
            Thread.sleep(5000);
        }
        catch(InterruptedException ie) {
        }
    }

    // Testeo de verificación fallida para creación de cuenta
    // Razón: Correo no válido
    @Test
    @Order(1)
    public void failedVerification() {
        // Se dirige a la página de Log In
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        // Se ingresa el mail de prueba
        WebElement email = driver.findElement(By.id("email_create"));
        email.sendKeys("mail.test.cl");
        // Se hace click en ingresar
        WebElement button = driver.findElement(By.id("SubmitCreate"));
        button.click();

        // Se confirma el mensaje de error
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'Invalid email address.')]"));
        Assertions.assertNotNull(message);
    }

    // Testeo de verificación exitosa para creación de cuenta
    @Test
    @Order(2)
    public void verification() {
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        WebElement email = driver.findElement(By.id("email_create"));
        email.sendKeys("mail@test.cl");
        WebElement button = driver.findElement(By.id("SubmitCreate"));
        button.click();

        String expected = "http://automationpractice.com/index.php?controller=authentication&back=my-account#account-creation";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
        }

    // Testeo de ingreso fallido al sistema
    // Razón: Campos Vacíos
    @Test
    @Order(3)
    public void blankSignIn() {
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        WebElement button = driver.findElement(By.id("SubmitLogin"));
        button.click();

        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'An email address required.')]"));
        Assertions.assertNotNull(message);
    }


    // Testeo de ingreso exitoso al sistema
    @Test
    @Order(4)
    public void signIn() {
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("test@mail.cl");
        WebElement password = driver.findElement(By.id("passwd"));
        password.sendKeys("fp30t");
        WebElement button = driver.findElement(By.id("SubmitLogin"));
        button.click();

        String expected = "http://automationpractice.com/index.php?controller=my-account";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    // Testeo de añadir un objeto al carrito de compras
    @Test
    @Order(5)
    public void addToCart() {
        driver.get("http://automationpractice.com/index.php");
        WebElement searchbox = driver.findElement(By.id("search_query_top"));
        searchbox.sendKeys("Blouse");
        searchbox.sendKeys(Keys.ENTER);
        driver.get("http://automationpractice.com/index.php?controller=search&orderby=position&orderway=desc&search_query=blouse&submit_search=");
        WebElement image = driver.findElement(By.linkText("Blouse"));
        image.click();
        driver.get("http://automationpractice.com/index.php?id_product=2&controller=product&search_query=blouse&results=1");
        WebElement add = driver.findElement(By.id("add_to_cart"));
        add.click();
    }

    // Testeo de eliminar un objeto del carrito de compras
    @Test
    @Order(6)
    public void deleteFromCart() {
        driver.get("http://automationpractice.com/index.php");
        WebElement searchbox = driver.findElement(By.id("search_query_top"));
        searchbox.sendKeys("Blouse");
        searchbox.sendKeys(Keys.ENTER);
        driver.get("http://automationpractice.com/index.php?id_product=2&controller=product&search_query=blouse&results=1");
        WebElement add = driver.findElement(By.id("add_to_cart"));
        add.click();
        WebElement checkout = driver.findElement(By.className("btn btn-default button button-medium"));
        checkout.click();
        driver.get("http://automationpractice.com/index.php?controller=order");
        WebElement delete = driver.findElement(By.id("2_7_0_598222"));
        delete.click();
    }

    // Testeo para cambiar la talla a M
    @Test
    @Order(7)
    public void changeSize() {
        driver.get("http://automationpractice.com/index.php");
        WebElement searchbox = driver.findElement(By.id("search_query_top"));
        searchbox.sendKeys("Blouse");
        searchbox.sendKeys(Keys.ENTER);
        driver.get("http://automationpractice.com/index.php?controller=search&orderby=position&orderway=desc&search_query=blouse&submit_search=");
        WebElement image = driver.findElement(By.linkText("Blouse"));
        image.click();
        driver.get("http://automationpractice.com/index.php?id_product=2&controller=product&search_query=blouse+&results=1");
        Select change = new Select(driver.findElement(By.id("group_1")));
        change.selectByValue("2");
    }

    // Testeo de envío del producto y aceptación de los terminos y condiciones
    @Test
    @Order(8)
    public void sendProduct() {
        driver.get("http://automationpractice.com/index.php?controller=order");
        WebElement terms_license = driver.findElement(By.id("uniform.cgv"));
        terms_license.click();
        WebElement send = driver.findElement(By.name("processCarrier"));
        send.click();
    }


    // Testeo de una busqueda vacía
    @Test
    @Order(9)
    public void blankSearch() {
        driver.get("http://automationpractice.com/index.php");
        WebElement searchbox = driver.findElement(By.id("search_query_top"));
        searchbox.sendKeys(Keys.ENTER);

        String expected = "http://automationpractice.com/index.php?controller=search&orderby=position&orderway=desc&search_query=&submit_search=";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'Please enter a search keyword')]"));
        Assertions.assertNotNull(message);
    }

    // Testeo de busqueda con una palabra clave no relacionada
    @Test
    @Order(10)
    public void nonsenseSearch() {
        driver.get("http://automationpractice.com/index.php");
        WebElement searchbox = driver.findElement(By.id("search_query_top"));
        searchbox.sendKeys("Pelota");
        searchbox.sendKeys(Keys.ENTER);

        String expected = "http://automationpractice.com/index.php?controller=search&orderby=position&orderway=desc&search_query=Pelota&submit_search=";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'No results were found for your search \"Pelota\"')]"));
        Assertions.assertNotNull(message);
    }

    // Testeo de envio de mensaje vacío a atención al cliente
    @Test
    @Order(11)
    public void blankCustomerMessage() {
        driver.get("http://automationpractice.com/index.php?controller=contact");
        Select subject = new Select(driver.findElement(By.id("id_contact")));
        subject.selectByValue("2");
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("test@mail.cl");
        WebElement button = driver.findElement(By.id("submitMessage"));
        button.click();

        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'The message cannot be blank.')]"));
        Assertions.assertNotNull(message);
    }

    // Testeo de mensaje de atención al cliente
    @Test
    @Order(12)
    public void customerMessage() {
        driver.get("http://automationpractice.com/index.php?controller=contact");
        Select subject = new Select(driver.findElement(By.id("id_contact")));
        subject.selectByValue("2");
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("test@mail.cl");
        WebElement message = driver.findElement(By.id("message"));
        message.sendKeys("test message");
        WebElement button = driver.findElement(By.id("submitMessage"));
        button.click();

        List<WebElement> notification= driver.findElements(By.xpath("//*[contains(text(),'Your message has been successfully sent to our team.')]"));
        Assertions.assertNotNull(notification);
    }

}
