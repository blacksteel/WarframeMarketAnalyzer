package dataSourceHandlers;

import static enums.jsonProps.WarframeMarketPropName.AVG_PRICE;
import static enums.jsonProps.WarframeMarketPropName.CLOSED_TRADES_STATS;
import static enums.jsonProps.WarframeMarketPropName.ITEMS;
import static enums.jsonProps.WarframeMarketPropName.ITEM_NAME;
import static enums.jsonProps.WarframeMarketPropName.MAX_PRICE;
import static enums.jsonProps.WarframeMarketPropName.MIN_PRICE;
import static enums.jsonProps.WarframeMarketPropName.NUM_SALES;
import static enums.jsonProps.WarframeMarketPropName.PAYLOAD;
import static enums.jsonProps.WarframeMarketPropName.RANK;
import static enums.jsonProps.WarframeMarketPropName.TIME_PERIOD_48HRS;
import static enums.jsonProps.WarframeMarketPropName.TIME_PERIOD_90DAYS;
import static main.MainRunner.getGson;
import static utils.JSONUtils.getDblProp;
import static utils.JSONUtils.getIntProp;
import static utils.JSONUtils.getJsonArray;
import static utils.JSONUtils.getJsonObj;
import static utils.JSONUtils.getStrProp;

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

import items.WarframeItem;
import main.Config;
import tradeStats.StandardTradeStats;

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

	protected static boolean isTradableItem(String itemName) throws IOException{
		return getTradableItemNamesList().contains(itemName.toLowerCase());
	}

	private static List<String> getTradableItemNamesList() throws IOException{
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
			output.add(getStrProp(iter.next(), ITEM_NAME).toLowerCase());
		}

		return output;
	}

	public void processItems(List<? extends WarframeItem<?>> items) throws IOException, InterruptedException{
		for(WarframeItem<?> item: items){
			if(Config.WRITE_DEBUG_INFO_TO_CONSOLE) System.out.println("Processing item: " + item.getName());

			item.populateTradeStats(this);
		}
	}

	public TradeStatsPair getAndProcessTradeData(String itemName, boolean isRanked, int rankToPriceCheck)
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

	private void sleepBetweenMarketRequests() throws InterruptedException{
		if(lastMarketRequestTimes.size() == NUM_ALLOWED_REQUESTS_PER_TIME_PERIOD){
			long sleepTime = REQUEST_LIMIT_TIME_PERIOD - (System.currentTimeMillis() - lastMarketRequestTimes.poll());

			if(sleepTime > 0){
				Thread.sleep(sleepTime);
			}
		}
	}

	private String getWFMarketURLItemStatsSuffix(String itemName){
		itemName = itemName.trim().toLowerCase().replace(" ", "_").replace("-", "_").replace("'", "").replace("&", "and");
		return "/" + itemName + WF_MARKET_URL_ITEM_STATS_SUFFIX;
	}

	public class TradeStatsPair{
		public final StandardTradeStats stats48Hrs;
		public final StandardTradeStats stats90Days;

		public TradeStatsPair(StandardTradeStats stats48Hours, StandardTradeStats stats90Days){
			this.stats48Hrs = stats48Hours;
			this.stats90Days = stats90Days;
		}
	}
}