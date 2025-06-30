package tests;

import extensions.LoginExtension;
import io.qameta.allure.Step;
import models.*;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;
import static specs.DemoqaSpecs.demoqaRequestSpec;
import static specs.DemoqaSpecs.demoqaResponseSpec;
import static tests.TestData.*;

public class BookStoreSteps {

    @Step("Удаляем все книги из коллекции профиля")
    public BookStoreSteps deleteAllBooksFromCollection() {
        given(demoqaRequestSpec)
                .header("Authorization", "Bearer " + LoginExtension.getLoginResponse().getToken())
                .queryParams("UserId", LoginExtension.getLoginResponse().getUserId())
                .when()
                .delete(BOOKS_END_POINT)
                .then()
                .spec(demoqaResponseSpec)
                .statusCode(HTTP_NO_CONTENT);

        return this;
    }

    @Step("Удаляем одну книгу из коллекции профиля")
    public BookStoreSteps deleteOneBookFromCollection
            (String isbnValue) {
        DeleteOneBookBodyModel deleteBookData = new DeleteOneBookBodyModel();
        deleteBookData.setUserId(LoginExtension.getLoginResponse().getUserId());
        deleteBookData.setIsbn(isbnValue);

        given(demoqaRequestSpec)
                .header("Authorization", "Bearer " + LoginExtension.getLoginResponse().getToken())
                .body(deleteBookData)
                .when()
                .delete(BOOK_END_POINT)
                .then()
                .spec(demoqaResponseSpec)
                .statusCode(HTTP_NO_CONTENT);

        return this;
    }

    @Step("Добавляем книгу/книги в коллекцию профиля")
    public BookStoreSteps addBooksToCollection
            (List<IsbnModel> listOfIsbn) {

        AddListOfBooksModel newBookData = new AddListOfBooksModel();
        newBookData.setUserId( LoginExtension.getLoginResponse().getUserId());
        newBookData.setCollectionOfIsbns(listOfIsbn);

        given(demoqaRequestSpec)
                .header("Authorization", "Bearer " + LoginExtension.getLoginResponse().getToken())
                .body(newBookData)
                .when()
                .post(BOOKS_END_POINT)
                .then()
                .spec(demoqaResponseSpec)
                .statusCode(HTTP_CREATED);

        return this;
    }

    @Step("Проверяем отображение книги/книг в коллекции профиля")
    public BookStoreSteps checkBookNames(List<String> bookNameValue) {
        open("/profile");
        $$(".rt-td a").shouldHave(texts(bookNameValue));

        return this;
    }

    @Step("Проверяем количество отображаемых книг")
    public BookStoreSteps checkBooksQuantity(Integer quantityValue) {
        open("/profile");
        $$(".rt-td a").shouldHave(size(quantityValue));

        return this;
    }
}
