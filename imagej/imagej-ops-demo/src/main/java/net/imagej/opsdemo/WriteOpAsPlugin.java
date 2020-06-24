
package net.imagej.opsdemo;

import org.scijava.Context;
import org.scijava.ops.OpEnvironment;
import org.scijava.ops.OpService;
import org.scijava.ops.core.Op;
import org.scijava.ops.function.Computers;
import org.scijava.ops.function.Functions;
import org.scijava.ops.function.Inplaces;
import org.scijava.ops.function.Producer;
import org.scijava.param.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginService;
import org.scijava.types.TypeService;

/**
 * How to declare an {@link Op} as a Scijava {@link Plugin}
 *
 * @author Gabriel Selzer
 * @see Computers
 * @see Functions
 * @see Inplaces
 */
public class WriteOpAsPlugin {

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

	static void run(OpEnvironment opEnv) {
		// Build the Op
		Producer<String> demoOp = opEnv.op("demo.opPlugin") //
			.input() // Producers have no inputs
			.outType(String.class) // Our op needs to return a String
			.producer();

		// Run the Op -> Print the String
		System.out.println(demoOp.create());
	}

	public static void main(String... args) {
		OpEnvironment opEnv = getOpEnvironment();

		System.out.println("// -- Obtain declared Op from the OpEnvironment -- //");
		run(opEnv);
	}

}

/**
 * A barebones {@link Op} defined as a Scijava {@link Plugin}. Note the
 * following:
 * <ul>
 * <li>The {@code @Plugin} annotation makes the Op discoverable and available to
 * the {@link OpEnvironment}
 * <li>The Op implements {@link Producer}, one of the many vanilla Op types.
 * </ul>
 * 
 * @author Gabriel Selzer
 * @see Producer
 */
@Plugin(type = Op.class, name = "demo.opPlugin")
@Parameter(key = "output")
class DemoOpPlugin implements Producer<String> {

	@Override
	public String create() {
		return "This is how you create an Op as a Plugin!";
	}

}
