package org.javakontor.sherlog.util;

import java.io.File;

/**
 * <p>
 * Implements utility methods to support design-by-contract. If a condition is evaluated to false, a RuntimeException
 * will be thrown.
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * @author Daniel Kasmeroglu (daniel.kasmeroglu@kasisoft.net)
 */
public class Assert {

  /**
   * <p>
   * Assert that the specified object is not null.
   * </p>
   *
   * @param object
   *          the object that must be set.
   */
  public static void notNull(final Object object) {
    notNull("Object has to be set!", object);
  }

  /**
   * <p>
   * Assert that the specified object is not null.
   * </p>
   *
   * @param message
   *          an error message
   * @param object
   *          the object that must be set.
   */
  public static void notNull(final String message, final Object object) {
    if (object == null) {
      throw new RuntimeException("Precondition violated: " + message);
    }
  }

  /**
   * Asserts that the given parameter is an instance of the given type
   *
   * @param parameterName
   *          The name of the parameter that is checked
   * @param parameter
   *          The actual parameter value
   * @param expectedType
   *          The type the parameter should be an instance of
   */
  public static void instanceOf(String parameterName, Object parameter, Class<?> expectedType) {
    if (parameter == null) {
      throw new RuntimeException("Precondition violated: Parameter '" + parameterName + "' should be of type '"
          + expectedType.getName() + "' but was null");
    }

    if (!expectedType.isInstance(parameter)) {
      throw new RuntimeException("Precondition violated: Parameter '" + parameterName + "' should be of type '"
          + expectedType.getName() + "' but is a '" + parameter.getClass().getName() + "'");
    }
  }

  /**
   * <p>
   * Assert that the supplied string provides a value or not.
   * </p>
   *
   * @param string
   *          the string that must provide a value.
   */
  public static void nonEmpty(final String string) {
    notNull(string);
    if (string.length() == 0) {
      final String msg = "Precondition violated: An empty string is not allowed here !";
      throw new RuntimeException(msg);
    }
  }

  /**
   * <p>
   * Assert that the specified file is not null and exists.
   * </p>
   *
   * @param file
   *          the file that must exist.
   */
  public static void exists(final File file) {
    notNull(file);
    if (!file.exists()) {
      final String msg = String.format("Precondition violated: %s has to exist!", file.getAbsolutePath());
      throw new RuntimeException(msg);
    }
  }

  /**
   * <p>
   * Assert that the specified file is not null, exists and is a file.
   * </p>
   *
   * @param file
   *          the file that must be a file.
   */
  public static void isFile(final File file) {
    Assert.exists(file);
    if (!file.isFile()) {
      final String msg = String.format("Precondition violated: %s has to be a file, not a directory!", file
          .getAbsolutePath());
      throw new RuntimeException(msg);
    }
  }

  /**
   * <p>
   * Assert that the specified file is not null, exists and is a directory.
   * </p>
   *
   * @param file
   *          the file that must be a directory.
   */
  public static void isDirectory(final File file) {
    Assert.exists(file);
    if (!file.isDirectory()) {
      final String msg = String.format("Precondition violated: %s has to be a directory, not a file!", file
          .getAbsolutePath());
      throw new RuntimeException(msg);
    }
  }

  /**
   * <p>
   * Assert that the given condition is <code>true</code>
   * </p>
   *
   * @param condition
   *          the condition
   * @param msg
   *          the message
   */
  public static void assertTrue(final boolean condition, final String msg) {
    if (!condition) {
      throw new RuntimeException(String.format("Precondition violated: %s", msg));
    }
  }

  /**
   * <p>
   * Checks whether a value is in a specific range or not.
   * </p>
   *
   * @param value
   *          the value that shall be tested.
   * @param from
   *          the lower bound inclusive.
   * @param to
   *          the upper bound inclusive.
   */
  public static void inRange(final int value, final int from, final int to) {
    if ((value < from) || (value > to)) {
      final String msg = String.format("Precondition violated: %d must be within the range %d..%d !", new Object[] {
          new Integer(value), new Integer(from), new Integer(to) });
      throw new RuntimeException(msg);
    }
  }

}