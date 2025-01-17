package net.imagej.ops2.create;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

import net.imagej.ImgPlus;
import net.imagej.ImgPlusMetadata;
import net.imagej.ops2.create.img.Imgs;
import net.imagej.ops2.create.kernel.DefaultCreateKernel2ndDerivBiGauss;
import net.imagej.ops2.create.kernel.DefaultCreateKernelBiGauss;
import net.imagej.ops2.create.kernel.DefaultCreateKernelGabor;
import net.imagej.ops2.create.kernel.DefaultCreateKernelGauss;
import net.imagej.ops2.create.kernel.DefaultCreateKernelGibsonLanni;
import net.imagej.ops2.create.kernel.DefaultCreateKernelLog;
import net.imagej.ops2.create.kernel.DefaultCreateKernelSobel;
import net.imglib2.Cursor;
import net.imglib2.Dimensions;
import net.imglib2.FinalDimensions;
import net.imglib2.Interval;
import net.imglib2.IterableInterval;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.img.ImgFactory;
import net.imglib2.img.array.ArrayImgFactory;
import net.imglib2.roi.labeling.ImgLabeling;
import net.imglib2.roi.labeling.LabelingMapping;
import net.imglib2.type.BooleanType;
import net.imglib2.type.NativeType;
import net.imglib2.type.Type;
import net.imglib2.type.logic.BitType;
import net.imglib2.type.numeric.ComplexType;
import net.imglib2.type.numeric.IntegerType;
import net.imglib2.type.numeric.complex.ComplexDoubleType;
import net.imglib2.type.numeric.complex.ComplexFloatType;
import net.imglib2.type.numeric.integer.ByteType;
import net.imglib2.type.numeric.integer.IntType;
import net.imglib2.type.numeric.integer.LongType;
import net.imglib2.type.numeric.integer.ShortType;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.type.numeric.integer.UnsignedIntType;
import net.imglib2.type.numeric.integer.UnsignedShortType;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.util.Util;
import net.imglib2.view.Views;

import org.joml.Vector3d;
import org.joml.Vector3f;
import org.scijava.Priority;
import org.scijava.ops.OpField;
import org.scijava.ops.core.OpCollection;
import org.scijava.ops.function.Functions;
import org.scijava.ops.function.Functions;
import org.scijava.ops.function.Producer;
import org.scijava.plugin.Plugin;

@Plugin(type = OpCollection.class)
public class Creators<N extends NativeType<N>, L, I extends IntegerType<I>, T extends Type<T>, C extends ComplexType<C>, W extends ComplexType<W> & NativeType<W>, B extends BooleanType<B>> {

	/* ImgFactories */

	String iF = "imgFactory";

	@OpField(names = "create, create.imgFactory", params = "imgFactory")
	public final Producer<ImgFactory<DoubleType>> factorySource = () -> new ArrayImgFactory(new DoubleType());

	// note that dims is not actually passed to the ImgFactory but instead is
	// inspected to determine which will be returned.
	@OpField(names = "create, create.imgFactory", params = "dimensions, imgFactory")
	public final Function<Dimensions, ImgFactory<DoubleType>> factoryFromDims = (dims) -> Util
			.getSuitableImgFactory(dims, new DoubleType());

	@OpField(names = "create, create.imgFactory", params = "dimensions, type, imgFactory")
	public final BiFunction<Dimensions, L, ImgFactory<L>> factoryFromDimsAndType = Util::getSuitableImgFactory;

	@OpField(names = "create, create.imgFactory", params = "img, factory")
	public final Function<Img<L>, ImgFactory<L>> factoryFromImg = (img) -> img.factory();

	/* Imgs */

	@OpField(names = "create, create.img", params = "dimensions, type, factory, img")
	public final Functions.Arity3<Dimensions, T, ImgFactory<T>, Img<T>> imgFromDimsTypeAndFactory = (dims, type,
			factory) -> Imgs.create(factory, dims, type);

	@OpField(names = "create, create.img", params = "dimensions, type, img")
	public final BiFunction<Dimensions, T, Img<T>> imgFromDimsAndType = (dims, type) -> {
		ImgFactory<T> factory = dims instanceof Img<?> ? ((Img<T>) dims).factory()
				: Util.getSuitableImgFactory(dims, type);
		return Imgs.create(factory, dims, type);
	};

	@OpField(names = "create, create.img", params = "intArray, img")
	public final Function<int[], Img<DoubleType>> imgFromIntArray = (array) -> {
		FinalDimensions dims = new FinalDimensions(array);
		DoubleType type = new DoubleType();
		return Imgs.create(Util.getSuitableImgFactory(dims, type), dims, type);
	};

