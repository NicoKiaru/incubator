/*
 * This is autogenerated source code -- DO NOT EDIT. Instead, edit the
 * corresponding template in templates/ and rerun bin/generate.groovy.
 */

package org.scijava.ops.function;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.scijava.functions.Functions;
import org.scijava.functions.Producer;
import org.scijava.ops.OpEnvironment;
import org.scijava.types.Nil;
import org.scijava.types.Types;

/**
 * Container class for
 * higher-<a href="https://en.wikipedia.org/wiki/Arity">arity</a>
 * {@link Function}-style functional interfaces&mdash;i.e. with functional
 * method {@code apply} with a number of arguments corresponding to the arity.
 * <ul>
 * <li>For 0-arity (nullary) functions, use {@link Producer} (and notice the
 * functional method there is named {@link Producer\#create()}).</li>
 * <li>For 1-arity (unary) functions, use {@link Function}.</li>
 * <li>For 2-arity (binary) functions, use {@link BiFunction}.</li>
 * </ul>
 *
 * @author Curtis Rueden
 * @author Gabriel Selzer
 */
public final class FunctionUtils {

	private FunctionUtils() {
		// NB: Prevent instantiation of utility class.
	}

	/**
	 * All known function types and their arities. The entries are sorted by
	 * arity, i.e., the {@code i}-th entry has an arity of {@code i}.
	 */
	public static final BiMap<Integer, Class<?>> ALL_FUNCTIONS;

	static {
		final Map<Integer, Class<?>> functions = new HashMap<>(10);
		functions.put(0, Producer.class);
		functions.put(1, Function.class);
		functions.put(2, BiFunction.class);
		functions.put(3, Functions.Arity3.class);
		functions.put(4, Functions.Arity4.class);
		functions.put(5, Functions.Arity5.class);
		functions.put(6, Functions.Arity6.class);
		functions.put(7, Functions.Arity7.class);
		functions.put(8, Functions.Arity8.class);
		functions.put(9, Functions.Arity9.class);
		functions.put(10, Functions.Arity10.class);
		functions.put(11, Functions.Arity11.class);
		functions.put(12, Functions.Arity12.class);
		functions.put(13, Functions.Arity13.class);
		functions.put(14, Functions.Arity14.class);
		functions.put(15, Functions.Arity15.class);
		functions.put(16, Functions.Arity16.class);
		ALL_FUNCTIONS = ImmutableBiMap.copyOf(functions);
	}

	/**
	 * @return {@code true} if the given type is a {@link #ALL_FUNCTIONS known}
	 *         function type, {@code false} otherwise.<br>
	 *         Note that only the type itself and not its type hierarchy is
	 *         considered.
	 * @throws NullPointerException If {@code type} is {@code null}.
	 */
	public static boolean isFunction(Type type) {
		return ALL_FUNCTIONS.containsKey(Types.raw(type));
	}


