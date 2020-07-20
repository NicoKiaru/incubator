package org.scijava.param;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Marker interface for optional parameters
 *
 * @author Gabriel Selzer
 */
@Target(ElementType.PARAMETER)
public @interface Optional {
}
