package org.javakontor.sherlog.application.internal.heap;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import org.javakontor.sherlog.application.internal.ApplicationMessages;

/**
 * This panel displays information about the memory consumption of the current VM. It provides a button that triggers
 * the garbage collector.
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class HeapPanel extends JPanel {

  public static final Font      STANDARD_FONT    = new Font("Dialog", Font.PLAIN, 13);

  /**
   * 
   */
  private static final long     serialVersionUID = 1L;

  public final static int       MAX_LENGTH       = "132M/256M".length();

  private final HeapProgressBar _heapBar;

  private final JLabel          _gcLabel;

  private final ImageIcon       _gcIcon;

  private final ImageIcon       _gcRunningIcon;

  /**
   * A {@link Timer}, that periodically collects the heap state information
   */
  private Timer                 _timer;

  public void dispose() {
    if (this._timer != null) {
      this._timer.cancel();
      this._timer = null;
    }
  }

  public HeapPanel() {
    // create icons
    this._gcIcon = new ImageIcon(getClass().getResource("trash.gif"));
    this._gcRunningIcon = new ImageIcon(getClass().getResource("trash-selected.gif"));

    // set layout
    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

    // create components
    this._heapBar = new HeapProgressBar();
    // this._heapLabel = new HeapLabel();

    this._gcLabel = new JLabel(this._gcIcon);
    this._gcLabel.addMouseListener(new GcIconMouseListener());
    this._gcLabel.setToolTipText(ApplicationMessages.runGarbageCollector);

    add(this._heapBar); // Label displaying heap state
    add(Box.createHorizontalStrut(5)); // fixed space
    add(this._gcLabel); // icon displaying the "trash can"

    this._timer = new Timer("HeapWatcherTimer", true);
    this._timer.schedule(new NotifyTask(), 0, 1000);

  }

  /**
   * A {@link TimerTask} that periodically updates the progress bar with the current heap state
   */
  class NotifyTask extends TimerTask {
    @Override
    public void run() {
      // determine current heap state
      final HeapState heapState = HeapState.getCurrentHeapState();

      // set heap state
      setHeapState(heapState);
    }
  }

  /**
   * Set the specified {@link HeapState} to the progress bar
   */
  public void setHeapState(final HeapState heapState) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        HeapPanel.this._heapBar.setHeapState(heapState);
      }
    });

  }

  /**
   * A {@link JProgressBar} that displayes heap state information
   */
  class HeapProgressBar extends JProgressBar {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private HeapState         _currentHeapState;

    public HeapProgressBar() {
      setStringPainted(true);
    }

    @Override
    public Dimension getPreferredSize() {
      // determine a reasonable pref size by calculating the maximal text width
      final Dimension dim = new Dimension(getTextWidth(), super.getPreferredSize().height);
      return dim;
    }

    /**
     * Calculate a optimal panel width
     */
    public int getTextWidth() {
      final int columns = MAX_LENGTH;
      final FontMetrics metrics = getFontMetrics(getFont());
      final int width = metrics.charWidth('M') * columns;
      return width;
    }

    @Override
    public Dimension getMaximumSize() {
      return getPreferredSize();
    }

    @Override
    public Dimension getMinimumSize() {
      return getPreferredSize();
    }

    /**
     * 
     * Set the heap state that should be displayed and re-paints the component
     * 
     * @param heapState
     */
    public void setHeapState(final HeapState heapState) {
      // redraw only if neccessary
      if ((heapState != null) && heapState.equals(this._currentHeapState)) {
        return;
      }
      this._currentHeapState = heapState;

      // update the component with new heap state information and Re-paint
      setString(heapState.getUsedMemoryInMb() + "M/" + heapState.getTotalMemoryInMb() + "M");
      setMaximum((int) heapState.getTotalMemoryInMb());
      setValue((int) heapState.getUsedMemoryInMb());

      setToolTipText(String.format(ApplicationMessages.heapUsageToolTip, heapState.getUsedMemoryInMb(), heapState
          .getTotalMemoryInMb(), heapState.getMaxMemoryInMb()));

    }

  }

  /**
   * A MouseListener that triggers the gc when clicked
   * 
   * 
   */
  class GcIconMouseListener extends MouseAdapter {

    /**
     * avoid running parallel if user clicks multiple times
     */
    private boolean isRunning = false;

    @Override
    public void mouseClicked(final MouseEvent e) {
      if (!this.isRunning && (e.getButton() == MouseEvent.BUTTON1)) {
        this.isRunning = true;

        // Set wait cursor and "running" icon to give user a visual feedback
        final Cursor origCursor = HeapPanel.this.getCursor();
        HeapPanel.this._gcLabel.setIcon(HeapPanel.this._gcRunningIcon);
        HeapPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        // Since GC might be a long running action, execute on own thread, to not block
        // the UI
        new Thread(new Runnable() {
          public void run() {
            try {
              for (int i = 0; i < 2; i++) {
                System.gc();
                System.runFinalization();
              }
            } finally {
              // Reset icons and mouse cursor after running the gc
              SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                  HeapPanel.this._gcLabel.setIcon(HeapPanel.this._gcIcon);
                  HeapPanel.this.setCursor(origCursor);
                  GcIconMouseListener.this.isRunning = false;
                }
              });
            }
          }
        }, "Heap Panel - GC Runner").start();

      }
    }
  }
}
