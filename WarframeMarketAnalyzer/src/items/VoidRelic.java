package items;

import static enums.jsonProps.WarframeWikiPropName.*;
import static enums.comparable.MiscWarframeTerms.*;
import static enums.VoidRelicRefinement.*;
import static utils.JSONUtils.*;
import static utils.MiscUtils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dataSourceHandlers.WarframeMarketHandler;
import enums.VoidRelicRefinement;
import enums.comparable.DropRarity;
import enums.comparable.VoidRelicEra;
import tradeStats.VoidRelicTradeStats;
import utils.TokenList;

public class VoidRelic extends WarframeItem{
	private static final String TRADE_STATS_HEADER_48_HRS =
			"48HrAvg(Int),48HrAvg(Ex),48HrAvg(Flaw),48HrAvg(Rad),"
			+ "48HrLow(Int),48HrHigh(Int),48HrLow(Rad),48HrHigh(Rad),"
			+ "48HrNumSales(Int),48HrNumSales(Ex),48HrNumSales(Flaw),48HrNumSales(Rad)";
	
	private static final String TRADE_STATS_HEADER_90_DAYS =
			"90DayAvg(Int),90DayAvg(Ex),90DayAvg(Flaw),90DayAvg(Rad),"
			+ "90DayLow(Int),90DayHigh(Int),90DayLow(Rad),90DayHigh(Rad),"
			+ "90DayNumSales(Int),90DayNumSales(Ex),90DayNumSales(Flaw),90DayNumSales(Rad)";
	
	private static final String DATA_HEADER_SUFFIX =
			"CommonDrop1,CommonDrop2,CommonDrop3,UncommonDrop1,UncommonDrop2,RareDrop,"
			+ "CommonDrop1DucatVal,CommonDrop1AvgPlatVal48Hrs,CommonDrop1AvgPlatVal90Days,"
			+ "CommonDrop2DucatVal,CommonDrop2AvgPlatVal48Hrs,CommonDrop2AvgPlatVal90Days,"
			+ "CommonDrop3DucatVal,CommonDrop3AvgPlatVal48Hrs,CommonDrop3AvgPlatVal90Days,"
			+ "UncommonDrop1DucatVal,UncommonDrop1AvgPlatVal48Hrs,UncommonDrop1AvgPlatVal90Days,"
			+ "UncommonDrop2DucatVal,UncommonDrop2AvgPlatVal48Hrs,UncommonDrop2AvgPlatVal90Days,"
			+ "RareDropDucatVal,RareDropAvgPlatVal48Hrs,RareDropAvgPlatVal90Days,"
			+ "CommonDrop1ItemName,CommonDrop1PartName,CommonDrop2ItemName,CommonDrop2PartName,"
			+ "CommonDrop3ItemName,CommonDrop3PartName,UncommonDrop1ItemName,UncommonDrop1PartName,"
			+ "UncommonDrop2ItemName,UncommonDrop2PartName,RareDropItemName,RareDropPartName,"
			+ "DropsForma,DropsFormaCommon,DropsFormaUncommon,IsVaulted,IsBaroExclusive,"
			+ "AvgDucatVal(Int), AvgDucatVal(Ex), AvgDucatVal(Flaw), AvgDucatVal(Rad),"
			+ "AvgPlatVal48Hrs(Int), AvgPlatVal48Hrs(Ex), AvgPlatVal48Hrs(Flaw), AvgPlatVal48Hrs(Rad),"
			+ "AvgPlatVal90Days(Int), AvgPlatVal90Days(Ex), AvgPlatVal90Days(Flaw), AvgPlatVal90Days(Rad),"
			+ "BestAvgDucatRefinement,BestAvgPlatRefinement48Hrs,BestAvgPlatRefinement90Days,"
			+ "AvgDucatValChange(Int=>Ex), AvgDucatValChange(Int=>Flaw), AvgDucatValChange(Int=>Rad),"
			+ "AvgDucatValChange(Ex=>Flaw), AvgDucatValChange(Ex=>Rad), AvgDucatValChange(Flaw=>Rad),"
			+ "AvgPlatVal48HrsChange(Int=>Ex), AvgPlatVal48HrsChange(Int=>Flaw), AvgPlatVal48HrsChange(Int=>Rad),"
			+ "AvgPlatVal48HrsChange(Ex=>Flaw), AvgPlatVal48HrsChange(Ex=>Rad), AvgPlatVal48HrsChange(Flaw=>Rad),"
			+ "AvgPlatVal90DaysChange(Int=>Ex), AvgPlatVal90DaysChange(Int=>Flaw), AvgPlatVal90DaysChange(Int=>Rad),"
			+ "AvgPlatVal90DaysChange(Ex=>Flaw), AvgPlatVal90DaysChange(Ex=>Rad), AvgPlatVal90DaysChange(Flaw=>Rad),"
			+ "AvgDucatValChangePerTrace(Int=>Ex), AvgDucatValChangePerTrace(Int=>Flaw), AvgDucatValChangePerTrace(Int=>Rad),"
			+ "AvgDucatValChangePerTrace(Ex=>Flaw), AvgDucatValChangePerTrace(Ex=>Rad), AvgDucatValChangePerTrace(Flaw=>Rad),"
			+ "AvgPlatVal48HrsChangePerTrace(Int=>Ex), AvgPlatVal48HrsChangePerTrace(Int=>Flaw), AvgPlatVal48HrsChangePerTrace(Int=>Rad),"
			+ "AvgPlatVal48HrsChangePerTrace(Ex=>Flaw), AvgPlatVal48HrsChangePerTrace(Ex=>Rad), AvgPlatVal48HrsChangePerTrace(Flaw=>Rad),"
			+ "AvgPlatVal90DaysChangePerTrace(Int=>Ex), AvgPlatVal90DaysChangePerTrace(Int=>Flaw), AvgPlatVal90DaysChangePerTrace(Int=>Rad),"
			+ "AvgPlatVal90DaysChangePerTrace(Ex=>Flaw), AvgPlatVal90DaysChangePerTrace(Ex=>Rad), AvgPlatVal90DaysChangePerTrace(Flaw=>Rad),"
			+ "IntShareAvgDucatVal,IntShareAvgPlatVal48Hrs,IntShareAvgPlatVal90Days,RadShareAvgDucatVal,RadShareAvgPlatValue48Hrs,RadShareAvgPlatValue90Days";

