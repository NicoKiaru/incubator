package org.scijava.ops.stats;

import java.util.function.Function;
import java.util.stream.StreamSupport;

import org.scijava.ops.OpClass;
import org.scijava.ops.core.Op;
import org.scijava.param.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.struct.ItemIO;

public class Size {

	@OpClass(names = "stats.size", params = "iterable, size")
	public static class StatsSizeFunction<T> implements Function<Iterable<T>, Long>{

		@Override
		public Long apply(Iterable<T> iterable) {
			return StreamSupport.stream(iterable.spliterator(), false).count();
		}
	}
	
	   @OpClass(names = "stats.size", params = "iterable, size")
	    public static class StatsSizeFunctionDouble<T> implements Function<Iterable<T>, Double>{

	        @Override
	        public Double apply(Iterable<T> iterable) {
	            return (double) StreamSupport.stream(iterable.spliterator(), false).count();
	        }
	    }
}
