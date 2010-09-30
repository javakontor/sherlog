package org.javakontor.sherlog.util.ui;

import javax.swing.JTable;
import javax.swing.JViewport;

/**
 * A {@link JTable} that adds some methods from a Java6 JTable to Java-5 in order to get the same API with Java5 and
 * Java6
 * 
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class SherlogTable extends JTable {

  private boolean           fillsViewportHeight;

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Sets whether or not this table is always made large enough to fill the height of an enclosing viewport. If the
   * preferred height of the table is smaller than the viewport, then the table will be stretched to fill the viewport.
   * In other words, this ensures the table is never smaller than the viewport. The default for this property is {@code
   * false}.
   * 
   * @param fillsViewportHeight
   *          whether or not this table is always made large enough to fill the height of an enclosing viewport
   * @see #getFillsViewportHeight
   * @see #getScrollableTracksViewportHeight
   * @since 1.6
   * @beaninfo bound: true description: Whether or not this table is always made large enough to fill the height of an
   *           enclosing viewport
   */
  public void setFillsViewportHeight(boolean fillsViewportHeight) {
    boolean old = this.fillsViewportHeight;
    this.fillsViewportHeight = fillsViewportHeight;
    resizeAndRepaint();
    firePropertyChange("fillsViewportHeight", old, fillsViewportHeight);
  }

  /**
   * Returns whether or not this table is always made large enough to fill the height of an enclosing viewport.
   * 
   * @return whether or not this table is always made large enough to fill the height of an enclosing viewport
   * @see #setFillsViewportHeight
   * @since 1.6
   */
  public boolean getFillsViewportHeight() {
    return fillsViewportHeight;
  }

  /**
   * <p>
   * Returns {@code true} if the preferred height of the table is smaller than the viewport's height.
   * </p>
   * <p>
   * Taken from the Java6 implementation of this method.
   * </p>
   * 
   * @see javax.swing.JTable#getScrollableTracksViewportHeight()
   */
  @Override
  public boolean getScrollableTracksViewportHeight() {
    return getFillsViewportHeight() && getParent() instanceof JViewport
        && (((JViewport) getParent()).getHeight() > getPreferredSize().height);
  }

  /**
   * Taken from Java6 JTable
   * 
   * @param viewRowIndex
   * @return
   */
  public int convertRowIndexToModel(int viewRowIndex) {
    return viewRowIndex;
  }
  
  /**
   * Taken from Java6 JTable
   * 
   * @param modelRowIndex
   * @return
   */
  public int convertRowIndexToView(int modelRowIndex) {
    return modelRowIndex;
  }


}
