package com.spotify.oauth2.api;

import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.response.Response;
import java.time.Instant;
import java.util.HashMap;



// Token check and refresh
public class TokenManager {

    private static String access_token;
    private static Instant expiry_time;

    public synchronized static String getToken() {
       try {
           if (access_token == null || Instant.now().isAfter(expiry_time)){
               System.out.println("Renewing Token ...");
               Response response = renewToken();
               access_token = response.path("access_token");
               int expiryDurationInSeconds = response.path("expires_in");
               expiry_time = Instant.now().plusSeconds(expiryDurationInSeconds);
           }else {
               System.out.println("Token is available");
           }
       }catch (Exception e) {
           throw new RuntimeException("ATTENTION! Failed to get token");
       }
       return access_token;
    }


    private static Response renewToken() {
        HashMap<String,String> formParams = new HashMap<>();
        formParams.put("client_id", ConfigLoader.getInstance().getClientId());
        formParams.put("client_secret",ConfigLoader.getInstance().getClientSecret());
        formParams.put("refresh_token",ConfigLoader.getInstance().getRefreshToken());
        formParams.put("grant_type",ConfigLoader.getInstance().getGrantType());

        /*
        form params kullanmamın sebebi requestin formatının
        x-www-form-urlencoded olması.Map parametrelerini form şeklinde
        gönderdim.
         */

        Response response = RestResource.postAccount(formParams);

        if (response.statusCode() != 200) {
            throw new RuntimeException("ATTENTION! Renew Token failed");
        }
        return response;
    }


}
