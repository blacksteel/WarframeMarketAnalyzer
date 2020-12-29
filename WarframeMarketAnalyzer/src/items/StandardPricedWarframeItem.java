package items;

import tradeStats.StandardTradeStats;

public abstract class StandardPricedWarframeItem extends WarframeItem{
	
	public StandardPricedWarframeItem(String name){
		super(name);
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
