package com.spotify.oauth2.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.spotify.oauth2.api.Routes.BASE_PATH;

public class SpecBuilder implements IUrls {

    public static RequestSpecification getRequestSpec () {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath(BASE_PATH)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }


    // For renew token
    public static RequestSpecification getAccountRequestSpec () {
        return new RequestSpecBuilder().
                setBaseUri(ACCOUNT_BASE_URL)
                .setContentType(ContentType.URLENC) // x-form-urlencoded
                .log(LogDetail.ALL)
                .build();
    }


    public static ResponseSpecification getResponseSpec () {
        return new ResponseSpecBuilder().
                log(LogDetail.ALL)
                .build();
    }
}
