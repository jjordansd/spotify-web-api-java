package se.michaelthelin.spotify;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import se.michaelthelin.spotify.SpotifyProtos.Artist;
import se.michaelthelin.spotify.SpotifyProtos.Album;
import se.michaelthelin.spotify.SpotifyProtos.AlbumType;
import se.michaelthelin.spotify.SpotifyProtos.Image;
import se.michaelthelin.spotify.SpotifyProtos.SimpleArtist;
import se.michaelthelin.spotify.SpotifyProtos.SimpleAlbum;
import se.michaelthelin.spotify.SpotifyProtos.Track;
import se.michaelthelin.spotify.SpotifyProtos.ExternalId;

import javax.print.attribute.standard.JobName;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

  public static Image newImage(String json) {
    return newImage(JSONObject.fromObject(json));
  }

  private static Image newImage(JSONObject jsonObject) {
    Image.Builder builder = Image.newBuilder();
    builder.setHeight(jsonObject.getInt("height"));
    builder.setImageUrl(jsonObject.getString("image_url"));
    builder.setWidth(jsonObject.getInt("width"));
    return builder.build();
  }

  public static List<Artist> newArtistList(String json) {
    return newArtistList(JSONObject.fromObject(json));
  }

  private static List<Artist> newArtistList(JSONObject jsonObject) {
    List<Artist> returnedArtists = new ArrayList<Artist>();
    JSONArray artistsObject = jsonObject.getJSONArray("artists");
    for (int i = 0; i < artistsObject.size(); i++) {
      returnedArtists.add(newArtist(artistsObject.getJSONObject(i)));
    }
    return returnedArtists;
  }

  public static Artist newArtist(String json) {
    return newArtist(JSONObject.fromObject(json));
  }

  private static Artist newArtist(JSONObject jsonObject) {
    if (jsonObject == null || jsonObject.isNullObject()) {
      return null;
    }
    Artist.Builder artistBuilder = Artist.newBuilder();

    artistBuilder.setApiLink(jsonObject.getString("api_link"));

    JSONArray apiLinkArray = jsonObject.getJSONArray("genres");
    for (int i = 0; i < apiLinkArray.size(); i++) {
      artistBuilder.addGenres(apiLinkArray.getString(i));
    }

    artistBuilder.setId(jsonObject.getString("id"));

    Artist.Images.Builder artistImagesBuilder = Artist.Images.newBuilder();
    JSONObject imagesJsonObject = jsonObject.getJSONObject("images");

    JSONObject smallImageJsonObject = imagesJsonObject.getJSONObject("SMALL");
    Image smallImage = newImage(smallImageJsonObject);
    artistImagesBuilder.setSMALL(smallImage);

    JSONObject mediumImageJsonObject = imagesJsonObject.getJSONObject("MEDIUM");
    Image mediumImage = newImage(mediumImageJsonObject);
    artistImagesBuilder.setMEDIUM(mediumImage);

    JSONObject largeImageJsonObject = imagesJsonObject.getJSONObject("LARGE");
    Image largeImage = newImage(largeImageJsonObject);
    artistImagesBuilder.setLARGE(largeImage);

    JSONObject xLargeImageJsonObject = imagesJsonObject.getJSONObject("XLARGE");
    Image xLargeImage = newImage(xLargeImageJsonObject);
    artistImagesBuilder.setXLARGE(xLargeImage);

    artistBuilder.setImages(artistImagesBuilder.build());

    artistBuilder.setLink(jsonObject.getString("link"));
    artistBuilder.setName(jsonObject.getString("name"));
    artistBuilder.setPopularity(jsonObject.getInt("popularity"));
    artistBuilder.setSpotifyUri(jsonObject.getString("spotify_uri"));
    artistBuilder.setType(jsonObject.getString("type"));

    return artistBuilder.build();
  }

  public static Album newAlbum(String json) {
    return newAlbum(JSONObject.fromObject(json));
  }

  private static Album newAlbum(JSONObject jsonObject) {
    if (jsonObject == null || jsonObject.isNullObject()) {
      return null;
    }
    Album.Builder albumBuilder = Album.newBuilder();

    albumBuilder.setAlbumType(AlbumType.valueOf(jsonObject.getString("album_type")));
    albumBuilder.setApiLink(jsonObject.getString("api_link"));

    JSONArray artistsJsonArray = jsonObject.getJSONArray("artists");
    for (int i = 0; i < artistsJsonArray.size(); i++) {
      albumBuilder.addArtists(newSimpleArtist(artistsJsonArray.getJSONObject(i)));
    }

    albumBuilder.setId(jsonObject.getString("id"));
    albumBuilder.setLink(jsonObject.getString("link"));
    albumBuilder.setName(jsonObject.getString("name"));
    albumBuilder.setPopularity(jsonObject.getInt("popularity"));
    albumBuilder.setReleaseYear(jsonObject.getInt("release_year"));
    albumBuilder.setSpotifyUri(jsonObject.getString("spotify_uri"));
    albumBuilder.setType(jsonObject.getString("type"));

    return albumBuilder.build();
  }

  public static SimpleAlbum newSimpleALbum(String json) {
    return newSimpleAlbum(JSONObject.fromObject(json));
  }

  private static SimpleAlbum newSimpleAlbum(JSONObject jsonObject) {
    if (jsonObject == null || jsonObject.isNullObject()) {
      return null;
    }
    SimpleAlbum.Builder albumBuilder = SimpleAlbum.newBuilder();

    albumBuilder.setApiLink(jsonObject.getString("api_link"));
    albumBuilder.setId(jsonObject.getString("id"));
    albumBuilder.setName(jsonObject.getString("name"));
    albumBuilder.setSpotifyUri("spotify_uri");
    albumBuilder.setType("album");

    return albumBuilder.build();
  }

  public static List<Album> newAlbumsList(String json) {
    return newAlbumsList(JSONObject.fromObject(json));
  }

  private static List<Album> newAlbumsList(JSONObject jsonObject) {
    List<Album> returnedAlbums = new ArrayList<Album>();
    JSONArray albumsJsonArray = jsonObject.getJSONArray("albums");
    for (int i = 0; i < albumsJsonArray.size(); i++) {
      returnedAlbums.add(newAlbum(albumsJsonArray.getJSONObject(i)));
    }
    return returnedAlbums;
  }

  public static SimpleArtist newSimpleArtist(String json) {
    return newSimpleArtist(JSONObject.fromObject(json));
  }

  private static SimpleArtist newSimpleArtist(JSONObject jsonObject) {
    if (jsonObject == null || jsonObject.isNullObject()) {
      return null;
    }
    SimpleArtist.Builder artistBuilder = SimpleArtist.newBuilder();

    artistBuilder.setApiLink(jsonObject.getString("api_link"));
    artistBuilder.setId(jsonObject.getString("id"));
    artistBuilder.setName(jsonObject.getString("name"));
    artistBuilder.setSpotifyUri(jsonObject.getString("spotify_uri"));
    artistBuilder.setType(jsonObject.getString("type"));

    return artistBuilder.build();
  }

  private static List<SimpleArtist> newSimpleArtistsList(JSONArray artists) {
   List<SimpleArtist> returnedArtists = new ArrayList<SimpleArtist>();
    for (int i = 0; i < artists.size(); i++) {
      returnedArtists.add(newSimpleArtist(artists.getJSONObject(i)));
    }
    return returnedArtists;
  }

  public static Track newTrack(String json) {
    return newTrack(JSONObject.fromObject(json));
  }

  private static Track newTrack(JSONObject jsonObject) {
    Track.Builder trackBuilder = Track.newBuilder();

    SimpleAlbum album = newSimpleAlbum(jsonObject.getJSONObject("album"));
    trackBuilder.setAlbum(album);

    trackBuilder.setApiLink(jsonObject.getString("api_link"));

    List<SimpleArtist> simpleArtists = newSimpleArtistsList(jsonObject.getJSONArray("artists"));
    trackBuilder.addAllArtists(simpleArtists);

    trackBuilder.addAllAvailableMarkets(jsonObject.getJSONArray("available_markets"));

    trackBuilder.setDiscNumber(jsonObject.getInt("disc_number"));
    trackBuilder.setDurationMs(jsonObject.getInt("duration_ms"));
    trackBuilder.setExplicit(jsonObject.getBoolean("explicit"));
    trackBuilder.addAllExternalIds(newExternalIdList(jsonObject.getJSONArray("external_ids")));
    trackBuilder.setId(jsonObject.getString("id"));
    trackBuilder.setLink(jsonObject.getString("link"));
    trackBuilder.setName(jsonObject.getString("name"));
    trackBuilder.setPopularity(jsonObject.getInt("popularity"));
    trackBuilder.setPreviewUrl(jsonObject.getString("preview_url"));
    trackBuilder.setSpotifyUrl(jsonObject.getString("spotify_uri"));
    trackBuilder.setTrackNumber(jsonObject.getInt("track_number"));
    trackBuilder.setType(jsonObject.getString("type"));

    return trackBuilder.build();
  }

  private static List<ExternalId> newExternalIdList(JSONArray externalIds) {
    List<ExternalId> returnedExternalIds = new ArrayList<ExternalId>();
    for (int i = 0; i < externalIds.size(); i++) {
      returnedExternalIds.add(newExternalId(externalIds.getJSONObject(i)));
    }
    return returnedExternalIds;
  }

  private static ExternalId newExternalId(JSONObject externalId) {
    ExternalId.Builder builder = ExternalId.newBuilder();
    builder.setId(externalId.getString("id"));
    builder.setType(externalId.getString("type"));
    return builder.build();
  }

  public static List<Track> newTracksList(String json) {
    return newTracksList(JSONObject.fromObject(json));
  }

  private static List<Track> newTracksList(JSONObject jsonObject) {
    List<Track> returnedTracks = new ArrayList<Track>();
    JSONArray tracks = jsonObject.getJSONArray("tracks");
    for (int i = 0; i < tracks.size(); i++) {
      returnedTracks.add(newTrack(tracks.getJSONObject(i)));
    }
    return returnedTracks;
  }
}