	public final VoidRelicEra relicEra;
	public final String relicName;
	public final boolean isVaulted;
	public final boolean isBaroExclusive;
	public final RelicDrops relicDrops;

	public final Map<VoidRelicRefinement, Double> refinementToAvgDucatValMap = new HashMap<>();
	public final Map<VoidRelicRefinement, Double> refinementToAvgPlatVal48HrsMap = new HashMap<>();
	public final Map<VoidRelicRefinement, Double> refinementToAvgPlatVal90DaysMap = new HashMap<>();

	public final VoidRelicRefinement bestAvgDucatRefinement;
	public final VoidRelicRefinement bestAvgPlatRefinement48Hrs;
	public final VoidRelicRefinement bestAvgPlatRefinement90Days;

	public VoidRelic(JsonElement jsonObjectElement, Map<String, PrimePart> primePartNamesToPartsMap){
		this((JsonObject)jsonObjectElement, primePartNamesToPartsMap);
	}

	public VoidRelic(JsonObject jsonObject, Map<String, PrimePart> primePartNamesToPartsMap){
		super(getName(jsonObject));

		StringTokenizer nameTokens = new StringTokenizer(name);
		relicEra = VoidRelicEra.getByValue(nameTokens.nextToken());
		relicName = trimAndCapitalizeCorrectly(nameTokens.nextToken());

		isVaulted = jsonObject.has(IS_VAULTED.value) ? (getIntProp(jsonObject, IS_VAULTED) == 1) : true;
		isBaroExclusive = jsonObject.has(IS_BARO_EXCLUSIVE.value) ? (getIntProp(jsonObject, IS_BARO_EXCLUSIVE) == 1) : false;
		
		relicDrops = new RelicDrops(getJsonArray(jsonObject, RELIC_DROPS), primePartNamesToPartsMap);

		bestAvgDucatRefinement = populateRefinementToAvgDucatValMap();
		bestAvgPlatRefinement48Hrs = populateRefinementToAvgPlatVal48HrsMap();
		bestAvgPlatRefinement90Days = populateRefinementToAvgPlatVal90DaysMap();
	}

	private VoidRelicRefinement populateRefinementToAvgDucatValMap(){
		return populateMapWithAverages(
				new Double[]{(double)relicDrops.commonDrops.get(0).ducatVal,
						(double)relicDrops.commonDrops.get(1).ducatVal,
						(double)relicDrops.commonDrops.get(2).ducatVal},
				new Double[]{(double)relicDrops.uncommonDrops.get(0).ducatVal,
						(double)relicDrops.uncommonDrops.get(1).ducatVal},
				(double)relicDrops.rareDrop.ducatVal,
				refinementToAvgDucatValMap);
	}

	private VoidRelicRefinement populateRefinementToAvgPlatVal48HrsMap(){
		return populateMapWithAverages(
				new Double[]{relicDrops.commonDrops.get(0).avgPlatVal48Hrs,
						relicDrops.commonDrops.get(1).avgPlatVal48Hrs,
						relicDrops.commonDrops.get(2).avgPlatVal48Hrs},
				new Double[]{relicDrops.uncommonDrops.get(0).avgPlatVal48Hrs,
						relicDrops.uncommonDrops.get(1).avgPlatVal48Hrs},
				relicDrops.rareDrop.avgPlatVal48Hrs,
				refinementToAvgPlatVal48HrsMap);
	}

	private VoidRelicRefinement populateRefinementToAvgPlatVal90DaysMap(){
		return populateMapWithAverages(
				new Double[]{relicDrops.commonDrops.get(0).avgPlatVal90Days,
						relicDrops.commonDrops.get(1).avgPlatVal90Days,
						relicDrops.commonDrops.get(2).avgPlatVal90Days},
				new Double[]{relicDrops.uncommonDrops.get(0).avgPlatVal90Days,
						relicDrops.uncommonDrops.get(1).avgPlatVal90Days},
				relicDrops.rareDrop.avgPlatVal90Days,
				refinementToAvgPlatVal90DaysMap);
	}