	@SuppressWarnings({ "unchecked" })
	public static <O, T> ArityN<O> matchN(final OpEnvironment env,
		final String opName, final Nil<O> outType, final Nil<?>... inTypes)
	{
		Object op = matchHelper(env, opName, ALL_FUNCTIONS.get(inTypes.length),
			outType, inTypes);
		if (op instanceof Producer) {
			return new Arity0AsN<>((Producer<O>) op);
		}
		else if (op instanceof Function) {
			return new Arity1AsN<>((Function<Object, O>) op);
		}
		else if (op instanceof BiFunction) {
			return new Arity2AsN<>((BiFunction<Object, Object, O>) op);
		}
		else if (op instanceof Functions.Arity3) {
			return new Arity3AsN<>((Functions.Arity3<Object, Object, Object, O>) op);
		}
		else if (op instanceof Functions.Arity4) {
			return new Arity4AsN<>((Functions.Arity4<Object, Object, Object, Object, O>) op);
		}
		else if (op instanceof Functions.Arity5) {
			return new Arity5AsN<>((Functions.Arity5<Object, Object, Object, Object, Object, O>) op);
		}
		else if (op instanceof Functions.Arity6) {
			return new Arity6AsN<>((Functions.Arity6<Object, Object, Object, Object, Object, Object, O>) op);
		}
		else if (op instanceof Functions.Arity7) {
			return new Arity7AsN<>((Functions.Arity7<Object, Object, Object, Object, Object, Object, Object, O>) op);
		}
		else if (op instanceof Functions.Arity8) {
			return new Arity8AsN<>((Functions.Arity8<Object, Object, Object, Object, Object, Object, Object, Object, O>) op);
		}
		else if (op instanceof Functions.Arity9) {
			return new Arity9AsN<>((Functions.Arity9<Object, Object, Object, Object, Object, Object, Object, Object, Object, O>) op);
		}
		else if (op instanceof Functions.Arity10) {
			return new Arity10AsN<>((Functions.Arity10<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, O>) op);
		}
		else if (op instanceof Functions.Arity11) {
			return new Arity11AsN<>((Functions.Arity11<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, O>) op);
		}
		else if (op instanceof Functions.Arity12) {
			return new Arity12AsN<>((Functions.Arity12<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, O>) op);
		}
		else if (op instanceof Functions.Arity13) {
			return new Arity13AsN<>((Functions.Arity13<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, O>) op);
		}
		else if (op instanceof Functions.Arity14) {
			return new Arity14AsN<>((Functions.Arity14<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, O>) op);
		}
		else if (op instanceof Functions.Arity15) {
			return new Arity15AsN<>((Functions.Arity15<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, O>) op);
		}
		return new Arity16AsN<>((Functions.Arity16<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, O>) op);
	}

	@SuppressWarnings({ "unchecked" })
	public static <O> Producer<O> match(final OpEnvironment env, final String opName, final Nil<O> outType)
	{
		return matchHelper(env, opName, Producer.class, outType);
	}

	@SuppressWarnings({ "unchecked" })
	public static <I, O> Function<I, O> match(final OpEnvironment env, final String opName, final Nil<I> inType, final Nil<O> outType)
	{
		return matchHelper(env, opName, Function.class, outType, inType);
	}

	@SuppressWarnings({ "unchecked" })
	public static <I1, I2, O> BiFunction<I1, I2, O> match(final OpEnvironment env, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<O> outType)
	{
		return matchHelper(env, opName, BiFunction.class, outType, in1Type, in2Type);
	}

	@SuppressWarnings({ "unchecked" })
	public static <I1, I2, I3, O> Functions.Arity3<I1, I2, I3, O> match(final OpEnvironment env, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<O> outType)
	{
		return matchHelper(env, opName, Functions.Arity3.class, outType, in1Type, in2Type, in3Type);
	}

	@SuppressWarnings({ "unchecked" })
	public static <I1, I2, I3, I4, O> Functions.Arity4<I1, I2, I3, I4, O> match(final OpEnvironment env, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<O> outType)
	{
		return matchHelper(env, opName, Functions.Arity4.class, outType, in1Type, in2Type, in3Type, in4Type);
	}

	@SuppressWarnings({ "unchecked" })
	public static <I1, I2, I3, I4, I5, O> Functions.Arity5<I1, I2, I3, I4, I5, O> match(final OpEnvironment env, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<I5> in5Type, final Nil<O> outType)
	{
		return matchHelper(env, opName, Functions.Arity5.class, outType, in1Type, in2Type, in3Type, in4Type, in5Type);
	}

	@SuppressWarnings({ "unchecked" })
	public static <I1, I2, I3, I4, I5, I6, O> Functions.Arity6<I1, I2, I3, I4, I5, I6, O> match(final OpEnvironment env, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<I5> in5Type, final Nil<I6> in6Type, final Nil<O> outType)
	{
		return matchHelper(env, opName, Functions.Arity6.class, outType, in1Type, in2Type, in3Type, in4Type, in5Type, in6Type);
	}

