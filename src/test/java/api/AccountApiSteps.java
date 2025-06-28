package api;

import api.models.LoginResponseDto;
import io.qameta.allure.Step;
import models.LoginBodyModel;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static specs.DemoqaSpecs.demoqaRequestSpec;
import static specs.DemoqaSpecs.demoqaResponseSpec;

public class AccountApiSteps {

    @Step("Авторизуемся на сайте @WithLogin")
    public static LoginResponseDto demoqaAuth() {
        LoginBodyModel authData = new LoginBodyModel();
        authData.setUserName("EMalysh");
        authData.setPassword("Vbrd_100)%");

        given(demoqaRequestSpec)
                .body(authData)
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .spec(demoqaResponseSpec)
                .statusCode(HTTP_OK);


        return given(demoqaRequestSpec)
                .body(authData)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(demoqaResponseSpec)
                .statusCode(HTTP_OK)
                .extract().as(LoginResponseDto.class);
    }
}
