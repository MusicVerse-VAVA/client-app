package com.musicverse.client;

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
	
	public boolean registerUser(String email, String username, String password) {
		val payload = new ObjectNode();
		payload.set("email", email);
		payload.set("username", username);
		payload.set("password", password);
		return queryServerJson("register", payload, (code, response) -> response.getString("status").equals("ok"));
	}
	
	public JsonNode authenticate(String email, String password) {
		val payload = new ObjectNode();
		payload.set("email", email);
		payload.set("password", password);
		return queryServerJson("auth", payload, (code, response) -> {
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
		return queryServerJson("playlists", payload, (code, response) -> {
			if (response.getString("status").equals("ok")) {
				return response.get("playlists");
			} else {
				return null;
			}
		});
	}

	private <R> R queryServerJson(String url, JsonNode request, ServerQueryCallback<JsonNode, R> callback) {
		return queryServerBinary(url, request, (code, bin) -> {
			val json = JsonNode.parse(new String(bin));
			return callback.processResponse(code, json);
		});
	}

	@SneakyThrows
	private <R> R queryServerBinary(String url, JsonNode request, ServerQueryCallback<byte[], R> callback) {
		val requestURI = String.format("%s:%d/%s", hostname, port, url);
		val conn = (HttpURLConnection) new URL(requestURI).openConnection();
		conn.setRequestProperty("User-Agent", "musicverse-client");
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		val os = conn.getOutputStream();
		os.write(request.toString().getBytes(StandardCharsets.UTF_8));
		os.flush();
		os.close();
		val content = conn.getInputStream().readAllBytes();
		return callback.processResponse(conn.getResponseCode(), content);
	}

	public interface ServerQueryCallback<T, R> {
		R processResponse(int responseCode, T responseBody);
	}
	

}
