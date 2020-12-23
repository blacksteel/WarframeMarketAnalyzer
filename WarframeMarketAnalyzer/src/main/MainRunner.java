package main;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.google.gson.Gson;


public class MainRunner {
	private static Gson gson = null;

	private JFrame frame;
	private JPanel contentPanel;
	private OptionPanel optionPanel;

	public static void main(String[] args) throws IOException, InterruptedException{		
		new MainRunner();
	}

	private MainRunner() throws IOException{
		frame = new JFrame("WarframeMarketAnalyzer");
		contentPanel = new JPanel();
		optionPanel = new OptionPanel(contentPanel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new GridLayout(1, 2, 5, 5));
		contentPanel.add(optionPanel);

		frame.getContentPane().add(contentPanel);

//		frame.setSize(220, 90);
		frame.pack();

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2 - frame.getSize().width/2, dim.height/2 - frame.getSize().height/2);

		frame.setVisible(true);
	}

	public static Gson getGson(){
		if(gson == null){
			gson = new Gson();
		}

		return gson;
	}
}