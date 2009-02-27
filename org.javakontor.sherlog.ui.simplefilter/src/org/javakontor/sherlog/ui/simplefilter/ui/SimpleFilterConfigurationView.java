package org.javakontor.sherlog.ui.simplefilter.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import org.javakontor.sherlog.core.LogLevel;
import org.lumberjack.application.mvc.AbstractView;
import org.lumberjack.application.mvc.DefaultReasonForChange;
import org.lumberjack.application.mvc.ModelChangedEvent;

public class SimpleFilterConfigurationView extends AbstractView<SimpleFilterConfigurationModel, DefaultReasonForChange> {

  private JComboBox _levelComboBox;

  private JComboBox _threadComboBox;

  private JComboBox _categoryComboBox;

  private JComboBox _messageComboBox;

  protected SimpleFilterConfigurationView(SimpleFilterConfigurationModel model) {
    super(model);
    model.addModelChangedListener(this);
  }

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public void modelChanged(ModelChangedEvent<SimpleFilterConfigurationModel, DefaultReasonForChange> event) {
    SimpleFilterConfigurationModel model = (SimpleFilterConfigurationModel) event.getSource();
    this._levelComboBox.setSelectedItem(model.getLogLevel());

    setContent(this._threadComboBox, model.getThreadName(), model.getThreadNameHistory());
    setContent(this._categoryComboBox, model.getCategory(), model.getCategoryHistory());
    setContent(this._messageComboBox, model.getMessage(), model.getMessageHistory());
  }

  protected void setContent(JComboBox comboBox, String selectedItem, String... history) {
    HistoryComboBoxModel comboBoxModel = (HistoryComboBoxModel) comboBox.getModel();
    comboBoxModel.setContent(selectedItem, history);
  }

  @Override
  protected void setUp() {

    createComponents();
    addListener();
  }

  private void createComponents() {

    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    DefaultComboBoxModel levelModel = new DefaultComboBoxModel(getModel().getAllLogLevels());
    this._levelComboBox = createAndAddComboBox(SimpleFilterMessages.level, levelModel);
    this._levelComboBox.setEditable(false);

    this._threadComboBox = createAndAddComboBox(SimpleFilterMessages.thread);
    this._categoryComboBox = createAndAddComboBox(SimpleFilterMessages.category);
    this._messageComboBox = createAndAddComboBox(SimpleFilterMessages.message);
  }

  JComboBox createAndAddComboBox(String label) {
    return createAndAddComboBox(label, new HistoryComboBoxModel());
  }

  JComboBox createAndAddComboBox(String label, ComboBoxModel model) {

    JComboBox comboBox = new AlignedComboBox(model);
    new EditPopupMenu(comboBox);

    comboBox.setEditable(true);
    add(new JLabel(label));
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

    AlignedComboBox(ComboBoxModel model) {
      super(model);
      setAlignmentX(0);
    }

    @Override
    public Dimension getMaximumSize() {
      Dimension maximumSize = super.getMaximumSize();
      return new Dimension(maximumSize.width, getPreferredSize().height);
    }
  }

  /**
   * TODO add to controller ?
   */
  private void addListener() {
    this._levelComboBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        LogLevel logLevel = (LogLevel) SimpleFilterConfigurationView.this._levelComboBox.getSelectedItem();
        getModel().setLogLevel(logLevel);
      }
    });
    this._threadComboBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        getModel().setThreadName((String) SimpleFilterConfigurationView.this._threadComboBox.getSelectedItem());
      }
    });
    this._categoryComboBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        getModel().setCategory((String) SimpleFilterConfigurationView.this._categoryComboBox.getSelectedItem());
      }
    });
    this._messageComboBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        getModel().setMessage((String) SimpleFilterConfigurationView.this._messageComboBox.getSelectedItem());
      }
    });
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

    public Object getElementAt(int index) {
      return this._items.get(index);
    }

    public int getSize() {
      return this._items.size();
    }

    public void addElement(Object anObject) {
      throw new UnsupportedOperationException("addElement");

    }

    public void insertElementAt(Object obj, int index) {
      throw new UnsupportedOperationException("insertElementAt");
    }

    public void removeElement(Object obj) {
      throw new UnsupportedOperationException("removeElement");
    }

    public void removeElementAt(int index) {
      throw new UnsupportedOperationException("removeElementAt");
    }

    public Object getSelectedItem() {
      return this._selectedItem;
    }

    public void setSelectedItem(Object anItem) {
      if (((this._selectedItem != null) && !this._selectedItem.equals(anItem))
          || ((this._selectedItem == null) && (anItem != null))) {
        this._selectedItem = anItem;
        fireContentsChanged(this, -1, -1);
      }
    }

    public void setContent(String selectedItem, String... history) {
      this._items.clear();
      for (Object object : history) {
        this._items.add(object);
      }
      setSelectedItem(selectedItem);
      fireContentsChanged(this, -1, -1);
    }

  }
}