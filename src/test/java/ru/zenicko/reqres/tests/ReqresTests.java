package ru.zenicko.reqres.tests;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.zenicko.reqres.config.EndPoints;
import ru.zenicko.reqres.lombok.UserResponse;
import ru.zenicko.reqres.models.User;
import ru.zenicko.reqres.specs.Specs;
import org.hamcrest.beans.SamePropertyValuesAs;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.text.MatchesPattern.matchesPattern;

@Tag("ReqresTests")
@DisplayName("Testing API https://reqres.in/")
public class ReqresTests {
    @BeforeAll
    static void SetUp() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    @DisplayName("List users: check status code ")
    void shouldBeStatusCode200InResponseListUsers() {
        // @formatter:off
        RestAssured
                .given()
                    .spec(Specs.requestSpec)
                .when()
                    .pathParams("numPage", 2)
                    .get(EndPoints.usersPage2)
                .then()
                    .spec(Specs.responseSpec)
                    .log().all();
        // @formatter:on
    }
    @Test
    @DisplayName("List users: check avatar fields by findAll")
    void shouldBePathOfAvatarCorrectInResponseListUsers() {
        // @formatter:off
        RestAssured
                .given()
                    .spec(Specs.requestSpec)
                .when()
                    .pathParams("numPage", 2)
                    .get(EndPoints.usersPage2)
                .then()
                    .spec(Specs.responseSpec)
                    .log().all()
                    .body("data.findAll{it.avatar =~/.*.image.jpg/}.avatar.flatten()",
                        hasItem("https://reqres.in/img/faces/7-image.jpg"));
        // @formatter:on
    }

    @Test
    @DisplayName("Single users: check a value of a field response")
    void shouldBeStatusCode200InResponseSingleUser() {
        // @formatter:off
        RestAssured
                .given()
                    .spec(Specs.requestSpec)
                .when()
                    .get(EndPoints.userTwo)
                .then()
                    .spec(Specs.responseSpec)
                    .body("data.id", is(2));
        // @formatter:on
    }

    @Test
    @DisplayName("Single user: check existing field")
    void shouldHaveFieldInResponseSingleUser() {
        // @formatter:off
        RestAssured
                .given()
                    .spec(Specs.requestSpec)
                .when()
                    .get(EndPoints.userTwo)
                .then()
                    .spec(Specs.responseSpec)
                    .body("$", hasKey("data"));
        // @formatter:on
    }

    @Test
    @DisplayName("Single <resource> not found: check body is empty")
    void shouldBeBodyNullSingleResource() {
        // @formatter:off
        String jsonBodyActually =
                RestAssured
                        .given()
                            .spec(Specs.requestSpec)
                        .when()
                            .get(EndPoints.unknownTwoThree)
                        .then()
                            .statusCode(404)
                        .and()
                            .extract()
                            .response()
                            .asString();
        // @formatter:on
        Assertions.assertThat(jsonBodyActually).isEqualTo("{}");
    }

    @Test
    @DisplayName("Create: validation response JSON by schema.json")
    void validateResponseCreateNewUser() {
        String jsonUser = "{\"name\": \"morpheus\", \"job\": \"leader\"}";
        // @formatter:off
        String jsonActual =
                RestAssured
                        .given()
                            .spec(Specs.requestSpecAddContentType)
                        .body(jsonUser)
                            .post(EndPoints.users)
                        .then()
                            .spec(Specs.responseSpec201)
                        .and()
                            .extract()
                            .response()
                            .asString();
        // @formatter:on
        System.out.println(jsonActual);

        assertThat(jsonActual, JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonschemes/schema-new-user.json"));
    }

    @Test
    @DisplayName("Create by Model: validation Name and Job in response JSON")
    void validateResponseCreateNewUserModels() {
        //String jsonUser = "{\"name\": \"morpheus\", \"job\": \"leader\"}";
        User user = new User();
        user.setName("morpheus");
        user.setJob("leader");

        // @formatter:off
        User userResponse =
            RestAssured
                    .given()
                        .spec(Specs.requestSpecAddContentType)
                    .body(user)
                        .post(EndPoints.users)
                    .then()
                        .spec(Specs.responseSpec201)
                    .and()
                        .extract()
                        .body()
                        .as(User.class);
        // @formatter:on
        System.out.println(userResponse.toString());

        assertThat(user, SamePropertyValuesAs.samePropertyValuesAs(userResponse));
    }

    @Test
    @DisplayName("Create by Lombok: validation Name and Job in response JSON")
    void validateResponseCreateNewUserLombok() {
        User user = new User();
        user.setName("morpheus");
        user.setJob("leader");

        // @formatter:off
        UserResponse userResponse =
                RestAssured
                        .given()
                            .spec(Specs.requestSpecAddContentType)
                            .body(user)
                            .post(EndPoints.users)
                        .then()
                            .spec(Specs.responseSpec201)
                        .and()
                            .extract()
                            .body()
                            .as(UserResponse.class);
        // @formatter:on
        System.out.println(userResponse.toString());

        System.out.println(Integer.parseInt(userResponse.getId()));
        assertThat(userResponse.getId(), is(notNullValue()));
        assertThat(Integer.parseInt(userResponse.getId()), greaterThan(0));
    }

    @Test
    @DisplayName("Create a new user: validate response field of `createdAt` by RegularExpression")
    void validateResponseCreateNewUserRegularExpression() {
        User user = new User();
        user.setName("morpheus");
        user.setJob("leader");

        // @formatter:off
        UserResponse userResponse =
                RestAssured
                        .given()
                            .spec(Specs.requestSpecAddContentType)
                        .body(user)
                            .post(EndPoints.users)
                        .then()
                            .spec(Specs.responseSpec201)
                        .and()
                            .extract()
                            .body()
                            .as(UserResponse.class);
        // @formatter:on
        System.out.println(userResponse.toString());

        System.out.println(userResponse.getCreatedAt());
        assertThat(userResponse.getCreatedAt(), is(notNullValue()));
        assertThat(userResponse.getCreatedAt(), matchesPattern("202[2-5]-[0-1][0-9]-[1-3][0-9]T[0-2][0-9]:[0-5][0-9]:[0-5][0-9][.][0-9]{3}Z"));
    }
}