	@SuppressWarnings({ "unchecked" })
	public static <I1, I2, I3, I4, I5, I6, I7, O> Functions.Arity7<I1, I2, I3, I4, I5, I6, I7, O> match(final OpEnvironment env, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<I5> in5Type, final Nil<I6> in6Type, final Nil<I7> in7Type, final Nil<O> outType)
	{
		return matchHelper(env, opName, Functions.Arity7.class, outType, in1Type, in2Type, in3Type, in4Type, in5Type, in6Type, in7Type);
	}

	@SuppressWarnings({ "unchecked" })
	public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Functions.Arity8<I1, I2, I3, I4, I5, I6, I7, I8, O> match(final OpEnvironment env, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<I5> in5Type, final Nil<I6> in6Type, final Nil<I7> in7Type, final Nil<I8> in8Type, final Nil<O> outType)
	{
		return matchHelper(env, opName, Functions.Arity8.class, outType, in1Type, in2Type, in3Type, in4Type, in5Type, in6Type, in7Type, in8Type);
	}

	@SuppressWarnings({ "unchecked" })
	public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Functions.Arity9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> match(final OpEnvironment env, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<I5> in5Type, final Nil<I6> in6Type, final Nil<I7> in7Type, final Nil<I8> in8Type, final Nil<I9> in9Type, final Nil<O> outType)
	{
		return matchHelper(env, opName, Functions.Arity9.class, outType, in1Type, in2Type, in3Type, in4Type, in5Type, in6Type, in7Type, in8Type, in9Type);
	}

	@SuppressWarnings({ "unchecked" })
	public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Functions.Arity10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> match(final OpEnvironment env, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<I5> in5Type, final Nil<I6> in6Type, final Nil<I7> in7Type, final Nil<I8> in8Type, final Nil<I9> in9Type, final Nil<I10> in10Type, final Nil<O> outType)
	{
		return matchHelper(env, opName, Functions.Arity10.class, outType, in1Type, in2Type, in3Type, in4Type, in5Type, in6Type, in7Type, in8Type, in9Type, in10Type);
	}

	@SuppressWarnings({ "unchecked" })
	public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, O> Functions.Arity11<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, O> match(final OpEnvironment env, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<I5> in5Type, final Nil<I6> in6Type, final Nil<I7> in7Type, final Nil<I8> in8Type, final Nil<I9> in9Type, final Nil<I10> in10Type, final Nil<I11> in11Type, final Nil<O> outType)
	{
		return matchHelper(env, opName, Functions.Arity11.class, outType, in1Type, in2Type, in3Type, in4Type, in5Type, in6Type, in7Type, in8Type, in9Type, in10Type, in11Type);
	}

	@SuppressWarnings({ "unchecked" })
	public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, O> Functions.Arity12<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, O> match(final OpEnvironment env, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<I5> in5Type, final Nil<I6> in6Type, final Nil<I7> in7Type, final Nil<I8> in8Type, final Nil<I9> in9Type, final Nil<I10> in10Type, final Nil<I11> in11Type, final Nil<I12> in12Type, final Nil<O> outType)
	{
		return matchHelper(env, opName, Functions.Arity12.class, outType, in1Type, in2Type, in3Type, in4Type, in5Type, in6Type, in7Type, in8Type, in9Type, in10Type, in11Type, in12Type);
	}

	@SuppressWarnings({ "unchecked" })
	public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, O> Functions.Arity13<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, O> match(final OpEnvironment env, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<I5> in5Type, final Nil<I6> in6Type, final Nil<I7> in7Type, final Nil<I8> in8Type, final Nil<I9> in9Type, final Nil<I10> in10Type, final Nil<I11> in11Type, final Nil<I12> in12Type, final Nil<I13> in13Type, final Nil<O> outType)
	{
		return matchHelper(env, opName, Functions.Arity13.class, outType, in1Type, in2Type, in3Type, in4Type, in5Type, in6Type, in7Type, in8Type, in9Type, in10Type, in11Type, in12Type, in13Type);
	}

