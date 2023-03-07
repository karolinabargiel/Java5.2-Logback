import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

@Execution(ExecutionMode.CONCURRENT)
public class TitleTest extends TestBase {
    public static Stream<Arguments> testDataProvider() {
        return Stream.of(
                Arguments.of("Filmweb - filmy takie jak Ty!", "https://www.filmweb.pl/"));

    }


    @ParameterizedTest
    @ValueSource(strings = {"Rozwiązania i usługi IT, inżynierii i BPO - Sii Polska"})
    @DisplayName("SiiPortal title")
    @Tag("regression")
    @Tag("Sii portal")
    void shouldValidateCorrectTitleSii(String expectedTitle) {
        //GIVEN
        String url = "https://sii.pl/";
        driver.get(url);
        //WHEN
        String actualTitle = driver.getTitle();
        //THEN
        assertThat(actualTitle).isEqualTo(expectedTitle);

    }

    @ParameterizedTest
    @CsvSource({"Onet – Jesteś na bieżąco, https://www.onet.pl/"})
    @DisplayName("Onet title")
    @Tag("regression")
    @Tag("Onet")
    void shouldValidateCorrectTitleOnet(String expectedTitle, String url) {
        //GIVEN
        driver.get(url);
        //WHEN
        String actualTitle = driver.getTitle();
        //THEN
        assertThat(actualTitle).isEqualTo(expectedTitle);

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/testdata.csv")
    @DisplayName("Kotuszkowo title")
    @Tag("regression")
    @Tag("Kotuszkowo")
    void shouldValidateCorrectTitleKotuszkowo(String url, String expectedTitle) {
        //GIVEN
        driver.get(url);
        //WHEN
        String actualTitle = driver.getTitle();
        //THEN
        assertThat(actualTitle).isEqualTo(expectedTitle);

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
        String actualTitle = driver.getTitle();
        //THEN
        assertThat(actualTitle).isEqualTo(expectedTitle);

    }

    @ParameterizedTest
    @CsvSource({"WebDriver | Selenium, https://www.selenium.dev/documentation/webdriver/"})
    @DisplayName("Selenium documentation title")
    @Tag("regression")
    @Tag("Selenium")
    void shouldValidateCorrectTitleSelenium(String expectedTitle, String url) {
        //GIVEN
        driver.get(url);
        //WHEN
        String actualTitle = driver.getTitle();
        //THEN
        assertThat(actualTitle).isEqualTo(expectedTitle);

    }

}
