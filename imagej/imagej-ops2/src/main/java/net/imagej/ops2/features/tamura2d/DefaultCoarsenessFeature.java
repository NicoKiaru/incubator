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
package net.imagej.ops2.features.tamura2d;

import java.util.ArrayList;
import java.util.HashMap;

import net.imglib2.Cursor;
import net.imglib2.FinalInterval;
import net.imglib2.IterableInterval;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.algorithm.neighborhood.RectangleShape;
import net.imglib2.algorithm.neighborhood.Shape;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.outofbounds.OutOfBoundsFactory;
import net.imglib2.outofbounds.OutOfBoundsMirrorFactory;
import net.imglib2.outofbounds.OutOfBoundsMirrorFactory.Boundary;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.util.Intervals;

import org.scijava.ops.OpDependency;
import org.scijava.ops.core.Op;
import org.scijava.ops.function.Computers;
import org.scijava.ops.function.Computers;
import org.scijava.param.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.struct.ItemIO;

/**
 * 
 * Implementation of Tamura's Coarseness feature
 * 
 * @author Andreas Graumann (University of Konstanz)
 *
 * @param <I>
 * @param <O>
 */
@Plugin(type = Op.class, name = "features.tamura.coarseness")
@Parameter(key = "input")
@Parameter(key = "output")
public class DefaultCoarsenessFeature<I extends RealType<I>, O extends RealType<O>>
		implements Computers.Arity1<RandomAccessibleInterval<I>, O> {

	@OpDependency(name = "filter.mean")
	private Computers.Arity3<RandomAccessibleInterval<I>, Shape, //
			OutOfBoundsFactory<I, RandomAccessibleInterval<I>>, RandomAccessibleInterval<I>> meanOp;

	@SuppressWarnings("unchecked")
	@Override
	public void compute(final RandomAccessibleInterval<I> input, final O output) {
		if (input.numDimensions() != 2)
			throw new IllegalArgumentException("Only 2 dimensional images allowed!");
		HashMap<Integer, Img<I>> meanImages = new HashMap<>();

		// get mean images
		for (int i = 1; i <= 5; i++) {
			meanImages.put(i, mean(input, i));
		}

		ArrayList<Double> maxDifferences = sizedLeadDiffValues(input, meanImages);

		double out = 0.0;
		for (Double i : maxDifferences) {
			out += i;
		}

		out /= maxDifferences.size();

		output.set((O) new DoubleType(out));
	}

	/**
	 * 
	 * For every point calculate differences between the not overlapping
	 * neighborhoods on opposite sides of the point in horizontal and vertical
	 * direction. At each point take the highest difference value when considering
	 * all directions together.
	 * 
	 * @param input
	 *            Input image
	 * @param meanImages
	 *            Mean images
	 * @return Array containing all leadding difference values
	 */
	private ArrayList<Double> sizedLeadDiffValues(final RandomAccessibleInterval<I> input,
			final HashMap<Integer, Img<I>> meanImages) {

		long[] pos = new long[input.numDimensions()];
		long[] dim = new long[input.numDimensions()];
		input.dimensions(dim);

		ArrayList<Double> maxDifferences = new ArrayList<>();
		Cursor<I> cursor = meanImages.get(1).cursor();

		while (cursor.hasNext()) {

			cursor.next();

			// NB: the smallest possible value for maxDiff is 0
			double maxDiff = 0;

			for (int i = 1; i <= 5; i++) {

				RandomAccess<I> ra1 = meanImages.get(i).randomAccess();
				RandomAccess<I> ra2 = meanImages.get(i).randomAccess();

				for (int d = 0; d < input.numDimensions(); d++) {

					cursor.localize(pos);

					if (pos[d] + 2 * i + 1 < dim[d]) {

						ra1.setPosition(pos);
						double val1 = ra1.get().getRealDouble();

						pos[d] += 2 * i + 1;
						ra2.setPosition(pos);
						double val2 = ra2.get().getRealDouble();

						double diff = Math.abs(val2 - val1);
						maxDiff = diff >= maxDiff ? diff : maxDiff;
					}
				}
			}

			maxDifferences.add(maxDiff);
		}
		return maxDifferences;
	}

	/**
	 * Apply mean filter with given size of reactangle shape
	 * 
	 * @param input
	 *            Input image
	 * @param i
	 *            Size of rectangle shape
	 * @return Filered mean image
	 */
	@SuppressWarnings("unchecked")
	private Img<I> mean(final RandomAccessibleInterval<I> input, final int i) {

		long[] dims = new long[input.numDimensions()];
		input.dimensions(dims);

		final byte[] array = new byte[(int) Intervals.numElements(new FinalInterval(dims))];
		Img<I> meanImg = (Img<I>) ArrayImgs.unsignedBytes(array, dims);

		OutOfBoundsMirrorFactory<I, RandomAccessibleInterval<I>> oobFactory = new OutOfBoundsMirrorFactory<>(
				Boundary.SINGLE);

		meanOp.compute(input, new RectangleShape(i, true), oobFactory, meanImg);

		return meanImg;
	}

}
