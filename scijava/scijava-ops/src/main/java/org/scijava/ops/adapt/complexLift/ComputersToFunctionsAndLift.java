/*
 * #%L
 * SciJava Operations: a framework for reusable algorithms.
 * %%
 * Copyright (C) 2016 - 2019 SciJava Ops developers.
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

/*
* This is autogenerated source code -- DO NOT EDIT. Instead, edit the
* corresponding template in templates/ and rerun bin/generate.groovy.
*/

/*
 * #%L
 * SciJava Operations: a framework for reusable algorithms.
 * %%
 * Copyright (C) 2016 - 2019 SciJava Ops developers.
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

/*
* This is autogenerated source code -- DO NOT EDIT. Instead, edit the
* corresponding template in templates/ and rerun bin/generate.groovy.
*/

package org.scijava.ops.adapt.complexLift;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.scijava.Priority;
import org.scijava.ops.OpDependency;
import org.scijava.ops.core.Op;
import org.scijava.ops.function.Computers;
import org.scijava.ops.function.Functions;
import org.scijava.param.Parameter;
import org.scijava.plugin.Plugin;

/**
 * Collection of adaptation Ops to convert {@link Computers} into
 * {@link Functions} and then lift them so that they run on {@link Iterable}s as input.
 * 
 * @author Gabriel Selzer
 */
public class ComputersToFunctionsAndLift {

	@Plugin(type = Op.class, name = "adapt", priority = Priority.LOW)
	public static class Computer1ToFunction1AndLiftViaSource<I, O>
			implements Function<Computers.Arity1<I, O>, Function<Iterable<I>, Iterable<O>>> {

		@OpDependency(name = "adapt", adaptable = false)
		Function<Computers.Arity1<I, O>, Function<I, O>> adaptor;
		@OpDependency(name = "adapt", adaptable = false)
		Function<Function<I, O>, Function<Iterable<I>, Iterable<O>>> lifter;

		@Override
		public Function<Iterable<I>, Iterable<O>> apply(Computers.Arity1<I, O> computer) {
			return lifter.apply(adaptor.apply(computer));
		}

	}

	@Plugin(type = Op.class, name = "adapt", priority = Priority.LOW)
	public static class Computer2ToFunction2AndLiftViaSource<I1, I2, O>
			implements Function<Computers.Arity2<I1, I2, O>, BiFunction<Iterable<I1>, Iterable<I2>, Iterable<O>>> {

		@OpDependency(name = "adapt", adaptable = false)
		Function<Computers.Arity2<I1, I2, O>, BiFunction<I1, I2, O>> adaptor;
		@OpDependency(name = "adapt", adaptable = false)
		Function<BiFunction<I1, I2, O>, BiFunction<Iterable<I1>, Iterable<I2>, Iterable<O>>> lifter;

		@Override
		public BiFunction<Iterable<I1>, Iterable<I2>, Iterable<O>> apply(Computers.Arity2<I1, I2, O> computer) {
			return lifter.apply(adaptor.apply(computer));
		}

	}

	@Plugin(type = Op.class, name = "adapt", priority = Priority.LOW)
	public static class Computer3ToFunction3AndLiftViaSource<I1, I2, I3, O>
			implements Function<Computers.Arity3<I1, I2, I3, O>, Functions.Arity3<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<O>>> {

		@OpDependency(name = "adapt", adaptable = false)
		Function<Computers.Arity3<I1, I2, I3, O>, Functions.Arity3<I1, I2, I3, O>> adaptor;
		@OpDependency(name = "adapt", adaptable = false)
		Function<Functions.Arity3<I1, I2, I3, O>, Functions.Arity3<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<O>>> lifter;

		@Override
		public Functions.Arity3<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<O>> apply(Computers.Arity3<I1, I2, I3, O> computer) {
			return lifter.apply(adaptor.apply(computer));
		}

	}

	@Plugin(type = Op.class, name = "adapt", priority = Priority.LOW)
	public static class Computer4ToFunction4AndLiftViaSource<I1, I2, I3, I4, O>
			implements Function<Computers.Arity4<I1, I2, I3, I4, O>, Functions.Arity4<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<O>>> {

		@OpDependency(name = "adapt", adaptable = false)
		Function<Computers.Arity4<I1, I2, I3, I4, O>, Functions.Arity4<I1, I2, I3, I4, O>> adaptor;
		@OpDependency(name = "adapt", adaptable = false)
		Function<Functions.Arity4<I1, I2, I3, I4, O>, Functions.Arity4<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<O>>> lifter;

		@Override
		public Functions.Arity4<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<O>> apply(Computers.Arity4<I1, I2, I3, I4, O> computer) {
			return lifter.apply(adaptor.apply(computer));
		}

	}

