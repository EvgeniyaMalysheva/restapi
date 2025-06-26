package tests;

import io.qameta.allure.Step;
import models.demoqa.*;
import org.openqa.selenium.Cookie;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;
import static specs.DemoqaSpecs.demoqaRequestSpec;
import static specs.DemoqaSpecs.demoqaResponseSpec;
import static tests.TestData.*;

public class BookStoreSteps {

    @Step("Авторизуемся на сайте")
    public LoginResponseModel demoqaAuth() {
        LoginBodyModel authData = new LoginBodyModel();
        authData.setUserName(DEMOQA_LOGIN);
        authData.setPassword(DEMOQA_PASSWORD);

        return given(demoqaRequestSpec)
                .body(authData)
                .when()
                .post(LOGIN_END_POINT)
                .then()
                .spec(demoqaResponseSpec)
                .statusCode(HTTP_OK)
                .extract().as(LoginResponseModel.class);
    }

    @Step("Подменяем cookie на сайте")
    public BookStoreSteps setDemoqaCookie
            (String userIdValue, String tokenValue, String expiresValue) {
        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", userIdValue));
        getWebDriver().manage().addCookie(new Cookie("expires", expiresValue));
        getWebDriver().manage().addCookie(new Cookie("token", tokenValue));

        return this;
    }

    @Step("Удаляем все книги из коллекции профиля")
    public BookStoreSteps deleteAllBooksFromProfileCollection
            (String userIdValue, String tokenValue) {
        given(demoqaRequestSpec)
                .header("Authorization", "Bearer " + tokenValue)
                .queryParams("UserId", userIdValue)
                .when()
                .delete(BOOKS_END_POINT)
                .then()
                .spec(demoqaResponseSpec)
                .statusCode(HTTP_NO_CONTENT);

        return this;
    }

    @Step("Удаляем одну книгу из коллекции профиля")
    public BookStoreSteps deleteOneBookFromProfileCollection
            (String isbnValue, String userIdValue, String tokenValue) {
        DeleteOneBookBodyModel deleteBookData = new DeleteOneBookBodyModel();
        deleteBookData.setUserId(userIdValue);
        deleteBookData.setIsbn(isbnValue);

        given(demoqaRequestSpec)
                .header("Authorization", "Bearer " + tokenValue)
                .body(deleteBookData)
                .when()
                .delete(BOOK_END_POINT)
                .then()
                .spec(demoqaResponseSpec)
                .statusCode(HTTP_NO_CONTENT);

        return this;
    }

    @Step("Добавляем книгу/книги в коллекцию профиля")
    public BookStoreSteps addBooksToProfileCollection
            (List<IsbnModel> listOfIsbn, String userIdValue, String tokenValue) {

        AddListOfBooksModel newBookData = new AddListOfBooksModel();
        newBookData.setUserId(userIdValue);
        newBookData.setCollectionOfIsbns(listOfIsbn);

        given(demoqaRequestSpec)
                .header("Authorization", "Bearer " + tokenValue)
                .body(newBookData)
                .when()
                .post(BOOKS_END_POINT)
                .then()
                .spec(demoqaResponseSpec)
                .statusCode(HTTP_CREATED);

        return this;
    }

    @Step("Проверяем отображение книги/книг в коллекции профиля")
    public BookStoreSteps checkBookNamesInProfileCollection(List<String> bookNameValue) {
        open("/profile");
        $$(".rt-td a").shouldHave(texts(bookNameValue));

        return this;
    }

    @Step("Проверяем количество отображаемых книг")
    public BookStoreSteps checkBooksQuantityInProfileCollection(Integer quantityValue) {
        open("/profile");
        $$(".rt-td a").shouldHave(size(quantityValue));

        return this;
    }
}