	@OpField(names = "create, create.img", params = "integerArray, img")
	public final Function<Integer[], Img<DoubleType>> imgFromIntegerArray = (array) -> imgFromIntArray
			.apply(Arrays.stream(array).mapToInt(Integer::intValue).toArray());

	@OpField(names = "create, create.img", params = "longArray, img")
	public final Function<long[], Img<DoubleType>> imgFromPrimitiveLongArray = (array) -> {
		FinalDimensions dims = new FinalDimensions(array);
		DoubleType type = new DoubleType();
		return Imgs.create(Util.getSuitableImgFactory(dims, type), dims, type);
	};

	@OpField(names = "create, create.img", params = "longArray, img")
	public final Function<Long[], Img<DoubleType>> imgFromLongArray = (array) -> imgFromPrimitiveLongArray
			.apply(Arrays.stream(array).mapToLong(Long::longValue).toArray());

	@OpField(names = "create, create.img", params = "ii, img", priority = Priority.NORMAL)
	public final Function<IterableInterval<T>, Img<T>> imgFromII = (ii) -> imgFromDimsAndType.apply(ii,
			ii.firstElement());

	@OpField(names = "create, create.img", params = "inputImg, img", priority = Priority.HIGH)
	public final Function<Img<T>, Img<T>> imgFromImg = (img) -> Imgs.create(img.factory(), img, img.firstElement());

	@OpField(names = "create, create.img", params = "interval, img", priority = Priority.LOW)
	public final Function<Interval, Img<DoubleType>> imgFromInterval = (interval) -> {
		DoubleType type = new DoubleType();
		return Imgs.create(Util.getSuitableImgFactory(interval, type), interval, type);
	};

	@OpField(names = "create, create.img", params = "rai, img", priority = Priority.NORMAL)
	public final Function<RandomAccessibleInterval<T>, Img<T>> imgFromRAI = (rai) -> imgFromDimsAndType.apply(rai,
			Util.getTypeFromInterval(rai));

	/* IntegerType */

	@OpField(names = "create, create.integerType", params = "integerType", priority = Priority.NORMAL)
	public final Producer<LongType> integerTypeSource = () -> new LongType();

	/* Type */

	@OpField(names = "create, create.type", params = "sampleType, type")
	public final Function<T, T> typeFromSampleType = (sample) -> sample.createVariable();

	@OpField(names = "create, create.type", params = "booleanType", priority = Priority.LOW)
	public final Producer<BitType> booleanTypeSource = () -> new BitType();

	/* ImgLabeling */

	@OpField(names = "create, create.imgLabeling", params = "img, imgLabeling")
	public final Function<Img<I>, ImgLabeling<L, I>> imgLabelingFromImg = ImgLabeling::new;

	@OpField(names = "create, create.imgLabeling", params = "dimensions, type, factory, imgLabeling")
	public final Functions.Arity3<Dimensions, I, ImgFactory<I>, ImgLabeling<L, I>> imgLabelingFromDimsTypeAndFactory = (dims,
			type, factory) -> {
		Img<I> img = Imgs.create(factory, dims, type);
		return imgLabelingFromImg.apply(img);
	};

	@OpField(names = "create, create.imgLabeling", params = "dimensions, type, imgLabeling")
	public final BiFunction<Dimensions, I, ImgLabeling<L, I>> imgLabelingFromDimsAndType = (dims,
			type) -> imgLabelingFromDimsTypeAndFactory.apply(dims, type, Util.getSuitableImgFactory(dims, type));

	/* ImgPlus */

	@OpField(names = "create, create.imgPlus", params = "img, imgPlug")
	public final Function<Img<T>, ImgPlus<T>> imgPlusFromImg = ImgPlus::new;

	@OpField(names = "create, create.imgPlus", params = "img, imgPlusMetadata, imgPlus")
	public final BiFunction<Img<T>, ImgPlusMetadata, ImgPlus<T>> imgPlusFromImgAndMetadata = ImgPlus::new;

	/* Kernel */

	@OpField(names = "create, create.kernel", params = "values, type, kernelRAI")
	public final BiFunction<double[][], C, RandomAccessibleInterval<C>> kernel2DFromValuesAndType = (arr, type) -> {
		FinalDimensions dims = new FinalDimensions(new long[] { arr.length, arr[0].length });
		RandomAccessibleInterval<C> rai = (RandomAccessibleInterval<C>) imgFromDimsAndType.apply(dims, (T) type);
		Cursor<C> cursor = Views.iterable(rai).cursor();
		for (int j = 0; j < arr.length; j++) {
			for (int k = 0; k < arr[j].length; k++) {
				cursor.fwd();
				cursor.get().setReal(arr[j][k]);
			}
		}

		return rai;
	};

