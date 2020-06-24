
package net.imagej.opsdemo;

import java.util.function.Function;

import net.imagej.ops2.types.adapt.LiftComputersToRAI;
import net.imglib2.Cursor;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.integer.UnsignedByteType;

import org.scijava.Context;
import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpField;
import org.scijava.ops.OpService;
import org.scijava.ops.adapt.functional.ComputersToFunctionsViaSource;
import org.scijava.ops.core.Op;
import org.scijava.ops.core.OpCollection;
import org.scijava.ops.function.Computers;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginService;
import org.scijava.types.Nil;
import org.scijava.types.TypeService;
import org.scijava.util.MersenneTwisterFast;

/**
 * How to adapt an {@link Op} manually using an {@code adapt} Op.
 * <p>
 * Note that adapting Ops often comes at a performance cost. Consider the
 * adaptation of a {@link Computers.Arity1} to a {@code Function} (using
 * {@link ComputersToFunctionsViaSource.Computer1ToFunction1ViaSource}): in
 * order to call this {@code Computer} as a {@code Function}, you must create an
 * output {@link Object} every time the {@code Function} is called so that the
 * {@code Computer} has a preallcated output. This is (often) fine if you only
 * need to use the Op once, but for repeated calls consider calling the Op as
 * its declared Type.
 * <p>
 * Note that this performance cost is not standard; some adaptations can be
 * performed in a way that boosts performance. Suppose you have a
 * {@code Computer} that operates on {@link RealType}s, and you wish to call
 * this Op on an {@link Img}. Using {@link LiftComputersToRAI}, we can apply
 * this Op across all pixels of the {@code Img} in an efficient manner.
 * <p>
 * <b>While this behavior is showcased for completeness, we do not suggest that
 * it should be used. In most cases, it is better to allow the matcher to
 * automagically adapt the Op. This avoids the wall of text shown below. </b>
 *
 * @author Gabriel Selzer
 * @see AdaptOpAutomagically
 */
@Plugin(type = OpCollection.class)
public class AdaptOpManually {

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
		// Fill a test image with integers in the range [0, 64)
		Img<UnsignedByteType> img = ArrayImgs.unsignedBytes(size);
		MersenneTwisterFast betterRNG = new MersenneTwisterFast(0xf1eece);
		for (UnsignedByteType b : img) {
			b.set(betterRNG.nextInt(256 / 4));
		}
		return img;
	}

	public static void run(OpEnvironment opEnv) {
		Img<UnsignedByteType> input = generateDemoImage(2, 2);

		// Create an output image to pass to the Op
		Img<UnsignedByteType> output = opEnv.op("create.img") //
			.input(input) //
			.outType(new Nil<Img<UnsignedByteType>>()
			{}) //
			.apply();

		// Build the Op
		Computers.Arity1<UnsignedByteType, UnsignedByteType> pixelComputer = opEnv
			.op("demo.uselessPixelOp") // Ask for an Op with the given name
			.inType(UnsignedByteType.class) // Provide the input image
			.outType(UnsignedByteType.class) // Provide the output image
			.computer(); // Compute directly (i.e. do not give us a Computer)

		// Obtain the adapt Op
		Function<Computers.Arity1<UnsignedByteType, UnsignedByteType>, Computers.Arity1<RandomAccessibleInterval<UnsignedByteType>, RandomAccessibleInterval<UnsignedByteType>>> adaptor =
			opEnv.op("adapt") // Ask for an Op with the given name
				.inType(new Nil<Computers.Arity1<UnsignedByteType, UnsignedByteType>>()
				{}) // Provide the input image
				.outType(
					new Nil<Computers.Arity1<RandomAccessibleInterval<UnsignedByteType>, RandomAccessibleInterval<UnsignedByteType>>>()
					{}) // Provide the output image
				.function(); // Compute directly (i.e. do not give us a Computer)

		// Adapt the Op
		Computers.Arity1<RandomAccessibleInterval<UnsignedByteType>, RandomAccessibleInterval<UnsignedByteType>> raiComputer =
			adaptor.apply(pixelComputer);

		// Apply the Op
		raiComputer.compute(input, output);

		// Show output of Op
		Cursor<UnsignedByteType> inputCursor = input.cursor();
		Cursor<UnsignedByteType> outputCursor = output.cursor();
		while (inputCursor.hasNext()) {
			System.out.println(inputCursor.next().get() + " x 4 = " + outputCursor
				.next().get());
		}
	}

	public static void main(String... args) {
		OpEnvironment opEnv = getOpEnvironment();

		System.out.println("// -- Obtain adapted Op from the OpEnvironment -- //");
		run(opEnv);
	}

	/**
	 * A useless Op
	 * <p>
	 * Note that the Op operates on {@link RealType}s, not on
	 * {@link RandomAccessibleInterval}s.
	 * 
	 * @author Gabriel Selzer
	 * @see LiftComputersToRAI#lift1
	 */
	@OpField(names = "demo.uselessPixelOp")
	public final Computers.Arity1<UnsignedByteType, UnsignedByteType> demoOpField =
		(in, out) -> out.set(in.get() * 4);

}
