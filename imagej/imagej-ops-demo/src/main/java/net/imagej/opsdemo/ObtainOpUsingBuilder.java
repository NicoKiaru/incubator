
package net.imagej.opsdemo;

import java.util.function.BiFunction;

import org.scijava.Context;
import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpService;
import org.scijava.ops.core.Op;
import org.scijava.ops.core.builder.OpBuilder;
import org.scijava.ops.function.Computers;
import org.scijava.plugin.PluginService;
import org.scijava.types.TypeService;

/**
 * How to obtain an {@link Op} using an {@link OpBuilder}. If you are only going
 * to use the {@code Op} once, it is cleaner to use the {@code OpBuilder} to get
 * the answer directly.
 *
 * @author Gabriel Selzer
 * @see RunOpUsingBuilder
 */
public class ObtainOpUsingBuilder {

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
	 * Build an {@link Op} using {@link Class}es to declare the input and output
	 * types.
	 */
	public static void buildOpUsingClasses(OpEnvironment opEnv) {

		// Build an Op
		BiFunction<Double, Double, Double> adder = opEnv.op("math.add") // provide the name of the desired Op
				.inType(Double.class, Double.class) // declare that the input should be a Double
				.outType(Double.class) // declare that the output should be a Double
				.function(); // ask for a Function (NOTE: not always a java.util.function.Function, but always returns a new Object)

		// Run the Op
		Double result = adder.apply(5.0, 2.0);
		System.out.println("5 + 2 = " + result);
	}

	/**
	 * Build an {@link Op} using {@link Object}s to declare the input and output
	 * types.
	 */
	public static void buildOpUsingObjects(OpEnvironment opEnv) {
		// declare our input and output arrays
		double[] inputs = { 1.0, 4.0, 9.0, 16.0 };
		double[] outputs = { 0.0, 0.0, 0.0, 0.0 };

		// Build an Op
		Computers.Arity1<double[], double[]> sqrtOp = opEnv.op("math.sqrt") // provide the name of the desired Op
				.input(inputs) // declare that the input IS a double[]
				.output(outputs) // declare that the output IS a double[]
				.computer(); // ask for a Computer

		// Run the Op
		sqrtOp.compute(inputs, outputs);
		for (int i = 0; i < inputs.length; i++) {
			System.out.println("The square root of " + inputs[i] + " is " +
				outputs[i]);
		}
	}

	public static void main(String... args) {
		// Obtain an OpEnvironment
		OpEnvironment opEnv = getOpEnvironment();

		System.out.println("// -- Running an Op by building with Classes -- //");
		buildOpUsingClasses(opEnv);
		System.out.println("// -- Running an Op by building with Objects -- //");
		buildOpUsingObjects(opEnv);
	}

}
