package items;

import static enums.comparable.MiscWarframeTerms.*;
import static enums.comparable.UniqueNameSnippet.*;
import static enums.jsonProps.WarframeStatusPropName.*;
import static enums.SharedString.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dataSourceHandlers.WarframeMarketHandler;

import static utils.JSONUtils.*;

import java.io.IOException;

public class Mod extends StandardPricedWarframeItem{
	private static final String DATA_HEADER_SUFFIX = "Rank,MaxRank,Type,Compatibility,Rarity,IsAugment,IsSet,IsConclaveOnly";

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
			this.type = type;
			this.compatibility = compatName;
			this.isRanked = false;
			this.rankToPriceCheck = 0;
			this.maxRank = 0;
			this.isConclaveOnly = true;
		}
		else{
			this.isConclaveOnly = false;

			if(PARAZON.valueEquals(compatName)){
				//Parazon mod
				if(REQUIEM_MOD_UNIQUE_NAME_SNIPPET.containsValue(uniqueName)){
					//Requiem mod, searching for full ranks rather than 0 ranks
					this.type = REQUIEM.value;
					this.compatibility = PARAZON.value;
					this.isRanked = true;
					this.rankToPriceCheck = 3;
					this.maxRank = 3;
				}
				else{
					//Other parazon mod, not a ranked item
					this.type = PARAZON.value;
					this.compatibility = PARAZON.value;
					this.isRanked = false;
					this.rankToPriceCheck = 0;
					this.maxRank = 0;
				}
			}
			else{
				this.type = type;
				this.compatibility = compatName;
				this.isRanked = (maxRank > 0);
				this.rankToPriceCheck = 0;
				this.maxRank = maxRank;
			}
		}
		
		this.rarity = getStrProp(jsonObject, RARITY);
		this.isAugment = jsonObject.has(AUGMENT.value) ? getBoolProp(jsonObject, AUGMENT) : false;
		this.isSet = (jsonObject.has(MOD_SET.value));
	}

	@Override
	public String getDataSuffix(){
		return (isRanked ? rankToPriceCheck : NOT_APPLICABLE.value) +
				"," + (maxRank == 0 ? NOT_APPLICABLE.value : maxRank) + 
				"," + type +
				"," + compatibility +
				"," + rarity +
				"," + isAugment +
				"," + isSet +
				"," + isConclaveOnly;
	}

	public static String getHeaderSuffix(){
		return DATA_HEADER_SUFFIX;
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