	private static VoidRelicRefinement populateMapWithAverages(Double[] commonVals, Double[] uncommonVals, Double rareVal,
			Map<VoidRelicRefinement, Double> mapToPopulate){
		//Returns the refinement level with the highest avg val
		VoidRelicRefinement bestRefinement = null;
				
		if(isMissingPriceData(commonVals, uncommonVals, rareVal)){
			for(VoidRelicRefinement refinement: VoidRelicRefinement.values()){
				mapToPopulate.put(refinement, null);
			}
		}
		else{
			double largestVal = 0;

			for(VoidRelicRefinement refinement: VoidRelicRefinement.values()){
				double avgVal = 0;

				for(double val: commonVals) avgVal += (val*refinement.commonDropChance);
				for(double val: uncommonVals) avgVal += (val*refinement.uncommonDropChance);
				avgVal += (rareVal*refinement.rareDropChance);

				mapToPopulate.put(refinement, avgVal);

				if(avgVal > largestVal) {
					bestRefinement = refinement;
					largestVal = avgVal;
				}
			}
		}

		return bestRefinement;
	}

	@Override
	public String getDataSuffix(){
		TokenList outputTokens = new TokenList();

		RelicDrop commonDrop1 = relicDrops.commonDrops.get(0);
		RelicDrop commonDrop2 = relicDrops.commonDrops.get(1);
		RelicDrop commonDrop3 = relicDrops.commonDrops.get(2);
		RelicDrop uncommonDrop1 = relicDrops.uncommonDrops.get(0);
		RelicDrop uncommonDrop2 = relicDrops.uncommonDrops.get(1);
		RelicDrop rareDrop = relicDrops.rareDrop;
		
		double avgDucatValInt = refinementToAvgDucatValMap.get(VoidRelicRefinement.INTACT);
		double avgDucatValEx = refinementToAvgDucatValMap.get(VoidRelicRefinement.EXCEPTIONAL);
		double avgDucatValFlaw = refinementToAvgDucatValMap.get(VoidRelicRefinement.FLAWLESS);
		double avgDucatValRad = refinementToAvgDucatValMap.get(VoidRelicRefinement.RADIANT);
		
		Double avgPlatVal48HrsInt = refinementToAvgPlatVal48HrsMap.get(VoidRelicRefinement.INTACT);
		Double avgPlatVal48HrsEx = refinementToAvgPlatVal48HrsMap.get(VoidRelicRefinement.EXCEPTIONAL);
		Double avgPlatVal48HrsFlaw = refinementToAvgPlatVal48HrsMap.get(VoidRelicRefinement.FLAWLESS);
		Double avgPlatVal48HrsRad = refinementToAvgPlatVal48HrsMap.get(VoidRelicRefinement.RADIANT);

		Double avgPlatVal90DaysInt = refinementToAvgPlatVal90DaysMap.get(VoidRelicRefinement.INTACT);
		Double avgPlatVal90DaysEx = refinementToAvgPlatVal90DaysMap.get(VoidRelicRefinement.EXCEPTIONAL);
		Double avgPlatVal90DaysFlaw = refinementToAvgPlatVal90DaysMap.get(VoidRelicRefinement.FLAWLESS);
		Double avgPlatVal90DaysRad = refinementToAvgPlatVal90DaysMap.get(VoidRelicRefinement.RADIANT);

		double avgDucatValDiffIntToEx = avgDucatValEx - avgDucatValInt;
		double avgDucatValDiffIntToFlaw = avgDucatValFlaw - avgDucatValInt;
		double avgDucatValDiffIntToRad = avgDucatValRad - avgDucatValInt;
		double avgDucatValDiffExToFlaw = avgDucatValFlaw - avgDucatValEx;
		double avgDucatValDiffExToRad = avgDucatValRad - avgDucatValEx;
		double avgDucatValDiffFlawToRad = avgDucatValRad - avgDucatValFlaw;
		
		Double avgPlatVal48HrsDiffIntToEx = subtractWithNullCheck(avgPlatVal48HrsEx, avgPlatVal48HrsInt);
		Double avgPlatVal48HrsDiffIntToFlaw = subtractWithNullCheck(avgPlatVal48HrsFlaw, avgPlatVal48HrsInt);
		Double avgPlatVal48HrsDiffIntToRad = subtractWithNullCheck(avgPlatVal48HrsRad, avgPlatVal48HrsInt);
		Double avgPlatVal48HrsDiffExToFlaw = subtractWithNullCheck(avgPlatVal48HrsFlaw, avgPlatVal48HrsEx);
		Double avgPlatVal48HrsDiffExToRad = subtractWithNullCheck(avgPlatVal48HrsRad, avgPlatVal48HrsEx);
		Double avgPlatVal48HrsDiffFlawToRad = subtractWithNullCheck(avgPlatVal48HrsRad, avgPlatVal48HrsFlaw);

		Double avgPlatVal90DaysDiffIntToEx = subtractWithNullCheck(avgPlatVal90DaysEx, avgPlatVal90DaysInt);
		Double avgPlatVal90DaysDiffIntToFlaw = subtractWithNullCheck(avgPlatVal90DaysFlaw, avgPlatVal90DaysInt);
		Double avgPlatVal90DaysDiffIntToRad = subtractWithNullCheck(avgPlatVal90DaysRad, avgPlatVal90DaysInt);
		Double avgPlatVal90DaysDiffExToFlaw = subtractWithNullCheck(avgPlatVal90DaysFlaw, avgPlatVal90DaysEx);
		Double avgPlatVal90DaysDiffExToRad = subtractWithNullCheck(avgPlatVal90DaysRad, avgPlatVal90DaysEx);
		Double avgPlatVal90DaysDiffFlawToRad = subtractWithNullCheck(avgPlatVal90DaysRad, avgPlatVal90DaysFlaw);

		int tracesIntToEx = EXCEPTIONAL.numVoidTracesToUpgrade - INTACT.numVoidTracesToUpgrade;
		int tracesIntToFlaw = FLAWLESS.numVoidTracesToUpgrade - INTACT.numVoidTracesToUpgrade;
		int tracesIntToRad = RADIANT.numVoidTracesToUpgrade - INTACT.numVoidTracesToUpgrade;
		int tracesExToFlaw = FLAWLESS.numVoidTracesToUpgrade - EXCEPTIONAL.numVoidTracesToUpgrade;
		int tracesExToRad = RADIANT.numVoidTracesToUpgrade - EXCEPTIONAL.numVoidTracesToUpgrade;
		int tracesFlawToRad = RADIANT.numVoidTracesToUpgrade - FLAWLESS.numVoidTracesToUpgrade;
		
		double avgDucatValDiffPerTraceIntToEx = avgDucatValDiffIntToEx/tracesIntToEx;
		double avgDucatValDiffPerTraceIntToFlaw = avgDucatValDiffIntToFlaw/tracesIntToFlaw;
		double avgDucatValDiffPerTraceIntToRad = avgDucatValDiffIntToRad/tracesIntToRad;
		double avgDucatValDiffPerTraceExToFlaw = avgDucatValDiffExToFlaw/tracesExToFlaw;
		double avgDucatValDiffPerTraceExToRad = avgDucatValDiffExToRad/tracesExToRad;
		double avgDucatValDiffPerTraceFlawToRad = avgDucatValDiffFlawToRad/tracesFlawToRad;
		
		Double avgPlatVal48HrsDiffPerTraceIntToEx = divideWithNullCheck(avgPlatVal48HrsDiffIntToEx, tracesIntToEx);
		Double avgPlatVal48HrsDiffPerTraceIntToFlaw = divideWithNullCheck(avgPlatVal48HrsDiffIntToFlaw, tracesIntToFlaw);
		Double avgPlatVal48HrsDiffPerTraceIntToRad = divideWithNullCheck(avgPlatVal48HrsDiffIntToRad, tracesIntToRad);
		Double avgPlatVal48HrsDiffPerTraceExToFlaw = divideWithNullCheck(avgPlatVal48HrsDiffExToFlaw, tracesExToFlaw);
		Double avgPlatVal48HrsDiffPerTraceExToRad = divideWithNullCheck(avgPlatVal48HrsDiffExToRad, tracesExToRad);
		Double avgPlatVal48HrsDiffPerTraceFlawToRad = divideWithNullCheck(avgPlatVal48HrsDiffFlawToRad, tracesFlawToRad);
		 
		Double avgPlatVal90DaysDiffPerTraceIntToEx = divideWithNullCheck(avgPlatVal90DaysDiffIntToEx, tracesIntToEx);
		Double avgPlatVal90DaysDiffPerTraceIntToFlaw = divideWithNullCheck(avgPlatVal90DaysDiffIntToFlaw, tracesIntToFlaw);
		Double avgPlatVal90DaysDiffPerTraceIntToRad = divideWithNullCheck(avgPlatVal90DaysDiffIntToRad, tracesIntToRad);
		Double avgPlatVal90DaysDiffPerTraceExToFlaw = divideWithNullCheck(avgPlatVal90DaysDiffExToFlaw, tracesExToFlaw);
		Double avgPlatVal90DaysDiffPerTraceExToRad = divideWithNullCheck(avgPlatVal90DaysDiffExToRad, tracesExToRad);
		Double avgPlatVal90DaysDiffPerTraceFlawToRad = divideWithNullCheck(avgPlatVal90DaysDiffFlawToRad, tracesFlawToRad);
		
		Double intShareAvgDucatVal = calculateIntShareAvgDucatVal();
		Double intShareAvgPlatVal48Hrs = calculateIntShareAvgPlatVal48Hrs();
		Double intShareAvgPlatVal90Days = calculateIntShareAvgPlatVal90Days();
		
		Double radShareAvgDucatVal = calculateRadShareAvgDucatVal();
		Double radShareAvgPlatVal48Hrs = calculateRadShareAvgPlatVal48Hrs();
		Double radShareAvgPlatVal90Days = calculateRadShareAvgPlatVal90Days();
		
		outputTokens.add(commonDrop1.name); //CommonDrop1

		outputTokens.add(commonDrop2.name); //CommonDrop2

		outputTokens.add(commonDrop3.name); //CommonDrop3

		outputTokens.add(uncommonDrop1.name); //UncommonDrop1

		outputTokens.add(uncommonDrop2.name); //UncommonDrop2

		outputTokens.add(rareDrop.name); //RareDrop

		outputTokens.add(commonDrop1.ducatVal);//CommonDrop1DucatVal
		outputTokens.add(commonDrop1.avgPlatVal48Hrs);//CommonDrop1AvgPlatVal48Hrs
		outputTokens.add(commonDrop1.avgPlatVal90Days);//CommonDrop1AvgPlatVal90Days

		outputTokens.add(commonDrop2.ducatVal);//CommonDrop2DucatVal
		outputTokens.add(commonDrop2.avgPlatVal48Hrs);//CommonDrop2AvgPlatVal48Hrs
		outputTokens.add(commonDrop2.avgPlatVal90Days);//CommonDrop2AvgPlatVal90Days

		outputTokens.add(commonDrop3.ducatVal);//CommonDrop3DucatVal
		outputTokens.add(commonDrop3.avgPlatVal48Hrs);//CommonDrop3AvgPlatVal48Hrs
		outputTokens.add(commonDrop3.avgPlatVal90Days);//CommonDrop3AvgPlatVal90Days

		outputTokens.add(uncommonDrop1.ducatVal);//UncommonDrop1DucatVal
		outputTokens.add(uncommonDrop1.avgPlatVal48Hrs);//UncommonDrop1AvgPlatVal48Hrs
		outputTokens.add(uncommonDrop1.avgPlatVal90Days);//UncommonDrop1AvgPlatVal90Days

		outputTokens.add(uncommonDrop2.ducatVal);//UncommonDrop2DucatVal
		outputTokens.add(uncommonDrop2.avgPlatVal48Hrs);//UncommonDrop2AvgPlatVal48Hrs
		outputTokens.add(uncommonDrop2.avgPlatVal90Days);//UncommonDrop2AvgPlatVal90Days

		outputTokens.add(rareDrop.ducatVal);//RareDropDucatVal
		outputTokens.add(rareDrop.avgPlatVal48Hrs);//RareDropAvgPlatVal48Hrs
		outputTokens.add(rareDrop.avgPlatVal90Days);//RareDropAvgPlatVal90Days

		outputTokens.add(commonDrop1.itemName);//CommonDrop1ItemName
		outputTokens.add(commonDrop1.partName);//CommonDrop1PartName

		outputTokens.add(commonDrop2.itemName);//CommonDrop2ItemName
		outputTokens.add(commonDrop2.partName);//CommonDrop2PartName

		outputTokens.add(commonDrop3.itemName);//CommonDrop3ItemName
		outputTokens.add(commonDrop3.partName);//CommonDrop3PartName

		outputTokens.add(uncommonDrop1.itemName);//UncommonDrop1ItemName
		outputTokens.add(uncommonDrop1.partName);//UncommonDrop1PartName

		outputTokens.add(uncommonDrop2.itemName);//UncommonDrop2ItemName
		outputTokens.add(uncommonDrop2.partName);//UncommonDrop2PartName

		outputTokens.add(rareDrop.itemName);//RareDropItemName
		outputTokens.add(rareDrop.itemName);//RareDropPartName

		outputTokens.add(relicDrops.dropsForma());//DropsForma
		outputTokens.add(relicDrops.dropsFormaCommon);//DropsFormaCommon
		outputTokens.add(relicDrops.dropsFormaUncommon);//DropsFormaUncommon

		outputTokens.add(isVaulted);//IsVaulted
		outputTokens.add(isBaroExclusive);//IsBaroExclusive

		outputTokens.add(avgDucatValInt);//AvgDucatVal(Int)
		outputTokens.add(avgDucatValEx);//AvgDucatVal(Ex)
		outputTokens.add(avgDucatValFlaw);//AvgDucatVal(Flaw)
		outputTokens.add(avgDucatValRad);//AvgDucatVal(Rad)

		outputTokens.add(avgPlatVal48HrsInt);//AvgPlatVal48Hrs(Int)
		outputTokens.add(avgPlatVal48HrsEx);//AvgPlatVal48Hrs(Ex)
		outputTokens.add(avgPlatVal48HrsFlaw);//AvgPlatVal48Hrs(Flaw)
		outputTokens.add(avgPlatVal48HrsRad);//AvgPlatVal48Hrs(Rad)

		outputTokens.add(avgPlatVal90DaysInt);//AvgPlatVal90Days(Int)
		outputTokens.add(avgPlatVal90DaysEx);//AvgPlatVal90Days(Ex)
		outputTokens.add(avgPlatVal90DaysFlaw);//AvgPlatVal90Days(Flaw)
		outputTokens.add(avgPlatVal90DaysRad);//AvgPlatVal90Days(Rad)

		outputTokens.add(bestAvgDucatRefinement.name);//BestAvgDucatRefinement
		outputTokens.add(bestAvgPlatRefinement48Hrs.name);//BestAvgPlatRefinement48Hrs
		outputTokens.add(bestAvgPlatRefinement90Days.name);//BestAvgPlatRefinement90Days

		outputTokens.add(avgDucatValDiffIntToEx);//AvgDucatValChange(Int=>Ex)
		outputTokens.add(avgDucatValDiffIntToFlaw);//AvgDucatValChange(Int=>Flaw)
		outputTokens.add(avgDucatValDiffIntToRad);//AvgDucatValChange(Int=>Rad)
		outputTokens.add(avgDucatValDiffExToFlaw);//AvgDucatValChange(Ex=>Flaw)
		outputTokens.add(avgDucatValDiffExToRad);//AvgDucatValChange(Ex=>Rad)
		outputTokens.add(avgDucatValDiffFlawToRad);//AvgDucatValChange(Flaw=>Rad)

		outputTokens.add(avgPlatVal48HrsDiffIntToEx);//AvgPlatVal48HrsChange(Int=>Ex)
		outputTokens.add(avgPlatVal48HrsDiffIntToFlaw);//AvgPlatVal48HrsChange(Int=>Flaw)
		outputTokens.add(avgPlatVal48HrsDiffIntToRad);//AvgPlatVal48HrsChange(Int=>Rad)
		outputTokens.add(avgPlatVal48HrsDiffExToFlaw);//AvgPlatVal48HrsChange(Ex=>Flaw)
		outputTokens.add(avgPlatVal48HrsDiffExToRad);//AvgPlatVal48HrsChange(Ex=>Rad)
		outputTokens.add(avgPlatVal48HrsDiffFlawToRad);//AvgPlatVal48HrsChange(Flaw=>Rad)
		
		outputTokens.add(avgPlatVal90DaysDiffIntToEx);//AvgPlatVal90DaysChange(Int=>Ex)
		outputTokens.add(avgPlatVal90DaysDiffIntToFlaw);//AvgPlatVal90DaysChange(Int=>Flaw)
		outputTokens.add(avgPlatVal90DaysDiffIntToRad);//AvgPlatVal90DaysChange(Int=>Rad)
		outputTokens.add(avgPlatVal90DaysDiffExToFlaw);//AvgPlatVal90DaysChange(Ex=>Flaw)
		outputTokens.add(avgPlatVal90DaysDiffExToRad);//AvgPlatVal90DaysChange(Ex=>Rad)
		outputTokens.add(avgPlatVal90DaysDiffFlawToRad);//AvgPlatVal90DaysChange(Flaw=>Rad)

		outputTokens.add(avgDucatValDiffPerTraceIntToEx);//AvgDucatValChangePerTrace(Int=>Ex)
		outputTokens.add(avgDucatValDiffPerTraceIntToFlaw);//AvgDucatValChangePerTrace(Int=>Flaw)
		outputTokens.add(avgDucatValDiffPerTraceIntToRad);//AvgDucatValChangePerTrace(Int=>Rad)
		outputTokens.add(avgDucatValDiffPerTraceExToFlaw);//AvgDucatValChangePerTrace(Ex=>Flaw)
		outputTokens.add(avgDucatValDiffPerTraceExToRad);//AvgDucatValChangePerTrace(Ex=>Rad)
		outputTokens.add(avgDucatValDiffPerTraceFlawToRad);//AvgDucatValChangePerTrace(Flaw=>Rad)

		outputTokens.add(avgPlatVal48HrsDiffPerTraceIntToEx);//AvgPlatVal48HrsChangePerTrace(Int=>Ex)
		outputTokens.add(avgPlatVal48HrsDiffPerTraceIntToFlaw);//AvgPlatVal48HrsChangePerTrace(Int=>Flaw)
		outputTokens.add(avgPlatVal48HrsDiffPerTraceIntToRad);//AvgPlatVal48HrsChangePerTrace(Int=>Rad)
		outputTokens.add(avgPlatVal48HrsDiffPerTraceExToFlaw);//AvgPlatVal48HrsChangePerTrace(Ex=>Flaw)
		outputTokens.add(avgPlatVal48HrsDiffPerTraceExToRad);//AvgPlatVal48HrsChangePerTrace(Ex=>Rad)
		outputTokens.add(avgPlatVal48HrsDiffPerTraceFlawToRad);//AvgPlatVal48HrsChangePerTrace(Flaw=>Rad)
		
		outputTokens.add(avgPlatVal90DaysDiffPerTraceIntToEx);//AvgPlatVal90DaysChangePerTrace(Int=>Ex)
		outputTokens.add(avgPlatVal90DaysDiffPerTraceIntToFlaw);//AvgPlatVal90DaysChangePerTrace(Int=>Flaw)
		outputTokens.add(avgPlatVal90DaysDiffPerTraceIntToRad);//AvgPlatVal90DaysChangePerTrace(Int=>Rad)
		outputTokens.add(avgPlatVal90DaysDiffPerTraceExToFlaw);//AvgPlatVal90DaysChangePerTrace(Ex=>Flaw)
		outputTokens.add(avgPlatVal90DaysDiffPerTraceExToRad);//AvgPlatVal90DaysChangePerTrace(Ex=>Rad)
		outputTokens.add(avgPlatVal90DaysDiffPerTraceFlawToRad);//AvgPlatVal90DaysChangePerTrace(Flaw=>Rad)
		
		outputTokens.add(intShareAvgDucatVal);//IntShareAvgDucatVal
		outputTokens.add(intShareAvgPlatVal48Hrs);//IntShareAvgPlatVal48Hrs
		outputTokens.add(intShareAvgPlatVal90Days);//IntShareAvgPlatVal90Days

		outputTokens.add(radShareAvgDucatVal);//RadShareAvgDucatVal
		outputTokens.add(radShareAvgPlatVal48Hrs);//RadShareAvgPlatVal48Hrs
		outputTokens.add(radShareAvgPlatVal90Days);//RadShareAvgPlatVal90Days
		
		return outputTokens.toCSV();
	}
	
