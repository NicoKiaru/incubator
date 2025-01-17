/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2014 - 2018 ImageJ developers.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package net.imagej.ops2.stats;

import java.util.List;

import net.imglib2.RandomAccessibleInterval;
import net.imglib2.loops.LoopBuilder;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.integer.LongType;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.util.Util;

import org.scijava.Priority;
import org.scijava.ops.OpDependency;
import org.scijava.ops.core.Op;
import org.scijava.ops.function.Computers;
import org.scijava.param.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.struct.ItemIO;

/**
 * {@link Op} to calculate the {@code stats.variance} using the
 * {@code stats.stdDev} using the two-pass algorithm.
 * 
 * @author Daniel Seebacher (University of Konstanz)
 * @author Christian Dietz (University of Konstanz)
 * @param <I>
 *            input type
 * @param <O>
 *            output type
 * @see <a href=
 *      "https://en.wikipedia.org/wiki/Algorithms_for_calculating_variance#Two-pass_algorithm">
 *      Wikipedia </a>
 */
@Plugin(type = Op.class, name = "stats.variance", priority = Priority.HIGH)
@Parameter(key = "raiInput")
@Parameter(key = "variance")
public class DefaultVariance<I extends RealType<I>, O extends RealType<O>> implements Computers.Arity1<RandomAccessibleInterval<I>, O> {

	@OpDependency(name = "stats.mean")
	private Computers.Arity1<RandomAccessibleInterval<I>, DoubleType> meanOp;

	@OpDependency(name = "stats.size")
	private Computers.Arity1<RandomAccessibleInterval<I>, LongType> sizeOp;

	@Override
	public void compute(final RandomAccessibleInterval<I> input, final O output) {

		final DoubleType mean = new DoubleType();
		meanOp.compute(input, mean);
		final LongType size = new LongType(0);
		sizeOp.compute(input, size);

		List<DoubleType> chunkSums = LoopBuilder.setImages(input).multiThreaded().forEachChunk(chunk -> {
			DoubleType chunkSum = new DoubleType(0);
			DoubleType temp = new DoubleType();
			chunk.forEachPixel(pixel -> {
				double x = pixel.getRealDouble();
				temp.set((x - mean.getRealDouble()) * (x - mean.getRealDouble()));
				chunkSum.add(temp);
			});
			return chunkSum;
		});

		double sum = chunkSums.parallelStream().mapToDouble(chunkSum -> chunkSum
			.get()).sum();

		output.setReal(sum / (size.get() - 1));
	}
}
