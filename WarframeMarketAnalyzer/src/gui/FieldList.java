package gui;

import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.TransferHandler;
import javax.swing.border.BevelBorder;

import enums.fields.IFieldEnum;

public class FieldList<T extends Enum<T> & IFieldEnum> extends JPanel {

	private Class<T> enumClass;
	private DataFlavor fieldDataFlavor;
	private JList<FieldItem<T>> fieldList;
	private DefaultListModel<FieldItem<T>> fieldModel;

	public FieldList(String label, Class<T> enumClass, List<FieldItem<T>> initial) throws ClassNotFoundException {
		this.enumClass = enumClass;
		String dataFlavor = DataFlavor.javaJVMLocalObjectMimeType +
                ";class=\""+enumClass.getCanonicalName()+"\"";
		fieldDataFlavor = new DataFlavor(dataFlavor);

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		add(new JLabel(label));

		fieldModel = new DefaultListModel<>();
		for (FieldItem<T> field : initial) {
			fieldModel.addElement(field);
		}
		fieldList = new JList<>(fieldModel);
		fieldList.setDragEnabled(true);
		fieldList.setDropMode(DropMode.INSERT);
		fieldList.setTransferHandler(new ReorderHandler());

		JScrollPane scroll = new JScrollPane(fieldList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		scroll.setPreferredSize(new Dimension(200, 300));

		add(scroll);

		setVisible(true);
	}

	public List<FieldItem<T>> removeSelectedFields() {
		List<FieldItem<T>> selected = getSelectedFields();
		for (FieldItem<T> field : selected) {
			fieldModel.removeElement(field);
		}
		return selected;
	}

	public List<FieldItem<T>> removeAllFields() {
		List<FieldItem<T>> includedField = getIncludedFields();
		fieldModel.removeAllElements();
		return includedField;
	}

	public void addFields(List<FieldItem<T>> newFields) {
		fieldModel.addAll(newFields);
	}

	public List<FieldItem<T>> getSelectedFields() {
		List<FieldItem<T>> included = new ArrayList<>();
		// Get the index of all the selected items
	    int[] selectedIx = fieldList.getSelectedIndices();

	    // Get all the selected items using the indices
	    for (int i = 0; i < selectedIx.length; i++) {
	    	included.add(fieldModel.getElementAt(selectedIx[i]));
	    }

	    return included;
	}

	public List<FieldItem<T>> getIncludedFields() {
		List<FieldItem<T>> included = new ArrayList<>();
		Enumeration<FieldItem<T>> elements = fieldModel.elements();
		while(elements.hasMoreElements()) {
			included.add(elements.nextElement());
		}
		return included;
	}

	@Override
	public void setEnabled(boolean enabled) {
		fieldList.setEnabled(enabled);
	}

	protected void setFields(List<FieldItem<T>> fields) {
		fieldModel.removeAllElements();
		fieldModel.addAll(fields);
	}

	private class ReorderHandler extends TransferHandler {

        private int newIndex;
        private boolean isSame;

        @Override
        public int getSourceActions(JComponent comp) {
            return MOVE;
        }

        @Override
        public Transferable createTransferable(JComponent comp) {
        	List<FieldItemTransferable> moveValues = new ArrayList<>();
        	for (int idx : fieldList.getSelectedIndices()) {
        		FieldItem<T> field = fieldModel.get(idx);
        		moveValues.add(new FieldItemTransferable(field, idx));
        	}
        	// On each transfer, start it as false. Set it to true during the import.
        	isSame = false;
            return new FieldTransferable(moveValues);
        }

        @Override
        public void exportDone(JComponent comp, Transferable trans, int action) {
            if (action == MOVE) {
            	List<FieldItemTransferable> moveList = ((FieldTransferable)trans).moveValues;
            	// Work backward through the list so we don't need to deal with adjusting the indices
            	// to deal with items removed above.
        		for (int i=moveList.size()-1; i>=0; i--) {
        			FieldItemTransferable transferField = moveList.get(i);
        			int idx = transferField.index;
                	// If moving in the same list, may need to offset the index by the newly moved items
        			if (isSame && idx >= newIndex) {
        				fieldModel.remove(idx+moveList.size());
        			} else {
        				fieldModel.remove(idx);
        			}
        		}
            }
        }

        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            return support.isDataFlavorSupported(fieldDataFlavor);
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            try {

            	List<FieldItemTransferable> moveList = (List<FieldItemTransferable>) support.getTransferable().getTransferData(fieldDataFlavor);
                JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
                newIndex = dl.getIndex();
            	isSame = true;
                List<FieldItem<T>> fieldList = new ArrayList<>();
                for (FieldItemTransferable fieldItemTransferable : moveList) {
                	fieldList.add(fieldItemTransferable.fieldItem);
                }
                fieldModel.addAll(newIndex, fieldList);
                return true;
            } catch (UnsupportedFlavorException | IOException | ClassCastException e) {
                e.printStackTrace();
            }

            return false;
        }
	}

	private class FieldTransferable implements Transferable {
		private List<FieldItemTransferable> moveValues;

		private FieldTransferable(List<FieldItemTransferable> moveValues) {
			this.moveValues = moveValues;
		}

		@Override
		public DataFlavor[] getTransferDataFlavors() {
			return List.<DataFlavor>of(fieldDataFlavor).toArray(new DataFlavor[1]);
		}

		@Override
		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return flavor == fieldDataFlavor;
		}

		@Override
		public List<FieldItemTransferable> getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
			return moveValues;
		}
	}

	private class FieldItemTransferable {
		private final FieldItem<T> fieldItem;
		private final int index;

		private FieldItemTransferable(FieldItem<T> fieldItem, int index) {
			this.fieldItem = fieldItem;
			this.index = index;
		}

	}
}
