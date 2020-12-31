package items;

import static enums.VoidRelicRefinement.EXCEPTIONAL;
import static enums.VoidRelicRefinement.FLAWLESS;
import static enums.VoidRelicRefinement.INTACT;
import static enums.VoidRelicRefinement.RADIANT;
import static enums.comparable.MiscWarframeTerms.BLUEPRINT;
import static enums.comparable.MiscWarframeTerms.COLLAR;
import static enums.comparable.MiscWarframeTerms.FORMA;
import static enums.comparable.MiscWarframeTerms.KAVASA;
import static enums.jsonProps.WarframeWikiPropName.DROPPED_ITEM_NAME;
import static enums.jsonProps.WarframeWikiPropName.DROPPED_ITEM_PART;
import static enums.jsonProps.WarframeWikiPropName.DROPPED_ITEM_RARITY;
import static enums.jsonProps.WarframeWikiPropName.IS_BARO_EXCLUSIVE;
import static enums.jsonProps.WarframeWikiPropName.IS_VAULTED;
import static enums.jsonProps.WarframeWikiPropName.RELIC_DROPS;
import static enums.jsonProps.WarframeWikiPropName.RELIC_ERA;
import static enums.jsonProps.WarframeWikiPropName.RELIC_NAME;
import static utils.JSONUtils.getIntProp;
import static utils.JSONUtils.getJsonArray;
import static utils.JSONUtils.getStrProp;
import static utils.MiscUtils.isMissingData;
import static utils.MiscUtils.trimAndCapitalizeCorrectly;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dataSourceHandlers.WarframeMarketHandler;
import dataSourceHandlers.WarframeMarketHandler.TradeStatsPair;
import enums.VoidRelicRefinement;
import enums.comparable.DropRarity;
import enums.fields.RelicFieldEnum;
import main.results.TypeResults;
import utils.MiscUtils;

public class VoidRelic extends WarframeItem<RelicFieldEnum>{

