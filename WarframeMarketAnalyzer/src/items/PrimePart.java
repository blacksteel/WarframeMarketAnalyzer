 package items;

import static enums.comparable.MiscWarframeTerms.BLUEPRINT;
import static enums.comparable.MiscWarframeTerms.KAVASA;
import static enums.comparable.MiscWarframeTerms.SET;
import static utils.MiscUtils.trimAndCapitalizeCorrectly;

import java.io.IOException;

import dataSourceHandlers.WarframeMarketHandler;
import dataSourceHandlers.WarframeMarketHandler.TradeStatsPair;
import enums.fields.PrimeFieldEnum;
import main.results.TypeResults;

public class PrimePart extends WarframeItem<PrimeFieldEnum>{

	private Integer ducats;
	private TradeStatsPair tradeStats;

	public PrimePart(String itemName, String partName, String type, boolean isVaulted, boolean isFullSet,
			Integer numInFullSet, Integer ducats, TypeResults<PrimeFieldEnum> results) {
		super(results);

		String baseItemName;
		if (KAVASA.containsValue(itemName) && !(partName.contains(SET.value) || partName.contains(BLUEPRINT.value))) {
			baseItemName = "Kavasa Prime";
		} else {
			baseItemName = itemName;
		}
		setResult(PrimeFieldEnum.Name, baseItemName+ " " + partName);

		setResult(PrimeFieldEnum.ItemName, trimAndCapitalizeCorrectly(itemName));
		setResult(PrimeFieldEnum.PartName,trimAndCapitalizeCorrectly(partName));
		setResult(PrimeFieldEnum.Type, trimAndCapitalizeCorrectly(type));
		setResult(PrimeFieldEnum.IsVaulted, isVaulted);
		setResult(PrimeFieldEnum.IsFullSet, isFullSet);
		setResult(PrimeFieldEnum.NumInFullSet, numInFullSet);
		this.ducats = ducats;
		setResult(PrimeFieldEnum.Ducats, ducats);
	}

	@Override
	public void populateTradeStats(WarframeMarketHandler marketHandler) throws IOException, InterruptedException {
		tradeStats = marketHandler.getAndProcessTradeData(getName(), isRanked(), getRankToPriceCheck());

		setResult(PrimeFieldEnum.Num48Hr, tradeStats.stats48Hrs.numSales);
		if (tradeStats.stats48Hrs.numSales > 0) {
			setResult(PrimeFieldEnum.Avg48Hr, tradeStats.stats48Hrs.avgPrice);
			setResult(PrimeFieldEnum.High48Hr, tradeStats.stats48Hrs.maxPrice);
			setResult(PrimeFieldEnum.Low48Hr, tradeStats.stats48Hrs.minPrice);
		} else {
			setResult(PrimeFieldEnum.Avg48Hr, NA);
			setResult(PrimeFieldEnum.High48Hr, NA);
			setResult(PrimeFieldEnum.Low48Hr, NA);
		}

		setResult(PrimeFieldEnum.Num90Day, tradeStats.stats90Days.numSales);
		if (tradeStats.stats90Days.numSales > 0) {
			setResult(PrimeFieldEnum.Avg90Day, tradeStats.stats90Days.avgPrice);
			setResult(PrimeFieldEnum.High90Day, tradeStats.stats90Days.maxPrice);
			setResult(PrimeFieldEnum.Low90Day, tradeStats.stats90Days.minPrice);
		} else {
			setResult(PrimeFieldEnum.Avg90Day, NA);
			setResult(PrimeFieldEnum.High90Day, NA);
			setResult(PrimeFieldEnum.Low90Day, NA);
		}

		setResult(PrimeFieldEnum.Ducats48, (tradeStats.stats48Hrs.numSales == 0) ? NA :
			ducats.doubleValue()/tradeStats.stats48Hrs.avgPrice);
		setResult(PrimeFieldEnum.Ducats90, (tradeStats.stats90Days.numSales == 0) ? NA :
			ducats.doubleValue()/tradeStats.stats90Days.avgPrice);
	}

	public TradeStatsPair getTradeStats() {
		return tradeStats;
	}

	public Integer getDucats() {
		return ducats;
	}
}