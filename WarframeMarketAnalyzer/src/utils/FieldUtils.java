package utils;

import java.util.ArrayList;
import java.util.List;

import enums.fields.IFieldEnum;
import gui.FieldItem;

public class FieldUtils {

	public static <T extends Enum<T> & IFieldEnum> String fieldListToHeader(List<FieldItem<T>> fields) {
		String header = "";
		if (fields != null && fields.size() > 0) {
			for (FieldItem<T> field : fields) {
				header += field + ",";
			}
			header = header.substring(0, header.length() - 1);
		}
		return header;
	}

	public static <T extends Enum<T> & IFieldEnum> List<FieldItem<T>> defaultFields(Class<T> enumCLass) {
		List<FieldItem<T>> defaultList = new ArrayList<>();
		for (T enumVal : enumCLass.getEnumConstants()) {
			if (enumVal.isIncudedByDefault()) {
				defaultList.add(new FieldItem<T>(enumVal));
			}
		}
		return defaultList;
	}

	public static <T extends Enum<T> & IFieldEnum> List<FieldItem<T>> nonDefaultFields(Class<T> enumCLass) {
		List<FieldItem<T>> nonDefaultList = new ArrayList<>();
		for (T enumVal : enumCLass.getEnumConstants()) {
			if (!enumVal.isIncudedByDefault()) {
				nonDefaultList.add(new FieldItem<T>(enumVal));
			}
		}
		return nonDefaultList;
	}
}
