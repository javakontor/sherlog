package org.javakontor.sherlog.test.ui;

import java.awt.Component;

import org.netbeans.jemmy.ComponentChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TracingComponentChooser implements ComponentChooser {

	private final ComponentChooser	_delegate;

	protected final Logger							_log	= LoggerFactory.getLogger(getClass());

	public TracingComponentChooser(ComponentChooser delegate) {
		super();
		_delegate = delegate;
	}

	public boolean checkComponent(Component comp) {

		_log.debug(String.format("While waiting for %s\n     checking component with name '%s' of type '%s' (%s)",
				_delegate.getDescription(), comp.getName(), comp.getClass().getName(), comp));
		return _delegate.checkComponent(comp);
	}

	public String getDescription() {
		return "Traced Aufrufe fuer " + _delegate.getDescription();
	}

}
