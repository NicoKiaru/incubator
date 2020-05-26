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

package net.imagej.ops2.filter.addNoise;

import net.imglib2.Localizable;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.loops.LoopBuilder;
import net.imglib2.type.numeric.RealType;
import net.imglib2.util.Intervals;

import org.scijava.ops.OpDependency;
import org.scijava.ops.core.Op;
import org.scijava.ops.function.Computers;
import org.scijava.param.Mutable;
import org.scijava.plugin.Plugin;
import org.scijava.util.MersenneTwisterFast;

/**
 * Adds Uniform Noise to a {@link RandomAccessibleInterval}. Note that the
 * output is always clamped; when wrapped output is desired, use (TODO)
 * 
 * @author Gabriel Selzer
 */
@Plugin(type = Op.class, name = "filter.addUniformNoise")
public class AddUniformNoise<I extends RealType<I>> implements
	Computers.Arity4<RandomAccessibleInterval<I>, I, I, Long, RandomAccessibleInterval<I>>
{
	
	// Number of blocks per dimension.
	// Suppose blocksPerDim is 4. Then there are 4^n blocks in the image.
	int blocksPerDim = 4;

	@Override
	public void compute(RandomAccessibleInterval<I> input, I rangeMin, I rangeMax,
		Long seed, @Mutable RandomAccessibleInterval<I> output)
	{
		RandomAccessibleInterval<Localizable> positions = Intervals.positions(input);
		LoopBuilder.setImages(input, positions, output).multiThreaded().forEachChunk( chunk -> {
			MersenneTwisterFast rng = null;
			chunk.forEachPixel((in, pos, out) -> {
				if (rng == null) {
					long offset = 0;
					for (int i = 0; i <)
					rng = new MersenneTwisterFast(seed + pos.getLongPosition(0));
				}
			}); 
			
			return null;
		});

	}

}

/**
 * Adds Uniform Noise to a {@link RandomAccessibleInterval} using the default
 * seed. N.B. we use the same seed every time to ensure deterministic behavior.
 * 
 * @author Gabriel Selzer
 */
@Plugin(type = Op.class, name = "filter.addUniformNoise")
class AddUniformNoiseDefaultSeed<I extends RealType<I>> implements
	Computers.Arity3<RandomAccessibleInterval<I>, I, I, RandomAccessibleInterval<I>>
{

	@OpDependency(name = "filter.addUniformNoise")
	public Computers.Arity4<RandomAccessibleInterval<I>, I, I, Long, RandomAccessibleInterval<I>> noiseOp;

	private Long defaultSeed = 0xabcdef1234567890L;

	@Override
	public void compute(RandomAccessibleInterval<I> input, I rangeMin, I rangeMax,
		@Mutable RandomAccessibleInterval<I> out)
	{
		noiseOp.compute(input, rangeMin, rangeMax, defaultSeed, out);
	}

}
