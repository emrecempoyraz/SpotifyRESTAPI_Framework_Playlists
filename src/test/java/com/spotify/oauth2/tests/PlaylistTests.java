package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.spotify.oauth2.utils.FakerUtils.generateDescription;
import static com.spotify.oauth2.utils.FakerUtils.generateName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests extends BaseTest {

    @Test(description = "Create Playlist on Spotify",priority = 1)
    public void ShouldBeAbleToCreateAPlaylist () {

        Playlist requestPlaylist = playlistBuilder(generateName(),generateDescription(),false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(),StatusCode.CODE_201);
        assertPlaylistEqual(response.as(Playlist.class),requestPlaylist);

    }

    @Test(description = "Get Playlist from Spotify",priority = 2)
    public void ShouldBeAbleToGetAPlaylist () {

        Playlist requestPlaylist = playlistBuilder("Updated Playlist Name","Updated playlist description",true);
        Response response = PlaylistApi.get(DataLoader.getInstance().getPlaylistId());
        assertStatusCode(response.statusCode(),StatusCode.CODE_200);
        assertPlaylistEqual(response.as(Playlist.class),requestPlaylist);

    }

    @Test(description = "Update Playlist on Spotify",priority = 3)
    public void ShouldBeAbleToUpdateAPlaylist () {

        Playlist requestPlaylist = playlistBuilder(generateName(),generateDescription(),false);
        Response response = PlaylistApi.update(DataLoader.getInstance().getUpdatePlaylistId(), requestPlaylist);
        assertThat(response.getStatusCode(),equalTo(StatusCode.CODE_200.code));

    }

    @Test(description = "Try to create Playlist without sending name value on Spotify",priority = 4)
    public void ShouldNotBeAbleToCreateAPlaylistWithoutName () {

        Playlist requestPlaylist = playlistBuilder("",generateDescription(),false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertThat(response.getStatusCode(),equalTo(StatusCode.CODE_400.code));
        assertError(response.as(Error.class),StatusCode.CODE_400);
    }

    @Test(description = "Try to create Playlist with expired token on Spotify",priority = 5)
    public void ShouldNotBeAbleToCreateAPlaylistWithExpiredToken () {

        String invalid_token = "12345";
        Playlist requestPlaylist = playlistBuilder(generateName(),generateDescription(),false);
        Response response = PlaylistApi.postInvalidToken(invalid_token,requestPlaylist);
        assertStatusCode(response.getStatusCode(),StatusCode.CODE_401);
        assertError(response.as(Error.class),StatusCode.CODE_401);
    }

    public Playlist playlistBuilder(String name, String description, boolean _public) {
        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setDescription(description);
        playlist.set_public(_public);
        return playlist;
    }

    public void assertPlaylistEqual (Playlist responsePlaylist, Playlist requestPlaylist) {
        assertThat(responsePlaylist.getName(),equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(),equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(),equalTo(requestPlaylist.get_public()));
    }

    public void assertStatusCode(int actualStatusCode, StatusCode statusCode){
        assertThat(actualStatusCode,equalTo(statusCode.code));
    }

    public void assertError(Error responseErr,StatusCode statusCode){
        assertThat(responseErr.getError().getStatus(),equalTo(statusCode.code));
        assertThat(responseErr.getError().getMessage(),equalTo(statusCode.message));
    }
}
