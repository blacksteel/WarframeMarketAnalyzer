package items;

import tradeStats.StandardTradeStats;

public abstract class StandardPricedWarframeItem extends WarframeItem{
	private static final String STANDARD_TRADE_STATS_HEADER_48_HRS = "48HrAvg,48HrLow,48HrHigh,48HrNumSales";
	private static final String STANDARD_TRADE_STATS_HEADER_90_DAYS = "90DayAvg,90DayLow,90DayHigh,90DayNumSales";
	
	public StandardPricedWarframeItem(String name){
		super(name);
	}

	public static String getTradeStatsHeader48Hrs(){
		return STANDARD_TRADE_STATS_HEADER_48_HRS;
	}

	public static String getTradeStatsHeader90Days(){
		return STANDARD_TRADE_STATS_HEADER_90_DAYS;
	}
	
	@Override
	public StandardTradeStats getTradeStats48Hrs(){
		return (StandardTradeStats)tradeStats48Hrs;
	}
	
	@Override
	public StandardTradeStats getTradeStats90Days(){
		return (StandardTradeStats)tradeStats90Days;
	}
}
