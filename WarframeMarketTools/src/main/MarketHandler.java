package main;

import static enums.SharedString.NOT_APPLICABLE;
import static enums.jsonProps.MarketPropName.*;
import static utils.JSONUtils.*;
import static utils.MiscUtils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import items.WarframeItem;

public class MarketHandler {
	private static final String MARKET_BASE_URL = "https://api.warframe.market/v1/items";
	private static final String MARKET_ITEM_LIST_STATS_SUFFIX = "/statistics";
	
	//Warframe Market API only supports 3 requests per second
	private static final long REQUIRED_SLEEP_TIME_BETWEEN_MARKET_REQUESTS = 340;

	private static List<String> tradableItemNamesList = null;
	
	private long lastMarketRequestTime = 0;
	private Gson gson;
	
	protected MarketHandler(Gson gson) throws IOException{
		this.gson = gson;
	}

	protected static List<String> getTradableItemNamesList() throws IOException{
		if(tradableItemNamesList == null) {
			tradableItemNamesList = getTradableItemsListFromMarket();
		}
		
		return tradableItemNamesList;
	}
	
	private static List<String> getTradableItemsListFromMarket() throws IOException{
		List<String> output = new ArrayList<String>();
		
		String marketPayload = getFromServer(MARKET_BASE_URL, new String[]{"platform", "language"}, new String[]{"pc", "en"});
		JsonObject tradableItemsObject = (new Gson()).fromJson(marketPayload.toString(), JsonObject.class);

		JsonArray tradableItemsArray = getJsonArray(getJsonObj(tradableItemsObject, PAYLOAD), ITEMS);
		
		for(Iterator<JsonElement> iter = tradableItemsArray.iterator(); iter.hasNext();){
			output.add(getStrProp(iter.next(), ITEM_NAME));
		}
		
		return output;
	}
	
	protected List<String> processItems(List<? extends WarframeItem> items)
			throws IOException, InterruptedException{
		List<String> outputData = new ArrayList<String>();

		int maxItemNameLength = 0;

		for(WarframeItem item: items){
			int nameLength = item.name.length();
			if(nameLength > maxItemNameLength){
				maxItemNameLength = nameLength;
			}
		}

		for(WarframeItem item: items){
			String marketPayload = getFromServer(
					getFinalMarketURL(item.name), new String[]{"platform", "language"}, new String[]{"pc", "en"});

			lastMarketRequestTime = System.currentTimeMillis();

			if(marketPayload != null){
				JsonObject tradeInfo = gson.fromJson(marketPayload.toString(), JsonObject.class);
				outputData.add(processAllTradeInfo(item, tradeInfo, maxItemNameLength));
			}

			sleepBetweenMarketRequests();
		}
		
		return outputData;
	}

	private String processAllTradeInfo(WarframeItem item, JsonObject allTradeInfoObject, int maxModNameLength)
			throws IOException{
		JsonObject closedTradesStatsObject = getJsonObj(getJsonObj(allTradeInfoObject, PAYLOAD), CLOSED_TRADES_STATS);

		TradeStats stats48Hrs = processTradeData(item, getJsonArray(closedTradesStatsObject, TIME_PERIOD_48HRS));
		TradeStats stats90Days = processTradeData(item, getJsonArray(closedTradesStatsObject, TIME_PERIOD_90DAYS));

		if(Config.WRITE_OUTPUT_TO_CONSOLE){
			dumpToConsole(item.name, maxModNameLength + 3, stats48Hrs, stats90Days);
		}

		if(Config.WRITE_DEBUG_INFO_TO_CONSOLE) {
			System.out.println(buildOutputLine(item, stats48Hrs, stats90Days));
		}
		
		return buildOutputLine(item, stats48Hrs, stats90Days);
	}

	private String buildOutputLine(WarframeItem item, TradeStats stats48Hrs, TradeStats stats90Days){
		String outputLine = item.name + ",";

		if(stats48Hrs.numSales > 0){
			outputLine += stats48Hrs.avgPrice + "," +stats48Hrs.minPrice + "," + stats48Hrs.maxPrice + ",";
		}
		else{
			outputLine += NOT_APPLICABLE.value + "," + NOT_APPLICABLE.value + "," + NOT_APPLICABLE.value + ",";
		}

		outputLine += stats48Hrs.numSales + ",";

		if(stats90Days.numSales > 0) {
			outputLine += stats90Days.avgPrice + "," + stats90Days.minPrice + "," + stats90Days.maxPrice + ",";
		}
		else{
			outputLine += NOT_APPLICABLE.value + "," + NOT_APPLICABLE.value + "," + NOT_APPLICABLE.value + ",";
		}

		outputLine += stats90Days.numSales;

		String dataSuffix = item.getDataSuffix(stats48Hrs.avgPrice, stats90Days.avgPrice);

		if(!dataSuffix.trim().isEmpty()){
			outputLine += "," + dataSuffix;
		}

		return outputLine;
	}

