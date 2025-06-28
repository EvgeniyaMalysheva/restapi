package tests;

import io.qameta.allure.Owner;
import models.IsbnModel;
import models.LoginResponseModel;
import org.junit.jupiter.api.*;

import java.util.List;

@Tag("bookstore")
@Owner("Evgeniya Malysheva")
public class BookCollectionTests extends TestBase {

    private final String firstBookId = "9781449365035",
            firstBookName = "Speaking JavaScript",
            secondBookId = "9781491904244",
            secondBookName = "You Don't Know JS";
    private final List<IsbnModel> listOfOneBook = List.of
            (new IsbnModel(firstBookId));
    private final List<IsbnModel> listOfTwoBooks = List.of
            (new IsbnModel(firstBookId), new IsbnModel(secondBookId));
    private final List<String> listOfOneBookName = List.of(firstBookName);
    private final List<String> listOfTwoBookNames = List.of(firstBookName, secondBookName);
    private final List<String> listOfSecondBookName = List.of(secondBookName);
    private String userId,
            token,
            expires;

    @BeforeEach
    public void authentificateAndSetParams() {
        LoginResponseModel authResponse = newBookStoreSession.demoqaAuth();
        userId = authResponse.getUserId();
        token = authResponse.getToken();
        expires = authResponse.getExpires();
    }

    @Test
    @DisplayName("Добавляем в коллекцию профиля одну книгу")
    void addBookToCollectionTest() {
        newBookStoreSession.deleteAllBooksFromProfileCollection(userId, token)
                .addBooksToProfileCollection(listOfOneBook, userId, token)
                .setDemoqaCookie(userId, token, expires)
                .checkBookNamesInProfileCollection(listOfOneBookName)
                .checkBooksQuantityInProfileCollection(1);
    }

    @Test
    @DisplayName("Добавляем в коллекцию профиля две книги, одну удаляем")
    void addTwoBooksToCollection_thenDeleteOneBook_Test() {
        newBookStoreSession.deleteAllBooksFromProfileCollection(userId, token)
                .addBooksToProfileCollection(listOfTwoBooks, userId, token)
                .setDemoqaCookie(userId, token, expires)
                .checkBookNamesInProfileCollection(listOfTwoBookNames)
                .checkBooksQuantityInProfileCollection(2)
                .deleteOneBookFromProfileCollection(firstBookId, userId, token)
                .checkBookNamesInProfileCollection(listOfSecondBookName)
                .checkBooksQuantityInProfileCollection(1);
    }
}
