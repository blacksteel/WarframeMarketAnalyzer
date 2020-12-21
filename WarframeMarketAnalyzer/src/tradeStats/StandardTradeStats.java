package tradeStats;

import static enums.SharedString.NOT_APPLICABLE;

public class StandardTradeStats extends TradeStats{
	public final int numSales;
	public final double minPrice;
	public final double maxPrice;
	public final double avgPrice;

	public StandardTradeStats(int numSales, double minPrice, double maxPrice, double avgPrice){
		this.numSales = numSales;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.avgPrice = avgPrice;
	}

	@Override
	public String toOutputString(){
		String output = "";
		
		if(numSales > 0){
			output += (avgPrice + "," + minPrice + "," + maxPrice + ",");
		}
		else{
			output += (NOT_APPLICABLE.value + "," + NOT_APPLICABLE.value + "," + NOT_APPLICABLE.value + ",");
		}
		
		output += numSales;
		
		return output;
	}
}