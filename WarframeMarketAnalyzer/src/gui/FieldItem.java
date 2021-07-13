package gui;

import enums.fields.IFieldEnum;

public class FieldItem<T extends Enum<T> & IFieldEnum> {

	private final T fieldEnum;
	private String displayString = null;

	public FieldItem(T fieldEnum) {
		this.fieldEnum = fieldEnum;
	}

	public void setDisplayString(String displayString) {
		this.displayString = displayString;
	}

	@Override
	public String toString() {
		if (displayString != null) {
			return displayString;
		} else {
			return fieldEnum.getDisplayName();
		}
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof FieldItem && fieldEnum == ((FieldItem)other).fieldEnum;
	}

	public T getEnum() {
		return fieldEnum;
	}

	public boolean equalsEnum(T fieldEnum) {
		return this.fieldEnum == fieldEnum;
	}
}
