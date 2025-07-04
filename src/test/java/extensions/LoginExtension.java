package extensions;

import api.AccountApiSteps;
import models.LoginResponseModel;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginExtension implements BeforeEachCallback {
    private static LoginResponseModel response;

    public static LoginResponseModel getLoginResponse() {
        return response;
    }
    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        response = AccountApiSteps.demoqaAuth();

        String userIdValue = response.getUserId();
        String tokenValue = response.getToken();
        String expiresValue = response.getExpires();

        open("/images/Toolsqa.jpg");
        getWebDriver().manage().addCookie(new Cookie("userID", userIdValue));
        getWebDriver().manage().addCookie(new Cookie("expires", expiresValue));
        getWebDriver().manage().addCookie(new Cookie("token", tokenValue));
    }
}
