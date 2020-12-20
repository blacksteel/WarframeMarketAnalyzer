package enums.comparable.interfaces;

public interface ComparableByStringValueIgnoreCase extends ComparableByValue<String>{
	
	@Override
	default public boolean valueEquals(String string){
		return getValue().equalsIgnoreCase(string);
	}
	
	default public boolean containsValue(String string){
		return string.toLowerCase().contains(getValue().toLowerCase());
	}
	
	default public boolean endsWithValue(String string){
		return string.toLowerCase().endsWith(getValue().toLowerCase());
	}
}
