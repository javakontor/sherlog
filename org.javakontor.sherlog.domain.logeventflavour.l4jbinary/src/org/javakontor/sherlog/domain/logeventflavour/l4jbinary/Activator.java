package org.javakontor.sherlog.domain.logeventflavour.l4jbinary;

import java.util.Dictionary;
import java.util.Hashtable;

import org.javakontor.sherlog.domain.impl.reader.ObjectLogEventProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

  public void start(BundleContext bundleContext) throws Exception {

    Dictionary<String, String> dictionary = new Hashtable<String, String>();

    dictionary.put("logevent.flavour", "log4j 1.2.15 (binary)");

    bundleContext.registerService(ObjectLogEventProvider.class.getName(), new L4JBinaryLogEventProvider(), dictionary);
  }

  public void stop(BundleContext bundleContext) throws Exception {
    //
  }

}
