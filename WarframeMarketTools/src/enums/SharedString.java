package enums;

public enum SharedString{
	NOT_APPLICABLE("N/A");
	
	public final String value;
	
	private SharedString(String value){
		this.value = value;
	}
}
