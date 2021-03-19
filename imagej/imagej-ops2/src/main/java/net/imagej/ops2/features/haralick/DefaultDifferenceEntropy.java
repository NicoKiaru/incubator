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
package net.imagej.ops2.features.haralick;

import java.util.function.Function;

import net.imagej.ops2.image.cooccurrenceMatrix.MatrixOrientation;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.DoubleType;

import org.scijava.ops.OpDependency;
import org.scijava.ops.core.Op;
import org.scijava.plugin.Plugin;
import org.scijava.struct.ItemIO;

/**
 * 
 * Implementation of Difference Entropy Haralick Feature
 * 
 * @author Andreas Graumann (University of Konstanz)
 * @author Christian Dietz (University of Konstanz)
 * @author Tim-Oliver Buchholz (University of Konstanz)
 */
@Plugin(type = Op.class, name = "features.haralick.differenceEntropy")
public class DefaultDifferenceEntropy<T extends RealType<T>> extends AbstractHaralickFeature<T> {

	// Avoid log 0
	private static final double EPSILON = Double.MIN_NORMAL;

	@OpDependency(name = "features.haralick.coocPXMinusY")
	private Function<double[][], double[]> coocPXMinusYFunc;

	@Override
	/**
	 * TODO
	 *
	 * @param input
	 * @param numGreyLevels
	 * @param distance
	 * @param matrixOrientation
	 * @return the output
	 */
	public DoubleType apply(final RandomAccessibleInterval<T> input, final Integer numGreyLevels, final Integer distance,
			final MatrixOrientation orientation) {
		final double[][] matrix = getCooccurrenceMatrix(input, numGreyLevels, distance, orientation);

		final double[] pxminusy = coocPXMinusYFunc.apply(matrix);
		final int nrGrayLevels = matrix.length;

		double res = 0;
		for (int k = 0; k < nrGrayLevels; k++) {
			res += pxminusy[k] * Math.log(pxminusy[k] + EPSILON);
		}

		DoubleType output = new DoubleType();
		output.set(-res);
		return output;

	}

}
