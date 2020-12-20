package main;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import com.google.gson.Gson;

import enums.ItemType;
import items.Mod;
import items.PrimePart;

public class MainRunner implements ActionListener{	
	private long launchTime;
	private Gson gson;

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

		gson = new Gson();
	}

	private void runAnalyzer() throws IOException, InterruptedException{
		List<Mod> tradableModsList = DataSourceHandler.handleMods(gson);
		List<PrimePart> tradablePrimePartsList = DataSourceHandler.handlePrimes(gson);

		MarketHandler marketHandler = new MarketHandler(gson);
		OutputFileWriter outputWriter = new OutputFileWriter(launchTime);

		List<String> modOutput = marketHandler.processItems(tradableModsList);
		outputWriter.writeOutput(modOutput, ItemType.MOD, tradableModsList.get(0).getHeaderSuffix());

		List<String> primePartsOutput = marketHandler.processItems(tradablePrimePartsList);
		outputWriter.writeOutput(primePartsOutput, ItemType.PRIME_PARTS, tradablePrimePartsList.get(0).getHeaderSuffix());
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

				if(Config.WRITE_DEBUG_INFO_TO_CONSOLE){
					System.out.println("Starting at " + launchTime);
				}

				new SwingWorker<Void, Void>(){
					public Void doInBackground() throws IOException, InterruptedException{
						runAnalyzer();
						return null;
					}

					public void done(){
						if(Config.WRITE_DEBUG_INFO_TO_CONSOLE){
							long endTime = System.currentTimeMillis();
							long runTime = (endTime - launchTime);

							if(Config.WRITE_DEBUG_INFO_TO_CONSOLE){
								System.out.println("Ending at " + endTime + ". Total runtime was " + runTime);
							}
						}

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
}
