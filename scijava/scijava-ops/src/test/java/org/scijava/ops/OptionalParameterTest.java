package org.scijava.ops;

import java.util.function.Function;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.scijava.ops.core.OpCollection;
import org.scijava.param.Optional;
import org.scijava.plugin.Plugin;

/**
 * 
 * @author Gabriel Selzer
 *
 */
@Plugin(type = OpCollection.class)
public class OptionalParameterTest extends AbstractTestEnvironment {
	
	@OpField(names = "test.optionalOpField")
	public final Function<Integer, Float> optionalField = (@Optional Integer in) -> {
		Integer nonNullIn = in == null ? 0 : in;
		return nonNullIn + 0.5f;
	};
	
	@Test
	public void testOptionalOpField() {
		Float output = ops.op("test.optionalOpField") //
			.input() //
			.outType(Float.class) //
			.create(); //
		Assertions.assertEquals(0.5f, output, 0);
	}
	

}
