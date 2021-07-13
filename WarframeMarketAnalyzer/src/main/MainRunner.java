package main;

import java.io.IOException;

import com.google.gson.Gson;

import gui.MainPanel;


public class MainRunner {
	private static Gson gson = null;

	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException{
		AnalysisInvoker invoker = new AnalysisInvoker();
		MainPanel main = new MainPanel(invoker);
		main.setVisible(true);
	}

	public static Gson getGson(){
		if(gson == null){
			gson = new Gson();
		}

		return gson;
	}
}