	private TradeStats processTradeData(WarframeItem item, JsonArray tradeDataArray){
		double minPrice = Double.MAX_VALUE;
		double maxPrice = Double.MIN_VALUE;
		double totalValue = 0;
		int numSales = 0;

		for(Iterator<JsonElement> iter = tradeDataArray.iterator(); iter.hasNext();){
			JsonObject saleDataObject = iter.next().getAsJsonObject();

			if(item.isRanked() && getIntProp(saleDataObject, RANK) != item.getRankToPriceCheck()){
				//Sale data for ranked item with a different number of ranks than we want to price check
				//Ignore for now
			}
			else{
				int volume = getIntProp(saleDataObject, NUM_SALES);
				numSales += volume;

				double thisMinPrice = getDblProp(saleDataObject, MIN_PRICE);
				double thisMaxPrice = getDblProp(saleDataObject, MAX_PRICE);
				totalValue += volume*getDblProp(saleDataObject, AVG_PRICE);

				if(thisMinPrice < minPrice) minPrice = thisMinPrice;
				if(thisMaxPrice > maxPrice) maxPrice = thisMaxPrice;
			}
		}

		return new TradeStats(numSales, minPrice, maxPrice, totalValue/numSales);
	}

	@SuppressWarnings("unused")
	private void dumpToConsole(String itemName, int namePaddingLength, TradeStats stats48Hrs, TradeStats stats90Days){
		String outputString = padString(itemName, namePaddingLength);

		boolean noTradeData = (stats48Hrs.numSales == 0 && stats90Days.numSales == 0);

		if(stats48Hrs.numSales > 0){
			outputString += "48 Hr: | ";
			outputString += "Num Sales: " + padString(stats48Hrs.numSales + "", 8);
			outputString += "Avg: " + padString(trimTo3DecimalPlaces(stats48Hrs.avgPrice) + "", 10);
			outputString += "Low: " + padString(trimTo3DecimalPlaces(stats48Hrs.minPrice) + "", 10);
			outputString += "High: " + padString(trimTo3DecimalPlaces(stats48Hrs.maxPrice) + "" , 10);
			outputString += " |    ";
		}
		else if(noTradeData && Config.SKIP_ITEMS_WITH_NO_DATA){
			//Don't print anything
		}
		else{
			outputString += "48 Hr: | ";
			outputString += evenlyPadString("NO AVAILABLE TRADE DATA", 65);
			outputString += " |    ";
		}

		if(stats90Days.numSales > 0){
			outputString += "90 Day: | ";
			outputString += "Num Sales: " + padString(stats90Days.numSales + "", 8);
			outputString += "Avg: " + padString(trimTo3DecimalPlaces(stats90Days.avgPrice) + "", 10);
			outputString += "Low: " + padString(trimTo3DecimalPlaces(stats90Days.minPrice) + "", 10);
			outputString += "High: " + padString(trimTo3DecimalPlaces(stats90Days.maxPrice) + "" , 10);
			outputString += " |    ";
		}
		else if(noTradeData && Config.SKIP_ITEMS_WITH_NO_DATA){
			//Don't print anything
		}
		else{
			outputString += "90 Day: | ";
			outputString += evenlyPadString("NO AVAILABLE TRADE DATA", 65);
			outputString += " |    ";
		}
		
		System.out.println(outputString);
	}

	private void sleepBetweenMarketRequests() throws InterruptedException{
		long sleepTime = REQUIRED_SLEEP_TIME_BETWEEN_MARKET_REQUESTS;
		long iterationDuration = (System.currentTimeMillis() - lastMarketRequestTime);

		//Note: iterationDuration > sleepTime is v
		if(iterationDuration < sleepTime){
			sleepTime -= iterationDuration;
			Thread.sleep(sleepTime);
		}
	}

	private String getFinalMarketURL(String itemName){
		itemName = itemName.trim().toLowerCase().replace(" ", "_");
		return MARKET_BASE_URL + "/" + itemName + MARKET_ITEM_LIST_STATS_SUFFIX;
	}

	private static class TradeStats{
		public final int numSales;
		public final double minPrice;
		public final double maxPrice;
		public final double avgPrice;

		public TradeStats(int numSales, double minPrice, double maxPrice, double avgPrice){
			this.numSales = numSales;
			this.minPrice = minPrice;
			this.maxPrice = maxPrice;
			this.avgPrice = avgPrice;
		}
	}
}