	// TODO do we want to support this and if so is this the right way to do it?
	@OpField(names = "create, create.kernel", params = "values, kernelRAI")
	public final Function<double[][], RandomAccessibleInterval<DoubleType>> kernel2DFromValues = (
			arr) -> (RandomAccessibleInterval<DoubleType>) kernel2DFromValuesAndType.apply(arr, (C) new DoubleType());

	/* Gaussian Kernel */

	@OpField(names = "create, create.kernelGauss", params = "numDims, type, gaussKernelRAI")
	public final BiFunction<double[], C, RandomAccessibleInterval<C>> kernelGauss = (numDims, type) -> {
		return DefaultCreateKernelGauss.createKernel(numDims, type, imgFromDimsAndType);
	};

	// TODO do we want to support this and if so is this the right way to do it?
	@OpField(names = "create, create.kernelGauss", params = "sigmas, gaussKernelRAI")
	public final Function<double[], RandomAccessibleInterval<DoubleType>> kernelGaussDoubleType = (
			sigmas) -> (RandomAccessibleInterval<DoubleType>) kernelGauss.apply(sigmas, (C) new DoubleType());

	@OpField(names = "create, create.kernelGauss", params = "sigma, numDimensions, outType, gaussKernelRAI")
	public final Functions.Arity3<Double, Integer, C, RandomAccessibleInterval<C>> kernelGaussSymmetric = (sigma, numDims,
			type) -> {
		double[] sigmas = new double[numDims];
		Arrays.fill(sigmas, sigma);
		return kernelGauss.apply(sigmas, type);
	};

	// TODO is this cast safe?
	@OpField(names = "create, create.kernelGauss", params = "sigma, numDimensions, gaussKernelRAI")
	public final BiFunction<Double, Integer, RandomAccessibleInterval<DoubleType>> kernelGaussSymmetricDoubleType = (
			sigma, numDims) -> (RandomAccessibleInterval<DoubleType>) kernelGaussSymmetric.apply(sigma, numDims,
					(C) new DoubleType());

	/* Kernel Log */

	@OpField(names = "create, create.kernelLog", params = "sigmas, outType, logKernelRAI")
	public final BiFunction<double[], C, RandomAccessibleInterval<C>> kernelLog = (sigmas,
			type) -> DefaultCreateKernelLog.createKernel(sigmas, type, imgFromDimsAndType);

	@OpField(names = "create, create.kernelLog", params = "sigmas, logKernelRAI")
	public final Function<double[], RandomAccessibleInterval<DoubleType>> kernelLogDoubleType = (
			sigmas) -> (RandomAccessibleInterval<DoubleType>) kernelLog.apply(sigmas, (C) new DoubleType());

	@OpField(names = "create, create.kernelLog", params = "sigma, numDimensions, outType, logKernelRAI")
	public final Functions.Arity3<Double, Integer, C, RandomAccessibleInterval<C>> kernelLogSymmetric = (sigma, numDims,
			type) -> {
		double[] sigmas = new double[numDims];
		Arrays.fill(sigmas, sigma);
		return kernelLog.apply(sigmas, type);
	};

	@OpField(names = "create, create.kernelLog", params = "sigma, numDimensions, logKernelRAI")
	public final BiFunction<Double, Integer, RandomAccessibleInterval<DoubleType>> kernelLogSymmetricDoubleType = (
			sigma, numDims) -> (RandomAccessibleInterval<DoubleType>) kernelLogSymmetric.apply(sigma, numDims,
					(C) new DoubleType());

	/* Kernel Diffraction */

	@OpField(names = "create, create.kernelDiffraction", 
			params = "dimensions, NA, lambda, ns, ni, resLateral, " +
				"resAxial, pZ, type, diffractionKernelRAI")
	public final Functions.Arity9<Dimensions, Double, Double, Double, Double, Double, Double, Double, W, Img<W>> kernelDiffraction = (
			dims, NA, lambda, ns, ni, resLateral, resAxial, pZ, type) -> DefaultCreateKernelGibsonLanni
					.createKernel(dims, NA, lambda, ns, ni, resLateral, resAxial, pZ, type, imgFromDimsAndType);

	/* Kernel BiGauss */

