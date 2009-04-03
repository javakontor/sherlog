package org.javakontor.sherlog.domain.logeventflavour.textexample;

import java.util.Dictionary;
import java.util.Hashtable;

import org.javakontor.sherlog.domain.impl.reader.TextLogEventProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

  public void start(BundleContext bundleContext) throws Exception {

    Dictionary<String, String> dictionary = new Hashtable<String, String>();

    dictionary.put("logevent.flavour", "text example");

    bundleContext.registerService(TextLogEventProvider.class.getName(), new TextExampleLogEventProvider(), dictionary);
  }

  public void stop(BundleContext bundleContext) throws Exception {
    //
  }

}
