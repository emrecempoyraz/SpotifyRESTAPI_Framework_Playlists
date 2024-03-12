package com.spotify.oauth2.api;


import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oauth2.api.Routes.API;
import static com.spotify.oauth2.api.Routes.TOKEN;
import static com.spotify.oauth2.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestResource {


    public static Response post(String path,String token,Object requestPlaylist) {

        return given(getRequestSpec())
                .body(requestPlaylist)
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .when()
                .post(path)
                .then()
                .spec(getResponseSpec())
                .extract()
                .response();
    }

        /*
        form params kullanmamın sebebi requestin formatının
        x-www-form-urlencoded olması.Map parametrelerini form şeklinde
        gönderdim.
         */
    public static Response postAccount (HashMap<String,String> formParams) {
        return given(getAccountRequestSpec())
                .formParams(formParams) // x-form-urlencoded
                .when()
                .post(API + TOKEN)
                .then()
                .spec(getResponseSpec())
                .extract()
                .response();
    }


    public static Response get(String path,String token) {
        return given(getRequestSpec())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .when()
                .get(path)
                .then()
                .spec(getResponseSpec())
                .extract()
                .response();

    }

    public static Response update(String path,String token,Object requestPlayList) {
        return given(getRequestSpec())
                .body(requestPlayList)
                .auth().oauth2(token)
                .when()
                .put(path)
                .then()
                .spec(getResponseSpec())
                .extract()
                .response();
    }
}
