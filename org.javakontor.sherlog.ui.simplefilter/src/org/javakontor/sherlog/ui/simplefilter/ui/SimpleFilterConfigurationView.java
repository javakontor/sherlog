package org.javakontor.sherlog.ui.simplefilter.ui;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.MutableComboBoxModel;

import org.javakontor.sherlog.application.mvc.AbstractView;
import org.javakontor.sherlog.application.mvc.DefaultReasonForChange;
import org.javakontor.sherlog.application.mvc.ModelChangedEvent;
import org.javakontor.sherlog.util.ui.EditPopupMenu;

public class SimpleFilterConfigurationView extends AbstractView<SimpleFilterConfigurationModel, DefaultReasonForChange> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private JComboBox         _levelComboBox;

  private JComboBox         _threadComboBox;

  private JComboBox         _categoryComboBox;

  private JComboBox         _messageComboBox;

  public SimpleFilterConfigurationView(final SimpleFilterConfigurationModel model) {
    super(model);
  }

  @Override
  public void onModelChanged(final ModelChangedEvent<SimpleFilterConfigurationModel, DefaultReasonForChange> event) {
    final SimpleFilterConfigurationModel model = (SimpleFilterConfigurationModel) event.getSource();
    this._levelComboBox.setSelectedItem(model.getLogLevel());

    setContent(this._threadComboBox, model.getThreadName(), model.getThreadNameHistory());
    setContent(this._categoryComboBox, model.getCategory(), model.getCategoryHistory());
    setContent(this._messageComboBox, model.getMessage(), model.getMessageHistory());
  }

  JComboBox getLevelComboBox() {
    return this._levelComboBox;
  }

  JComboBox getThreadComboBox() {
    return this._threadComboBox;
  }

  JComboBox getCategoryComboBox() {
    return this._categoryComboBox;
  }

  JComboBox getMessageComboBox() {
    return this._messageComboBox;
  }

  protected void setContent(final JComboBox comboBox, final String selectedItem, final String... history) {
    final HistoryComboBoxModel comboBoxModel = (HistoryComboBoxModel) comboBox.getModel();
    comboBoxModel.setContent(selectedItem, history);
  }

  @Override
  protected void setUp() {

    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    final DefaultComboBoxModel levelModel = new DefaultComboBoxModel(getModel().getAllLogLevels());
    this._levelComboBox = createAndAddComboBox(SimpleFilterMessages.level, levelModel);
    this._levelComboBox.setEditable(false);

    this._threadComboBox = createAndAddComboBox(SimpleFilterMessages.thread);
    this._categoryComboBox = createAndAddComboBox(SimpleFilterMessages.category);
    this._messageComboBox = createAndAddComboBox(SimpleFilterMessages.message);
  }

  private JComboBox createAndAddComboBox(final String label) {
    return createAndAddComboBox(label, new HistoryComboBoxModel());
  }

  private JComboBox createAndAddComboBox(final String label, final ComboBoxModel model) {

    final JComboBox comboBox = new AlignedComboBox(model);
    EditPopupMenu.createEditPopupMenuFor(comboBox);

    comboBox.setEditable(true);
    final JLabel jlabel = new JLabel(label);
    // jlabel.setForeground(Color.ORANGE);
    add(jlabel);
    add(Box.createVerticalStrut(2));
    add(comboBox);
    add(Box.createVerticalStrut(7));

    return comboBox;
  }

  final class AlignedComboBox extends JComboBox {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    AlignedComboBox(final ComboBoxModel model) {
      super(model);
      setAlignmentX(0);
    }

    @Override
    public Dimension getMaximumSize() {
      final Dimension maximumSize = super.getMaximumSize();
      return new Dimension(maximumSize.width, getPreferredSize().height);
    }
  }

  class HistoryComboBoxModel extends AbstractListModel implements MutableComboBoxModel {

    private final List<Object> _items;

    private Object             _selectedItem;

    /**
     * 
     */
    private static final long  serialVersionUID = 1L;

    HistoryComboBoxModel() {
      this._items = new LinkedList<Object>();
    }

    public Object getElementAt(final int index) {
      return this._items.get(index);
    }

    public int getSize() {
      return this._items.size();
    }

    public void addElement(final Object anObject) {
      throw new UnsupportedOperationException("addElement");

    }

    public void insertElementAt(final Object obj, final int index) {
      throw new UnsupportedOperationException("insertElementAt");
    }

    public void removeElement(final Object obj) {
      throw new UnsupportedOperationException("removeElement");
    }

    public void removeElementAt(final int index) {
      throw new UnsupportedOperationException("removeElementAt");
    }

    public Object getSelectedItem() {
      return this._selectedItem;
    }

    public void setSelectedItem(final Object anItem) {
      if (((this._selectedItem != null) && !this._selectedItem.equals(anItem))
          || ((this._selectedItem == null) && (anItem != null))) {
        this._selectedItem = anItem;
        fireContentsChanged(this, -1, -1);
      }
    }

    public void setContent(final String selectedItem, final String... history) {
      this._items.clear();
      for (final Object object : history) {
        this._items.add(object);
      }
      setSelectedItem(selectedItem);
      fireContentsChanged(this, -1, -1);
    }

  }
}