	@OpField(names = "create, create.kernelBiGauss", 
			params = "sigmas, numDimensions, outType, biGaussKernelRAI")
	public final Functions.Arity3<double[], Integer, C, RandomAccessibleInterval<C>> kernelBiGauss = (sigmas, numDims,
			outType) -> DefaultCreateKernelBiGauss.createKernel(sigmas, numDims, outType, imgFromDimsAndType);

	@OpField(names = "create, create.kernelBiGauss", params = "sigmas, numDimensions, biGaussKernelRAI")
	public final BiFunction<double[], Integer, RandomAccessibleInterval<DoubleType>> kernelBiGaussDoubleType = (sigmas,
			numDims) -> (RandomAccessibleInterval<DoubleType>) kernelBiGauss.apply(sigmas, numDims,
					(C) new DoubleType());

	@OpField(names = "create, create.kernel2ndDerivBiGauss", 
			params = "sigmas, numDims, outType, biGaussKernelRAI")
	public final Functions.Arity3<double[], Integer, C, RandomAccessibleInterval<C>> kernel2ndDerivBiGauss = (sigmas, numDims,
			outType) -> DefaultCreateKernel2ndDerivBiGauss.createKernel(sigmas, numDims, outType, imgFromDimsAndType);

	@OpField(names = "create, create.kernel2ndDerivBiGauss",
			params = "sigmas, numDimensions, biGaussKernelRAI")
	public final BiFunction<double[], Integer, RandomAccessibleInterval<DoubleType>> kernel2ndDerivBiGaussDoubleType = (
			sigmas, numDims) -> (RandomAccessibleInterval<DoubleType>) kernel2ndDerivBiGauss.apply(sigmas, numDims,
					(C) new DoubleType());

	/* Kernel Gabor */

	@OpField(names = "create, create.kernelGabor",
			params = "sigmas, periods, outType, gaborKernelRAI")
	public final Functions.Arity3<double[], double[], C, RandomAccessibleInterval<C>> kernelGabor = (sigmas, periods,
			outType) -> DefaultCreateKernelGabor.createKernel(sigmas, periods, outType, imgFromDimsAndType);

	@OpField(names = "create, create.kernelGabor", 
			params = "sigmas, periods, gaborKernelRAI")
	public final BiFunction<double[], double[], RandomAccessibleInterval<DoubleType>> kernelGaborDouble = (sigmas,
			periods) -> (RandomAccessibleInterval<DoubleType>) kernelGabor.apply(sigmas, periods, (C) new DoubleType());

	@OpField(names = "create, create.kernelGabor",
			params = "sigmas, periods, gaborKernelRAI")
	public final BiFunction<double[], double[], RandomAccessibleInterval<FloatType>> kernelGaborFloat = (sigmas,
			periods) -> (RandomAccessibleInterval<FloatType>) kernelGabor.apply(sigmas, periods, (C) new FloatType());

	@OpField(names = "create, create.kernelGabor",
			params = "sigmas, periods, gaborKernelRAI")
	public final BiFunction<double[], double[], RandomAccessibleInterval<ComplexDoubleType>> kernelGaborComplexDouble = (
			sigmas, periods) -> (RandomAccessibleInterval<ComplexDoubleType>) kernelGabor.apply(sigmas, periods,
					(C) new ComplexDoubleType());

	@OpField(names = "create, create.kernelGabor",
			params = "sigmas, periods, gaborKernelRAI")
	public final BiFunction<double[], double[], RandomAccessibleInterval<ComplexFloatType>> kernelGaborComplexFloat = (
			sigmas, periods) -> (RandomAccessibleInterval<ComplexFloatType>) kernelGabor.apply(sigmas, periods,
					(C) new ComplexFloatType());

	@OpField(names = "create, create.kernelGabor",
			params = "sigmas, periods, outType, gaborKernelRAI")
	public final Functions.Arity3<Double, double[], C, RandomAccessibleInterval<C>> kernelGaborSingleSigma = (sigma, periods,
			outType) -> {
		double[] sigmas = new double[periods.length];
		Arrays.fill(sigmas, sigma);
		return DefaultCreateKernelGabor.createKernel(sigmas, periods, outType, imgFromDimsAndType);
	};

	@OpField(names = "create, create.kernelGabor",
			params = "sigmas, periods, gaborKernelRAI")
	public final BiFunction<Double, double[], RandomAccessibleInterval<DoubleType>> kernelGaborDoubleSingleSigma = (
			sigma, periods) -> {
		double[] sigmas = new double[periods.length];
		Arrays.fill(sigmas, sigma);
		return (RandomAccessibleInterval<DoubleType>) kernelGabor.apply(sigmas, periods, (C) new DoubleType());
	};

