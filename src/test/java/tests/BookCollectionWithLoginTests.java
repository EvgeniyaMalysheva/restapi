package tests;

import annotations.WithLogin;
import io.qameta.allure.Owner;
import models.IsbnModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

@Tag("bookstore")
@Owner("Evgeniya Malysheva")
public class BookCollectionWithLoginTests extends TestBase {
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

    @Test
    @WithLogin
    @DisplayName("Добавляем в коллекцию профиля одну книгу")
    void addBookToCollectionTest() {
        newBookStoreSession.deleteAllBooksFromProfileCollection()
                .addBooksToProfileCollection(listOfOneBook)
                .checkBookNamesInProfileCollection(listOfOneBookName)
                .checkBooksQuantityInProfileCollection(1);
    }

    @Test
    @WithLogin
    @DisplayName("Добавляем в коллекцию профиля две книги, одну удаляем")
    void addTwoBooksToCollection_thenDeleteOneBook_Test() {
        newBookStoreSession.deleteAllBooksFromProfileCollection()
                .addBooksToProfileCollection(listOfTwoBooks)
                .checkBookNamesInProfileCollection(listOfTwoBookNames)
                .checkBooksQuantityInProfileCollection(2)
                .deleteOneBookFromProfileCollection(firstBookId)
                .checkBookNamesInProfileCollection(listOfSecondBookName)
                .checkBooksQuantityInProfileCollection(1);
    }
}
