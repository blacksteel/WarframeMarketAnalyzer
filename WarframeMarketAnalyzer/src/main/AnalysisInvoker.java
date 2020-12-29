package main;

import static utils.MiscUtils.getDate;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import dataSourceHandlers.WarframeMarketHandler;
import dataSourceHandlers.WarframeStatusHandler;
import dataSourceHandlers.WarframeWikiHandler;
import enums.ItemType;
import enums.fields.ModFieldEnum;
import enums.fields.PrimeFieldEnum;
import enums.fields.RelicFieldEnum;
import gui.IOptionProvider;
import items.Mod;
import items.PrimePart;
import items.VoidRelic;
import main.results.AnalysisResults;

public class AnalysisInvoker {
	private long launchTime;

	public void invokeAnalysis(IOptionProvider optionProvider) {
		try {
	
			launchTime = System.currentTimeMillis();
	
			System.out.println("Starting at " + getDate(launchTime));
	
			new SwingWorker<Void, Void>(){
				@Override
				public Void doInBackground() throws IOException, InterruptedException, IllegalAccessException,
				IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
					try{
						runAnalyzer(optionProvider);
					}
					catch(Exception ex){
						ex.printStackTrace();
	
						JOptionPane.showMessageDialog(
								null, "Something went horribly wrong =(", "Well crap...", JOptionPane.ERROR_MESSAGE);
						System.exit(1);
					}
	
					return null;
				}
	
				@Override
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
		catch(Exception ex){
			ex.printStackTrace();
	
			JOptionPane.showMessageDialog(
					null, "Something went horribly wrong =(", "Well crap...", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	private void runAnalyzer(IOptionProvider optionProvider) throws IOException, InterruptedException, IllegalAccessException,
	IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		WarframeStatusHandler statusHandler = new WarframeStatusHandler();
		WarframeMarketHandler marketHandler = new WarframeMarketHandler();
		WarframeWikiHandler wikiHandler = new WarframeWikiHandler();
		OutputFileWriter outputWriter = new OutputFileWriter(launchTime);

		AnalysisResults results = new AnalysisResults(optionProvider);
		
		if(optionProvider.processMods()){
			List<Mod> tradableModsList = statusHandler.handleMods();
			marketHandler.processItems(tradableModsList);
			outputWriter.<ModFieldEnum>writeOutput(tradableModsList, results.getTypeResults(ItemType.MOD));
		}

		if(optionProvider.processPrimes()){
			List<PrimePart> primePartsList = statusHandler.handlePrimes();
			marketHandler.processItems(primePartsList);
			outputWriter.<PrimeFieldEnum>writeOutput(primePartsList, results.getTypeResults(ItemType.PRIME_PART));

			if(optionProvider.processRelics()) {
				List<VoidRelic> relicsList = wikiHandler.handleVoidRelics(primePartsList);
				marketHandler.processItems(relicsList);
				outputWriter.<RelicFieldEnum>writeOutput(relicsList, results.getTypeResults(ItemType.VOID_RELIC));
			}
		}
	}
}
