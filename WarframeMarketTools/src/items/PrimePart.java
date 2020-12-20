package items;

import static utils.MiscUtils.trimTo3DecimalPlaces;

import enums.SharedString;

public class PrimePart extends WarframeItem{
	private static final String DATA_HEADER_SUFFIX = "ItemName,PartName,Type,IsVaulted,IsFullSet,NumInFullSet,Ducats,DucatsPerPlat(48HrsAvgPrice),DucatsPerPlat(90DaysAvgPrice)";

	public final String itemName;
	public final String partName;
	public final String type;
	public final boolean isVaulted;
	public final boolean isFullSet;
	public final Integer numInFullSet;
	public final Integer ducats;

	public PrimePart(String itemName, String partName, String type, boolean isVaulted, boolean isFullSet, Integer numInFullSet, Integer ducats){
		super(itemName + " " + partName);

		this.itemName = itemName;
		this.partName = partName;
		this.type = type;
		this.isVaulted = isVaulted;
		this.isFullSet = isFullSet;
		this.numInFullSet = numInFullSet;
		this.ducats = ducats;
	}

	@Override
	public String getDataSuffix(double avgPrice48Hrs, double avgPrice90Days){
		return itemName +
				"," + partName +
				"," + type +
				"," + isVaulted +
				"," + isFullSet +
				"," + (numInFullSet == null ? SharedString.NOT_APPLICABLE.value : numInFullSet) +
				"," + ducats +
				"," + trimTo3DecimalPlaces(ducats/avgPrice48Hrs) +
				"," + trimTo3DecimalPlaces(ducats/avgPrice90Days);
	}

	@Override
	public String getHeaderSuffix(){
		return DATA_HEADER_SUFFIX;
	}

	@Override
	public int getRankToPriceCheck(){
		return -1;
	}
}