package utils;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class TokenList extends ArrayList<String>{
	private static final String NOT_APPLICABLE = "N/A";
	private static final char CSV_DELIMITER = ',';
	
	public TokenList(){
		super();
	}
	
	public void add(Integer token){
		if(token == null) addNull();
		else super.add(token.toString());
	}
	
	public void add(Double token){
		if(token == null) addNull();
		else super.add(token.toString());
	}
	
	public void add(Boolean token){
		if(token == null) addNull();
		else super.add(token.toString());
	}
	
	public void addNull(){
		super.add(null);
	}
	
	public String toCSV(){
		String output = "";
		
		for(int i = 0; i < size(); ++i){
			if(i != 0){
				output += CSV_DELIMITER;
			}
			
			String token = get(i);
			output += (token == null) ? NOT_APPLICABLE : token;
		}
		
		return output;
	}
}