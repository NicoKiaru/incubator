
package net.imagej.opsdemo;

import org.scijava.Context;
import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpService;
import org.scijava.ops.core.Op;
import org.scijava.ops.core.builder.OpBuilder;
import org.scijava.plugin.PluginService;
import org.scijava.types.TypeService;

/**
 * How to run an {@link Op} using an {@link OpBuilder}. If you intend to use the
 * {@code Op} more than once OR do not have the {@link Object}s needed to run
 * the {@code Op} when constructing it, you should obtain the {@code Op}
 * instead.
 *
 * @author Gabriel Selzer
 * @see ObtainOpUsingBuilder
 */
public class RunOpUsingBuilder {

	/**
	 * Uses a {@link Context} to obtain an {@OpEnvironment}.
	 *
	 * @return an {@link OpEnvironment} that can be used to obtain {@link Op}s
	 */
	public static OpEnvironment getOpEnvironment() {
		// Note that all three of these Services are necessary
		Context context = new Context(OpService.class, PluginService.class,
			TypeService.class);
		OpService ops = context.getService(OpService.class);
		return ops.env();
	}

	/**
	 * Run an {@link Op} using {@link Object}s to declare the input and output
	 * types. Note that doing this DOES NOT allow you to keep the Op for repeated
	 * use.
	 */
	public static void runOpUsingObjects(OpEnvironment opEnv) {
		// declare our input and output arrays
		double[] inputs = { 1.0, 4.0, 9.0, 16.0 };
		double[] outputs = { 0.0, 0.0, 0.0, 0.0 };

		// Construct the Op, and run it immediately
		opEnv.op("math.sqrt") // provide the name of the desired Op
			.input(inputs) // declare that the input IS a double[]
			.output(outputs) // declare that the output IS a double[]
			.compute(); // compute the result on the given Objects

		// Print results
		for (int i = 0; i < inputs.length; i++) {
			System.out.println("The square root of " + inputs[i] + " is " +
				outputs[i]);
		}
	}

	public static void main(String... args) {
		// Obtain an OpEnvironment
		OpEnvironment opEnv = getOpEnvironment();

		System.out.println("// -- Running an Op by building with Objects -- //");
		runOpUsingObjects(opEnv);
	}

}
