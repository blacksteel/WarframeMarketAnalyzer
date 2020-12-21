package tradeStats;

import static enums.SharedString.NOT_APPLICABLE;

import java.util.HashMap;
import java.util.Map;

import enums.VoidRelicRefinement;

public class VoidRelicTradeStats extends TradeStats{
	public final Map<VoidRelicRefinement, StandardTradeStats> tradeStats;

	public VoidRelicTradeStats(){
		tradeStats = new HashMap<>();
	}

	@Override
	public String toOutputString(){
		String output = "";
		String numSalesString = "";

		for(VoidRelicRefinement refinement: VoidRelicRefinement.values()){
			StandardTradeStats theseTradeStats = tradeStats.get(refinement);

			if(theseTradeStats.numSales > 0){
				output += (theseTradeStats.avgPrice + "," + theseTradeStats.minPrice + "," + theseTradeStats.maxPrice + ",");
			}
			else{
				output += (NOT_APPLICABLE.value + "," + NOT_APPLICABLE.value + "," + NOT_APPLICABLE.value + ",");
			}

			numSalesString += theseTradeStats.numSales + ",";
		}
		
		//Drop the last comma
		numSalesString = numSalesString.substring(0, numSalesString.length() - 1);
		output += numSalesString;

		return output;
	}
}