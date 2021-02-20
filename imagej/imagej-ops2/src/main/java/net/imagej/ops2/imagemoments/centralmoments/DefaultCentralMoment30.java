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

package net.imagej.ops2.imagemoments.centralmoments;

import java.util.List;

import net.imagej.ops2.imagemoments.AbstractImageMomentOp;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.loops.LoopBuilder;
import net.imglib2.type.numeric.RealType;
import net.imglib2.util.Intervals;

import org.scijava.ops.OpDependency;
import org.scijava.ops.core.Op;
import org.scijava.ops.function.Computers;
import org.scijava.param.Parameter;
import org.scijava.plugin.Plugin;

/**
 * {@link Op} to calculate the {@code imageMoments.centralMoment30}.
 * 
 * @author Daniel Seebacher (University of Konstanz)
 * @author Christian Dietz (University of Konstanz)
 * @param <I>
 *            input type
 * @param <O>
 *            output type
 */
@Plugin(type = Op.class, name = "imageMoments.centralMoment30", label = "Image Moment: CentralMoment30")
@Parameter(key = "input")
@Parameter(key = "output")
public class DefaultCentralMoment30<I extends RealType<I>, O extends RealType<O>>
		implements AbstractImageMomentOp<I, O> {

	@OpDependency(name = "imageMoments.moment00")
	private Computers.Arity1<RandomAccessibleInterval<I>, O> moment00Func;

	@OpDependency(name = "imageMoments.moment10")
	private Computers.Arity1<RandomAccessibleInterval<I>, O> moment10Func;

	@OpDependency(name = "math.power")
	private Computers.Arity2<O, Integer, O> powerOp;

	@Override
	public void computeMoment(final RandomAccessibleInterval<I> input,
		final O output)
	{
		final O moment00 = output.createVariable();
		moment00Func.compute(input, moment00);
		final O moment10 = output.createVariable();
		moment10Func.compute(input, moment10);

		final O centerX = moment10.copy();
		centerX.div(moment00);

		List<O> sums = LoopBuilder.setImages(input, Intervals.positions(input))
			.multiThreaded().forEachChunk(chunk -> {
				O sum = output.createVariable();
				O temp = output.createVariable();
				O x = output.createVariable();
				chunk.forEachPixel((pixel, pos) -> {
					// x = (pixelPos - centerX)^3
					temp.setReal(pos.getDoublePosition(0));
					temp.sub(centerX);
					powerOp.compute(temp, 3, x);
					// temp = pixelVal * (pixelPos - centerX)^3
					temp.setReal(pixel.getRealDouble());
					temp.mul(x);
					sum.add(temp);
				});
				return sum;
			});

		output.setZero();
		for (O sum : sums)
			output.add(sum);
	}
}
