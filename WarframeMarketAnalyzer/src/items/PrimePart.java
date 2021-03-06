 package items;

import static utils.MiscUtils.trimAndCapitalizeCorrectly;
import static enums.comparable.MiscWarframeTerms.*;

import java.io.IOException;

import dataSourceHandlers.WarframeMarketHandler;
import tradeStats.StandardTradeStats;
import utils.TokenList;

public class PrimePart extends StandardPricedWarframeItem{
	private static final String DATA_HEADER_SUFFIX = "ItemName,PartName,Type,IsVaulted,IsFullSet,NumInFullSet,Ducats,DucatsPerPlat(48HrsAvgPrice),DucatsPerPlat(90DaysAvgPrice)";

	public final String itemName;
	public final String partName;
	public final String type;
	public final boolean isVaulted;
	public final boolean isFullSet;
	public final Integer numInFullSet;
	public final Integer ducats;

	public PrimePart(String itemName, String partName, String type, boolean isVaulted, boolean isFullSet, Integer numInFullSet, Integer ducats){
		super((KAVASA.containsValue(itemName) ?
				(!(partName.contains(SET.value) || partName.contains(BLUEPRINT.value)) ?
						"Kavasa Prime" :
						itemName) :
				itemName)
				+ " " + partName);

		this.itemName = trimAndCapitalizeCorrectly(itemName);
		this.partName = trimAndCapitalizeCorrectly(partName);
		this.type = trimAndCapitalizeCorrectly(type);
		this.isVaulted = isVaulted;
		this.isFullSet = isFullSet;
		this.numInFullSet = numInFullSet;
		this.ducats = ducats;
	}

	@Override
	public String getDataSuffix(){
		TokenList outputTokens = new TokenList();
		
		StandardTradeStats tradeStats48Hrs = getTradeStats48Hrs();
		StandardTradeStats tradeStats90Days = getTradeStats90Days();
		
		outputTokens.add(itemName);
		outputTokens.add(partName);
		outputTokens.add(type);
		outputTokens.add(isVaulted);
		outputTokens.add(isFullSet);
		outputTokens.add((numInFullSet == null) ? null : numInFullSet);
		outputTokens.add(ducats);
		outputTokens.add((tradeStats48Hrs.numSales == 0) ? null : ducats/tradeStats48Hrs.avgPrice);
		outputTokens.add((tradeStats90Days.numSales == 0) ? null : ducats/tradeStats90Days.avgPrice);
		
		return outputTokens.toCSV();
	}

	public static String getHeaderSuffix(){
		return DATA_HEADER_SUFFIX;
	}
	
	@Override
	public void populateTradeStats(WarframeMarketHandler marketHandler) throws IOException, InterruptedException{
		marketHandler.processItemStandard(this);
	}
}