	private double calculateIntShareAvgDucatVal(){
		return calculateShareVal(
				new Double[]{
						(double)relicDrops.commonDrops.get(0).ducatVal,
						(double)relicDrops.commonDrops.get(1).ducatVal,
						(double)relicDrops.commonDrops.get(2).ducatVal,
						(double)relicDrops.uncommonDrops.get(0).ducatVal,
						(double)relicDrops.uncommonDrops.get(1).ducatVal,
						(double)relicDrops.rareDrop.ducatVal},
				new double[]{
						INTACT.commonDropChance,
						INTACT.commonDropChance,
						INTACT.commonDropChance,
						INTACT.uncommonDropChance,
						INTACT.uncommonDropChance,
						INTACT.rareDropChance});
	}

	private double calculateIntShareAvgPlatVal48Hrs(){
		return calculateShareVal(
				new Double[]{
						relicDrops.commonDrops.get(0).avgPlatVal48Hrs,
						relicDrops.commonDrops.get(1).avgPlatVal48Hrs,
						relicDrops.commonDrops.get(2).avgPlatVal48Hrs,
						relicDrops.uncommonDrops.get(0).avgPlatVal48Hrs,
						relicDrops.uncommonDrops.get(1).avgPlatVal48Hrs,
						relicDrops.rareDrop.avgPlatVal48Hrs},
				new double[]{
						INTACT.commonDropChance,
						INTACT.commonDropChance,
						INTACT.commonDropChance,
						INTACT.uncommonDropChance,
						INTACT.uncommonDropChance,
						INTACT.rareDropChance});
	}

