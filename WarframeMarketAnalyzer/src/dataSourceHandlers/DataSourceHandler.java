package dataSourceHandlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

abstract class DataSourceHandler{
	protected final String baseUrl;
	
	protected DataSourceHandler(String baseUrl){
		this.baseUrl = baseUrl;
	}
	
	protected String getFromDataSource(String urlSuffix, String[] propKeys, String[] propVals) throws IOException{
		return getFromServer(baseUrl + urlSuffix, propKeys, propVals);
	}
	
	protected static String getFromServer(String urlString, String[] propKeys, String[] propVals) throws IOException{
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
