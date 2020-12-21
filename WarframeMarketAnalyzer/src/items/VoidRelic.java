package items;

import static enums.jsonProps.WarframeWikiPropName.*;
import static enums.comparable.MiscWarframeTerms.*;
import static enums.VoidRelicRefinement.*;
import static utils.JSONUtils.*;

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

public class VoidRelic extends WarframeItem{
	private static final String TRADE_STATS_HEADER_48_HRS =
			"48HrAvg(Int),48HrLow(Int),48HrHigh(Int),"
					+ ",48HrAvg(Ex),48HrLow(Ex),48HrHigh(Ex),"
					+ ",48HrAvg(Flaw),48HrLow(Flaw),48HrHigh(Flaw),"
					+ ",48HrAvg(Rad),48HrLow(Rad),48HrHigh(Rad),"
					+ "48HrNumSales(Int),48HrNumSales(Ex),48HrNumSales(Flaw),48HrNumSales(Rad)";
	private static final String TRADE_STATS_HEADER_90_DAYS =
			"90DayAvg(Int),90DayLow(Int),90DayHigh(Int),"
					+ ",90DayAvg(Ex),90DayLow(Ex),90DayHigh(Ex),"
					+ ",90DayAvg(Flaw),90DayLow(Flaw),90DayHigh(Flaw),"
					+ ",90DayAvg(Rad),90DayLow(Rad),90DayHigh(Rad),"
					+ "90DayNumSales(Int),90DayNumSales(Ex),90DayNumSales(Flaw),90DayNumSales(Rad)";
	private static final String DATA_HEADER_SUFFIX =
			"CommonDrop1,CommonDrop2,CommonDrop3,UncommonDrop1,UncommonDrop2,RareDrop,"
					+ "CommonDrop1ItemName,CommonDrop1PartName,CommonDrop2ItemName,CommonDrop2PartName,"
					+ "CommonDrop3ItemName,CommonDrop3PartName,UncommonDrop1ItemName,UncommonDrop1PartName,"
					+ "UncommonDrop2ItemName,UncommonDrop2PartName,RareDropItemName,RareDropPartName,"
					+ "DropsForma,DropsFormaCommon,DropsFormaUncommon,IsVaulted,"
					+ "AvgDucatVal(Int), AvgDucatVal(Ex), AvgDucatVal(Flaw), AvgDucatVal(Rad),"
					+ "AvgPlatVal48Hrs(Int), AvgPlatVal48Hrs(Ex), AvgPlatVal48Hrs(Flaw), AvgPlatVal48Hrs(Rad),"
					+ "AvgPlatVal90Days(Int), AvgPlatVal90Days(Ex), AvgPlatVal90Days(Flaw), AvgPlatVal90Days(Rad),"
					+ "BestAvgDucatRefinement,BestAvgPlatRefinement48Hrs,BestAvgPlatRefinement90Days,"
					+ "AvgDucatValChange(Int => Ex), AvgDucatValChange(Int => Flaw), AvgDucatValChange(Int => Rad),"
					+ "AvgDucatValChange(Ex => Flaw), AvgDucatValChange(Ex => Rad), AvgDucatValChange(Flaw => Rad),"
					+ "AvgPlatValChange(Int => Ex)48Hrs, AvgPlatValChange(Int => Flaw)48Hrs, AvgPlatValChange(Int => Rad)48Hrs,"
					+ "AvgPlatValChange(Ex => Flaw)48Hrs, AvgPlatValChange(Ex => Rad)48Hrs, AvgPlatValChange(Flaw => Rad)48Hrs,"
					+ "AvgPlatValChange(Int => Ex)90Days, AvgPlatValChange(Int => Flaw)90Days, AvgPlatValChange(Int => Rad)90Days,"
					+ "AvgPlatValChange(Ex => Flaw)90Days, AvgPlatValChange(Ex => Rad)90Days, AvgPlatValChange(Flaw => Rad)90Days,"
					+ "AvgDucatValChangePerTrace(Int => Ex), AvgDucatValChangePerTrace(Int => Flaw), AvgDucatValChangePerTrace(Int => Rad),"
					+ "AvgDucatValChangePerTrace(Ex => Flaw), AvgDucatValChangePerTrace(Ex => Rad), AvgDucatValChangePerTrace(Flaw => Rad),"
					+ "AvgPlatValChangePerTrace(Int => Ex)48Hrs, AvgPlatValChangePerTrace(Int => Flaw)48Hrs, AvgPlatValChangePerTrace(Int => Rad)48Hrs,"
					+ "AvgPlatValChangePerTrace(Ex => Flaw)48Hrs, AvgPlatValChangePerTrace(Ex => Rad)48Hrs, AvgPlatValChangePerTrace(Flaw => Rad)48Hrs,"
					+ "AvgPlatValChangePerTrace(Int => Ex)90Days, AvgPlatValChangePerTrace(Int => Flaw)90Days, AvgPlatValChangePerTrace(Int => Rad)90Days,"
					+ "AvgPlatValChangePerTrace(Ex => Flaw)90Days, AvgPlatValChangePerTrace(Ex => Rad)90Days, AvgPlatValChangePerTrace(Flaw => Rad)90Days,"
					+ "IntShareAvgPlatVal48Hrs,IntShareAvgPlatVal90Days,RadShareAvgPlatValue48Hrs,RadShareAvgPlatValue90Days";

