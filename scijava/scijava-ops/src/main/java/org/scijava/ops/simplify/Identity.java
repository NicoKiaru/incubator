
package org.scijava.ops.simplify;

import java.util.function.Function;

import org.scijava.ops.core.Op;
import org.scijava.plugin.Plugin;

@Unsimplifiable
@Plugin(type = Op.class, name = "simplify, focus")
public class Identity<T> implements Function<T, T> {

	public Identity() {}

	/**
	 * @param t the object to be simplified
	 * @return the simplified object (since we are doing an identity
	 *         simplification, this is just a reference to the input object).
	 */
	@Override
	public T apply(T t) {
		return t;
	}
}
