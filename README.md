# ConnectionHandler
- 특정 URL에 get / post 수행

## 예제
- src
  ```java
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

  ```
- 결과
  ```
  200
  {"elevators":[{"passengers":[],"id":0,"floor":1,"status":"STOPPED"}],"is_end":false,"token":"PsWgp","timestamp":0}
  PsWgp
  ConnectionHandler.Response [statusCode=200, jsonObject={"calls":[{"start":4,"end":1,"id":0,"timestamp":0},{"start":4,"end":3,"id":1,"timestamp":0}],"elevators":[{"passengers":[],"id":0,"floor":1,"status":"STOPPED"}],"is_end":false,"token":"PsWgp","timestamp":0}]
  ```
