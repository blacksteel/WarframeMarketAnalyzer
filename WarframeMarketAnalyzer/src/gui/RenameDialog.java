package gui;
import java.awt.Frame;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import enums.fields.IFieldEnum;

public class RenameDialog<T extends Enum<T> & IFieldEnum> extends JDialog implements /*ActionListener, */PropertyChangeListener {

	private JTextField textField;

	private JOptionPane optionPane;

	private String renameActionStr = "Rename";
	private String cancelActionStr = "Cancel";

	private FieldItem<T> field;
	private FieldList<T> fieldList;

	/** Creates the reusable dialog. */
	public RenameDialog(Frame parent, FieldList<T> fieldList, FieldItem<T> field) {
		super(parent, true);
		setTitle("Rename Field");
		this.field = field;
		this.fieldList = fieldList;

		textField = new JTextField(field.toString(), 10);

		//Create an array of the text and components to be displayed.
		String msgString1 = "Rename field:";
		Object[] array = {msgString1, textField};

		//Create an array specifying the number of dialog buttons
		//and their text.
		Object[] options = {renameActionStr, cancelActionStr};

		//Create the JOptionPane.
		optionPane = new JOptionPane(array,
				JOptionPane.PLAIN_MESSAGE,
				JOptionPane.YES_NO_OPTION,
				null,
				options,
				cancelActionStr);

		//Make this dialog display it.
		setContentPane(optionPane);

		//Handle window closing correctly.
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		//Register an event handler that reacts to option pane state changes.
		optionPane.addPropertyChangeListener(this);

		pack();
	}

	/** This method reacts to state changes in the option pane. */
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		String prop = e.getPropertyName();

		if (isVisible()
				&& (e.getSource() == optionPane)
				&& (JOptionPane.VALUE_PROPERTY.equals(prop) ||
						JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
			if (renameActionStr.equals(optionPane.getValue())) {
				fieldList.fieldChanged(field, textField.getText());
			}
			dispose();
		}
	}
}
