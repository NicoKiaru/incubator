
package org.scijava.param;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import org.scijava.struct.ItemIO;
import org.scijava.struct.Member;

/**
 * {@link Member} backed by a {@link Field} annotated by {@link Parameter}.
 *
 * @author Curtis Rueden
 * @param <T>
 */
public abstract class AnnotatedParameterMember<T> implements Member<T> {

	/** Type, or a subtype thereof, which houses the field. */
	private final Type itemType;

	/** Annotation describing the item. */
	private final Parameter annotation;
	
	private ItemIO itemIO;

	public AnnotatedParameterMember(final Type itemType,
		final Parameter annotation)
	{
		this.itemType = itemType;
		this.annotation = annotation;
	}

	// -- AnnotatedParameterMember methods --

	public Parameter getAnnotation() {
		return annotation;
	}

	// -- Member methods --

	@Override
	public String getKey() {
		return getAnnotation().key();
	}

	@Override
	public Type getType() {
		return itemType;
	}

	@Override
	public ItemIO getIOType() {
		if (itemIO == null) {
			// CTR START HERE: if AUTO here, compute what it actually is and cache.
			itemIO = getAnnotation().itemIO();
		}
		return itemIO;
	}
	
	@Override
	public boolean isStruct() {
		return getAnnotation().struct();
	}
}
