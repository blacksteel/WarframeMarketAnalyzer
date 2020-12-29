package tradeStats;

import utils.TokenList;


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
		TokenList outputTokens = new TokenList();

		if(numSales > 0){
			outputTokens.add(avgPrice);
			outputTokens.add(minPrice);
			outputTokens.add(maxPrice);
		}
		else{
			outputTokens.addNull();
			outputTokens.addNull();
			outputTokens.addNull();
		}

		outputTokens.add(numSales);

		return outputTokens.toCSV();
	}
}