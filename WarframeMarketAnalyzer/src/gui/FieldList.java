package gui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

import enums.fields.IFieldEnum;

public class FieldList<T extends Enum<T> & IFieldEnum> extends JPanel {

	private Class<T> enumClass;
	private JList<T> fieldList;
	private DefaultListModel<T> fieldModel;

	public FieldList(String label, Class<T> enumClass, List<T> initial) {
		this.enumClass = enumClass;
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		add(new JLabel(label));

		fieldModel = new DefaultListModel<>();
		for (T field : initial) {
			fieldModel.addElement(field);
		}
		fieldList = new JList<>(fieldModel);

		JScrollPane scroll = new JScrollPane(fieldList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		scroll.setPreferredSize(new Dimension(200, 300));

		add(scroll);

		setVisible(true);
	}

	public void removeFields(EnumSet<T> fields) {
		for (T field : fields) {
			fieldModel.removeElement(field);
		}
	}

	public void addFields(EnumSet<T> fields) {
		for (T field : fields) {
			if (!fieldModel.contains(field)) {
				fieldModel.addElement(field);
			}
		}
	}

	public EnumSet<T> getSelectedFields() {
		EnumSet<T> included = EnumSet.noneOf(enumClass);
		// Get the index of all the selected items
	    int[] selectedIx = fieldList.getSelectedIndices();

	    // Get all the selected items using the indices
	    for (int i = 0; i < selectedIx.length; i++) {
	    	included.add(fieldModel.getElementAt(selectedIx[i]));
	    }

	    return included;
	}

	public List<T> getIncludedFields() {
		List<T> included = new ArrayList<>();
		// TODO This could probably be done more efficiently
		for (T field : enumClass.getEnumConstants()) {
			if (fieldModel.contains(field)) {
				included.add(field);
			}
		}
		return included;
	}

	@Override
	public void setEnabled(boolean enabled) {
		fieldList.setEnabled(enabled);
	}
}
