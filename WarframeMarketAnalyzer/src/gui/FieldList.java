package gui;

import java.awt.Dimension;
import java.util.EnumSet;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.border.BevelBorder;

import enums.fields.IFieldEnum;

public class FieldList<T extends Enum<T> & IFieldEnum> extends JScrollPane {

	private JList<T> fieldList;
	private ListModel<T> fieldModel;
	
	public FieldList(Class<T> enumClass, EnumSet<T> initial) {
		super(constructList(enumClass, initial), VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_AS_NEEDED);
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		setPreferredSize(new Dimension(200, 300));
		
		fieldList = (JList<T>)getViewport().getView();
		fieldModel = fieldList.getModel();

		setVisible(true);
	}
	
	private static <T extends Enum<T> & IFieldEnum> JList<T> constructList(Class<T> enumClass, EnumSet<T> initial) {
		DefaultListModel<T> model = new DefaultListModel<>();
		for (T field : enumClass.getEnumConstants()) {
			if (initial.contains(field)) {
				model.addElement(field);
			}
		}		
		return new JList<>(model);
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		fieldList.setEnabled(enabled);
	}
}