	private double calculateIntShareAvgPlatVal90Days(){
		return calculateShareVal(
				new Double[]{
						relicDrops.commonDrops.get(0).avgPlatVal90Days,
						relicDrops.commonDrops.get(1).avgPlatVal90Days,
						relicDrops.commonDrops.get(2).avgPlatVal90Days,
						relicDrops.uncommonDrops.get(0).avgPlatVal90Days,
						relicDrops.uncommonDrops.get(1).avgPlatVal90Days,
						relicDrops.rareDrop.avgPlatVal90Days},
				new double[]{
						INTACT.commonDropChance,
						INTACT.commonDropChance,
						INTACT.commonDropChance,
						INTACT.uncommonDropChance,
						INTACT.uncommonDropChance,
						INTACT.rareDropChance});
	}

	private double calculateRadShareAvgDucatVal(){
		return calculateShareVal(
				new Double[]{
						(double)relicDrops.commonDrops.get(0).ducatVal,
						(double)relicDrops.commonDrops.get(1).ducatVal,
						(double)relicDrops.commonDrops.get(2).ducatVal,
						(double)relicDrops.uncommonDrops.get(0).ducatVal,
						(double)relicDrops.uncommonDrops.get(1).ducatVal,
						(double)relicDrops.rareDrop.ducatVal},
				new double[]{
						RADIANT.commonDropChance,
						RADIANT.commonDropChance,
						RADIANT.commonDropChance,
						RADIANT.uncommonDropChance,
						RADIANT.uncommonDropChance,
						RADIANT.rareDropChance});
	}

