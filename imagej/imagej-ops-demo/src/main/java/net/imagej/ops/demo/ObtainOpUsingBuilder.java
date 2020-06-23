
package net.imagej.ops.demo;

import java.util.function.BiFunction;

import org.scijava.ops.OpService;
import org.scijava.ops.core.Op;
import org.scijava.ops.core.builder.OpBuilder;
import org.scijava.ops.function.Computers;

/**
 * How to obtain an {@link Op} using an {@link OpBuilder}
 *
 * @author Gabriel Selzer
 */
public class ObtainOpUsingBuilder extends AbstractOpDemo{

	/**
	 * Build an {@link Op} using {@link Class}es to declare the input and output
	 * types.
	 */
	public static void buildOpUsingClasses(OpService ops) {

		// Build an Op
		BiFunction<Double, Double, Double> adder = ops.op("math.add") // provide the name of the desired Op
				.inType(Double.class, Double.class) // declare that the input should be a Double
				.outType(Double.class) // declare that the output should be a Double
				.function(); // ask for a Function (NOTE: not always a java.lang.Function, but always returns a new Object)

		// Run the Op
		Double result = adder.apply(5.0, 2.0);
		System.out.println("5 + 2 = " + result);
	}

	/**
	 * Build an {@link Op} using {@link Object}s to declare the input and output
	 * types.
	 */
	public static void buildOpUsingObjects(OpService ops) {
		// declare our input and output arrays
		double[] inputs = {1.0, 4.0, 9.0, 16.0};
		double[] outputs = {0.0, 0.0, 0.0, 0.0};

		// Build an Op
		Computers.Arity1<double[], double[]> sqrtOp = ops.op("math.sqrt") // provide the name of the desired Op
				.input(inputs) // declare that the input IS a double[]
				.output(outputs) // declare that the output IS a double[]
				.computer(); // ask for a Computer

		// Run the Op
		sqrtOp.compute(inputs, outputs);
		for(int i = 0; i < inputs.length; i++) {
			System.out.println("The square root of " + inputs[i] + " is " + outputs[i]);
		}
	}

	public static void main(String... args) {
		// Obtain an OpService
		OpService ops = getOpService();

		System.out.println("// -- Running an Op by building with Classes -- //");
		buildOpUsingClasses(ops);
		System.out.println("// -- Running an Op by building with Objects -- //");
		buildOpUsingObjects(ops);
	}

}
