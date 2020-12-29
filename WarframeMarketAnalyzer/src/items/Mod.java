package items;

import static enums.comparable.MiscWarframeTerms.PARAZON;
import static enums.comparable.MiscWarframeTerms.REQUIEM;
import static enums.comparable.UniqueNameSnippet.CONCLAVE_MOD_UNIQUE_NAME_SNIPPET;
import static enums.comparable.UniqueNameSnippet.REQUIEM_MOD_UNIQUE_NAME_SNIPPET;
import static enums.jsonProps.WarframeStatusPropName.AUGMENT;
import static enums.jsonProps.WarframeStatusPropName.COMPAT_NAME;
import static enums.jsonProps.WarframeStatusPropName.MAX_RANK;
import static enums.jsonProps.WarframeStatusPropName.MOD_SET;
import static enums.jsonProps.WarframeStatusPropName.RARITY;
import static enums.jsonProps.WarframeStatusPropName.SIMPLE_NAME;
import static enums.jsonProps.WarframeStatusPropName.TYPE;
import static enums.jsonProps.WarframeStatusPropName.UNIQUE_NAME;
import static utils.JSONUtils.getBoolProp;
import static utils.JSONUtils.getIntProp;
import static utils.JSONUtils.getStrProp;
import static utils.MiscUtils.trimAndCapitalizeCorrectly;

import java.io.IOException;

import com.google.gson.JsonObject;

import dataSourceHandlers.WarframeMarketHandler;
import dataSourceHandlers.WarframeMarketHandler.TradeStatsPair;
import enums.fields.ModFieldEnum;
import main.results.TypeResults;

public class Mod extends WarframeItem<ModFieldEnum> {

	private int rankToPriceCheck;
	private boolean isRanked;

	public Mod(JsonObject jsonObject, TypeResults<ModFieldEnum> results) {
		super(results);

		setResult(ModFieldEnum.Name, getStrProp(jsonObject, SIMPLE_NAME));

		String type = getStrProp(jsonObject, TYPE);
		if(type.toLowerCase().endsWith(" mod")){
			type = type.substring(0, (type.length() - 4));
		}

		String uniqueName = getStrProp(jsonObject, UNIQUE_NAME);
		String compatName = getStrProp(jsonObject, COMPAT_NAME);
		int maxRank = getIntProp(jsonObject, MAX_RANK);

		if(CONCLAVE_MOD_UNIQUE_NAME_SNIPPET.containsValue(uniqueName)){
			setResult(ModFieldEnum.Type, trimAndCapitalizeCorrectly(type));
			setResult(ModFieldEnum.IsAugment, "false");
			rankToPriceCheck = 0;
			maxRank = 0;
			setResult(ModFieldEnum.IsConclaveOnly,"true");
		}
		else{
			setResult(ModFieldEnum.IsConclaveOnly,"false");

			if(PARAZON.valueEquals(compatName)){
				//Parazon mod
				compatName = PARAZON.value;

				if(REQUIEM_MOD_UNIQUE_NAME_SNIPPET.containsValue(uniqueName)){
					//Requiem mod, searching for full ranks rather than 0 ranks
					setResult(ModFieldEnum.Type, REQUIEM.value);
					isRanked = true;
					rankToPriceCheck = 3;
					maxRank = 3;
				}
				else{
					//Other parazon mod, not a ranked item
					setResult(ModFieldEnum.Type, PARAZON.value);
					isRanked = false;
					rankToPriceCheck = 0;
					maxRank = 0;
				}
			}
			else{
				setResult(ModFieldEnum.Type, type);
				isRanked = (maxRank > 0);
				rankToPriceCheck = 0;
			}
		}

		setResult(ModFieldEnum.Rank,isRanked ? rankToPriceCheck : null);
		setResult(ModFieldEnum.MaxRank,(maxRank == 0) ? null : maxRank);
		setResult(ModFieldEnum.Compatibility,trimAndCapitalizeCorrectly(compatName));
		setResult(ModFieldEnum.Rarity,trimAndCapitalizeCorrectly(getStrProp(jsonObject, RARITY)));
		setResult(ModFieldEnum.IsAugment,jsonObject.has(AUGMENT.value) ? getBoolProp(jsonObject, AUGMENT) : false);
		setResult(ModFieldEnum.IsSet,jsonObject.has(MOD_SET.value));
	}

	@Override
	public boolean isRanked(){
		return isRanked;
	}

	@Override
	public int getRankToPriceCheck(){
		return rankToPriceCheck;
	}

	@Override
	public void populateTradeStats(WarframeMarketHandler marketHandler) throws IOException, InterruptedException {
		TradeStatsPair tradeStats = marketHandler.getAndProcessTradeData(getName(), isRanked(), getRankToPriceCheck());

		setResult(ModFieldEnum.Num48Hr, tradeStats.stats48Hrs.numSales);
		if (tradeStats.stats48Hrs.numSales > 0) {
			setResult(ModFieldEnum.Avg48Hr, tradeStats.stats48Hrs.avgPrice);
			setResult(ModFieldEnum.High48Hr, tradeStats.stats48Hrs.maxPrice);
			setResult(ModFieldEnum.Low48Hr, tradeStats.stats48Hrs.minPrice);
		} else {
			setResult(ModFieldEnum.Avg48Hr, NA);
			setResult(ModFieldEnum.High48Hr, NA);
			setResult(ModFieldEnum.Low48Hr, NA);
		}

		setResult(ModFieldEnum.Num90Day, tradeStats.stats90Days.numSales);
		if (tradeStats.stats90Days.numSales > 0) {
			setResult(ModFieldEnum.Avg90Day, tradeStats.stats90Days.avgPrice);
			setResult(ModFieldEnum.High90Day, tradeStats.stats90Days.maxPrice);
			setResult(ModFieldEnum.Low90Day, tradeStats.stats90Days.minPrice);
		} else {
			setResult(ModFieldEnum.Avg90Day, NA);
			setResult(ModFieldEnum.High90Day, NA);
			setResult(ModFieldEnum.Low90Day, NA);
		}
	}
}