	private double calculateRadShareAvgPlatVal48Hrs(){
		return calculateShareVal(
				new Double[]{
						relicDrops.commonDrops.get(0).avgPlatVal48Hrs,
						relicDrops.commonDrops.get(1).avgPlatVal48Hrs,
						relicDrops.commonDrops.get(2).avgPlatVal48Hrs,
						relicDrops.uncommonDrops.get(0).avgPlatVal48Hrs,
						relicDrops.uncommonDrops.get(1).avgPlatVal48Hrs,
						relicDrops.rareDrop.avgPlatVal48Hrs},
				new double[]{
						RADIANT.commonDropChance,
						RADIANT.commonDropChance,
						RADIANT.commonDropChance,
						RADIANT.uncommonDropChance,
						RADIANT.uncommonDropChance,
						RADIANT.rareDropChance});
	}

	private double calculateRadShareAvgPlatVal90Days(){
		return calculateShareVal(
				new Double[]{
						relicDrops.commonDrops.get(0).avgPlatVal90Days,
						relicDrops.commonDrops.get(1).avgPlatVal90Days,
						relicDrops.commonDrops.get(2).avgPlatVal90Days,
						relicDrops.uncommonDrops.get(0).avgPlatVal90Days,
						relicDrops.uncommonDrops.get(1).avgPlatVal90Days,
						relicDrops.rareDrop.avgPlatVal90Days},
				new double[]{
						RADIANT.commonDropChance,
						RADIANT.commonDropChance,
						RADIANT.commonDropChance,
						RADIANT.uncommonDropChance,
						RADIANT.uncommonDropChance,
						RADIANT.rareDropChance});
	}

