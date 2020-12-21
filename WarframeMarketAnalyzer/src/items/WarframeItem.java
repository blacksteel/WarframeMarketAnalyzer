package items;

import java.io.IOException;

import dataSourceHandlers.WarframeMarketHandler;
import tradeStats.TradeStats;

public abstract class WarframeItem{	
	public final String name;
	protected TradeStats tradeStats48Hrs;
	protected TradeStats tradeStats90Days;
	
	public WarframeItem(String name){
		this.name = name;
	}
	
	public boolean isRanked(){
		return false;
	}
	
	public TradeStats getTradeStats48Hrs(){
		return tradeStats48Hrs;
	}

	public void setTradeStats48Hours(TradeStats tradeStats48Hrs){
		this.tradeStats48Hrs = tradeStats48Hrs;
	}

	public TradeStats getTradeStats90Days(){
		return tradeStats90Days;
	}

	public void setTradeStats90Days(TradeStats tradeStats90Days){
		this.tradeStats90Days = tradeStats90Days;
	}
	
	public int getRankToPriceCheck(){
		return -1;
	}
	
	public static String getHeaderSuffix(){
		return null;
	}
	
	public static String getTradeStatsHeader48Hrs(){
		return "";
	}

	public static String getTradeStatsHeader90Days(){
		return "";
	}
	
	public abstract String getDataSuffix();
	public abstract void populateTradeStats(WarframeMarketHandler marketHandler) throws IOException, InterruptedException;
}