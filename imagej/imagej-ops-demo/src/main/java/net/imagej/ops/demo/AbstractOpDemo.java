
package net.imagej.ops.demo;

import org.scijava.Context;
import org.scijava.ops.OpService;
import org.scijava.ops.core.Op;
import org.scijava.plugin.PluginService;
import org.scijava.types.TypeService;

/**
 * Contains basic behavior similar to all Op Demos
 *
 * @author Gabriel Selzer
 */
public abstract class AbstractOpDemo {

	/**
	 * Uses a {@link Context} to obtain an {@OpService}.
	 *
	 * @return an {@link OpService} that can be used to obtain {@link Op}s
	 */
	public static OpService getOpService() {
		Context context = new Context(OpService.class, PluginService.class,
			TypeService.class);
		OpService ops = context.getService(OpService.class);
		return ops;
	}
}
