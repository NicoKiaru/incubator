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

package net.imagej.ops2.geom.geom2d;

import java.lang.reflect.Type;
import java.util.function.BiFunction;

import net.imglib2.RandomAccessibleInterval;
import net.imglib2.roi.geom.real.Polygon2D;
import net.imglib2.roi.labeling.LabelRegion;
import net.imglib2.type.logic.BoolType;

import org.scijava.Priority;
import org.scijava.convert.AbstractConverter;
import org.scijava.convert.ConversionRequest;
import org.scijava.convert.Converter;
import org.scijava.ops.OpService;
import org.scijava.ops.function.Functions;
import org.scijava.types.Nil;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 * Converts a {@link LabelRegion} to a polygon
 * 
 * @author Daniel Seebacher (University of Konstanz)
 */
@SuppressWarnings("rawtypes")
@Plugin(type = Converter.class, priority = Priority.VERY_HIGH)
public class LabelRegionToPolygonConverter extends
	AbstractConverter<LabelRegion, Polygon2D>
{
	@Parameter
	private OpService ops;

	private BiFunction<RandomAccessibleInterval<BoolType>, Boolean, Polygon2D> contourFunc;

	@SuppressWarnings({ "unchecked" })
	@Override
	public <T> T convert(final Object src, final Class<T> dest) {
		if (contourFunc == null) {
			contourFunc = Functions.match(ops.env(), "geom.contour", new Nil<RandomAccessibleInterval<BoolType>>() {}, new Nil<Boolean>() {},
					new Nil<Polygon2D>() {});
		}
		// FIXME: can we make this faster?
		if (!(src instanceof LabelRegion))
			throw new IllegalArgumentException("Input is not a LabelRegion, cannot complete the conversion.");
		final Polygon2D p = contourFunc.apply((LabelRegion) src, true);
		return (T) p;
	}

	@Override
	public Class<Polygon2D> getOutputType() {
		return Polygon2D.class;
	}

	@Override
	public Class<LabelRegion> getInputType() {
		return LabelRegion.class;
	}

	@Override
	public boolean supports(final ConversionRequest request) {
		if (ops == null) return false;

		final Object sourceObject = request.sourceObject();

		// we can decide if we can create a Polygon2D as we have to know the
		// dimensionality of the incoming object
		if (sourceObject == null || !(sourceObject instanceof LabelRegion)) {
			return false;
		}

		if (((LabelRegion) sourceObject).numDimensions() != 2) {
			return false;
		}

		Class<?> destClass = request.destClass();
		Type destType = request.destType();

		if (destClass != null && !(destClass == Polygon2D.class)) {
			return false;
		}
		else if (destType != null && !(destType == Polygon2D.class)) {
			return false;
		}

		return true;
	}

}