	@Plugin(type = Op.class, name = "adapt", priority = Priority.LOW)
	public static class Computer5ToFunction5AndLiftViaSource<I1, I2, I3, I4, I5, O>
			implements Function<Computers.Arity5<I1, I2, I3, I4, I5, O>, Functions.Arity5<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<O>>> {

		@OpDependency(name = "adapt", adaptable = false)
		Function<Computers.Arity5<I1, I2, I3, I4, I5, O>, Functions.Arity5<I1, I2, I3, I4, I5, O>> adaptor;
		@OpDependency(name = "adapt", adaptable = false)
		Function<Functions.Arity5<I1, I2, I3, I4, I5, O>, Functions.Arity5<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<O>>> lifter;

		@Override
		public Functions.Arity5<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<O>> apply(Computers.Arity5<I1, I2, I3, I4, I5, O> computer) {
			return lifter.apply(adaptor.apply(computer));
		}

	}

	@Plugin(type = Op.class, name = "adapt", priority = Priority.LOW)
	public static class Computer6ToFunction6AndLiftViaSource<I1, I2, I3, I4, I5, I6, O>
			implements Function<Computers.Arity6<I1, I2, I3, I4, I5, I6, O>, Functions.Arity6<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<O>>> {

		@OpDependency(name = "adapt", adaptable = false)
		Function<Computers.Arity6<I1, I2, I3, I4, I5, I6, O>, Functions.Arity6<I1, I2, I3, I4, I5, I6, O>> adaptor;
		@OpDependency(name = "adapt", adaptable = false)
		Function<Functions.Arity6<I1, I2, I3, I4, I5, I6, O>, Functions.Arity6<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<O>>> lifter;

		@Override
		public Functions.Arity6<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<O>> apply(Computers.Arity6<I1, I2, I3, I4, I5, I6, O> computer) {
			return lifter.apply(adaptor.apply(computer));
		}

	}

	@Plugin(type = Op.class, name = "adapt", priority = Priority.LOW)
	public static class Computer7ToFunction7AndLiftViaSource<I1, I2, I3, I4, I5, I6, I7, O>
			implements Function<Computers.Arity7<I1, I2, I3, I4, I5, I6, I7, O>, Functions.Arity7<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<O>>> {

		@OpDependency(name = "adapt", adaptable = false)
		Function<Computers.Arity7<I1, I2, I3, I4, I5, I6, I7, O>, Functions.Arity7<I1, I2, I3, I4, I5, I6, I7, O>> adaptor;
		@OpDependency(name = "adapt", adaptable = false)
		Function<Functions.Arity7<I1, I2, I3, I4, I5, I6, I7, O>, Functions.Arity7<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<O>>> lifter;

		@Override
		public Functions.Arity7<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<O>> apply(Computers.Arity7<I1, I2, I3, I4, I5, I6, I7, O> computer) {
			return lifter.apply(adaptor.apply(computer));
		}

	}

	@Plugin(type = Op.class, name = "adapt", priority = Priority.LOW)
	public static class Computer8ToFunction8AndLiftViaSource<I1, I2, I3, I4, I5, I6, I7, I8, O>
			implements Function<Computers.Arity8<I1, I2, I3, I4, I5, I6, I7, I8, O>, Functions.Arity8<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<O>>> {

		@OpDependency(name = "adapt", adaptable = false)
		Function<Computers.Arity8<I1, I2, I3, I4, I5, I6, I7, I8, O>, Functions.Arity8<I1, I2, I3, I4, I5, I6, I7, I8, O>> adaptor;
		@OpDependency(name = "adapt", adaptable = false)
		Function<Functions.Arity8<I1, I2, I3, I4, I5, I6, I7, I8, O>, Functions.Arity8<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<O>>> lifter;

		@Override
		public Functions.Arity8<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<O>> apply(Computers.Arity8<I1, I2, I3, I4, I5, I6, I7, I8, O> computer) {
			return lifter.apply(adaptor.apply(computer));
		}

	}

