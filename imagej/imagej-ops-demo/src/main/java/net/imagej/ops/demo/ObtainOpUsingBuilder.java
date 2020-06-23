
package net.imagej.ops.demo;

import java.util.function.BiFunction;

import org.scijava.Context;
import org.scijava.ops.OpService;
import org.scijava.plugin.PluginService;
import org.scijava.types.TypeService;

/**
 * How to get an Op.
 * 
 * TODO: Clean up, formalize
 * 
 * @author Gabriel Selzer
 */
public class ObtainOpUsingBuilder {

	public static void main(String... args) {
		Context context = new Context(OpService.class, PluginService.class, TypeService.class);
		OpService ops = context.getService(OpService.class);

		BiFunction<Double, Double, Double> adder = ops.op("math.add").inType(
			Double.class, Double.class).outType(Double.class).function();

		System.out.println(adder.apply(5.0, 2.0));

	}

}