	private Double calculateShareVal(Double[] vals, double[] probabilities){
		if(isMissingData(vals)) return null;
		
		double shareVal = 0;

		for(int i = 1; i < 6; ++i){
			for(int j = 1; j < 6; ++j){
				for(int k = 1; k < 6; ++k){
					for(int l = 1; l < 6; ++l){
						double maxVal = Math.max(Math.max(Math.max(vals[i], vals[j]), vals[k]), vals[l]);
						shareVal += (maxVal*probabilities[i]*probabilities[j]*probabilities[k]*probabilities[l]);
					}
				}
			}
		}

		return shareVal;
	}

	private static boolean isMissingPriceData(Double[] commonVals, Double[] uncommonVals, Double rareVal){
		Double[] vals = {commonVals[0], commonVals[1], commonVals[2], uncommonVals[0], uncommonVals[1], rareVal};
		
		return isMissingData(vals);
	}
	
	public static String getName(JsonElement jsonObjectElement){
		return getName((JsonObject)jsonObjectElement);
	}
	
	public static String getName(JsonObject jsonObject){
		String era = getStrProp(jsonObject, RELIC_ERA);
		String name = getStrProp(jsonObject, RELIC_NAME);

		return era + " " + name;
	}

	public static String getHeaderSuffix(){
		return DATA_HEADER_SUFFIX;
	}

