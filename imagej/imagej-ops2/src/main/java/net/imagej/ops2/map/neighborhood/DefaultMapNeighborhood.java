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

package net.imagej.ops2.map.neighborhood;

import net.imglib2.Cursor;
import net.imglib2.IterableInterval;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.algorithm.neighborhood.Neighborhood;
import net.imglib2.algorithm.neighborhood.Shape;
import net.imglib2.loops.LoopBuilder;
import net.imglib2.view.Views;

import org.scijava.Priority;
import org.scijava.ops.core.Op;
import org.scijava.ops.function.Computers;
import org.scijava.param.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.struct.ItemIO;

/**
 * Evaluates a {@link UnaryComputerOp} for each {@link Neighborhood} on the
 * input {@link RandomAccessibleInterval}.
 * 
 * @author Christian Dietz (University of Konstanz)
 * @author Martin Horn (University of Konstanz)
 * @param <I> input type
 * @param <O> output type
 */
@Plugin(type = Op.class, name = "map.neighborhood")
@Parameter(key = "input")
@Parameter(key = "shape")
@Parameter(key = "op")
@Parameter(key = "output")
public class DefaultMapNeighborhood<I, O> implements
	Computers.Arity3<RandomAccessibleInterval<I>, Shape, Computers.Arity1<Iterable<I>, O>, IterableInterval<O>>
{
	@Override
	public void compute(final RandomAccessibleInterval<I> in1, final Shape in2,
		final Computers.Arity1<Iterable<I>, O> computer, final IterableInterval<O> out)
	{
		// TODO can we do this through a mapper?
		Cursor<Neighborhood<I>> inCursor = in2.neighborhoodsSafe(in1).cursor();
		Cursor<O> outCursor = out.cursor(); 
		while(outCursor.hasNext()) {
			outCursor.fwd();
			inCursor.fwd();
			computer.accept(inCursor.get(), outCursor.get());
		}
	}

}

/**
 * Evaluates a computer for each {@link Neighborhood} on the input
 * {@link RandomAccessibleInterval} and sets the value of the corresponding
 * pixel on the output {@link RandomAccessibleInterval}. Similar to
 * {@link DefaultMapNeighborhood}, but passes the center pixel to the op as
 * well. This Op is set to higher priority than
 * {@link MapNeighborhoodWithCenter} since uses {@link LoopBuilder} to
 * multi-thread the process. Note that this process should be thread-safe so
 * long as the computer is state-less (which is a part of the contract for Ops).
 * We also assume that the input and output have identical dimensions.
 * 
 * @author Gabriel Selzer
 */
@Plugin(type = Op.class, name = "map.neighborhood", priority = Priority.HIGH)
@Parameter(key = "input")
@Parameter(key = "shape")
@Parameter(key = "op")
@Parameter(key = "output")
class MapNeighborhoodAllRAI<I, O> implements
	Computers.Arity3<RandomAccessibleInterval<I>, Shape, Computers.Arity1<Iterable<I>, O>, RandomAccessibleInterval<O>>
{

	@Override
	public void compute(final RandomAccessibleInterval<I> in1, final Shape in2,
		final Computers.Arity1<Iterable<I>, O> centerAwareOp,
		final RandomAccessibleInterval<O> out)
	{
		// generate a neighborhood image with the bounds of the input
		RandomAccessibleInterval<Neighborhood<I>> neighborhoodInput = Views
			.interval(in2.neighborhoodsRandomAccessibleSafe(in1), in1);

		LoopBuilder.setImages(neighborhoodInput, out).multiThreaded()
			.forEachPixel((neighborhood, outPixel) -> {
				centerAwareOp.compute(neighborhood, outPixel);
			});
	}

}
