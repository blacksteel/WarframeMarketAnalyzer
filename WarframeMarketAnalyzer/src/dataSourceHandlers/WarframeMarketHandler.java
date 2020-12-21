package dataSourceHandlers;

import static enums.jsonProps.WarframeMarketPropName.*;
import static main.MainRunner.getGson;
import static utils.JSONUtils.*;
import static utils.MiscUtils.*;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import enums.VoidRelicRefinement;
import items.VoidRelic;
import items.WarframeItem;
import main.Config;
import tradeStats.StandardTradeStats;
import tradeStats.VoidRelicTradeStats;

public class WarframeMarketHandler extends DataSourceHandler{
	private static final String WF_MARKET_BASE_URL = "https://api.warframe.market/v1/items";
	private static final String WF_MARKET_URL_ITEM_STATS_SUFFIX = "/statistics";

	//Warframe Market API only supports 3 requests per second
	private static final int NUM_ALLOWED_REQUESTS_PER_TIME_PERIOD = 3;
	private static final long REQUEST_LIMIT_TIME_PERIOD = 1000;

	private static List<String> tradableItemNamesList = null;

	private static Queue<Long> lastMarketRequestTimes = new ArrayDeque<>();

	public WarframeMarketHandler(){
		super(WF_MARKET_BASE_URL);
	}

	protected static List<String> getTradableItemNamesList() throws IOException{
		if(tradableItemNamesList == null) {
			tradableItemNamesList = getTradableItemsListFromMarket();
		}

		return tradableItemNamesList;
	}

	private static List<String> getTradableItemsListFromMarket() throws IOException{
		List<String> output = new ArrayList<String>();

		String marketPayload = getFromServer(WF_MARKET_BASE_URL, new String[]{"platform", "language"}, new String[]{"pc", "en"});
		JsonObject tradableItemsObject = (new Gson()).fromJson(marketPayload.toString(), JsonObject.class);

		JsonArray tradableItemsArray = getJsonArray(getJsonObj(tradableItemsObject, PAYLOAD), ITEMS);

		for(Iterator<JsonElement> iter = tradableItemsArray.iterator(); iter.hasNext();){
			output.add(getStrProp(iter.next(), ITEM_NAME));
		}

		return output;
	}

	public void processItems(List<? extends WarframeItem> items) throws IOException, InterruptedException{
		for(WarframeItem item: items){
			item.populateTradeStats(this);
		}
	}

	public void processItemStandard(WarframeItem item) throws IOException, InterruptedException{
		TradeStatsPair tradeStats = getAndProcessTradeData(item.name, item.isRanked(), item.getRankToPriceCheck());

		if(Config.WRITE_OUTPUT_TO_CONSOLE){
			dumpToConsole(item.name, tradeStats.stats48Hrs, tradeStats.stats90Days);
		}

		item.setTradeStats48Hours(tradeStats.stats48Hrs);
		item.setTradeStats90Days(tradeStats.stats90Days);
	}

	public void processVoidRelic(VoidRelic relic) throws IOException, InterruptedException{
		VoidRelicTradeStats stats48Hrs = new VoidRelicTradeStats();
		VoidRelicTradeStats stats90Days = new VoidRelicTradeStats();

		for(VoidRelicRefinement refinement: VoidRelicRefinement.values()){
			String relicFullName = relic.name + " " + refinement.name;
			TradeStatsPair tradeStats =
					getAndProcessTradeData(relicFullName, relic.isRanked(), relic.getRankToPriceCheck());

			stats48Hrs.tradeStats.put(refinement, tradeStats.stats48Hrs);
			stats90Days.tradeStats.put(refinement, tradeStats.stats90Days);
		}

		relic.setTradeStats48Hours(stats48Hrs);
		relic.setTradeStats90Days(stats90Days);
	}

	private TradeStatsPair getAndProcessTradeData(String itemName, boolean isRanked, int rankToPriceCheck)
			throws IOException, InterruptedException{
		sleepBetweenMarketRequests();

		TradeStatsPair retVal = null;

		String marketPayload = getFromDataSource(
				getWFMarketURLItemStatsSuffix(itemName), new String[]{"platform", "language"}, new String[]{"pc", "en"});

		lastMarketRequestTimes.add(System.currentTimeMillis());
		
		if(marketPayload != null){
			JsonObject tradeInfo = getGson().fromJson(marketPayload.toString(), JsonObject.class);

			JsonObject closedTradesStatsObject = getJsonObj(getJsonObj(tradeInfo, PAYLOAD), CLOSED_TRADES_STATS);

			StandardTradeStats stats48Hrs = processTradeData(
					getJsonArray(closedTradesStatsObject, TIME_PERIOD_48HRS), isRanked, rankToPriceCheck);
			StandardTradeStats stats90Days = processTradeData(
					getJsonArray(closedTradesStatsObject, TIME_PERIOD_90DAYS), isRanked, rankToPriceCheck);

			retVal = new TradeStatsPair(stats48Hrs, stats90Days);
		}

		return retVal;
	}

	private StandardTradeStats processTradeData(JsonArray tradeDataArray, boolean isRanked, int rankToPriceCheck){
		double minPrice = Double.MAX_VALUE;
		double maxPrice = Double.MIN_VALUE;
		double totalValue = 0;
		int numSales = 0;

		for(Iterator<JsonElement> iter = tradeDataArray.iterator(); iter.hasNext();){
			JsonObject saleDataObject = iter.next().getAsJsonObject();

			if(isRanked && getIntProp(saleDataObject, RANK) != rankToPriceCheck){
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

		return new StandardTradeStats(numSales, minPrice, maxPrice, totalValue/numSales);
	}

	@SuppressWarnings("unused")
	private void dumpToConsole(String itemName, StandardTradeStats stats48Hrs, StandardTradeStats stats90Days){
		String outputString = itemName;

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
		if(lastMarketRequestTimes.size() == NUM_ALLOWED_REQUESTS_PER_TIME_PERIOD){
			long sleepTime = REQUEST_LIMIT_TIME_PERIOD - (System.currentTimeMillis() - lastMarketRequestTimes.poll());

			if(sleepTime > 0){
				Thread.sleep(sleepTime);
			}
		}
	}

	private String getWFMarketURLItemStatsSuffix(String itemName){
		itemName = itemName.trim().toLowerCase().replace(" ", "_");
		return "/" + itemName + WF_MARKET_URL_ITEM_STATS_SUFFIX;
	}

	private class TradeStatsPair{
		public final StandardTradeStats stats48Hrs;
		public final StandardTradeStats stats90Days;

		public TradeStatsPair(StandardTradeStats stats48Hours, StandardTradeStats stats90Days){
			this.stats48Hrs = stats48Hours;
			this.stats90Days = stats90Days;
		}
	}
}
