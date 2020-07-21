package org.scijava.ops.stats;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.scijava.ops.OpClass;
import org.scijava.ops.OpDependency;

public class Mean {

	@OpClass(names = "stats.mean", params = "iterable, mean")
	public static class MeanFunction <N, O> implements Function<Iterable<N>, O>{

		@OpDependency(name = "math.add")
		Function<Iterable<N>, O> sumFunc;

		@OpDependency(name = "stats.size")
		Function<Iterable<N>, O> sizeFunc;
		
		@OpDependency(name = "math.div")
		BiFunction<O, O, O> divFunc;

		@Override
		public O apply(Iterable<N> iterable) {
			return divFunc.apply(sumFunc.apply(iterable), sizeFunc.apply(iterable));
		}
	}

}
