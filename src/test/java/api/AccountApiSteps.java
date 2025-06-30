package api;

import io.qameta.allure.Step;
import models.LoginBodyModel;
import models.LoginResponseModel;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static specs.DemoqaSpecs.demoqaRequestSpec;
import static specs.DemoqaSpecs.demoqaResponseSpec;
import static tests.TestData.*;

public class AccountApiSteps {

    @Step("Авторизуемся на сайте @WithLogin")
    public static LoginResponseModel demoqaAuth() {
        LoginBodyModel authData = (System.getProperty("restLogin") == null)
                ? new LoginBodyModel(DEMOQA_LOGIN, DEMOQA_PASSWORD)
                : new LoginBodyModel(System.getProperty("restLogin"), System.getProperty("restPass"));

        given(demoqaRequestSpec)
                .body(authData)
                .when()
                .post(GENERATE_TOKEN_END_POINT)
                .then()
                .spec(demoqaResponseSpec)
                .statusCode(HTTP_OK);


        return given(demoqaRequestSpec)
                .body(authData)
                .when()
                .post(LOGIN_END_POINT)
                .then()
                .spec(demoqaResponseSpec)
                .statusCode(HTTP_OK)
                .extract().as(LoginResponseModel.class);
    }
}
