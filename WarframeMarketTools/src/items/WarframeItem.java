package items;

public abstract class WarframeItem{
	public final String name;

	public WarframeItem(String name){
		this.name = name;
	}

	public WarframeItem(String name, boolean isRanked){
		this.name = name;
	}
	
	public boolean isRanked(){
		return false;
	}
	
	public abstract String getDataSuffix(double avgPrice48Hrs, double avgPrice90Days);
	public abstract String getHeaderSuffix();
	public abstract int getRankToPriceCheck();
}