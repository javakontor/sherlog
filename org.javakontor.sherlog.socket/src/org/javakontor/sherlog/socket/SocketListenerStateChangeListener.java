package org.javakontor.sherlog.socket;

/**
 * A listener that is invoked when the state of a {@link SocketListener} changes.
 * 
 * <p>
 * A state change event happens when a new connection is made or an existing connection is closed
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public interface SocketListenerStateChangeListener {

  public void stateChanged();

}
