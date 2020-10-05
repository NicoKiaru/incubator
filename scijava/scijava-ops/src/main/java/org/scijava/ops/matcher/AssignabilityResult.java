
package org.scijava.ops.matcher;

import java.lang.reflect.ParameterizedType;

import org.scijava.types.Types;

/**
 * There are multiple reasons that
 * {@link MatchingUtils#checkGenericAssignability(java.lang.reflect.Type, java.lang.reflect.ParameterizedType, boolean)}
 * could fail:
 * <ol>
 * <li>When the raw types do not match
 * <li>When {@code src} is <b>not</b> a {@link ParameterizedType}, and
 * {@link Types#isAssignable} returns {@code false}
 * <li>When the raw types match and {@code src} is a {@link ParameterizedType},
 * but 1+ of the type parameters are not assignable.
 * </ol>
 * It is of interest to know, for any given call to
 * {@code checkGenericAssignability}, the reason for failure. This class is
 * meant to describe the reason.
 * 
 * @author G
 */
public class AssignabilityResult {

	/** The overall assignability of src to dest */
	final boolean isAssignable;
	/**
	 * The assignability of each of src's type parameters to those of dest. The
	 * length of argsAssignable is <b>always</b> the number of type parameters of
	 * src.
	 */
	final boolean[] argsAssignable;

	//TODO: consider a 3-arg constructor
	public AssignabilityResult(boolean isAssignable,
		boolean[] argsAssignable)
	{
		this.isAssignable = isAssignable;
		this.argsAssignable = argsAssignable;
	}

	public boolean isAssignable() {
		return isAssignable;
	}

	public boolean[] argsAssignable() {
		return argsAssignable;
	}

}