	public VoidRelic(JsonObject jsonObject, Map<String, PrimePart> primePartNamesToPartsMap, TypeResults<RelicFieldEnum> results){
		super(results);
		setResult(RelicFieldEnum.Name,  getStrProp(jsonObject, RELIC_NAME));
		setResult(RelicFieldEnum.Era,  getStrProp(jsonObject, RELIC_ERA));
		setResult(RelicFieldEnum.NameEra,  getName(jsonObject));

		boolean isVaulted = jsonObject.has(IS_VAULTED.value) ? (getIntProp(jsonObject, IS_VAULTED) == 1) : true;
		setResult(RelicFieldEnum.IsVaulted,isVaulted);

		boolean isBaroExcludsive = jsonObject.has(IS_BARO_EXCLUSIVE.value) ? (getIntProp(jsonObject, IS_BARO_EXCLUSIVE) == 1) : false;
		setResult(RelicFieldEnum.IsBaroExclusive,isBaroExcludsive);

		RelicDrops relicDrops = new RelicDrops(getJsonArray(jsonObject, RELIC_DROPS), primePartNamesToPartsMap);

		RefinementMap refinementToAvgDucatValMap = populateRefinementToAvgDucatValMap(relicDrops);
		RefinementMap refinementToAvgPlatVal48HrsMap = populateRefinementToAvgPlatVal48HrsMap(relicDrops);
		RefinementMap refinementToAvgPlatVal90DaysMap = populateRefinementToAvgPlatVal90DaysMap(relicDrops);

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

		Double avgPlatVal48HrsDiffIntToEx = MiscUtils.subtractWithNullCheck(avgPlatVal48HrsEx, avgPlatVal48HrsInt);
		Double avgPlatVal48HrsDiffIntToFlaw = MiscUtils.subtractWithNullCheck(avgPlatVal48HrsFlaw, avgPlatVal48HrsInt);
		Double avgPlatVal48HrsDiffIntToRad = MiscUtils.subtractWithNullCheck(avgPlatVal48HrsRad, avgPlatVal48HrsInt);
		Double avgPlatVal48HrsDiffExToFlaw = MiscUtils.subtractWithNullCheck(avgPlatVal48HrsFlaw, avgPlatVal48HrsEx);
		Double avgPlatVal48HrsDiffExToRad = MiscUtils.subtractWithNullCheck(avgPlatVal48HrsRad, avgPlatVal48HrsEx);
		Double avgPlatVal48HrsDiffFlawToRad = MiscUtils.subtractWithNullCheck(avgPlatVal48HrsRad, avgPlatVal48HrsFlaw);

		Double avgPlatVal90DaysDiffIntToEx = MiscUtils.subtractWithNullCheck(avgPlatVal90DaysEx, avgPlatVal90DaysInt);
		Double avgPlatVal90DaysDiffIntToFlaw = MiscUtils.subtractWithNullCheck(avgPlatVal90DaysFlaw, avgPlatVal90DaysInt);
		Double avgPlatVal90DaysDiffIntToRad = MiscUtils.subtractWithNullCheck(avgPlatVal90DaysRad, avgPlatVal90DaysInt);
		Double avgPlatVal90DaysDiffExToFlaw = MiscUtils.subtractWithNullCheck(avgPlatVal90DaysFlaw, avgPlatVal90DaysEx);
		Double avgPlatVal90DaysDiffExToRad = MiscUtils.subtractWithNullCheck(avgPlatVal90DaysRad, avgPlatVal90DaysEx);
		Double avgPlatVal90DaysDiffFlawToRad = MiscUtils.subtractWithNullCheck(avgPlatVal90DaysRad, avgPlatVal90DaysFlaw);

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

		Double avgPlatVal48HrsDiffPerTraceIntToEx = MiscUtils.divideWithNullCheck(avgPlatVal48HrsDiffIntToEx, tracesIntToEx);
		Double avgPlatVal48HrsDiffPerTraceIntToFlaw = MiscUtils.divideWithNullCheck(avgPlatVal48HrsDiffIntToFlaw, tracesIntToFlaw);
		Double avgPlatVal48HrsDiffPerTraceIntToRad = MiscUtils.divideWithNullCheck(avgPlatVal48HrsDiffIntToRad, tracesIntToRad);
		Double avgPlatVal48HrsDiffPerTraceExToFlaw = MiscUtils.divideWithNullCheck(avgPlatVal48HrsDiffExToFlaw, tracesExToFlaw);
		Double avgPlatVal48HrsDiffPerTraceExToRad = MiscUtils.divideWithNullCheck(avgPlatVal48HrsDiffExToRad, tracesExToRad);
		Double avgPlatVal48HrsDiffPerTraceFlawToRad = MiscUtils.divideWithNullCheck(avgPlatVal48HrsDiffFlawToRad, tracesFlawToRad);

		Double avgPlatVal90DaysDiffPerTraceIntToEx = MiscUtils.divideWithNullCheck(avgPlatVal90DaysDiffIntToEx, tracesIntToEx);
		Double avgPlatVal90DaysDiffPerTraceIntToFlaw = MiscUtils.divideWithNullCheck(avgPlatVal90DaysDiffIntToFlaw, tracesIntToFlaw);
		Double avgPlatVal90DaysDiffPerTraceIntToRad = MiscUtils.divideWithNullCheck(avgPlatVal90DaysDiffIntToRad, tracesIntToRad);
		Double avgPlatVal90DaysDiffPerTraceExToFlaw = MiscUtils.divideWithNullCheck(avgPlatVal90DaysDiffExToFlaw, tracesExToFlaw);
		Double avgPlatVal90DaysDiffPerTraceExToRad = MiscUtils.divideWithNullCheck(avgPlatVal90DaysDiffExToRad, tracesExToRad);
		Double avgPlatVal90DaysDiffPerTraceFlawToRad = MiscUtils.divideWithNullCheck(avgPlatVal90DaysDiffFlawToRad, tracesFlawToRad);

		Double intShareAvgDucatVal = calculateIntShareAvgDucatVal(relicDrops);
		Double intShareAvgPlatVal48Hrs = calculateIntShareAvgPlatVal48Hrs(relicDrops);
		Double intShareAvgPlatVal90Days = calculateIntShareAvgPlatVal90Days(relicDrops);

		Double radShareAvgDucatVal = calculateRadShareAvgDucatVal(relicDrops);
		Double radShareAvgPlatVal48Hrs = calculateRadShareAvgPlatVal48Hrs(relicDrops);
		Double radShareAvgPlatVal90Days = calculateRadShareAvgPlatVal90Days(relicDrops);

		setResult(RelicFieldEnum.CommonDrop1, commonDrop1.name);
		setResult(RelicFieldEnum.CommonDrop1DucatVal, commonDrop1.ducatVal);
		setResult(RelicFieldEnum.CommonDrop1AvgPlatVal48Hrs, commonDrop1.avgPlatVal48Hrs);
		setResult(RelicFieldEnum.CommonDrop1AvgPlatVal90Days, commonDrop1.avgPlatVal90Days);

		setResult(RelicFieldEnum.CommonDrop2, commonDrop2.name);
		setResult(RelicFieldEnum.CommonDrop2DucatVal, commonDrop2.ducatVal);
		setResult(RelicFieldEnum.CommonDrop2AvgPlatVal48Hrs, commonDrop2.avgPlatVal48Hrs);
		setResult(RelicFieldEnum.CommonDrop2AvgPlatVal90Days, commonDrop2.avgPlatVal90Days);

		setResult(RelicFieldEnum.CommonDrop3, commonDrop3.name);
		setResult(RelicFieldEnum.CommonDrop3DucatVal, commonDrop3.ducatVal);
		setResult(RelicFieldEnum.CommonDrop3AvgPlatVal48Hrs, commonDrop3.avgPlatVal48Hrs);
		setResult(RelicFieldEnum.CommonDrop3AvgPlatVal90Days, commonDrop3.avgPlatVal90Days);

		setResult(RelicFieldEnum.UncommonDrop1, uncommonDrop1.name);
		setResult(RelicFieldEnum.UncommonDrop1DucatVal, uncommonDrop1.ducatVal);
		setResult(RelicFieldEnum.UncommonDrop1AvgPlatVal48Hrs, uncommonDrop1.avgPlatVal48Hrs);
		setResult(RelicFieldEnum.UncommonDrop1AvgPlatVal90Days, uncommonDrop1.avgPlatVal90Days);

		setResult(RelicFieldEnum.UncommonDrop2, uncommonDrop2.name);
		setResult(RelicFieldEnum.UncommonDrop2DucatVal, uncommonDrop2.ducatVal);
		setResult(RelicFieldEnum.UncommonDrop2AvgPlatVal48Hrs, uncommonDrop2.avgPlatVal48Hrs);
		setResult(RelicFieldEnum.UncommonDrop2AvgPlatVal90Days, uncommonDrop2.avgPlatVal90Days);

		setResult(RelicFieldEnum.RareDrop, rareDrop.name);
		setResult(RelicFieldEnum.RareDropDucatVal, rareDrop.ducatVal);
		setResult(RelicFieldEnum.RareDropAvgPlatVal48Hrs, rareDrop.avgPlatVal48Hrs);
		setResult(RelicFieldEnum.RareDropAvgPlatVal90Days, rareDrop.avgPlatVal90Days);

		setResult(RelicFieldEnum.CommonDrop1ItemName, commonDrop1.itemName);
		setResult(RelicFieldEnum.CommonDrop1PartName, commonDrop1.partName);

		setResult(RelicFieldEnum.CommonDrop2ItemName, commonDrop2.itemName);
		setResult(RelicFieldEnum.CommonDrop2PartName, commonDrop2.partName);

		setResult(RelicFieldEnum.CommonDrop3ItemName, commonDrop3.itemName);
		setResult(RelicFieldEnum.CommonDrop3PartName, commonDrop3.partName);

		setResult(RelicFieldEnum.UncommonDrop1ItemName, uncommonDrop1.itemName);
		setResult(RelicFieldEnum.UncommonDrop1PartName, uncommonDrop1.partName);

		setResult(RelicFieldEnum.UncommonDrop2ItemName, uncommonDrop2.itemName);
		setResult(RelicFieldEnum.UncommonDrop2PartName, uncommonDrop2.partName);

		setResult(RelicFieldEnum.RareDropItemName, rareDrop.itemName);
		setResult(RelicFieldEnum.RareDropPartName, rareDrop.itemName);

		setResult(RelicFieldEnum.DropsForma, relicDrops.dropsForma());
		setResult(RelicFieldEnum.DropsFormaCommon, relicDrops.dropsFormaCommon);
		setResult(RelicFieldEnum.DropsFormaUncommon, relicDrops.dropsFormaUncommon);

		setResult(RelicFieldEnum.AvgDucatValInt, avgDucatValInt);
		setResult(RelicFieldEnum.AvgDucatValEx, avgDucatValEx);
		setResult(RelicFieldEnum.AvgDucatValFlaw, avgDucatValFlaw);
		setResult(RelicFieldEnum.AvgDucatValRad, avgDucatValRad);

		setResult(RelicFieldEnum.AvgPlatVal48HrsInt, avgPlatVal48HrsInt);
		setResult(RelicFieldEnum.AvgPlatVal48HrsEx, avgPlatVal48HrsEx);
		setResult(RelicFieldEnum.AvgPlatVal48HrsFlaw, avgPlatVal48HrsFlaw);
		setResult(RelicFieldEnum.AvgPlatVal48HrsRad, avgPlatVal48HrsRad);

		setResult(RelicFieldEnum.AvgPlatVal90DaysInt, avgPlatVal90DaysInt);
		setResult(RelicFieldEnum.AvgPlatVal90DaysEx, avgPlatVal90DaysEx);
		setResult(RelicFieldEnum.AvgPlatVal90DaysFlaw, avgPlatVal90DaysFlaw);
		setResult(RelicFieldEnum.AvgPlatVal90DaysRad, avgPlatVal90DaysRad);

		setResult(RelicFieldEnum.BestAvgDucatRefinement, (refinementToAvgDucatValMap.getBestValue() == null) ?
				null : trimAndCapitalizeCorrectly(refinementToAvgDucatValMap.getBestValue().name));
		setResult(RelicFieldEnum.BestAvgPlatRefinement48Hrs, (refinementToAvgPlatVal48HrsMap.getBestValue() == null) ?
				null : trimAndCapitalizeCorrectly(refinementToAvgPlatVal48HrsMap.getBestValue().name));
		setResult(RelicFieldEnum.BestAvgPlatRefinement90Days, (refinementToAvgPlatVal90DaysMap.getBestValue() == null) ?
				null : trimAndCapitalizeCorrectly(refinementToAvgPlatVal90DaysMap.getBestValue().name));

		setResult(RelicFieldEnum.AvgDucatValChangeIntEx, avgDucatValDiffIntToEx);
		setResult(RelicFieldEnum.AvgDucatValChangeIntFlaw, avgDucatValDiffIntToFlaw);
		setResult(RelicFieldEnum.AvgDucatValChangeIntRad, avgDucatValDiffIntToRad);
		setResult(RelicFieldEnum.AvgDucatValChangeExFlaw, avgDucatValDiffExToFlaw);
		setResult(RelicFieldEnum.AvgDucatValChangeExRad, avgDucatValDiffExToRad);
		setResult(RelicFieldEnum.AvgDucatValChangeFlawRad, avgDucatValDiffFlawToRad);

		setResult(RelicFieldEnum.AvgPlatVal48HrsChangeIntEx, avgPlatVal48HrsDiffIntToEx);
		setResult(RelicFieldEnum.AvgPlatVal48HrsChangeIntFlaw, avgPlatVal48HrsDiffIntToFlaw);
		setResult(RelicFieldEnum.AvgPlatVal48HrsChangeIntRad, avgPlatVal48HrsDiffIntToRad);
		setResult(RelicFieldEnum.AvgPlatVal48HrsChangeExFlaw, avgPlatVal48HrsDiffExToFlaw);
		setResult(RelicFieldEnum.AvgPlatVal48HrsChangeExRad, avgPlatVal48HrsDiffExToRad);
		setResult(RelicFieldEnum.AvgPlatVal48HrsChangeFlawRad, avgPlatVal48HrsDiffFlawToRad);

		setResult(RelicFieldEnum.AvgPlatVal90DaysChangeIntEx, avgPlatVal90DaysDiffIntToEx);
		setResult(RelicFieldEnum.AvgPlatVal90DaysChangeIntFlaw, avgPlatVal90DaysDiffIntToFlaw);
		setResult(RelicFieldEnum.AvgPlatVal90DaysChangeIntRad, avgPlatVal90DaysDiffIntToRad);
		setResult(RelicFieldEnum.AvgPlatVal90DaysChangeExFlaw, avgPlatVal90DaysDiffExToFlaw);
		setResult(RelicFieldEnum.AvgPlatVal90DaysChangeExRad, avgPlatVal90DaysDiffExToRad);
		setResult(RelicFieldEnum.AvgPlatVal90DaysChangeFlawRad, avgPlatVal90DaysDiffFlawToRad);

		setResult(RelicFieldEnum.AvgDucatValChangePerTraceIntEx, avgDucatValDiffPerTraceIntToEx);
		setResult(RelicFieldEnum.AvgDucatValChangePerTraceIntFlaw, avgDucatValDiffPerTraceIntToFlaw);
		setResult(RelicFieldEnum.AvgDucatValChangePerTraceIntRad, avgDucatValDiffPerTraceIntToRad);
		setResult(RelicFieldEnum.AvgDucatValChangePerTraceExFlaw, avgDucatValDiffPerTraceExToFlaw);
		setResult(RelicFieldEnum.AvgDucatValChangePerTraceExRad, avgDucatValDiffPerTraceExToRad);
		setResult(RelicFieldEnum.AvgDucatValChangePerTraceFlawRad, avgDucatValDiffPerTraceFlawToRad);

		setResult(RelicFieldEnum.AvgPlatVal48HrsChangePerTraceIntEx, avgPlatVal48HrsDiffPerTraceIntToEx);
		setResult(RelicFieldEnum.AvgPlatVal48HrsChangePerTraceIntFlaw, avgPlatVal48HrsDiffPerTraceIntToFlaw);
		setResult(RelicFieldEnum.AvgPlatVal48HrsChangePerTraceIntRad, avgPlatVal48HrsDiffPerTraceIntToRad);
		setResult(RelicFieldEnum.AvgPlatVal48HrsChangePerTraceExFlaw, avgPlatVal48HrsDiffPerTraceExToFlaw);
		setResult(RelicFieldEnum.AvgPlatVal48HrsChangePerTraceExRad, avgPlatVal48HrsDiffPerTraceExToRad);
		setResult(RelicFieldEnum.AvgPlatVal48HrsChangePerTraceFlawRad, avgPlatVal48HrsDiffPerTraceFlawToRad);

		setResult(RelicFieldEnum.AvgPlatVal90DaysChangePerTraceIntEx, avgPlatVal90DaysDiffPerTraceIntToEx);
		setResult(RelicFieldEnum.AvgPlatVal90DaysChangePerTraceIntFlaw, avgPlatVal90DaysDiffPerTraceIntToFlaw);
		setResult(RelicFieldEnum.AvgPlatVal90DaysChangePerTraceIntRad, avgPlatVal90DaysDiffPerTraceIntToRad);
		setResult(RelicFieldEnum.AvgPlatVal90DaysChangePerTraceExFlaw, avgPlatVal90DaysDiffPerTraceExToFlaw);
		setResult(RelicFieldEnum.AvgPlatVal90DaysChangePerTraceExRad, avgPlatVal90DaysDiffPerTraceExToRad);
		setResult(RelicFieldEnum.AvgPlatVal90DaysChangePerTraceFlawRad, avgPlatVal90DaysDiffPerTraceFlawToRad);

		setResult(RelicFieldEnum.IntShareAvgDucatVal, intShareAvgDucatVal);
		setResult(RelicFieldEnum.IntShareAvgPlatVal48Hrs, intShareAvgPlatVal48Hrs);
		setResult(RelicFieldEnum.IntShareAvgPlatVal90Days, intShareAvgPlatVal90Days);

		setResult(RelicFieldEnum.RadShareAvgDucatVal, radShareAvgDucatVal);
		setResult(RelicFieldEnum.RadShareAvgPlatValue48Hrs, radShareAvgPlatVal48Hrs);
		setResult(RelicFieldEnum.RadShareAvgPlatValue90Days, radShareAvgPlatVal90Days);
	}

