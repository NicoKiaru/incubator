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

package net.imagej.ops2.geom;

import java.util.function.Function;

import net.imagej.mesh.Mesh;
import net.imglib2.RealLocalizable;
import net.imglib2.RealPoint;
import net.imglib2.type.numeric.real.DoubleType;

import org.scijava.ops.OpDependency;
import org.scijava.ops.core.Op;
import org.scijava.param.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.struct.ItemIO;

/**
 * Generic implementation of {@code geom.centroid}.
 * 
 * Computation based on http://wwwf.imperial.ac.uk/~rn/centroid.pdf.
 * 
 * @author Tim-Oliver Buchholz (University of Konstanz)
 */
@Plugin(type = Op.class, name = "geom.centroid", label = "Geometric: Centroid")
@Parameter(key = "input")
@Parameter(key = "centroid")
public class CentroidMesh implements Function<Mesh, RealLocalizable> {

	@OpDependency(name = "geom.size")
	private Function<Mesh, DoubleType> sizeFunc;

	@Override
	public RealLocalizable apply(final Mesh input) {

		double c_x = 0;
		double c_y = 0;
		double c_z = 0;

		for (int i = 0; i < input.triangles().size(); i++) {
			final long v0 = input.triangles().vertex0(i);
			final long v1 = input.triangles().vertex1(i);
			final long v2 = input.triangles().vertex2(i);
			final double nx = input.triangles().nx(i);
			final double ny = input.triangles().ny(i);
			final double nz = input.triangles().nz(i);
			final double v0x = input.vertices().x(v0);
			final double v0y = input.vertices().y(v0);
			final double v0z = input.vertices().z(v0);
			final double v1x = input.vertices().x(v1);
			final double v1y = input.vertices().y(v1);
			final double v1z = input.vertices().z(v1);
			final double v2x = input.vertices().x(v2);
			final double v2y = input.vertices().y(v2);
			final double v2z = input.vertices().z(v2);
			c_x += (1 / 24d) * nx * (Math.pow((v0x + v1x), 2) + Math.pow((v1x + v2x), 2) + Math.pow((v2x + v0x), 2));
			c_y += (1 / 24d) * ny * (Math.pow((v0y + v1y), 2) + Math.pow((v1y + v2y), 2) + Math.pow((v2y + v0y), 2));
			c_z += (1 / 24d) * nz * (Math.pow((v0z + v1z), 2) + Math.pow((v1z + v2z), 2) + Math.pow((v2z + v0z), 2));
		}

		double d = 1 / (2 * sizeFunc.apply(input).get());
		c_x *= d;
		c_y *= d;
		c_z *= d;

		return new RealPoint(-c_x, -c_y, -c_z);
	}
}
