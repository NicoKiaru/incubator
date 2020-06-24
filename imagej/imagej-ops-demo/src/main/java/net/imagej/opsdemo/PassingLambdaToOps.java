
package net.imagej.opsdemo;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

import net.imagej.ops2.types.adapt.LiftComputersToRAI;
import net.imglib2.Cursor;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.integer.UnsignedByteType;

import org.scijava.Context;
import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpService;
import org.scijava.ops.core.Op;
import org.scijava.ops.core.OpCollection;
import org.scijava.ops.function.Computers;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginService;
import org.scijava.types.Nil;
import org.scijava.types.TypeService;
import org.scijava.util.MersenneTwisterFast;

/**
 * How to pass lambdas to other Ops
 * <p>
 * Sometimes Ops take other Ops as parameters (for example, see imagej-ops2's
 * project Op), many of which are lambdas. The generic typing of lambdas,
 * however, is not retained in the bytecode; it follows that the Op matcher is
 * not able to determine whether a passed lambda satisfies the type required by
 * the Op.
 * <p>
 * This means that if we want to provide a lambda to an Op, we have to bake in
 * its type beforehand. This can be done using
 * {@link OpEnvironment#opify(Object, java.lang.reflect.Type)}.
 * <p>
 * This can be useful if you ever have a lambda that cannot be made into an Op.
 * <p>
 * Note that <strong>any</strong> Op returned by the Op matcher already knows
 * its type and thus does not need to be wrapped by {@code opify()} before being
 * passed back into the matcher.
 *
 * @author Gabriel Selzer
 */
@Plugin(type = OpCollection.class)
public class PassingLambdaToOps {

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
	 * Generates an {@link Img} with dimensions defined by {@code size}.
	 * 
	 * @param size - The dimensions of the returned {@code Img}
	 * @return an {@code Img}
	 */
	public static Img<UnsignedByteType> generateDemoImage(long... size) {
		// Fill a test image with integers in the range [0, 256)
		Img<UnsignedByteType> img = ArrayImgs.unsignedBytes(size);
		MersenneTwisterFast betterRNG = new MersenneTwisterFast(0xf1eece);
		for (UnsignedByteType b : img) {
			b.set(betterRNG.nextInt(256));
		}
		return img;
	}
	
	/**
	 * Bakes the type of {@link PassingLambdaToOps#demoOpField} into the lambda
	 * 
	 * @param opEnv - the {@link OpEnvironment}
	 * @return a subclass of {@link Computers.Arity1} that knows its generic type
	 */
	private static Computers.Arity1<Iterable<UnsignedByteType>, UnsignedByteType> getOpifiedLambda(OpEnvironment opEnv) {
		return opEnv.opify(demoOpField,
			new Nil<Computers.Arity1<Iterable<UnsignedByteType>, UnsignedByteType>>()
			{}.getType());
	}

	public static void run(OpEnvironment opEnv) {
		// Create image to project
		Img<UnsignedByteType> input = generateDemoImage(1, 8);

		// Create preallocated output with one fewer dimension
		Img<UnsignedByteType> demoOpOutput = generateDemoImage(1);

		// run project using demoOpField
		opEnv.op("project") //
			.input(input, getOpifiedLambda(opEnv), 1) //
			.output(demoOpOutput) //
			.compute();

		// Convert the image to an array so that we can print its values
		int[] inputValues = StreamSupport.stream(input.spliterator(), false)
			.mapToInt(pixel -> pixel.get()).toArray();

		// Compare values of image, result of lambda
		System.out.println("Input image values: " + Arrays.toString(inputValues));
		System.out.println("The maximum of the input image is " + demoOpOutput
			.firstElement());
	}

	public static void main(String... args) {
		OpEnvironment opEnv = getOpEnvironment();

		System.out.println(
			"// -- Using our lambda to project our input image -- //");
		run(opEnv);
	}

	/**
	 * A lambda that finds the max of the {@code Iterable}.
	 * <p>
	 * Note that the Op operates on {@link RealType}s, not on
	 * {@link RandomAccessibleInterval}s.
	 * 
	 * @author Gabriel Selzer
	 * @see LiftComputersToRAI#lift1
	 */
	public static final Computers.Arity1<Iterable<UnsignedByteType>, UnsignedByteType> demoOpField =
		(in, out) -> {
			out.setZero();
			Iterator<UnsignedByteType> itr = in.iterator();
			while (itr.hasNext()) {
				UnsignedByteType b = itr.next();
				if (b.compareTo(out) > 0) out.set(b);
			}
		};

}