	private RefinementMap populateRefinementToAvgDucatValMap(RelicDrops relicDrops){
		return populateMapWithAverages(
				new Double[]{(double)relicDrops.commonDrops.get(0).ducatVal,
						(double)relicDrops.commonDrops.get(1).ducatVal,
						(double)relicDrops.commonDrops.get(2).ducatVal},
				new Double[]{(double)relicDrops.uncommonDrops.get(0).ducatVal,
						(double)relicDrops.uncommonDrops.get(1).ducatVal},
				(double)relicDrops.rareDrop.ducatVal);
	}

	private RefinementMap populateRefinementToAvgPlatVal48HrsMap(RelicDrops relicDrops){
		return populateMapWithAverages(
				new Double[]{relicDrops.commonDrops.get(0).avgPlatVal48Hrs,
						relicDrops.commonDrops.get(1).avgPlatVal48Hrs,
						relicDrops.commonDrops.get(2).avgPlatVal48Hrs},
				new Double[]{relicDrops.uncommonDrops.get(0).avgPlatVal48Hrs,
						relicDrops.uncommonDrops.get(1).avgPlatVal48Hrs},
				relicDrops.rareDrop.avgPlatVal48Hrs);
	}

	private RefinementMap populateRefinementToAvgPlatVal90DaysMap(RelicDrops relicDrops){
		return populateMapWithAverages(
				new Double[]{relicDrops.commonDrops.get(0).avgPlatVal90Days,
						relicDrops.commonDrops.get(1).avgPlatVal90Days,
						relicDrops.commonDrops.get(2).avgPlatVal90Days},
				new Double[]{relicDrops.uncommonDrops.get(0).avgPlatVal90Days,
						relicDrops.uncommonDrops.get(1).avgPlatVal90Days},
				relicDrops.rareDrop.avgPlatVal90Days);
	}

