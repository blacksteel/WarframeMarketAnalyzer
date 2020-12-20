package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import enums.ItemType;

public class OutputFileWriter{
	protected static final String OUTPUT_FILE_NAME_SUFFIX_MODS = "MODS";
	protected static final String OUTPUT_FILE_NAME_SUFFIX_PRIMES = "PRIMES";
	
	private static final String OUTPUT_FILE_BASE_PATH = System.getProperty("user.home") + "\\WarframeMarketAnalyser\\";
	private static final String OUTPUT_FILE_BASE_NAME = "WFMarketData";
	private static final String OUTPUT_FILE_TYPE_SUFFIX = ".csv";

	private static final String SHARED_OUTPUT_FILE_HEADER = "Name,48HrAvg,48HrLow,48HrHigh,48HrNumSales,90DayAvg,90DayLow,90DayHigh,90DayNumSales";
	
	private final String timestamp;
	
	protected OutputFileWriter(long launchTime) {
		(new File(OUTPUT_FILE_BASE_PATH)).mkdirs();
		timestamp = "" + launchTime;
	}
	
	protected void writeOutput(List<String> output, ItemType itemType, String headerSuffix) throws IOException{
		
		PrintWriter outputWriter = getOutputWriter(itemType);
		
		try{
			outputWriter.println(SHARED_OUTPUT_FILE_HEADER + (headerSuffix.trim().isEmpty() ? "" : ("," + headerSuffix)));
			
			for(String string: output) {
				outputWriter.println(string);
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
}
