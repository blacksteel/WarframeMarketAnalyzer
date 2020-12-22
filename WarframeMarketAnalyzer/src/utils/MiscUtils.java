package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class MiscUtils {
	public static String padString(String string, int desiredLength) {
		for(int i = string.length(); i < desiredLength; ++i) {
			string += ' ';
		}

		return string;
	}

	public static String evenlyPadString(String string, int desiredLength){
		int stringLength = string.length();
		int paddingAmount = desiredLength - stringLength;
		int prePad = paddingAmount/2;
		int postPad = paddingAmount/2;
		if(paddingAmount%2 == 1){
			++postPad;
		}

		for(int i = 0; i < prePad; ++i) {
			string = ' ' + string;
		}

		for(int i = 0; i < postPad; ++i) {
			string += ' ';
		}

		return string;
	}

	public static double trimTo3DecimalPlaces(double input) {
		input *= 1000;
		input = Math.floor(input);
		return input/1000;
	}
	
	public static String trimAndCapitalizeCorrectly(String string){
		String retVal = "";
		StringTokenizer nameTokens = new StringTokenizer(string);
		
		while(nameTokens.hasMoreTokens()){
			String token = nameTokens.nextToken().toLowerCase();
			token = token.substring(0, 1).toUpperCase() + token.substring(1);
			
			retVal += token + " ";
		}
		
		return retVal.trim();
	}
	
	public static boolean isMissingData(Double[] vals){
		for(Double val: vals){
			if(val == null) return true;
		}
		
		return false;
	}
	
	public static Double subtractWithNullCheck(Double a, Double b){
		if(a == null || b == null) return null;
		return a - b;
	}
	
	public static Double divideWithNullCheck(Double a, Integer b){
		if(a == null || b == null) return null;
		return a/b;
	}
	
	public static String getDate(long timeInMillis){
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy HH:mm");    
		Date resultdate = new Date(timeInMillis);
		return sdf.format(resultdate);
	}
}