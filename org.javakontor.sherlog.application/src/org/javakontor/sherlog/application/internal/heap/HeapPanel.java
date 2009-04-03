package org.javakontor.sherlog.application.internal.heap;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.javakontor.sherlog.application.internal.ApplicationMessages;

/**
 * This panel displays information about the memory consumption of the current VM. It provides a button that triggers
 * the garbage collector.
 * 
 */
public class HeapPanel extends JPanel {

  public static final Font  STANDARD_FONT    = new Font("Dialog", Font.PLAIN, 13);

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public final static int   MAX_LENGTH       = "132M/256M".length();

  private final HeapLabel   _heapLabel;

  private final JLabel      _gcLabel;

  private final ImageIcon   _gcIcon;

  private final ImageIcon   _gcRunningIcon;

  /**
   * A {@link Timer}, that periodically collects the heap state information
   */
  private Timer             _timer;

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
    this._heapLabel = new HeapLabel();

    this._gcLabel = new JLabel(this._gcIcon);
    this._gcLabel.addMouseListener(new GcIconMouseListener());
    this._gcLabel.setToolTipText(ApplicationMessages.runGarbageCollector);

    add(this._heapLabel); // Label displaying heap state
    add(Box.createHorizontalStrut(5)); // fixed space
    add(this._gcLabel); // icon displaying the "trash can"

    this._timer = new Timer("HeapWatcherTimer", true);
    this._timer.schedule(new NotifyTask(), 0, 1000);

  }

  class NotifyTask extends TimerTask {
    @Override
    public void run() {

      final HeapState heapState = HeapState.getCurrentHeapState();
      setHeapState(heapState);
    }
  }

  public void setHeapState(final HeapState heapState) {
    SwingUtilities.invokeLater(new Runnable() {

      public void run() {
        HeapPanel.this._heapLabel.setHeapState(heapState);
      }
    });

  }

  /**
   * A label displaying memory statistics
   */
  class HeapLabel extends JLabel {

    /**
     * The default serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Color used to display the currently used memory
     */
    private final Color       _memUsedColor;

    /**
     * The {@link HeapState} that is currently displayed
     */
    private HeapState         _currentHeapState;

    @Override
    public Dimension getPreferredSize() {
      final Dimension dim = new Dimension(getTextWidth(), super.getPreferredSize().height);
      return dim;
    }

    @Override
    public Dimension getMaximumSize() {
      return getPreferredSize();
    }

    @Override
    public Dimension getMinimumSize() {
      return getPreferredSize();
    }

    HeapLabel() {
      super();
      this._memUsedColor = UIManager.getColor("ProgressBar.selectionBackground"); // new Color(102, 204, 255);
      setFont(STANDARD_FONT);
      setBorder(BorderFactory.createLineBorder(Color.GRAY));
      setHorizontalAlignment(SwingConstants.CENTER);
    }

    /**
     * Paint the panel
     */
    @Override
    public void paint(Graphics g) {
      if (this._currentHeapState != null) {

        final Color color = g.getColor();
        g.setColor(this._memUsedColor);
        final int height = HeapPanel.this._gcIcon.getIconHeight();
        long x = (getWidth() / this._currentHeapState.getTotalMemoryInMb());
        if (x < 1) {
          x = 1;
        }
        final int width = (int) (x * this._currentHeapState.getUsedMemoryInMb());
        g.drawRect(0, 0, width, height);
        g.fillRect(0, 0, width, height);
        g.setColor(color);
      }
      super.paint(g);
    }

    /**
     * Calculate a optimal panel width
     */
    public int getTextWidth() {
      final int columns = MAX_LENGTH;
      final FontMetrics metrics = getFontMetrics(getFont());
      final int width = metrics.charWidth('2') * columns;
      return width;
    }

    /**
     * 
     * Set the heap state that should be displayed and re-paints the component
     * 
     * @param heapState
     */
    public void setHeapState(HeapState heapState) {
      // redraw only if neccessary
      if ((heapState != null) && heapState.equals(this._currentHeapState)) {
        return;
      }
      this._currentHeapState = heapState;

      // update the component and Re-paint
      setText(heapState.getUsedMemoryInMb() + "/" + heapState.getTotalMemoryInMb() + "M");
      repaint();

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
    public void mouseClicked(MouseEvent e) {
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
