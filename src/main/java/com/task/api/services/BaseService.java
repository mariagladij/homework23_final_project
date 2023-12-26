package com.task.api.services;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseService {
    public static final String BASE_URI = "https://bookcart.azurewebsites.net/";

    protected RequestSpecification getRequestSpecification() {
        return RestAssured.given().baseUri(BASE_URI).contentType(ContentType.JSON).accept(ContentType.JSON).log().uri();
    }
}