	@SuppressWarnings({ "unchecked" })
	public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, O> Functions.Arity14<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, O> match(final OpEnvironment env, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<I5> in5Type, final Nil<I6> in6Type, final Nil<I7> in7Type, final Nil<I8> in8Type, final Nil<I9> in9Type, final Nil<I10> in10Type, final Nil<I11> in11Type, final Nil<I12> in12Type, final Nil<I13> in13Type, final Nil<I14> in14Type, final Nil<O> outType)
	{
		return matchHelper(env, opName, Functions.Arity14.class, outType, in1Type, in2Type, in3Type, in4Type, in5Type, in6Type, in7Type, in8Type, in9Type, in10Type, in11Type, in12Type, in13Type, in14Type);
	}

	@SuppressWarnings({ "unchecked" })
	public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, O> Functions.Arity15<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, O> match(final OpEnvironment env, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<I5> in5Type, final Nil<I6> in6Type, final Nil<I7> in7Type, final Nil<I8> in8Type, final Nil<I9> in9Type, final Nil<I10> in10Type, final Nil<I11> in11Type, final Nil<I12> in12Type, final Nil<I13> in13Type, final Nil<I14> in14Type, final Nil<I15> in15Type, final Nil<O> outType)
	{
		return matchHelper(env, opName, Functions.Arity15.class, outType, in1Type, in2Type, in3Type, in4Type, in5Type, in6Type, in7Type, in8Type, in9Type, in10Type, in11Type, in12Type, in13Type, in14Type, in15Type);
	}

	@SuppressWarnings({ "unchecked" })
	public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, I16, O> Functions.Arity16<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12, I13, I14, I15, I16, O> match(final OpEnvironment env, final String opName, final Nil<I1> in1Type, final Nil<I2> in2Type, final Nil<I3> in3Type, final Nil<I4> in4Type, final Nil<I5> in5Type, final Nil<I6> in6Type, final Nil<I7> in7Type, final Nil<I8> in8Type, final Nil<I9> in9Type, final Nil<I10> in10Type, final Nil<I11> in11Type, final Nil<I12> in12Type, final Nil<I13> in13Type, final Nil<I14> in14Type, final Nil<I15> in15Type, final Nil<I16> in16Type, final Nil<O> outType)
	{
		return matchHelper(env, opName, Functions.Arity16.class, outType, in1Type, in2Type, in3Type, in4Type, in5Type, in6Type, in7Type, in8Type, in9Type, in10Type, in11Type, in12Type, in13Type, in14Type, in15Type, in16Type);
	}

	@SuppressWarnings({ "unchecked" })
	private static <T> T matchHelper(final OpEnvironment env, final String opName,
		final Class<T> opClass, final Nil<?> outType, final Nil<?>... inTypes)
	{
		final Type[] types = new Type[inTypes.length + 1];
		for (int i = 0; i < inTypes.length; i++)
			types[i] = inTypes[i].getType();
		types[types.length - 1] = outType.getType();
		final Type specialType = Types.parameterize(opClass, types);
		return (T) env.op(opName, Nil.of(specialType), inTypes, outType);
	}

	public interface ArityN<O> {

		O apply(Object... ins);

		Object getOp();
	}

	protected static class Arity0AsN<O> implements ArityN<O> {

		Producer<O> func;

		public Arity0AsN(Producer<O> func) {
			this.func = func;
		}

		@Override
		public O apply(Object... ins) {
			return func.create();
		}

		@Override
		public Object getOp() {
			return func;
		}

	}

	protected static class Arity1AsN<O> implements ArityN<O> {

