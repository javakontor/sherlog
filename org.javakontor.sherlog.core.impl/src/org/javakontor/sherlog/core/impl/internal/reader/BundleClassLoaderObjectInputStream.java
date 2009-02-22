package org.javakontor.sherlog.core.impl.internal.reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

import org.osgi.framework.Bundle;

/**
 * <p>
 * The {@link BundleClassLoaderObjectInputStream} can be used to load class via a bundle's class loader.
 * </p>
 *
 * @author Gerd Wuetherich (gerd@gerd-wuetherich.de)
 */
public class BundleClassLoaderObjectInputStream extends ObjectInputStream {

  /** the bundle to load classes from */
  private final Bundle _bundle;

  /**
   * <p>
   * Create a new instance of type {@link BundleClassLoaderObjectInputStream}.
   * </p>
   *
   * @param bundle
   *          the bundle to load classes from
   * @param inputStream
   *          the inputStream
   * @throws IOException
   */
  public BundleClassLoaderObjectInputStream(final Bundle bundle, final InputStream inputStream) throws IOException {
    // call the super constructor
    super(inputStream);

    // set the bundle
    this._bundle = bundle;
  }

  /**
   * @see java.io.ObjectInputStream#resolveClass(java.io.ObjectStreamClass)
   */
  @Override
  protected Class<?> resolveClass(final ObjectStreamClass desc) throws IOException, ClassNotFoundException {

    // classes via the bundle class loader
    try {
      return this._bundle.loadClass(desc.getName());
    } catch (ClassNotFoundException e) {
      return super.resolveClass(desc);
    }
  }
}
