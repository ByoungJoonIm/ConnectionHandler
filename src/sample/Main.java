package sample;

import handler.ConnectionHandler;

public class Main {
	public static void main(String args[]){
		ConnectionHandler connectionHandler = new ConnectionHandler("http://localhost:8000");
		
		ConnectionHandler.Response startResponse = connectionHandler.post("/start/test/0/1", null, new String[0]);
		
		System.out.println(startResponse.statusCode);
		System.out.println(startResponse.jsonObject);
		String token = (String) startResponse.jsonObject.get("token");
		System.out.println(token);
		
		ConnectionHandler.Response oncallsResponse = connectionHandler.get("/oncalls",
				new String[] {"X-Auth-Token", token});
		
		System.out.println(oncallsResponse);
	}
}