		Function<Object, O> func;

		public Arity1AsN(Function<Object, O> func) {
			this.func = func;
		}

		@Override
		public O apply(Object... ins) {
			return func.apply(ins[0]);
		}

		@Override
		public Object getOp() {
			return func;
		}

	}

	protected static class Arity2AsN<O> implements ArityN<O> {

		BiFunction<Object, Object, O> func;

		public Arity2AsN(BiFunction<Object, Object, O> func) {
			this.func = func;
		}

		@Override
		public O apply(Object... ins) {
			return func.apply(ins[0], ins[1]);
		}

		@Override
		public Object getOp() {
			return func;
		}

	}

	protected static class Arity3AsN<O> implements ArityN<O> {

		Functions.Arity3<Object, Object, Object, O> func;

		public Arity3AsN(Functions.Arity3<Object, Object, Object, O> func) {
			this.func = func;
		}

		@Override
		public O apply(Object... ins) {
			return func.apply(ins[0], ins[1], ins[2]);
		}

		@Override
		public Object getOp() {
			return func;
		}

	}

	protected static class Arity4AsN<O> implements ArityN<O> {

		Functions.Arity4<Object, Object, Object, Object, O> func;

		public Arity4AsN(Functions.Arity4<Object, Object, Object, Object, O> func) {
			this.func = func;
		}

		@Override
		public O apply(Object... ins) {
			return func.apply(ins[0], ins[1], ins[2], ins[3]);
		}

		@Override
		public Object getOp() {
			return func;
		}

	}

	protected static class Arity5AsN<O> implements ArityN<O> {

		Functions.Arity5<Object, Object, Object, Object, Object, O> func;

		public Arity5AsN(Functions.Arity5<Object, Object, Object, Object, Object, O> func) {
			this.func = func;
		}

		@Override
		public O apply(Object... ins) {
			return func.apply(ins[0], ins[1], ins[2], ins[3], ins[4]);
		}

		@Override
		public Object getOp() {
			return func;
		}

	}

	protected static class Arity6AsN<O> implements ArityN<O> {

		Functions.Arity6<Object, Object, Object, Object, Object, Object, O> func;

		public Arity6AsN(Functions.Arity6<Object, Object, Object, Object, Object, Object, O> func) {
			this.func = func;
		}

		@Override
		public O apply(Object... ins) {
			return func.apply(ins[0], ins[1], ins[2], ins[3], ins[4], ins[5]);
		}

		@Override
		public Object getOp() {
			return func;
		}

	}

	protected static class Arity7AsN<O> implements ArityN<O> {

		Functions.Arity7<Object, Object, Object, Object, Object, Object, Object, O> func;

		public Arity7AsN(Functions.Arity7<Object, Object, Object, Object, Object, Object, Object, O> func) {
			this.func = func;
		}

		@Override
		public O apply(Object... ins) {
			return func.apply(ins[0], ins[1], ins[2], ins[3], ins[4], ins[5], ins[6]);
		}

		@Override
		public Object getOp() {
			return func;
		}

	}

	protected static class Arity8AsN<O> implements ArityN<O> {

		Functions.Arity8<Object, Object, Object, Object, Object, Object, Object, Object, O> func;

		public Arity8AsN(Functions.Arity8<Object, Object, Object, Object, Object, Object, Object, Object, O> func) {
			this.func = func;
		}

		@Override
		public O apply(Object... ins) {
			return func.apply(ins[0], ins[1], ins[2], ins[3], ins[4], ins[5], ins[6], ins[7]);
		}

		@Override
		public Object getOp() {
			return func;
		}

	}

	protected static class Arity9AsN<O> implements ArityN<O> {

		Functions.Arity9<Object, Object, Object, Object, Object, Object, Object, Object, Object, O> func;

		public Arity9AsN(Functions.Arity9<Object, Object, Object, Object, Object, Object, Object, Object, Object, O> func) {
			this.func = func;
		}

