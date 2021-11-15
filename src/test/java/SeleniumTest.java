import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.List;

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

    // Testeo de verificación para creación de cuenta con campo no válido
    @Test
    @Order(1)
    public void failedVerification() {
        // Se dirige a la página de Log In
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        // Se ingresa el mail de prueba
        WebElement email = driver.findElement(By.id("email_create"));
        email.sendKeys("mail.test.cl");
        // Se hace click en crear cuenta
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
        // Se dirige a la página de Log In
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        // Se ingresa el mail de prueba
        WebElement email = driver.findElement(By.id("email_create"));
        email.sendKeys("mail@test.cl");
        // Se hace click en crear cuenta
        WebElement button = driver.findElement(By.id("SubmitCreate"));
        button.click();

        // Se verifica la redirección a la página de creación de cuenta
        String expected = "http://automationpractice.com/index.php?controller=authentication&back=my-account#account-creation";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
        }

    // Testeo de ingreso al sistema con campos vacíos
    @Test
    @Order(3)
    public void blankSignIn() {
        // Se dirige a la página de Log In
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        // Se presiona el botón de ingresar a la cuenta
        WebElement button = driver.findElement(By.id("SubmitLogin"));
        button.click();

        // Se verifica el mensaje de error debido al campo vacío
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'An email address required.')]"));
        Assertions.assertNotNull(message);
    }

    // Testeo de ingreso exitoso al sistema
    @Test
    @Order(4)
    public void signIn() {
        // Se dirige a la página de Log In
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        // Se ingresa el mail de prueba
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("test@mail.cl");
        // Se ingresa la contraseña de prueba
        WebElement password = driver.findElement(By.id("passwd"));
        password.sendKeys("fp30t");
        // Se presiona el botón de ingresar a la cuenta
        WebElement button = driver.findElement(By.id("SubmitLogin"));
        button.click();

        // Se verifica la redirección a la página del usuario
        String expected = "http://automationpractice.com/index.php?controller=my-account";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
    }

    // Testeo de añadir un objeto al carrito de compras
    @Test
    @Order(5)
    public void addToCart() {
        // Se dirige a la página principal
        driver.get("http://automationpractice.com/index.php");
        // Se selecciona el elemento "Blouse"
        WebElement image = driver.findElement(By.linkText("Blouse"));
        image.click();
        // Se presiona el botón de añadir al carrito
        WebElement add = driver.findElement(By.id("add_to_cart"));
        add.click();

        // Se verifica el mensaje al añadir al carrito
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'Product successfully added to your shopping cart')]"));
        Assertions.assertNotNull(message);
    }

    // Testeo de shipping hasta antes del método de pago
    @Test
    @Order(6)
    public void directionShop() {
        /*DESCOMENTAR EN CASO DE PROBAR EL TEST POR SI SOLO*/
        /*
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("test@mail.cl");
        WebElement password = driver.findElement(By.id("passwd"));
        password.sendKeys("fp30t");
        WebElement button = driver.findElement(By.id("SubmitLogin"));
        button.click();
        driver.get("http://automationpractice.com/index.php");
        WebElement image = driver.findElement(By.linkText("Blouse"));
        image.click();
        WebElement add = driver.findElement(By.id("add_to_cart"));
        add.click();
        */
        // Se dirige a la pagina de pago
        driver.get("http://automationpractice.com/index.php?controller=order");
        // Se presiona el botón para seguir con el checkout
        WebElement checkout= driver.findElement(By.linkText("Proceed to checkout"));
        checkout.click();
        // Se presiona el botón para seguir con la dirección por defecto
        WebElement address = driver.findElement(By.name("processAddress"));
        address.click();
        // Se aceptan los terminos de uso
        WebElement terms = driver.findElement(By.id("cgv"));
        terms.click();
        // Se presiona el botón para pasar al shipping
        WebElement shipping = driver.findElement(By.name("processCarrier"));
        shipping.click();

        //Se verifica que la pagina se encuentre en el método de pago
        String pay ="http://automationpractice.com/index.php?controller=order&multi-shipping=" ;
        Boolean payUrl = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe(pay));
        Assertions.assertTrue(payUrl);
    }

    // Testeo de eliminar un objeto del carrito de compras
    @Test
    @Order(7)
    public void deleteToCart() {
        /*  DESCOMENTAR AL PROBAR ESTE TEST POR SI SOLO*/
        /*
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("test@mail.cl");
        WebElement password = driver.findElement(By.id("passwd"));
        password.sendKeys("fp30t");
        WebElement button = driver.findElement(By.id("SubmitLogin"));
        button.click();
        driver.get("http://automationpractice.com/index.php");
        WebElement image = driver.findElement(By.linkText("Blouse"));
        image.click();
        WebElement add = driver.findElement(By.id("add_to_cart"));
        add.click();
        */
        // Se accede a la orden y se elimina del carrito
        driver.get("http://automationpractice.com/index.php?controller=order");
        WebElement delete= driver.findElement(By.className("cart_quantity_delete"));
        delete.click();

        // Se verifica el mensaje indicando que no hay objetos en el carrito
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'Your shopping cart is empty.')]"));
        Assertions.assertNotNull(message);
    }

    // Testeo para cambiar un producto a talla M
    @Test
    @Order(8)
    public void changeSize() {
        // Se dirige a la página principal
        driver.get("http://automationpractice.com/index.php");
        // Se busca el producto Blouse
        WebElement image = driver.findElement(By.linkText("Blouse"));
        image.click();
        // Se cambia la talla a M
        Select change = new Select(driver.findElement(By.id("group_1")));
        change.selectByValue("2");

        // Se verifica el cambio de talla
        String size = driver.findElement(By.id("group_1")).getAttribute("value");
        assertEquals("2", size);
    }

    // Testeo de una busqueda vacía
    @Test
    @Order(9)
    public void blankSearch() {
        // Se dirige a la página principal
        driver.get("http://automationpractice.com/index.php");
        // Se presiona el botón de busqueda
        WebElement searchbox = driver.findElement(By.id("search_query_top"));
        searchbox.sendKeys(Keys.ENTER);

        // Se verifica la redirección a la página de búsqueda
        String expected = "http://automationpractice.com/index.php?controller=search&orderby=position&orderway=desc&search_query=&submit_search=";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
        // Se verifica el mensaje que indica busqueda vacía
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'Please enter a search keyword')]"));
        Assertions.assertNotNull(message);
    }

    // Testeo de busqueda con una palabra clave no relacionada
    @Test
    @Order(10)
    public void nonsenseSearch() {
        // Se dirige a la página principal
        driver.get("http://automationpractice.com/index.php");
        // Se realiza la búsqueda con la palabra "Pelota"
        WebElement searchbox = driver.findElement(By.id("search_query_top"));
        searchbox.sendKeys("Pelota");
        searchbox.sendKeys(Keys.ENTER);

        // Se verifica la redirección a la página de búsqueda
        String expected = "http://automationpractice.com/index.php?controller=search&orderby=position&orderway=desc&search_query=Pelota&submit_search=";
        Boolean url = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe(expected));
        Assertions.assertTrue(url);
        // Se vérifica el mensaje indicando que no se encontraron resultados
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'No results were found for your search \"Pelota\"')]"));
        Assertions.assertNotNull(message);
    }

    // Testeo de envio de mensaje vacío a atención al cliente
    @Test
    @Order(11)
    public void blankCustomerMessage() {
        // Se dirige a la página de contacto
        driver.get("http://automationpractice.com/index.php?controller=contact");
        // Se selecciona el tema como servicio al cliente
        Select subject = new Select(driver.findElement(By.id("id_contact")));
        subject.selectByValue("2");
        // Se ingresa el mail de prueba
        WebElement email = driver.findElement(By.id("email"));
        email.clear();
        email.sendKeys("test@mail.cl");
        // Se presiona el botón de enviar mensaje
        WebElement button = driver.findElement(By.id("submitMessage"));
        button.click();

        // Se verifica el mensaje de error indicando que el mensaje no puede estar vacío
        List<WebElement> message= driver.findElements(By.xpath("//*[contains(text(),'The message cannot be blank.')]"));
        Assertions.assertNotNull(message);
    }

    // Testeo de mensaje de atención al cliente
    @Test
    @Order(12)
    public void customerMessage() {
        // Se dirige a la pagina de contacto
        driver.get("http://automationpractice.com/index.php?controller=contact");
        // Se selecciona el tema como servicio al cliente
        Select subject = new Select(driver.findElement(By.id("id_contact")));
        subject.selectByValue("2");
        // Se ingresa el mail de prueba
        WebElement email = driver.findElement(By.id("email"));
        email.clear();
        email.sendKeys("test@mail.cl");
        // Se ingresa el mensaje de prueba
        WebElement message = driver.findElement(By.id("message"));
        message.sendKeys("test message");
        // Se presiona el botón de enviar mensaje
        WebElement button = driver.findElement(By.id("submitMessage"));
        button.click();

        // Se verifica el mensaje de envio correcto
        List<WebElement> notification= driver.findElements(By.xpath("//*[contains(text(),'Your message has been successfully sent to our team.')]"));
        Assertions.assertNotNull(notification);
    } 
   
    
}
