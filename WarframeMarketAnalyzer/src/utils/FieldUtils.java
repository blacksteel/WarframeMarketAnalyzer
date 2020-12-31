package utils;

import java.util.ArrayList;
import java.util.List;

import enums.fields.IFieldEnum;

public class FieldUtils {

	public static <T extends Enum<T> & IFieldEnum> String fieldListToHeader(List<T> fields) {
		String header = "";
		if (fields != null && fields.size() > 0) {
			for (T field : fields) {
				header += field.getDisplayName() + ",";
			}
			header = header.substring(0, header.length() - 1);
		}
		return header;
	}

	public static <T extends Enum<T> & IFieldEnum> List<T> defaultFields(Class<T> enumCLass) {
		List<T> defaultList = new ArrayList<>();
		for (T enumVal : enumCLass.getEnumConstants()) {
			if (enumVal.isIncudedByDefault()) {
				defaultList.add(enumVal);
			}
		}
		return defaultList;
	}

	public static <T extends Enum<T> & IFieldEnum> List<T> nonDefaultFields(Class<T> enumCLass) {
		List<T> nonDefaultList = new ArrayList<>();
		for (T enumVal : enumCLass.getEnumConstants()) {
			if (!enumVal.isIncudedByDefault()) {
				nonDefaultList.add(enumVal);
			}
		}
		return nonDefaultList;
	}
}
