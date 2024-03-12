package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.Routes.PLAYLISTS;
import static com.spotify.oauth2.api.Routes.USERS;
import static com.spotify.oauth2.api.TokenManager.getToken;



public class PlaylistApi {

    public static Response post(Playlist requestPlaylist) {
       return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS, getToken(), requestPlaylist);
    }

    public static Response postInvalidToken(String token,Playlist requestPlaylist) {
        return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS , token, requestPlaylist);

    }

    public static Response get(String playlistId) {
        return RestResource.get(PLAYLISTS + "/" + playlistId, getToken());
    }

    public static Response update(String playlistId ,Playlist requestPlayList) {
        return RestResource.update(PLAYLISTS + "/" + playlistId, getToken(), requestPlayList);

    }
}
