package org.grostarin.springboot.demorest.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.grostarin.springboot.demorest.domain.BannedBook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringBootBootstrapLiveTest_Banned {

    @LocalServerPort
    private int port;

    private String getApiRoot() {
        return "http://localhost:"+port+"/api/Banned-books";
    }

    @Test
    public void whenGetAllBannedBooks_thenOK() {
        final Response response = RestAssured.get(getApiRoot());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }



    @Test
    public void whenGetBannedBooksByTitle_thenOK() {
        final BannedBook bannedbook = createRandomBannedBook();
        createBannedBookAsUri(bannedbook);

        final Response response = RestAssured.get(getApiRoot() + "?title=" + bannedbook.getTitle());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class)
                .size() > 0);
    }



    @Test
    public void whenGetCreatedBannedBookById_thenOK() {
        final BannedBook bannedbook = createRandomBannedBook();
        final String location = createBannedBookAsUri(bannedbook);

        final Response response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(bannedbook.getTitle(), response.jsonPath()
                .get("title"));
    }

    @Test
    public void whenGetNotExistBannedBookById_thenNotFound() {
        final Response response = RestAssured.get(getApiRoot() + "/" + randomNumeric(4));
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    // POST
    @Test
    public void whenCreateNewBannedBook_thenCreated() {
        final BannedBook bannedbook = createRandomBannedBook();

        final Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(bannedbook)
                .post(getApiRoot());
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void whenInvalidBannedBook_thenError() {
        final BannedBook bannedbook = createRandomBannedBook();
        bannedbook.setAuthor(null);

        final Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(bannedbook)
                .post(getApiRoot());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedBannedBook_thenUpdated() {
        final BannedBook bannedbook = createRandomBannedBook();
        final String location = createBannedBookAsUri(bannedbook);

        bannedbook.setId(Long.parseLong(location.split("api/banned-books/")[1]));
        bannedbook.setAuthor("newAuthor");
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(bannedbook)
                .put(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("newAuthor", response.jsonPath()
                .get("author"));

    }

    @Test
    public void whenDeleteCreatedBannedBook_thenOk() {
        final BannedBook bannedbook = createRandomBannedBook();
        final String location = createBannedBookAsUri(bannedbook);

        Response response = RestAssured.delete(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    // ===============================

    private BannedBook createRandomBannedBook() {
        final BannedBook Bannedbook = new BannedBook();
        Bannedbook.setTitle(randomAlphabetic(10));
        Bannedbook.setAuthor(randomAlphabetic(15));
        return Bannedbook;
    }

    private String createBannedBookAsUri(BannedBook bannedbook) {
        final Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(bannedbook)
                .post(getApiRoot());
        return getApiRoot() + "/" + response.jsonPath()
                .get("id");
    }

}