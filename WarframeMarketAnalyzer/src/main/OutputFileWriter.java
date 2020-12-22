package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import enums.ItemType;
import items.WarframeItem;
import utils.TokenList;

public class OutputFileWriter{
	protected static final String OUTPUT_FILE_NAME_SUFFIX_MODS = "MODS";
	protected static final String OUTPUT_FILE_NAME_SUFFIX_PRIMES = "PRIMES";
	
	private static final String OUTPUT_FILE_BASE_PATH = System.getProperty("user.home") + "\\WarframeMarketAnalyser\\";
	private static final String OUTPUT_FILE_BASE_NAME = "WFMarketData";
	private static final String OUTPUT_FILE_TYPE_SUFFIX = ".csv";

	private static final String SHARED_OUTPUT_FILE_HEADER = "Name";
	
	private final String timestamp;
	
	protected OutputFileWriter(long launchTime){
		(new File(OUTPUT_FILE_BASE_PATH)).mkdirs();
		timestamp = "" + launchTime;
	}
	
	protected void writeOutput(List<? extends WarframeItem> items, ItemType itemType) throws IOException,
	IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		PrintWriter outputWriter = getOutputWriter(itemType);
		
		try{
			TokenList outputTokens = new TokenList();
			String tradeStatsHeader48Hrs = itemType.getTradeStatsHeader48Hrs().trim();
			String tradeStatsHeader90Days = itemType.getTradeStatsHeader90Days().trim();
			String headerSuffix = itemType.getHeaderSuffix().trim();
			
			outputTokens.add(SHARED_OUTPUT_FILE_HEADER);
			if(tradeStatsHeader48Hrs.length() > 0) outputTokens.add(tradeStatsHeader48Hrs);
			if(tradeStatsHeader90Days.length() > 0) outputTokens.add(tradeStatsHeader90Days);
			if(headerSuffix.length() > 0) outputTokens.add(headerSuffix);
			
			outputWriter.println(outputTokens.toCSV());
			
			for(WarframeItem item: items) {
				String outputLine = buildOutputLine(item);
				outputWriter.println(outputLine);
				
				if(Config.WRITE_DEBUG_INFO_TO_CONSOLE) {
					System.out.println(outputLine);
				}
			}
		}
		finally{
			outputWriter.close();
		}
	}
	
	private PrintWriter getOutputWriter(ItemType itemType) throws IOException{
		File modsOutputCSVFile = new File(getFinalOutputFileName(itemType));
		modsOutputCSVFile.createNewFile();
		return new PrintWriter(new FileWriter(modsOutputCSVFile));
	}
	
	private String getFinalOutputFileName(ItemType itemType){
		return OUTPUT_FILE_BASE_PATH +
				OUTPUT_FILE_BASE_NAME +
				"_" +
				timestamp +
				"_" +
				itemType.outputFileNameSuffix + OUTPUT_FILE_TYPE_SUFFIX;
	}
	
	protected static String getOutputPath(){
		return OUTPUT_FILE_BASE_PATH.replace('\\', '/').replace('/', '\\');
	}
	
	private static String buildOutputLine(WarframeItem item){
		TokenList outputTokens = new TokenList();
		
		outputTokens.add(item.name);

		String tradeStats48Hrs = item.getTradeStats48Hrs().toOutputString().trim();
		String tradeStats90Days = item.getTradeStats90Days().toOutputString().trim();
		String dataSuffix = item.getDataSuffix().trim();
		
		if(tradeStats48Hrs.length() > 0) outputTokens.add(tradeStats48Hrs);
		if(tradeStats90Days.length() > 0) outputTokens.add(tradeStats90Days);
		if(dataSuffix.length() > 0) outputTokens.add(dataSuffix);
		
		return outputTokens.toCSV();
	}
}