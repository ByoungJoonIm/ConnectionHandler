package handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class ConnectionHandler {
	public static class Response{
		public int statusCode;
		public JSONObject jsonObject;
		
		public Response(int statusCode, JSONObject jsonObject){
			this.statusCode = statusCode;
			this.jsonObject = jsonObject;
		}

		@Override
		public String toString() {
			return "ConnectionHandler.Response [statusCode=" + statusCode + ", jsonObject=" + jsonObject + "]";
		}
		
		
	}
	
	private static String host = null;
	
	public ConnectionHandler(String host){
		if(ConnectionHandler.host == null)
			ConnectionHandler.host = host;
	}
	
	public Response get(String actionUrl, String args[]){
		HttpURLConnection conn = null;
		JSONObject responseJson = null;
		int statusCode = 0;
		
		try{
			URL url = new URL(host + actionUrl);
			conn = (HttpURLConnection) url.openConnection();
			
			for (int i = 0; i < args.length; i+=2)
				conn.setRequestProperty(args[i], args[i + 1]);
			
			statusCode = conn.getResponseCode();
			
			if(statusCode == 200){
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = "";
				while((line = br.readLine()) != null)
					sb.append(line);
				responseJson = new JSONObject(sb.toString());
				
				br.close();
			} else {
				return new Response(statusCode, null);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println("JSON Format response°¡ ¾Æ´Ô");
			e.printStackTrace();
		}
		
		return new Response(statusCode, responseJson);
	}
	
	public Response post(String actionUrl, JSONObject sendingData, String args[]){
		HttpURLConnection conn = null;
		JSONObject responseJson = null;
		int statusCode = 0;
		
		try{
			URL url = new URL(host + actionUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			
			for (int i = 0; i < args.length; i+=2)
				conn.setRequestProperty(args[i], args[i + 1]);
			
			if(sendingData != null){
				conn.setDoOutput(true);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
				bw.write(sendingData.toString());
				bw.close();	
			}
			
			statusCode = conn.getResponseCode();
			
			if(statusCode == 200){
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = "";
				while((line = br.readLine()) != null)
					sb.append(line);
				responseJson = new JSONObject(sb.toString());
				
				br.close();
			} else {
				return new Response(statusCode, null);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println("JSON Format response°¡ ¾Æ´Ô");
			e.printStackTrace();
		}
		
		return new Response(statusCode, responseJson);
	}
}
