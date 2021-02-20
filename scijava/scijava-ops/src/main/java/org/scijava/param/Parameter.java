/*
 * #%L
 * SciJava Common shared library for SciJava software.
 * %%
 * Copyright (C) 2009 - 2017 Board of Regents of the University of
 * Wisconsin-Madison, Broad Institute of MIT and Harvard, and Max Planck
 * Institute of Molecular Cell Biology and Genetics.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package org.scijava.param;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.scijava.struct.ItemIO;

/**
 * An annotation for indicating a field is an input or output parameter. This
 * annotation is a useful way for plugins to declare their inputs and outputs
 * simply.
 * 
 * @author Johannes Schindelin
 * @author Grant Harris
 * @author Curtis Rueden
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE, ElementType.METHOD })
@Repeatable(Parameters.class)
public @interface Parameter {

	/**
	 * IMPORTANT: Parameter annotation instances are mutated using reflections
	 * in order to resolve {@link ItemIO#AUTO} which accesses this field
	 * by name. If the name is changed, this has to be changed accordingly.
	 */
	public static final String ITEMIO_FIELD_NAME = "itemIO";
	
	/**
	 * IMPORTANT: Parameter annotation instances are mutated using reflections
	 * in order to resolve {@link ItemIO#AUTO} which accesses this field
	 * by name. If the name is changed, this has to be changed accordingly.
	 */
	public static final String KEY_FIELD_NAME = "key";
	
	/** Defines a key for the parameter. */
	String key() default "";

	/**
	 * Defines the input/output type of the parameter.
	 * 
	 * @see ItemIO
	 */
	ItemIO itemIO() default ItemIO.AUTO;

	/**
	 * Defines whether the parameter references an object which itself has
	 * parameters. This allows for nested structures to be created.
	 */
	boolean struct() default false;
}