	@Plugin(type = Op.class, name = "adapt", priority = Priority.LOW)
	public static class Computer9ToFunction9AndLiftViaSource<I1, I2, I3, I4, I5, I6, I7, I8, I9, O>
			implements Function<Computers.Arity9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O>, Functions.Arity9<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<I9>, Iterable<O>>> {

		@OpDependency(name = "adapt", adaptable = false)
		Function<Computers.Arity9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O>, Functions.Arity9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O>> adaptor;
		@OpDependency(name = "adapt", adaptable = false)
		Function<Functions.Arity9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O>, Functions.Arity9<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<I9>, Iterable<O>>> lifter;

		@Override
		public Functions.Arity9<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<I9>, Iterable<O>> apply(Computers.Arity9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> computer) {
			return lifter.apply(adaptor.apply(computer));
		}

	}

	@Plugin(type = Op.class, name = "adapt", priority = Priority.LOW)
	public static class Computer10ToFunction10AndLiftViaSource<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O>
			implements Function<Computers.Arity10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O>, Functions.Arity10<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<I9>, Iterable<I10>, Iterable<O>>> {

		@OpDependency(name = "adapt", adaptable = false)
		Function<Computers.Arity10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O>, Functions.Arity10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O>> adaptor;
		@OpDependency(name = "adapt", adaptable = false)
		Function<Functions.Arity10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O>, Functions.Arity10<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<I9>, Iterable<I10>, Iterable<O>>> lifter;

		@Override
		public Functions.Arity10<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<I9>, Iterable<I10>, Iterable<O>> apply(Computers.Arity10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> computer) {
			return lifter.apply(adaptor.apply(computer));
		}

	}

	@Plugin(type = Op.class, name = "adapt", priority = Priority.LOW)
	public static class Computer11ToFunction11AndLiftViaSource<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, O>
			implements Function<Computers.Arity11<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, O>, Functions.Arity11<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<I9>, Iterable<I10>, Iterable<I11>, Iterable<O>>> {

		@OpDependency(name = "adapt", adaptable = false)
		Function<Computers.Arity11<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, O>, Functions.Arity11<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, O>> adaptor;
		@OpDependency(name = "adapt", adaptable = false)
		Function<Functions.Arity11<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, O>, Functions.Arity11<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<I9>, Iterable<I10>, Iterable<I11>, Iterable<O>>> lifter;

		@Override
		public Functions.Arity11<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<I9>, Iterable<I10>, Iterable<I11>, Iterable<O>> apply(Computers.Arity11<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, O> computer) {
			return lifter.apply(adaptor.apply(computer));
		}

	}

	@Plugin(type = Op.class, name = "adapt", priority = Priority.LOW)
	public static class Computer12ToFunction12AndLiftViaSource<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, O>
			implements Function<Computers.Arity12<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, O>, Functions.Arity12<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<I9>, Iterable<I10>, Iterable<I11>, Iterable<I12>, Iterable<O>>> {

		@OpDependency(name = "adapt", adaptable = false)
		Function<Computers.Arity12<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, O>, Functions.Arity12<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, O>> adaptor;
		@OpDependency(name = "adapt", adaptable = false)
		Function<Functions.Arity12<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, O>, Functions.Arity12<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<I9>, Iterable<I10>, Iterable<I11>, Iterable<I12>, Iterable<O>>> lifter;

		@Override
		public Functions.Arity12<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<I9>, Iterable<I10>, Iterable<I11>, Iterable<I12>, Iterable<O>> apply(Computers.Arity12<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, O> computer) {
			return lifter.apply(adaptor.apply(computer));
		}

	}

	@Plugin(type = Op.class, name = "adapt", priority = Priority.LOW)
	public static class Computer13ToFunction13AndLiftViaSource<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, O>
			implements Function<Computers.Arity13<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, O>, Functions.Arity13<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<I9>, Iterable<I10>, Iterable<I11>, Iterable<I12>, Iterable<I13>, Iterable<O>>> {

		@OpDependency(name = "adapt", adaptable = false)
		Function<Computers.Arity13<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, O>, Functions.Arity13<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, O>> adaptor;
		@OpDependency(name = "adapt", adaptable = false)
		Function<Functions.Arity13<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, O>, Functions.Arity13<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<I9>, Iterable<I10>, Iterable<I11>, Iterable<I12>, Iterable<I13>, Iterable<O>>> lifter;

		@Override
		public Functions.Arity13<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<I9>, Iterable<I10>, Iterable<I11>, Iterable<I12>, Iterable<I13>, Iterable<O>> apply(Computers.Arity13<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, O> computer) {
			return lifter.apply(adaptor.apply(computer));
		}

	}