	public static String getTradeStatsHeader48Hrs(){
		return TRADE_STATS_HEADER_48_HRS;
	}

	public static String getTradeStatsHeader90Days(){
		return TRADE_STATS_HEADER_90_DAYS;
	}

	@Override
	public void populateTradeStats(WarframeMarketHandler marketHandler) throws IOException, InterruptedException{
		marketHandler.processVoidRelic(this);
	}

	@Override
	public VoidRelicTradeStats getTradeStats48Hrs(){
		return (VoidRelicTradeStats)tradeStats48Hrs;
	}

	@Override
	public VoidRelicTradeStats getTradeStats90Days(){
		return (VoidRelicTradeStats)tradeStats90Days;
	}

	private class RelicDrops{
		public final List<RelicDrop> commonDrops = new ArrayList<>();
		public final List<RelicDrop> uncommonDrops = new ArrayList<>();
		public final RelicDrop rareDrop;
		public final boolean dropsFormaCommon;
		public final boolean dropsFormaUncommon;

		public RelicDrops(JsonArray relicDropsArray, Map<String, PrimePart> primePartNamesToPartsMap){
			RelicDrop rareDrop = null;
			boolean dropsFormaCommon = false;
			boolean dropsFormaUncommon = false;

			for(JsonElement dropElement: relicDropsArray){
				RelicDrop drop = new RelicDrop(dropElement, primePartNamesToPartsMap);

				switch(drop.rarity){
				case COMMON:
					commonDrops.add(drop);
					if(drop.isForma) dropsFormaCommon = true;
					break;
				case UNCOMMON:
					uncommonDrops.add(drop);
					if(drop.isForma) dropsFormaUncommon = true;
					break;
				case RARE:
					rareDrop = drop;
					break;
				default:
					break;
				}
			}

			this.rareDrop = rareDrop;
			this.dropsFormaCommon = dropsFormaCommon;
			this.dropsFormaUncommon = dropsFormaUncommon;
		}

		public boolean dropsForma(){
			return (dropsFormaCommon || dropsFormaUncommon);
		}
	}

	private class RelicDrop{
		public final String name;
		public final String itemName;
		public final String partName;
		public final DropRarity rarity;
		public final boolean isForma;
		public final int ducatVal;
		public final Double avgPlatVal48Hrs;
		public final Double avgPlatVal90Days;

		public RelicDrop(JsonElement relicDrop, Map<String, PrimePart> primePartNamesToPartsMap){
			this((JsonObject)relicDrop, primePartNamesToPartsMap);
		}

		public RelicDrop(JsonObject relicDrop, Map<String, PrimePart> primePartNamesToPartsMap){
			String name = "";
			String itemName = getStrProp(relicDrop, DROPPED_ITEM_NAME);
			String partName = getStrProp(relicDrop, DROPPED_ITEM_PART).trim();
			String primePartName = null;

			this.isForma = FORMA.valueEquals(itemName);

			//This is a special case
			if(KAVASA.valueEquals(itemName)){
				itemName = "Kavasa Prime Kubrow Collar";
				
				if(COLLAR.containsValue(partName)){
					partName = BLUEPRINT.value.toUpperCase();
					primePartName = "Kavasa Prime Kubrow Collar Blueprint";
				}
				else{
					primePartName = "Kavasa Prime " + partName;
				}
				
				name = primePartName;
			}
			else{
				//Remove "Blueprint" from parts other than main blueprints (Eg. Chassis/Neuroptics/Systems Blueprints)
				if(BLUEPRINT.endsWithValue(partName) && !BLUEPRINT.valueEquals(partName)){
					partName = partName.substring(0, partName.indexOf(' '));
				}

				if(isForma){
					name = itemName + " " + partName;
				}
				else{
					primePartName = itemName + " Prime " + partName;
					name = primePartName;
				}
			}

			this.name = trimAndCapitalizeCorrectly(name);
			this.itemName = trimAndCapitalizeCorrectly(itemName);
			this.partName = trimAndCapitalizeCorrectly(partName);
			this.rarity = DropRarity.getByValue(getStrProp(relicDrop, DROPPED_ITEM_RARITY));

			if(primePartName != null){
				PrimePart primePart = primePartNamesToPartsMap.get(primePartName.toUpperCase());
				
				this.ducatVal = primePart.ducats;
				this.avgPlatVal48Hrs = (primePart.getTradeStats48Hrs().numSales > 0) ? primePart.getTradeStats48Hrs().avgPrice : null;
				this.avgPlatVal90Days = (primePart.getTradeStats90Days().numSales > 0) ? primePart.getTradeStats90Days().avgPrice : null;
			}
			else{
				this.ducatVal = 0;
				this.avgPlatVal48Hrs = 0.0;
				this.avgPlatVal90Days = 0.0;
			}
		}
	}
}