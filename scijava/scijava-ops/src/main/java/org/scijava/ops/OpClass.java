package org.scijava.ops;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.scijava.Priority;
import org.scijava.annotations.Indexable;
import org.scijava.ops.core.Op;
import org.scijava.plugin.Plugin;

/** 
 * Annotates an op declared as a {@link Class}. 
 * @author Gabriel Selzer
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Indexable
public @interface OpClass {

	String names();

	// the names of the parameters (inputs and outputs) that will appear in a call
	// to help().
	String[] params() default "";

	double priority() default Priority.NORMAL;

}
