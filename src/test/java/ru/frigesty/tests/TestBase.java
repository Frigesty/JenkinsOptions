package ru.frigesty.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.frigesty.helpers.Attach;

import static java.lang.String.format;

public class TestBase {

    @BeforeAll
    static void beforeAll() {
        String wdHost = System.getProperty("wd", "selenoid.autotests");
        String getWdHost = format("https://user1:1234@%s/wd/hub", wdHost);
        String[] browser = System.getProperty("browser").split(":");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
        Configuration.pageLoadStrategy = System.getProperty("loadStrategy", "eager");
        Configuration.baseUrl = System.getProperty("baseUrl", "");
        Configuration.holdBrowserOpen = Boolean.parseBoolean(System.getProperty("holdBrowser", "false"));
        Configuration.remote = getWdHost;
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
        Configuration.browser = browser[0];
        Configuration.browserVersion = browser[1];
    }
    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }
}