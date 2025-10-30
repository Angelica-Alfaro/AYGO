package edu.escuelaing.arep.dockerdemo.loadbalancer.connection;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LogServiceConnection {
	
	private static final String USER_AGENT = "Mozilla/5.0";
	
	private static final LogServiceConnection _instance = new LogServiceConnection();

	public static LogServiceConnection getInstance() {
		return _instance;
	}

	public String sendGET(String get_url) throws IOException {
		String finalResponse = "None";
		URL obj = new URL(get_url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			finalResponse = response.toString();
		} 
		else {
			System.out.println("GET request not worked");
		}
		return finalResponse;

	}

	public String sendPOST(String post_url, String string) throws IOException {
		String finalResponse = "None";
		URL obj = new URL(post_url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);

		// For POST only - START
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write(string.getBytes());
		os.flush();
		os.close();
		// For POST only - END

		int responseCode = con.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			finalResponse = response.toString();
		} 
		else {
			System.out.println("POST request not worked");
		}
		return finalResponse;
	}

}
