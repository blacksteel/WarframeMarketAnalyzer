package utils;

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
}
