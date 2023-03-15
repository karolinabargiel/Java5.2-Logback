package com.sii.tests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class TitleTests extends TestBase {


    public static Stream<Arguments> testDataProvider() {
        return Stream.of(
                Arguments.of("Pulp Fiction - szukaj filmu w bazie Filmwebu", "https://www.filmweb.pl/"));

    }


    @ParameterizedTest
    @ValueSource(strings = {"Kontakt | Sii Polska"})
    @DisplayName("SiiPortal title")
    @Tag("prod")
    @Tag("Sii")
    void shouldValidateCorrectTitleSii(String expectedTitle) {
        //GIVEN
        String url = "https://sii.pl/";
        driver.get(url);
        log.info("URL application: {} ", url);
        WebElement contactButton = driver.findElement(By.cssSelector("[class*='js-contact-button']"));
        //WHEN
        contactButton.click();
        String actualTitle = driver.getTitle();
        log.info("Button was clicked, user moved to: {}", actualTitle);
        //THEN
        assertThat(actualTitle).isEqualTo(expectedTitle);

    }

    @ParameterizedTest
    @CsvSource({"Bliźnięta - horoskop zodiakalny dzienny - Magia Onet, https://www.onet.pl/"})
    @DisplayName("Onet horoscope")
    @Tag("regression")
    @Tag("Onet")
    void shouldCheckHoroscopeOnet(String expectedTitle, String url) {
        //GIVEN
        driver.get(url);
        WebElement acceptCookie = driver.findElement(By.cssSelector("[class*='cmp-intro_acceptAll']"));
        acceptCookie.click();
        log.info("Cookies accepted");
        WebElement horoscopButton = driver.findElement(By.xpath("//a[normalize-space()='Horoskop']"));
        horoscopButton.click();
        log.info("Horoscop page is accessed, title: {}", driver.getTitle());
        //WHEN
        WebElement horoscopGemini = driver.findElement(By.cssSelector("a[href='/horoskop/zodiakalny-dzienny/bliznieta']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(horoscopGemini).perform();
        horoscopGemini.click();
        String actualTitle = driver.getTitle();
        log.info("Moved to subpage: {}", actualTitle);
        //THEN
        assertThat(actualTitle).isEqualTo(expectedTitle);

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/testdata.csv")
    @DisplayName("Kotuszkowo url")
    @Tag("regression")
    @Tag("Kotuszkowo")
    void shouldValidateCorrectUrlKotuszkowo(String url, String expectedUrl) {
        //GIVEN
        driver.get(url);
        log.info("page is accessed: {}", url);
        //WHEN
        Actions actions = new Actions(driver);
        WebElement date = driver.findElement(By.cssSelector("a[href='http://kotuszkowo.pl/2021/11/']"));
        actions.moveToElement(date).perform();
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='http://kotuszkowo.pl/2022/10/']"))).click();
        log.info("Date in archive is chosen: {}", date);
        WebElement iframe = driver.findElement(By.xpath("//*[@id='aswift_9']"));
        driver.switchTo().frame(iframe);
        WebElement iframe2 = driver.findElement(By.xpath("//*[@id='ad_iframe']"));
        driver.switchTo().frame(iframe2);
        driver.findElement(By.xpath("//*[@id='dismiss-button']")).click();
        log.info("google ad dismissed");
        driver.switchTo().defaultContent();
        log.info("Current title: {}", driver.getTitle());
        //THEN
        assertThat(driver.getCurrentUrl()).isEqualTo(expectedUrl);

    }

    @ParameterizedTest
    @MethodSource("testDataProvider")
    @DisplayName("Filmweb title")
    @Tag("regression")
    @Tag("Filmweb")
    void shouldValidateCorrectTitleFilmweb(String expectedTitle, String url) {
        //GIVEN
        driver.get(url);
        //WHEN
        driver.findElement(By.xpath("//*[@id='didomi-notice-agree-button']")).click();
        log.info("Cookies accepted");
        WebElement search = driver.findElement(By.cssSelector("#inputSearch"));
        search.sendKeys("Pulp Fiction" + Keys.ENTER);
        String actualTitle = driver.getTitle();
        log.info("Input successfull. Moved to: {}", actualTitle);
        //THEN
        assertThat(actualTitle).isEqualTo(expectedTitle);

    }

    @ParameterizedTest
    @CsvSource({"https://www.selenium.dev/documentation/webdriver/"})
    @DisplayName("Selenium documentation header")
    @Tag("regression")
    @Tag("Selenium")
    void shouldValidateCorrectHeaderSelenium(String url) {
        //GIVEN
        driver.get(url);
        //WHEN
        driver.findElement(By.xpath("//li[@class='nav-item dropdown']//a[@id='navbarDropdown']")).click();
        driver.findElement(By.cssSelector("a[href='/about']")).click();
        driver.findElement(By.xpath("//a[normalize-space()='Read full story']")).click();
        WebElement seleniumHistory = driver.findElement(By.cssSelector(".display-1"));
        if (seleniumHistory.isDisplayed()) {
            log.info("Selenium history header is displayed");
        } else {
            log.info("Selenium history header is not displayed");
        }
        //THEN
        assertTrue(seleniumHistory.isDisplayed());

    }

}