	private static RefinementMap populateMapWithAverages(Double[] commonVals, Double[] uncommonVals, Double rareVal){
		RefinementMap mapToPopulate = new RefinementMap();

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
					mapToPopulate.setBestValue(refinement);
					largestVal = avgVal;
				}
			}
		}

		return mapToPopulate;
	}

	private Double calculateIntShareAvgDucatVal(RelicDrops relicDrops){
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

	private Double calculateIntShareAvgPlatVal48Hrs(RelicDrops relicDrops){
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

	private Double calculateIntShareAvgPlatVal90Days(RelicDrops relicDrops){
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

	private Double calculateRadShareAvgDucatVal(RelicDrops relicDrops){
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

	private Double calculateRadShareAvgPlatVal48Hrs(RelicDrops relicDrops){
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

	private Double calculateRadShareAvgPlatVal90Days(RelicDrops relicDrops){
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

	private static String getName(JsonObject jsonObject){
		String era = getStrProp(jsonObject, RELIC_ERA);
		String name = getStrProp(jsonObject, RELIC_NAME);

		return era + " " + name;
	}

	@Override
	public void populateTradeStats(WarframeMarketHandler marketHandler) throws IOException, InterruptedException{
		for(VoidRelicRefinement refinement: VoidRelicRefinement.values()){
			String relicFullName = getName() + " " + refinement.name;
			TradeStatsPair tradeStats =
					marketHandler.getAndProcessTradeData(relicFullName, isRanked(), getRankToPriceCheck());

			setResult(refinement.HrNum48, tradeStats.stats48Hrs.numSales);
			if (tradeStats.stats48Hrs.numSales > 0) {
				setResult(refinement.HrAvg48, tradeStats.stats48Hrs.avgPrice);
				setResult(refinement.HrHigh48, tradeStats.stats48Hrs.maxPrice);
				setResult(refinement.HrLow48, tradeStats.stats48Hrs.minPrice);
			} else {
				setResult(refinement.HrAvg48, NA);
				setResult(refinement.HrHigh48, NA);
				setResult(refinement.HrLow48, NA);
			}

			setResult(refinement.DayNum90, tradeStats.stats90Days.numSales);
			if (tradeStats.stats90Days.numSales > 0) {
				setResult(refinement.DayAvg90, tradeStats.stats90Days.avgPrice);
				setResult(refinement.DayHigh90, tradeStats.stats90Days.maxPrice);
				setResult(refinement.DayLow90, tradeStats.stats90Days.minPrice);
			} else {
				setResult(refinement.DayAvg90, NA);
				setResult(refinement.DayHigh90, NA);
				setResult(refinement.DayLow90, NA);
			}
		}
	}

//	@Override
//	public VoidRelicTradeStats getTradeStats48Hrs(){
//		return (VoidRelicTradeStats)tradeStats48Hrs;
//	}
//
//	@Override
//	public VoidRelicTradeStats getTradeStats90Days(){
//		return (VoidRelicTradeStats)tradeStats90Days;
//	}

	private static class RefinementMap extends HashMap<VoidRelicRefinement, Double> {
		private VoidRelicRefinement bestValue = null;

		public void setBestValue(VoidRelicRefinement bestValue) {
			this.bestValue = bestValue;
		}

		public VoidRelicRefinement getBestValue() {
			return bestValue;
		}
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

				this.ducatVal = primePart.getDucats();
				this.avgPlatVal48Hrs = (primePart.getTradeStats().stats48Hrs.numSales > 0) ? primePart.getTradeStats().stats48Hrs.avgPrice : null;
				this.avgPlatVal90Days = (primePart.getTradeStats().stats90Days.numSales > 0) ? primePart.getTradeStats().stats90Days.avgPrice : null;
			}
			else{
				this.ducatVal = 0;
				this.avgPlatVal48Hrs = 0.0;
				this.avgPlatVal90Days = 0.0;
			}
		}
	}
}