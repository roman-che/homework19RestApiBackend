package ru.inventorium.qa.restbackend;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import ru.inventorium.qa.restbackend.controller.LibraryController;
import ru.inventorium.qa.restbackend.controller.LibraryController.*;
import ru.inventorium.qa.restbackend.domain.BookInfo;
import ru.inventorium.qa.restbackend.service.BookService;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookControllerTests {

    @Test
    @Order(1)
    public void addNewBooksTests() {
        String bookData1 = "   {" +
                "        \"book_name\": \"Generation Swine\",\n" +
                "        \"book_author\": \"Hunter Thompson\",\n" +
                "        \"book_isbn\": \"9780330510783\",\n" +
                "        \"book_price\": 100" +
                "    }";

        String bookData2 = "    {" +
                "        \"book_name\": \"One flew over the cuckoo's nest\"," +
                "        \"book_author\": \"Ken Kesey\"," +
                "        \"book_isbn\": \"9780330235648\"," +
                "        \"book_price\": 150" +
                "    }";

        String bookData3 = "    {" +
                "        \"book_name\": \"Dead souls\"," +
                "        \"book_author\": \"Nikolai Gogol\"," +
                "        \"book_isbn\": \"9780393952926\"," +
                "        \"book_price\": 200" +
                "    }";

        String bookData4 = "    {" +
                "        \"book_name\": \"В лесу было накурено\"," +
                "        \"book_author\": \"Валерий Зеленогорский\"," +
                "        \"book_isbn\": \"5170373864\"," +
                "        \"book_price\": 250" +
                "    }";


        BookInfo newBook = given()
                .body(bookData1)
                .contentType(ContentType.JSON)
                .when().post("/books/newBook")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(BookInfo.class);

        assertThat(newBook.getBookName(), is("Generation Swine"));

        newBook = given()
                .body(bookData2)
                .contentType(ContentType.JSON)
                .when().post("/books/newBook")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(BookInfo.class);

        assertThat(newBook.getBookAuthor(), is("Ken Kesey"));

        newBook = given()
                .body(bookData3)
                .contentType(ContentType.JSON)
                .when().post("/books/newBook")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(BookInfo.class);

        assertThat(newBook.getBookPrice(), is(200L));

        newBook = given()
                .body(bookData4)
                .contentType(ContentType.JSON)
                .when().post("/books/newBook")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(BookInfo.class);
        assertThat(newBook.getBookIsbn(), is("5170373864"));
    }

    @Test
    public void searchBookByNameTest() {
        String nameToSearch = "Generation Swine";
        BookInfo response = given()
                .pathParam("bookName", nameToSearch)
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("/books/findByName/{bookName}")
                .then().statusCode(200)
                .extract().as(BookInfo.class);

        assertThat(response.getBookName(), is(nameToSearch));
    }

    @Test
    public void searchBookByAuthorTest() {
        String nameToSearch = "Валерий Зеленогорский";
        List<BookInfo> response = given()
                .pathParam("authorName", nameToSearch)
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("/books/findByAuthor/{authorName}")
                .then().statusCode(200)
                .extract().jsonPath().getList(".", BookInfo.class);

        assertThat(response.get(0).getBookAuthor(), is(nameToSearch));
    }

    @Test
    public void addNewBookNegativeTest() {
        String badBookData = "    {" +
                "        \"book_name\": \"В лесу было накурено\"," +
                "        \"book_author\": \"Валерий Зеленогорский\"," +
                "        \"book_isbn\": \"5170373864\"," +
                "    }";

        given()
                .body(badBookData)
                .contentType(ContentType.JSON)
                .when().post("/books/newBook")
                .then()
                .assertThat()
                .statusCode(400);

    }

    @Test
    public void searchBookByNameNegativeTest() {

        String badNameToSearch = "Большая советская энциклопедия";

        given().pathParam("bookName", badNameToSearch)
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("/books/findByName/{bookName}")
                .then().statusCode(400);
    }

    @Test
    public void searchBookByAuthorNegative() {

        String badNameToSearch = "Фёдор Достоевский";

        given().pathParam("authorName", badNameToSearch)
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get("/books/findByAuthor/{authorName}")
                .then().statusCode(400);
    }
}

