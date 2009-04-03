package org.javakontor.sherlog.util.nls;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Can be used to mark a <tt>public static, non-final</tt> field to be set by {@link NLS#initialize(Class)}
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NLSMessage {

  /**
   * A default value, that should be set if no other value for the annotated field can be found in an external
   * properties file
   * 
   * @return
   */
  public String value() default "";

}
