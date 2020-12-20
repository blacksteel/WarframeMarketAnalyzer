package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MiscUtils {
	public static String padString(String string, int desiredLength) {
		for(int i = string.length(); i < desiredLength; ++i) {
			string += ' ';
		}

		return string;
	}

	public static String evenlyPadString(String string, int desiredLength){
		int stringLength = string.length();
		int paddingAmount = desiredLength - stringLength;
		int prePad = paddingAmount/2;
		int postPad = paddingAmount/2;
		if(paddingAmount%2 == 1){
			++postPad;
		}

		for(int i = 0; i < prePad; ++i) {
			string = ' ' + string;
		}

		for(int i = 0; i < postPad; ++i) {
			string += ' ';
		}

		return string;
	}

	public static double trimTo3DecimalPlaces(double input) {
		input *= 1000;
		input = Math.floor(input);
		return input/1000;
	}
	
	public static String getFromServer(String urlString, String[] propKeys, String[] propVals) throws IOException{
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();

		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod("GET");

		for(int i = 0; i < propKeys.length; ++i){
			conn.setRequestProperty(propKeys[i], propVals[i]);
		}

		conn.setConnectTimeout(5000);
		conn.setReadTimeout(5000);

		conn.connect();

		if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String payload = inputStream.readLine();
			inputStream.close();
			return payload;
		}

		return null;
	}
}
