package utils;

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
}
