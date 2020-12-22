package tradeStats;

import java.util.HashMap;
import java.util.Map;

import enums.VoidRelicRefinement;
import utils.TokenList;

import static enums.VoidRelicRefinement.*;

public class VoidRelicTradeStats extends TradeStats{
	public final Map<VoidRelicRefinement, StandardTradeStats> tradeStats;

	public VoidRelicTradeStats(){
		tradeStats = new HashMap<>();
	}

	@Override
	public String toOutputString(){
		TokenList outputTokens = new TokenList();
		TokenList numSalesTokens = new TokenList();

		StandardTradeStats theseTradeStats;
		
		for(VoidRelicRefinement refinement: VoidRelicRefinement.values()){
			theseTradeStats = tradeStats.get(refinement);
			outputTokens.add((theseTradeStats.numSales > 0) ? theseTradeStats.avgPrice : null);
			numSalesTokens.add(theseTradeStats.numSales);
		}
		
		theseTradeStats = tradeStats.get(INTACT);
		if(theseTradeStats.numSales > 0){
			outputTokens.add(theseTradeStats.minPrice);
			outputTokens.add(theseTradeStats.maxPrice);
		}
		else{
			outputTokens.addNull();
			outputTokens.addNull();
		}
		
		theseTradeStats = tradeStats.get(RADIANT);
		if(theseTradeStats.numSales > 0){
			outputTokens.add(theseTradeStats.minPrice);
			outputTokens.add(theseTradeStats.maxPrice);
		}
		else{
			outputTokens.addNull();
			outputTokens.addNull();
		}
		
		outputTokens.addAll(numSalesTokens);

		return outputTokens.toCSV();
	}
}