	@Plugin(type = Op.class, name = "adapt", priority = Priority.LOW)
	public static class Computer14ToFunction14AndLiftViaSource<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, O>
			implements Function<Computers.Arity14<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, O>, Functions.Arity14<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<I9>, Iterable<I10>, Iterable<I11>, Iterable<I12>, Iterable<I13>, Iterable<I14>, Iterable<O>>> {

		@OpDependency(name = "adapt", adaptable = false)
		Function<Computers.Arity14<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, O>, Functions.Arity14<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, O>> adaptor;
		@OpDependency(name = "adapt", adaptable = false)
		Function<Functions.Arity14<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, O>, Functions.Arity14<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<I9>, Iterable<I10>, Iterable<I11>, Iterable<I12>, Iterable<I13>, Iterable<I14>, Iterable<O>>> lifter;

		@Override
		public Functions.Arity14<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<I9>, Iterable<I10>, Iterable<I11>, Iterable<I12>, Iterable<I13>, Iterable<I14>, Iterable<O>> apply(Computers.Arity14<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, O> computer) {
			return lifter.apply(adaptor.apply(computer));
		}

	}

	@Plugin(type = Op.class, name = "adapt", priority = Priority.LOW)
	public static class Computer15ToFunction15AndLiftViaSource<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, O>
			implements Function<Computers.Arity15<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, O>, Functions.Arity15<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<I9>, Iterable<I10>, Iterable<I11>, Iterable<I12>, Iterable<I13>, Iterable<I14>, Iterable<I15>, Iterable<O>>> {

		@OpDependency(name = "adapt", adaptable = false)
		Function<Computers.Arity15<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, O>, Functions.Arity15<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, O>> adaptor;
		@OpDependency(name = "adapt", adaptable = false)
		Function<Functions.Arity15<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, O>, Functions.Arity15<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<I9>, Iterable<I10>, Iterable<I11>, Iterable<I12>, Iterable<I13>, Iterable<I14>, Iterable<I15>, Iterable<O>>> lifter;

		@Override
		public Functions.Arity15<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<I9>, Iterable<I10>, Iterable<I11>, Iterable<I12>, Iterable<I13>, Iterable<I14>, Iterable<I15>, Iterable<O>> apply(Computers.Arity15<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, O> computer) {
			return lifter.apply(adaptor.apply(computer));
		}

	}

	@Plugin(type = Op.class, name = "adapt", priority = Priority.LOW)
	public static class Computer16ToFunction16AndLiftViaSource<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, I16, O>
			implements Function<Computers.Arity16<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, I16, O>, Functions.Arity16<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<I9>, Iterable<I10>, Iterable<I11>, Iterable<I12>, Iterable<I13>, Iterable<I14>, Iterable<I15>, Iterable<I16>, Iterable<O>>> {

		@OpDependency(name = "adapt", adaptable = false)
		Function<Computers.Arity16<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, I16, O>, Functions.Arity16<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, I16, O>> adaptor;
		@OpDependency(name = "adapt", adaptable = false)
		Function<Functions.Arity16<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, I16, O>, Functions.Arity16<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<I9>, Iterable<I10>, Iterable<I11>, Iterable<I12>, Iterable<I13>, Iterable<I14>, Iterable<I15>, Iterable<I16>, Iterable<O>>> lifter;

		@Override
		public Functions.Arity16<Iterable<I1>, Iterable<I2>, Iterable<I3>, Iterable<I4>, Iterable<I5>, Iterable<I6>, Iterable<I7>, Iterable<I8>, Iterable<I9>, Iterable<I10>, Iterable<I11>, Iterable<I12>, Iterable<I13>, Iterable<I14>, Iterable<I15>, Iterable<I16>, Iterable<O>> apply(Computers.Arity16<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, I16, O> computer) {
			return lifter.apply(adaptor.apply(computer));
		}

	}

}