	public final VoidRelicEra relicEra;
	public final String relicName;
	public final boolean isVaulted;
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
		relicName = nameTokens.nextToken();

		isVaulted = getBoolProp(jsonObject, IS_VAULTED);

		relicDrops = new RelicDrops(getJsonArray(jsonObject, RELIC_DROPS), primePartNamesToPartsMap);

		bestAvgDucatRefinement = populateRefinementToAvgDucatValMap();
		bestAvgPlatRefinement48Hrs = populateRefinementToAvgPlatVal48HrsMap();
		bestAvgPlatRefinement90Days = populateRefinementToAvgPlatVal90DaysMap();
	}

	private VoidRelicRefinement populateRefinementToAvgDucatValMap(){
		return populateMapWithAverages(
				new double[]{relicDrops.commonDrops.get(0).ducatVal,
						relicDrops.commonDrops.get(1).ducatVal,
						relicDrops.commonDrops.get(2).ducatVal},
				new double[]{relicDrops.uncommonDrops.get(0).ducatVal,
						relicDrops.uncommonDrops.get(1).ducatVal},
				relicDrops.rareDrop.ducatVal,
				refinementToAvgDucatValMap);
	}

	private VoidRelicRefinement populateRefinementToAvgPlatVal48HrsMap(){
		return populateMapWithAverages(
				new double[]{relicDrops.commonDrops.get(0).avgPlatVal48Hrs,
						relicDrops.commonDrops.get(1).avgPlatVal48Hrs,
						relicDrops.commonDrops.get(2).avgPlatVal48Hrs},
				new double[]{relicDrops.uncommonDrops.get(0).avgPlatVal48Hrs,
						relicDrops.uncommonDrops.get(1).avgPlatVal48Hrs},
				relicDrops.rareDrop.avgPlatVal48Hrs,
				refinementToAvgPlatVal48HrsMap);
	}

	private VoidRelicRefinement populateRefinementToAvgPlatVal90DaysMap(){
		return populateMapWithAverages(
				new double[]{relicDrops.commonDrops.get(0).avgPlatVal90Days,
						relicDrops.commonDrops.get(1).avgPlatVal90Days,
						relicDrops.commonDrops.get(2).avgPlatVal90Days},
				new double[]{relicDrops.uncommonDrops.get(0).avgPlatVal90Days,
						relicDrops.uncommonDrops.get(1).avgPlatVal90Days},
				relicDrops.rareDrop.avgPlatVal90Days,
				refinementToAvgPlatVal90DaysMap);
	}

	private static VoidRelicRefinement populateMapWithAverages(double[] commonVals, double[] uncommonVals, double rareVal,
			Map<VoidRelicRefinement, Double> mapToPopulate){
		//Returns the refinement level with the highest avg val
		VoidRelicRefinement bestRefinement = null;
		double largestVal = 0;

		for(VoidRelicRefinement refinement: VoidRelicRefinement.values()) {
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

		return bestRefinement;
	}

	@Override
	public String getDataSuffix(){
		double avgDucatValInt = refinementToAvgDucatValMap.get(VoidRelicRefinement.INTACT);
		double avgDucatValEx = refinementToAvgDucatValMap.get(VoidRelicRefinement.EXCEPTIONAL);
		double avgDucatValFlaw = refinementToAvgDucatValMap.get(VoidRelicRefinement.FLAWLESS);
		double avgDucatValRad = refinementToAvgDucatValMap.get(VoidRelicRefinement.RADIANT);

		double avgPlatVal48HrsInt = refinementToAvgPlatVal48HrsMap.get(VoidRelicRefinement.INTACT);
		double avgPlatVal48HrsEx = refinementToAvgPlatVal48HrsMap.get(VoidRelicRefinement.EXCEPTIONAL);
		double avgPlatVal48HrsFlaw = refinementToAvgPlatVal48HrsMap.get(VoidRelicRefinement.FLAWLESS);
		double avgPlatVal48HrsRad = refinementToAvgPlatVal48HrsMap.get(VoidRelicRefinement.RADIANT);

		double avgPlatVal90DaysInt = refinementToAvgPlatVal90DaysMap.get(VoidRelicRefinement.INTACT);
		double avgPlatVal90DaysEx = refinementToAvgPlatVal90DaysMap.get(VoidRelicRefinement.EXCEPTIONAL);
		double avgPlatVal90DaysFlaw = refinementToAvgPlatVal90DaysMap.get(VoidRelicRefinement.FLAWLESS);
		double avgPlatVal90DaysRad = refinementToAvgPlatVal90DaysMap.get(VoidRelicRefinement.RADIANT);

		double avgDucatValDiffIntToEx = avgDucatValEx - avgDucatValInt;
		double avgDucatValDiffIntToFlaw = avgDucatValFlaw - avgDucatValInt;
		double avgDucatValDiffIntToRad = avgDucatValRad - avgDucatValInt;
		double avgDucatValDiffExToFlaw = avgDucatValFlaw - avgDucatValEx;
		double avgDucatValDiffExToRad = avgDucatValRad - avgDucatValEx;
		double avgDucatValDiffFlawToRad = avgDucatValRad - avgDucatValFlaw;

		double avgPlatVal48HrsDiffIntToEx = avgPlatVal48HrsEx - avgPlatVal48HrsInt;
		double avgPlatVal48HrsDiffIntToFlaw = avgPlatVal48HrsFlaw - avgPlatVal48HrsInt;
		double avgPlatVal48HrsDiffIntToRad = avgPlatVal48HrsRad - avgPlatVal48HrsInt;
		double avgPlatVal48HrsDiffExToFlaw = avgPlatVal48HrsFlaw - avgPlatVal48HrsEx;
		double avgPlatVal48HrsDiffExToRad = avgPlatVal48HrsRad - avgPlatVal48HrsEx;
		double avgPlatVal48HrsDiffFlawToRad = avgPlatVal48HrsRad - avgPlatVal48HrsFlaw;

		double avgPlatVal90DaysDiffIntToEx = avgPlatVal90DaysEx - avgPlatVal90DaysInt;
		double avgPlatVal90DaysDiffIntToFlaw = avgPlatVal90DaysFlaw - avgPlatVal90DaysInt;
		double avgPlatVal90DaysDiffIntToRad = avgPlatVal90DaysRad - avgPlatVal90DaysInt;
		double avgPlatVal90DaysDiffExToFlaw = avgPlatVal90DaysFlaw - avgPlatVal90DaysEx;
		double avgPlatVal90DaysDiffExToRad = avgPlatVal90DaysRad - avgPlatVal90DaysEx;
		double avgPlatVal90DaysDiffFlawToRad = avgPlatVal90DaysRad - avgPlatVal90DaysFlaw;

		int tracesIntToEx = EXCEPTIONAL.numVoidTracesToUpgrade - INTACT.numVoidTracesToUpgrade;
		int tracesIntToFlaw = FLAWLESS.numVoidTracesToUpgrade - INTACT.numVoidTracesToUpgrade;
		int tracesIntToRad = RADIANT.numVoidTracesToUpgrade - INTACT.numVoidTracesToUpgrade;
		int tracesExToFlaw = FLAWLESS.numVoidTracesToUpgrade - EXCEPTIONAL.numVoidTracesToUpgrade;
		int tracesExToRad = RADIANT.numVoidTracesToUpgrade - EXCEPTIONAL.numVoidTracesToUpgrade;
		int tracesFlawToRad = RADIANT.numVoidTracesToUpgrade - FLAWLESS.numVoidTracesToUpgrade;

		return
				relicDrops.commonDrops.get(0).name + "," + //CommonDrop1
				relicDrops.commonDrops.get(1).name + "," + //CommonDrop2
				relicDrops.commonDrops.get(2).name + "," + //CommonDrop3

			relicDrops.uncommonDrops.get(0).name + "," + //UncommonDrop1
			relicDrops.uncommonDrops.get(1).name + "," + //UncommonDrop2

			relicDrops.rareDrop.name + "," + //RareDrop

			relicDrops.commonDrops.get(0).itemName + "," + //CommonDrop1ItemName
			relicDrops.commonDrops.get(0).partName + "," + //CommonDrop1PartName

			relicDrops.commonDrops.get(1).itemName + "," + //CommonDrop2ItemName
			relicDrops.commonDrops.get(1).partName + "," + //CommonDrop2PartName

			relicDrops.commonDrops.get(2).itemName + "," + //CommonDrop3ItemName
			relicDrops.commonDrops.get(2).partName + "," + //CommonDrop3PartName

			relicDrops.uncommonDrops.get(0).itemName + "," + //UncommonDrop1ItemName
			relicDrops.uncommonDrops.get(0).partName + "," + //UncommonDrop1PartName

			relicDrops.uncommonDrops.get(1).itemName + "," + //UncommonDrop2ItemName
			relicDrops.uncommonDrops.get(1).partName + "," + //UncommonDrop2PartName

			relicDrops.rareDrop.itemName + "," + //RareDropItemName
			relicDrops.rareDrop.partName + "," + //RareDropPartName

		    relicDrops.dropsForma() + "," + //DropsForma
		    relicDrops.dropsFormaCommon + "," + //DropsFormaCommon
		    relicDrops.dropsFormaUncommon + "," + //DropsFormaUncommon

		    isVaulted + "," + //IsVaulted

			avgDucatValInt + "," + //AvgDucatVal(Int)
			avgDucatValEx + "," + //AvgDucatVal(Ex)
			avgDucatValFlaw + "," + //AvgDucatVal(Flaw)
			avgDucatValRad + "," + //AvgDucatVal(Rad)

			avgPlatVal48HrsInt + "," + //AvgPlatVal48Hrs(Int)
			avgPlatVal48HrsEx + "," + //AvgPlatVal48Hrs(Ex)
			avgPlatVal48HrsFlaw + "," + //AvgPlatVal48Hrs(Flaw)
			avgPlatVal48HrsRad + "," + //AvgPlatVal48Hrs(Rad)

			avgPlatVal90DaysInt + "," + //AvgPlatVal90Days(Int)
			avgPlatVal90DaysEx + "," + //AvgPlatVal90Days(Ex)
			avgPlatVal90DaysFlaw + "," + //AvgPlatVal90Days(Flaw)
			avgPlatVal90DaysRad + "," + //AvgPlatVal90Days(Rad)

			bestAvgDucatRefinement.name + "," + //BestAvgDucatRefinement
			bestAvgPlatRefinement48Hrs.name + "," + //BestAvgPlatRefinement48Hrs
			bestAvgPlatRefinement90Days.name + "," + //BestAvgPlatRefinement90Days

			avgDucatValDiffIntToEx + "," + //AvgDucatValChange(Int=>Ex)
			avgDucatValDiffIntToFlaw + "," + //AvgDucatValChange(Int=>Flaw)
			avgDucatValDiffIntToRad + "," + //AvgDucatValChange(Int=>Rad)
			avgDucatValDiffExToFlaw + "," + //AvgDucatValChange(Ex=>Flaw)
			avgDucatValDiffExToRad + "," + //AvgDucatValChange(Ex=>Rad)
			avgDucatValDiffFlawToRad + "," + //AvgDucatValChange(Flaw=>Rad)

			avgPlatVal48HrsDiffIntToEx + "," + //AvgPlatVal48HrsChange(Int=>Ex)
			avgPlatVal48HrsDiffIntToFlaw + "," + //AvgPlatVal48HrsChange(Int=>Flaw)
			avgPlatVal48HrsDiffIntToRad + "," + //AvgPlatVal48HrsChange(Int=>Rad)
			avgPlatVal48HrsDiffExToFlaw + "," + //AvgPlatVal48HrsChange(Ex=>Flaw)
			avgPlatVal48HrsDiffExToRad + "," + //AvgPlatVal48HrsChange(Ex=>Rad)
			avgPlatVal48HrsDiffFlawToRad + "," + //AvgPlatVal48HrsChange(Flaw=>Rad)

			avgPlatVal90DaysDiffIntToEx + "," + //AvgPlatVal90DaysChange(Int=>Ex)
			avgPlatVal90DaysDiffIntToFlaw + "," + //AvgPlatVal90DaysChange(Int=>Flaw)
			avgPlatVal90DaysDiffIntToRad + "," + //AvgPlatVal90DaysChange(Int=>Rad)
			avgPlatVal90DaysDiffExToFlaw + "," + //AvgPlatVal90DaysChange(Ex=>Flaw)
			avgPlatVal90DaysDiffExToRad + "," + //AvgPlatVal90DaysChange(Ex=>Rad)
			avgPlatVal90DaysDiffFlawToRad + "," + //AvgPlatVal90DaysChange(Flaw=>Rad)

			avgDucatValDiffIntToEx/tracesIntToEx + "," + //AvgDucatValChangePerTrace(Int=>Ex)
			avgDucatValDiffIntToFlaw/tracesIntToFlaw + "," + //AvgDucatValChangePerTrace(Int=>Flaw)
			avgDucatValDiffIntToRad/tracesIntToRad + "," + //AvgDucatValChangePerTrace(Int=>Rad)
			avgDucatValDiffExToFlaw/tracesExToFlaw + "," + //AvgDucatValChangePerTrace(Ex=>Flaw)
			avgDucatValDiffExToRad/tracesExToRad + "," + //AvgDucatValChangePerTrace(Ex=>Rad)
			avgDucatValDiffFlawToRad/tracesFlawToRad + "," + //AvgDucatValChangePerTrace(Flaw=>Rad)

			avgPlatVal48HrsDiffIntToEx/tracesIntToEx + "," + //AvgPlatVal48HrsChangePerTrace(Int=>Ex)
			avgPlatVal48HrsDiffIntToFlaw/tracesIntToFlaw + "," + //AvgPlatVal48HrsChangePerTrace(Int=>Flaw)
			avgPlatVal48HrsDiffIntToRad/tracesIntToRad + "," + //AvgPlatVal48HrsChangePerTrace(Int=>Rad)
			avgPlatVal48HrsDiffExToFlaw/tracesExToFlaw + "," + //AvgPlatVal48HrsChangePerTrace(Ex=>Flaw)
			avgPlatVal48HrsDiffExToRad/tracesExToRad + "," + //AvgPlatVal48HrsChangePerTrace(Ex=>Rad)
			avgPlatVal48HrsDiffFlawToRad/tracesFlawToRad + "," + //AvgPlatVal48HrsChangePerTrace(Flaw=>Rad)

			avgPlatVal90DaysDiffIntToEx/tracesIntToEx + "," + //AvgPlatVal90DaysChangePerTrace(Int=>Ex)
			avgPlatVal90DaysDiffIntToFlaw/tracesIntToFlaw + "," + //AvgPlatVal90DaysChangePerTrace(Int=>Flaw)
			avgPlatVal90DaysDiffIntToRad/tracesIntToRad + "," + //AvgPlatVal90DaysChangePerTrace(Int=>Rad)
			avgPlatVal90DaysDiffExToFlaw/tracesExToFlaw + "," + //AvgPlatVal90DaysChangePerTrace(Ex=>Flaw)
			avgPlatVal90DaysDiffExToRad/tracesExToRad + "," + //AvgPlatVal90DaysChangePerTrace(Ex=>Rad)
			avgPlatVal90DaysDiffFlawToRad/tracesFlawToRad + "," + //AvgPlatVal90DaysChangePerTrace(Flaw=>Rad)

			calculateIntShareAvgDucatVal() + "," + //IntShareAvgDucatVal
			calculateIntShareAvgPlatVal48Hrs() + "," + //IntShareAvgPlatVal48Hrs
			calculateIntShareAvgPlatVal90Days() + "," + //IntShareAvgPlatVal90Days
			calculateRadShareAvgDucatVal() + "," + //RadShareAvgDucatVal
			calculateRadShareAvgPlatVal48Hrs() + "," + //RadShareAvgPlatValue48Hrs
			calculateRadShareAvgPlatVal90Days(); //RadShareAvgPlatValue90Days;
	}

	private double calculateIntShareAvgDucatVal(){
		return calculateShareVal(
				new double[]{
						relicDrops.commonDrops.get(0).ducatVal,
						relicDrops.commonDrops.get(1).ducatVal,
						relicDrops.commonDrops.get(2).ducatVal,
						relicDrops.uncommonDrops.get(0).ducatVal,
						relicDrops.uncommonDrops.get(1).ducatVal,
						relicDrops.rareDrop.ducatVal},
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
				new double[]{
						relicDrops.commonDrops.get(0).avgPlatVal48Hrs,
						relicDrops.commonDrops.get(1).avgPlatVal48Hrs,
						relicDrops.commonDrops.get(2).avgPlatVal48Hrs,
						relicDrops.uncommonDrops.get(0).avgPlatVal48Hrs,
						relicDrops.uncommonDrops.get(1).avgPlatVal48Hrs,
						relicDrops.rareDrop.ducatVal},
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
				new double[]{
						relicDrops.commonDrops.get(0).avgPlatVal90Days,
						relicDrops.commonDrops.get(1).avgPlatVal90Days,
						relicDrops.commonDrops.get(2).avgPlatVal90Days,
						relicDrops.uncommonDrops.get(0).avgPlatVal90Days,
						relicDrops.uncommonDrops.get(1).avgPlatVal90Days,
						relicDrops.rareDrop.ducatVal},
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
				new double[]{
						relicDrops.commonDrops.get(0).ducatVal,
						relicDrops.commonDrops.get(1).ducatVal,
						relicDrops.commonDrops.get(2).ducatVal,
						relicDrops.uncommonDrops.get(0).ducatVal,
						relicDrops.uncommonDrops.get(1).ducatVal,
						relicDrops.rareDrop.ducatVal},
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
				new double[]{
						relicDrops.commonDrops.get(0).avgPlatVal48Hrs,
						relicDrops.commonDrops.get(1).avgPlatVal48Hrs,
						relicDrops.commonDrops.get(2).avgPlatVal48Hrs,
						relicDrops.uncommonDrops.get(0).avgPlatVal48Hrs,
						relicDrops.uncommonDrops.get(1).avgPlatVal48Hrs,
						relicDrops.rareDrop.ducatVal},
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
				new double[]{
						relicDrops.commonDrops.get(0).avgPlatVal90Days,
						relicDrops.commonDrops.get(1).avgPlatVal90Days,
						relicDrops.commonDrops.get(2).avgPlatVal90Days,
						relicDrops.uncommonDrops.get(0).avgPlatVal90Days,
						relicDrops.uncommonDrops.get(1).avgPlatVal90Days,
						relicDrops.rareDrop.ducatVal},
				new double[]{
						RADIANT.commonDropChance,
						RADIANT.commonDropChance,
						RADIANT.commonDropChance,
						RADIANT.uncommonDropChance,
						RADIANT.uncommonDropChance,
						RADIANT.rareDropChance});
	}

	private double calculateShareVal(double[] vals, double[] probabilities){
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

	private static String getName(JsonObject jsonObject){
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
		public final double avgPlatVal48Hrs;
		public final double avgPlatVal90Days;

		public RelicDrop(JsonElement relicDrop, Map<String, PrimePart> primePartNamesToPartsMap){
			this((JsonObject)relicDrop, primePartNamesToPartsMap);
		}

		public RelicDrop(JsonObject relicDrop, Map<String, PrimePart> primePartNamesToPartsMap){
			String itemName = getStrProp(relicDrop, DROPPED_ITEM_NAME);
			String partName = getStrProp(relicDrop, DROPPED_ITEM_PART).trim();
			String primePartName = null;

			int ducatVal = 0;
			double avgPlatVal48Hrs = 0;
			double avgPlatVal90Days = 0;

			this.isForma = FORMA.valueEquals(itemName);

			//This is a special case
			if(KAVASA.containsValue(itemName)){
				itemName = "Kavasa Kubrow Collar";

				if(COLLAR.containsValue(partName)){
					partName = BLUEPRINT.value.toUpperCase();
				}

				primePartName = "Kavasa Prime Kubrow Collar Blueprint";
			}
			else{
				//Remove "Blueprint" from parts other than main blueprints (Eg. Chassis/Neuroptics/Systems Blueprints)
				if(BLUEPRINT.endsWithValue(partName) && !BLUEPRINT.valueEquals(partName)){
					partName = partName.substring(0, partName.indexOf(' '));
				}

				if(!isForma){
					primePartName = itemName + " Prime " + partName;
				}
			}

			this.name = primePartName;
			this.itemName = itemName;
			this.partName = partName;
			this.rarity = DropRarity.getByValue(getStrProp(relicDrop, DROPPED_ITEM_RARITY));

			if(primePartName != null){
				PrimePart primePart = primePartNamesToPartsMap.get(primePartName);

				ducatVal = primePart.ducats;
				avgPlatVal48Hrs = primePart.getTradeStats48Hrs().avgPrice;
				avgPlatVal90Days = primePart.getTradeStats90Days().avgPrice;
			}

			this.ducatVal = ducatVal;
			this.avgPlatVal48Hrs = avgPlatVal48Hrs;
			this.avgPlatVal90Days = avgPlatVal90Days;
		}
	}
}
