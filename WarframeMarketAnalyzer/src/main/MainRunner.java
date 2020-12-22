package main;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import com.google.gson.Gson;

import dataSourceHandlers.WarframeStatusHandler;
import dataSourceHandlers.WarframeWikiHandler;
import dataSourceHandlers.WarframeMarketHandler;
import enums.ItemType;
import items.Mod;
import items.PrimePart;
import items.VoidRelic;
import static utils.MiscUtils.getDate;

public class MainRunner implements ActionListener{
	private static Gson gson = null;

	private long launchTime;

	private JFrame frame;
	private JPanel contentPanel;
	private JButton runButton;
	private JButton exitButton;
	private JButton cancelButton;
	private JProgressBar progressBar;

	public static void main(String[] args) throws IOException, InterruptedException{		
		new MainRunner();
	}

	private MainRunner() throws IOException{
		frame = new JFrame("WarframeMarketAnalyzer");
		contentPanel = new JPanel();
		runButton = new JButton("Run Me!");
		exitButton = new JButton("No Thanks");
		cancelButton = new JButton("Oh God Make It Stop");
		progressBar = new JProgressBar();

		runButton.addActionListener(this);
		exitButton.addActionListener(this);
		cancelButton.addActionListener(this);

		progressBar.setIndeterminate(true);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new GridLayout(1, 2, 5, 5));
		contentPanel.add(runButton);
		contentPanel.add(exitButton);

		frame.getContentPane().add(contentPanel);

		frame.setSize(220, 90);
		//frame.pack();

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2 - frame.getSize().width/2, dim.height/2 - frame.getSize().height/2);

		frame.setVisible(true);
	}

	private void runAnalyzer() throws IOException, InterruptedException, IllegalAccessException,
	IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		WarframeStatusHandler statusHandler = new WarframeStatusHandler();
		WarframeMarketHandler marketHandler = new WarframeMarketHandler();
		WarframeWikiHandler wikiHandler = new WarframeWikiHandler();
		OutputFileWriter outputWriter = new OutputFileWriter(launchTime);

		if(Config.PROCESS_MODS){
			List<Mod> tradableModsList = statusHandler.handleMods();
			marketHandler.processItems(tradableModsList);
			outputWriter.writeOutput(tradableModsList, ItemType.MOD);
		}

		if(Config.PROCESS_PRIMES){
			List<PrimePart> primePartsList = statusHandler.handlePrimes();
			marketHandler.processItems(primePartsList);
			outputWriter.writeOutput(primePartsList, ItemType.PRIME_PART);

			if(Config.PROCESS_RELICS) {
				List<VoidRelic> relicsList = wikiHandler.handleVoidRelics(primePartsList);
				marketHandler.processItems(relicsList);
				outputWriter.writeOutput(relicsList, ItemType.VOID_RELIC);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e){
		try{
			if(e.getSource() == runButton){
				contentPanel.removeAll();
				contentPanel.setLayout(new GridLayout(2, 1, 5, 5));

				contentPanel.add(progressBar);
				contentPanel.add(cancelButton);

				contentPanel.revalidate();
				contentPanel.repaint();

				launchTime = System.currentTimeMillis();

				System.out.println("Starting at " + getDate(launchTime));

				new SwingWorker<Void, Void>(){
					public Void doInBackground() throws IOException, InterruptedException, IllegalAccessException,
					IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
						try{
							runAnalyzer();
						}
						catch(Exception ex){
							ex.printStackTrace();

							JOptionPane.showMessageDialog(
									null, "Something went horribly wrong =(", "Well crap...", JOptionPane.ERROR_MESSAGE);
							System.exit(1);
						}

						return null;
					}

					public void done(){
							long endTime = System.currentTimeMillis();
							long runTime = (endTime - launchTime);

								System.out.println("Ending at " + getDate(endTime) + ". Total runtime was " + (runTime/60000) + " minutes.");

						JOptionPane.showMessageDialog(
								null,
								"Success! Your output files can be found in the folder " + OutputFileWriter.getOutputPath(),
								"Huzzah And Such!",
								JOptionPane.INFORMATION_MESSAGE);

						System.exit(0);
					}
				}.execute();
			}
			if(e.getSource() == exitButton || e.getSource() == cancelButton){
				System.exit(0);
			}
		}
		catch(Exception ex){
			ex.printStackTrace();

			JOptionPane.showMessageDialog(
					null, "Something went horribly wrong =(", "Well crap...", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	public static Gson getGson(){
		if(gson == null){
			gson = new Gson();
		}

		return gson;
	}
}