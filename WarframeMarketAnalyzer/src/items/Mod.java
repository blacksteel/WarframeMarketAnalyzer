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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dataSourceHandlers.WarframeMarketHandler;
import utils.TokenList;

public class Mod extends WarframeItem{

	public final int rankToPriceCheck;
	public final int maxRank;
	public final String type;
	public final String compatibility;
	public final String rarity;
	public final boolean isAugment;
	public final boolean isSet;
	public final boolean isRanked;
	public final boolean isConclaveOnly;

	public Mod(JsonElement jsonObjectElement){
		this((JsonObject)jsonObjectElement);
	}

	public Mod(JsonObject jsonObject){
		super(getStrProp(jsonObject, SIMPLE_NAME));

		String type = getStrProp(jsonObject, TYPE);
		if(type.toLowerCase().endsWith(" mod")){
			type = type.substring(0, (type.length() - 4));
		}
		
		String uniqueName = getStrProp(jsonObject, UNIQUE_NAME);
		String compatName = getStrProp(jsonObject, COMPAT_NAME);
		int maxRank = getIntProp(jsonObject, MAX_RANK);
		
		if(CONCLAVE_MOD_UNIQUE_NAME_SNIPPET.containsValue(uniqueName)){
			this.type = trimAndCapitalizeCorrectly(type);
			this.isRanked = false;
			this.rankToPriceCheck = 0;
			this.maxRank = 0;
			this.isConclaveOnly = true;
		}
		else{
			this.isConclaveOnly = false;

			if(PARAZON.valueEquals(compatName)){
				//Parazon mod
				compatName = PARAZON.value;
				
				if(REQUIEM_MOD_UNIQUE_NAME_SNIPPET.containsValue(uniqueName)){
					//Requiem mod, searching for full ranks rather than 0 ranks
					this.type = REQUIEM.value;
					this.isRanked = true;
					this.rankToPriceCheck = 3;
					this.maxRank = 3;
				}
				else{
					//Other parazon mod, not a ranked item
					this.type = PARAZON.value;
					this.isRanked = false;
					this.rankToPriceCheck = 0;
					this.maxRank = 0;
				}
			}
			else{
				this.type = type;
				this.isRanked = (maxRank > 0);
				this.rankToPriceCheck = 0;
				this.maxRank = maxRank;
			}
		}
		
		this.compatibility = trimAndCapitalizeCorrectly(compatName);
		this.rarity = trimAndCapitalizeCorrectly(getStrProp(jsonObject, RARITY));
		this.isAugment = jsonObject.has(AUGMENT.value) ? getBoolProp(jsonObject, AUGMENT) : false;
		this.isSet = (jsonObject.has(MOD_SET.value));
	}

	@Override
	public String getDataSuffix(){
		TokenList outputTokens = new TokenList();
		
		outputTokens.add(isRanked ? rankToPriceCheck : null);
		outputTokens.add((maxRank == 0) ? null : maxRank);
		outputTokens.add(type);
		outputTokens.add(compatibility);
		outputTokens.add(rarity);
		outputTokens.add(isAugment);
		outputTokens.add(isSet);
		outputTokens.add(isConclaveOnly);

		return outputTokens.toCSV();
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
	public void populateTradeStats(WarframeMarketHandler marketHandler) throws IOException, InterruptedException{
		marketHandler.processItemStandard(this);
	}
}