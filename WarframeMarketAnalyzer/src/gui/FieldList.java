package gui;

import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

	public void removeFields(Collection<T> fields) {
		for (int i = fieldModel.getSize()-1; i >= 0; i--) {
			if (fields.contains(fieldModel.get(i).getEnum())) {
				fieldModel.remove(i);
			}
		}
	}

	public void removeFieldItems(Collection<FieldItem<T>> fields) {
		for (FieldItem<T> field : fields) {
			fieldModel.removeElement(field);
		}
	}

	public void addFields(List<T> fields) {
		for (T field : fields) {
			if (!fieldModel.contains(field)) {
				fieldModel.addElement(new FieldItem<T>(field));
			}
		}
	}

	public void addFieldsItems(List<FieldItem<T>> fields) {
		for (FieldItem<T> field : fields) {
			if (!fieldModel.contains(field)) {
				fieldModel.addElement(field);
			}
		}
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

        private int[] selectedIx;
        private int newIndex;

        @Override
        public int getSourceActions(JComponent comp) {
            return MOVE;
        }

        @Override
        public Transferable createTransferable(JComponent comp) {
    	    selectedIx = fieldList.getSelectedIndices();
    	    System.out.println(Arrays.toString(selectedIx));
    	    List<FieldItem<T>> moveValues = new ArrayList<>();
    	    for (int i=0; i<selectedIx.length; i++) {
    	    	moveValues.add(fieldModel.get(selectedIx[i]));
    	    }
            return new FieldTransferable(moveValues);
        }

        @Override
        public void exportDone(JComponent comp, Transferable trans, int action) {
            if (action == MOVE) {
//            	boolean isSame = fieldModel.contains(trans)

            	// Work backward through the list so we don't need to deal with adjusting the indices
            	// to deal with items removed above.
        		for (int i=selectedIx.length-1; i>=0; i--) {
        			int idx = selectedIx[i];
                	// If moving in the same list, may need to offset the index by the newly moved items
        			if (comp == fieldList && idx >= newIndex) {
        				System.out.println("Remove("+idx+") "+fieldModel.get(idx+selectedIx.length));
        				fieldModel.remove(idx+selectedIx.length);
        			} else {
        				System.out.println("Remove("+idx+") "+fieldModel.get(idx));
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
            	List<FieldItem<T>> fieldList = (List<FieldItem<T>>) support.getTransferable().getTransferData(fieldDataFlavor);
                JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
                newIndex = dl.getIndex();
                for (int i=0; i<fieldList.size(); i++) {
                	fieldModel.add(newIndex+i, fieldList.get(i));
                }
                return true;
            } catch (UnsupportedFlavorException | IOException | ClassCastException e) {
                e.printStackTrace();
            }

            return false;
        }
	}

	private class FieldTransferable implements Transferable {
		private List<FieldItem<T>> moveValues;

		private FieldTransferable(List<FieldItem<T>> moveValues) {
			this.moveValues = moveValues;
			System.out.println(Arrays.toString(moveValues.toArray()));
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
		public List<FieldItem<T>> getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
			return moveValues;
		}

	}
}
