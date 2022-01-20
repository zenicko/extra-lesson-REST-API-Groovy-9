package ru.zenicko.reqres.specs;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specs {
    public static RequestSpecification requestSpec =
            RestAssured
                    .with()
                    .baseUri("https://reqres.in")
                    .basePath("/api")
                    .log().all();

    public static RequestSpecification requestSpecAddContentType =
            requestSpec.contentType(ContentType.JSON);

    public static ResponseSpecification responseSpec =
                    new ResponseSpecBuilder()
                    .expectStatusCode(200)
                    .build();

    public static ResponseSpecification responseSpec201 =
                    new ResponseSpecBuilder()
                    .expectStatusCode(201)
                    .build();
}