		@Override
		public O apply(Object... ins) {
			return func.apply(ins[0], ins[1], ins[2], ins[3], ins[4], ins[5], ins[6], ins[7], ins[8]);
		}

		@Override
		public Object getOp() {
			return func;
		}

	}

	protected static class Arity10AsN<O> implements ArityN<O> {

		Functions.Arity10<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, O> func;

		public Arity10AsN(Functions.Arity10<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, O> func) {
			this.func = func;
		}

		@Override
		public O apply(Object... ins) {
			return func.apply(ins[0], ins[1], ins[2], ins[3], ins[4], ins[5], ins[6], ins[7], ins[8], ins[9]);
		}

		@Override
		public Object getOp() {
			return func;
		}

	}

	protected static class Arity11AsN<O> implements ArityN<O> {

		Functions.Arity11<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, O> func;

		public Arity11AsN(Functions.Arity11<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, O> func) {
			this.func = func;
		}

		@Override
		public O apply(Object... ins) {
			return func.apply(ins[0], ins[1], ins[2], ins[3], ins[4], ins[5], ins[6], ins[7], ins[8], ins[9], ins[10]);
		}

		@Override
		public Object getOp() {
			return func;
		}

	}

	protected static class Arity12AsN<O> implements ArityN<O> {

		Functions.Arity12<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, O> func;

		public Arity12AsN(Functions.Arity12<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, O> func) {
			this.func = func;
		}

		@Override
		public O apply(Object... ins) {
			return func.apply(ins[0], ins[1], ins[2], ins[3], ins[4], ins[5], ins[6], ins[7], ins[8], ins[9], ins[10], ins[11]);
		}

		@Override
		public Object getOp() {
			return func;
		}

	}

	protected static class Arity13AsN<O> implements ArityN<O> {

		Functions.Arity13<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, O> func;

		public Arity13AsN(Functions.Arity13<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, O> func) {
			this.func = func;
		}

		@Override
		public O apply(Object... ins) {
			return func.apply(ins[0], ins[1], ins[2], ins[3], ins[4], ins[5], ins[6], ins[7], ins[8], ins[9], ins[10], ins[11], ins[12]);
		}

		@Override
		public Object getOp() {
			return func;
		}

	}

	protected static class Arity14AsN<O> implements ArityN<O> {

		Functions.Arity14<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, O> func;

		public Arity14AsN(Functions.Arity14<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, O> func) {
			this.func = func;
		}

		@Override
		public O apply(Object... ins) {
			return func.apply(ins[0], ins[1], ins[2], ins[3], ins[4], ins[5], ins[6], ins[7], ins[8], ins[9], ins[10], ins[11], ins[12], ins[13]);
		}

		@Override
		public Object getOp() {
			return func;
		}

	}

	protected static class Arity15AsN<O> implements ArityN<O> {

		Functions.Arity15<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, O> func;

		public Arity15AsN(Functions.Arity15<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, O> func) {
			this.func = func;
		}

		@Override
		public O apply(Object... ins) {
			return func.apply(ins[0], ins[1], ins[2], ins[3], ins[4], ins[5], ins[6], ins[7], ins[8], ins[9], ins[10], ins[11], ins[12], ins[13], ins[14]);
		}

		@Override
		public Object getOp() {
			return func;
		}

	}

	protected static class Arity16AsN<O> implements ArityN<O> {

		Functions.Arity16<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, O> func;

		public Arity16AsN(Functions.Arity16<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, O> func) {
			this.func = func;
		}

		@Override
		public O apply(Object... ins) {
			return func.apply(ins[0], ins[1], ins[2], ins[3], ins[4], ins[5], ins[6], ins[7], ins[8], ins[9], ins[10], ins[11], ins[12], ins[13], ins[14], ins[15]);
		}

		@Override
		public Object getOp() {
			return func;
		}

	}

}
