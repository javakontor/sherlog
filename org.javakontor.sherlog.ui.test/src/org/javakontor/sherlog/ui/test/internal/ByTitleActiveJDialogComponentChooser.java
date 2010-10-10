package org.javakontor.sherlog.ui.test.internal;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;

import org.netbeans.jemmy.ComponentChooser;

public class ByTitleActiveJDialogComponentChooser implements ComponentChooser {

	private String	_expectedTitle;

	public ByTitleActiveJDialogComponentChooser(String expectedTitle) {
		super();
		this._expectedTitle = expectedTitle;
	}

	public boolean checkComponent(Component comp) {

		if (!(comp instanceof Window)) {
			return false;
		}

		Window window = (Window) comp;
		String windowTitle = null;

		if (window instanceof Dialog) {
			Dialog dialog = (Dialog) window;
			windowTitle = dialog.getTitle();
		} else if (window instanceof Frame) {
			Frame frame = (Frame) window;
			windowTitle = frame.getTitle();
		}

		if (!window.isActive())
			return false;

		if (!window.isVisible()) {
			return false;
		}
		return (windowTitle != null && windowTitle.trim().startsWith(_expectedTitle));
	}

	public String getDescription() {
		return "Aktives Fenster mit Titel " + _expectedTitle;
	}

}
