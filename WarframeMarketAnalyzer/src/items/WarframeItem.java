package items;

import java.io.IOException;

import dataSourceHandlers.WarframeMarketHandler;
import enums.fields.IFieldEnum;
import main.results.TypeResults;

public abstract class WarframeItem<T extends Enum<T> & IFieldEnum> {
	
	public static String NA = "N/A";
	private TypeResults<T> results;
	private int index;
	
//	protected TradeStats tradeStats48Hrs;
//	protected TradeStats tradeStats90Days;
	
	public WarframeItem(TypeResults<T> results){
		this.results = results;
		this.index = results.startNewResultRow();
	}
	
	public boolean isRanked(){
		return false;
	}
	
//	public TradeStats getTradeStats48Hrs(){
//		return tradeStats48Hrs;
//	}
//
//	public void setTradeStats48Hours(TradeStats tradeStats48Hrs){
//		this.tradeStats48Hrs = tradeStats48Hrs;
//	}
//
//	public TradeStats getTradeStats90Days(){
//		return tradeStats90Days;
//	}
//
//	public void setTradeStats90Days(TradeStats tradeStats90Days){
//		this.tradeStats90Days = tradeStats90Days;
//	}
	
	public int getRankToPriceCheck(){
		return -1;
	}
	
//	public static String getHeaderSuffix(){
//		return null;
//	}
//	
//	public static String getTradeStatsHeader48Hrs(){
//		return "";
//	}
//
//	public static String getTradeStatsHeader90Days(){
//		return "";
//	}

	protected void setResult(T field, Object value) {
		results.setResult(value != null ? value.toString() : NA, index, field);
	}

	public String getName() {
		return results.getName(index);
	}

	public abstract void populateTradeStats(WarframeMarketHandler marketHandler) throws IOException, InterruptedException;
}