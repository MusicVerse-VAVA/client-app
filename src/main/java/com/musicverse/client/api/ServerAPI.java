package com.musicverse.client.api;

import com.falsepattern.json.node.JsonNode;
import com.falsepattern.json.node.ObjectNode;
import lombok.SneakyThrows;
import lombok.val;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ServerAPI {
	private static ServerAPI instance;

	public static ServerAPI getInstance() {
		if (instance == null) {
			instance = new ServerAPI();
		}
		return instance;
	}

	private String hostname;
	private int port;

	private ServerAPI() {
		this.connect();
	}
	
	public void connect() {
		//TODO load this from a config file. Using localhost for now.
		hostname = "http://127.0.0.1";
		port = 10008;
	}
	
	public boolean registerUser(String email, String username, String password, int role) {
		val payload = new ObjectNode();
		payload.set("email", email);
		payload.set("username", username);
		payload.set("password", password);
		payload.set("access_level", role);
		if (queryServerJson("register", Method.POST, payload, (code, response) -> response.getString("status").equals("ok"))){
			return createArtist(email);
		}
		return false;
	}

	public JsonNode loadArtistByUser(int userId){
		val payload = new ObjectNode();
		payload.set("user_id", userId);
		return queryServerJson("loadArtistByUser", Method.POST, payload, (code, response) -> {
			if (response.getString("status").equals("ok")){
				return response.get("artist");
			}else{
				return null;
			}
		});
	}
	
	public JsonNode authenticate(String email, String password) {
		val payload = new ObjectNode();
		payload.set("email", email);
		payload.set("password", password);
		return queryServerJson("auth", Method.POST, payload, (code, response) -> {
			if (response.getString("status").equals("ok")) {
				return response.get("user");
			} else {
				return null;
			}
		});
	}

	public JsonNode getUserPlaylists(int id){
		val payload = new ObjectNode();
		payload.set("id", id);
		return queryServerJson("playlists", Method.POST, payload, (code, response) -> {
			if (response.getString("status").equals("ok")) {
				return response.get("playlists");
			} else {
				return null;
			}
		});
	}

	public JsonNode getPublicPlaylists(){
		val payload = new ObjectNode();
		return queryServerJson("allplaylists", Method.POST, payload, (code, response) -> {
			if (response.getString("status").equals("ok")) {
				return response.get("playlists");
			} else {
				return null;
			}
		});
	}

	public JsonNode getGenres(){
		val payload = new ObjectNode();
		return queryServerJson("genres", Method.GET, payload, (code, response) -> {
			if (response.getString("status").equals("ok")) {
				return response.get("genres");
			} else {
				return null;
			}
		});
	}

	public JsonNode getArtistsByGenre(int genre_id){
		if(genre_id < 0)
			return null;
		val payload = new ObjectNode();
		payload.set("genre_id",genre_id);
		return queryServerJson("artistsByGenre", Method.POST, payload, (code, response) -> {
			if (response.getString("status").equals("ok")) {
				System.out.println(response.get("artists"));
				return response.get("artists");
			} else {
				return null;
			}
		});
	}

	public JsonNode songsByPlaylist(int playlist_id){
		if(playlist_id < 0)
			return null;
		val payload = new ObjectNode();
		payload.set("playlist_id",playlist_id);
		return queryServerJson("songsByPlaylist", Method.POST, payload, (code, response) -> {
			if (response.getString("status").equals("ok")) {
				System.out.println(response.get("songs"));
				return response.get("songs");
			} else {
				return null;
			}
		});
	}

	public JsonNode songsByAlbum(int album_id){
		if(album_id < 0)
			return null;
		val payload = new ObjectNode();
		payload.set("album_id",album_id);
		return queryServerJson("songsByAlbum", Method.POST, payload, (code, response) -> {
			if (response.getString("status").equals("ok")) {
				System.out.println(response.get("songs"));
				return response.get("songs");
			} else {
				return null;
			}
		});
	}

	public boolean editArtist(int id, String nameField, String descriptionField, String genre){
		val payload = new ObjectNode();
		payload.set("id", id);
		payload.set("name", nameField);
		payload.set("description", descriptionField);
		payload.set("genre", genre);
		System.out.println(id + nameField + descriptionField + genre);
		return queryServerJson("editArtist", Method.POST, payload,
				(code, response) -> response.getString("status").equals("ok"));
	}

	public boolean createArtist(String user_email){
		val payload = new ObjectNode();
		payload.set("user_email", user_email);
		return queryServerJson("create_artist", Method.POST, payload, (code, response) -> response.getString("status").equals("ok"));
	}



	private <R> R queryServerJson(String url, Method method, JsonNode request, ServerQueryCallback<JsonNode, R> callback) {
		return queryServerBinary(url, method, request, (code, bin) -> {
			val json = JsonNode.parse(new String(bin));
			return callback.processResponse(code, json);
		});
	}

	@SneakyThrows
	private <R> R queryServerBinary(String url, Method method, JsonNode request, ServerQueryCallback<byte[], R> callback) {
		val requestURI = String.format("%s:%d/%s", hostname, port, url);
		val conn = (HttpURLConnection) new URL(requestURI).openConnection();
		conn.setRequestProperty("User-Agent", "musicverse-client");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod(method.name());
		conn.connect();
		val os = conn.getOutputStream();
		os.write(request.toString().getBytes(StandardCharsets.UTF_8));
		os.flush();
		os.close();
		val responseCode = conn.getResponseCode();
		if (responseCode != 200) {
			throw new IllegalStateException("ERROR: Server returned error code " + responseCode + ": " + conn.getResponseMessage());
		}
		val content = conn.getInputStream().readAllBytes();
		return callback.processResponse(conn.getResponseCode(), content);
	}

	public interface ServerQueryCallback<T, R> {
		R processResponse(int responseCode, T responseBody);
	}

	public enum Method {
		GET, POST
	}

}
