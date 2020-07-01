package org.scijava.ops;

import java.lang.annotation.Inherited;

import org.scijava.Priority;
import org.scijava.ops.core.Op;
import org.scijava.plugin.Plugin;

/** 
 * Annotates an op declared as a {@link Class}. 
 * @author Gabriel Selzer
 */
@Plugin(type = Op.class)
public @interface OpClass {

	String names();

	// the names of the parameters (inputs and outputs) that will appear in a call
	// to help().
	String[] params() default "";

	double priority() default Priority.NORMAL;

}
