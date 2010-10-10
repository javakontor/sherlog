package org.javakontor.sherlog.ui.loadwizard.filechooser;

import junit.framework.TestCase;

import org.javakontor.sherlog.application.mvc.DefaultReasonForChange;
import org.javakontor.sherlog.application.mvc.ModelChangedEvent;
import org.javakontor.sherlog.application.mvc.ModelChangedListener;
import org.javakontor.sherlog.domain.reader.LogEventFlavour;

public class LogFileChooserModelTest extends TestCase {

  public LogFileChooserModelTest(String name) {
    super(name);
  }

  public void test_Change_FileName() {
    MyLogEventFlavour myLogEventFlavour = new MyLogEventFlavour("myLogEventFlavour");
    LogFileChooserModel model = new LogFileChooserModel(myLogEventFlavour);

    MyModelChangeListener listener = new MyModelChangeListener();
    model.addModelChangedListener(listener);

    // make sure 'setFileName' only fires an event if it really changed the (previous) filename
    model.setFileName("fileName");
    listener.assertInvoked();
    model.setFileName("fileName");
    listener.assertNotInvoked();
    model.setFileName("anotherFileName");
    listener.assertInvoked();

    model.setFileName(null);
    listener.assertInvoked();
    model.setFileName(null);
    listener.assertNotInvoked();
  }

  public void test_Change_SelectedLogFlavour() {
    MyLogEventFlavour myLogEventFlavour = new MyLogEventFlavour("myLogEventFlavour");
    LogFileChooserModel model = new LogFileChooserModel(myLogEventFlavour);

    MyModelChangeListener listener = new MyModelChangeListener();
    model.addModelChangedListener(listener);

    final LogEventFlavour flavour = new MyLogEventFlavour("myLogEventFlavour");
    model.setSelectedLogEventFlavour(flavour);
    listener.assertInvoked();

    model.setSelectedLogEventFlavour(flavour);
    listener.assertNotInvoked();
    model.setSelectedLogEventFlavour(new MyLogEventFlavour("otherLogEventFlavour"));
    listener.assertInvoked();

  }

  class MyModelChangeListener implements ModelChangedListener<LogFileChooserModel, DefaultReasonForChange> {

    ModelChangedEvent<LogFileChooserModel, DefaultReasonForChange> lastEvent;

    public void reset() {
      this.lastEvent = null;
    }

    public void assertInvoked() {
      assertTrue("'modelChanged'-Callback method should have been invoked", lastEvent != null);
      reset();
    }

    public void assertNotInvoked() {
      assertTrue("'modelChanged'-Callback method should NOT been invoked", lastEvent == null);
      reset();
    }

    public void modelChanged(ModelChangedEvent<LogFileChooserModel, DefaultReasonForChange> event) {
      assertNotNull("Event must never be null", event);
      lastEvent = event;
    }
  }

  class MyLogEventFlavour implements LogEventFlavour {

    private final String _description;

    public MyLogEventFlavour(String description) {
      _description = description;
    }

    public String getDescription() {
      return _description;
    }

    public int getType() {
      return 0;
    }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof MyLogEventFlavour)) {
        return false;
      }

      return ((MyLogEventFlavour) o)._description.equals(_description);
    }
  }
}