	@OpField(names = "create, create.kernelGabor", params = "sigmas, periods, gaborKernelRAI")
	public final BiFunction<Double, double[], RandomAccessibleInterval<FloatType>> kernelGaborFloatSingleSigma = (sigma,
			periods) -> {
		double[] sigmas = new double[periods.length];
		Arrays.fill(sigmas, sigma);
		return (RandomAccessibleInterval<FloatType>) kernelGabor.apply(sigmas, periods, (C) new FloatType());
	};

	@OpField(names = "create, create.kernelGabor", params = "sigmas, periods, gaborKernelRAI")
	public final BiFunction<Double, double[], RandomAccessibleInterval<ComplexDoubleType>> kernelGaborComplexDoubleSingleSigma = (
			sigma, periods) -> {
		double[] sigmas = new double[periods.length];
		Arrays.fill(sigmas, sigma);
		return (RandomAccessibleInterval<ComplexDoubleType>) kernelGabor.apply(sigmas, periods,
				(C) new ComplexDoubleType());
	};

	@OpField(names = "create, create.kernelGabor", params = "sigmas, periods, gaborKernelRAI")
	public final BiFunction<Double, double[], RandomAccessibleInterval<ComplexFloatType>> kernelGaborComplexFloatSingleSigma = (
			sigma, periods) -> {
		double[] sigmas = new double[periods.length];
		Arrays.fill(sigmas, sigma);
		return (RandomAccessibleInterval<ComplexFloatType>) kernelGabor.apply(sigmas, periods,
				(C) new ComplexFloatType());
	};

	/* Kernel Sobel */

	@OpField(names = "create, create.kernelSobel", params = "outType, sobelKernelRAI")
	public final Function<C, RandomAccessibleInterval<C>> kernelSobel = (outType) -> DefaultCreateKernelSobel
			.createKernel(outType, imgFromDimsAndType);

	/* Labeling Mapping */

	// NOTE: We are returning an empty LabelingMapping, and because it is empty that
	// L can be anything. So in this case it is safe to return an object with an
	// unbounded type variable because the caller has to restrict it in the
	// declaration.
	@OpField(names = "create, create.labelingMapping", params = "labelingMapping")
	public final Producer<LabelingMapping<L>> labelingMappingSource = () -> new LabelingMapping<>(
			integerTypeSource.create());

	public final Function<Long, IntegerType> integerTypeFromLong = (maxValue) -> {
		if (maxValue <= 0L)
			return new IntType();
		if (maxValue <= 1L)
			return new BitType();
		if (maxValue <= 0x7fL)
			return new ByteType();
		if (maxValue <= 0xffL)
			return new UnsignedByteType();
		if (maxValue <= 0x7fffL)
			return new ShortType();
		if (maxValue <= 0xffffL)
			return new UnsignedShortType();
		if (maxValue <= 0x7fffffffL)
			return new IntType();
		if (maxValue <= 0xffffffffL)
			return new UnsignedIntType();
		return new LongType();
	};

	@OpField(names = "create, create.labelingMapping", params = "maxNumSets, labelingMapping")
	public final Function<Integer, LabelingMapping<L>> labelingMapping = (maxNumSets) -> new LabelingMapping<>(
			integerTypeFromLong.apply(maxNumSets.longValue()));

	/* Object */

	@OpField(names = "create, create.object", params = "class, object")
	public final Function<Class<L>, L> object = (clazz) -> {
		try {
			return clazz.newInstance();
		} catch (final InstantiationException exc) {
			throw new IllegalArgumentException(exc);
		} catch (final IllegalAccessException exc) {
			throw new IllegalArgumentException(exc);
		}
	};

	/* NativeType */

	@OpField(names = "create, create.nativeType", params = "nativeType", priority = Priority.HIGH)
	public final Producer<DoubleType> defaultNativeType = () -> new DoubleType();

	// TODO is this a safe cast?
	@OpField(names = "create, create.nativeType", params = "type, nativeType")
	public final Function<Class<N>, N> nativeTypeFromClass = (clazz) -> (N) object.apply((Class<L>) clazz);

	@OpField(names = "create, create.vector", params = "vector3d")
	public final Producer<Vector3d> defaultVector3d = () -> new Vector3d();

	@OpField(names = "create, create.vector", params = "vector3f")
	public final Producer<Vector3f> defaultVector3f = () -> new Vector3f();
}
