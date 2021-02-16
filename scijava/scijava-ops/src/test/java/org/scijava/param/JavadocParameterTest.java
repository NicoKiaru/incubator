package org.scijava.param;

import com.github.therapi.runtimejavadoc.ClassJavadoc;
import com.github.therapi.runtimejavadoc.MethodJavadoc;
import com.github.therapi.runtimejavadoc.RuntimeJavadoc;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.function.Function;

import org.junit.Test;
import org.scijava.ops.core.Op;
import org.scijava.ops.create.CreateOpCollection;
import org.scijava.ops.simplify.SimplificationUtils;
import org.scijava.plugin.Plugin;
import org.scijava.types.Types;

/**
 * Tests the ability of a Javadoc parser to scrape an Op's parameters out of its Javadoc
 * @author G
 *
 */
public class JavadocParameterTest {
	
	@Test
	public void testJavadocMethod() throws NoSuchMethodException, SecurityException {
		Method fMethod = SimplificationUtils.findFMethod(JavadocOp.class);
		Type[] exactParameterTypes = Types.getExactParameterTypes(fMethod, JavadocOp.class);
		Class<?>[] rawParamTypes = Arrays.stream(exactParameterTypes).map(t -> Types.raw(t)).toArray(Class[]::new);
		Method opMethod = JavadocOp.class.getMethod(fMethod.getName(), rawParamTypes);
		MethodJavadoc javadoc = RuntimeJavadoc.getJavadoc(opMethod);
		System.out.println("Functional Method javadoc" + javadoc);
		
		ClassJavadoc classdoc = RuntimeJavadoc.getJavadoc(org.scijava.ops.OpInfo.class);
		System.out.println("Class javadoc" + classdoc);
		
		ClassJavadoc createDoc = RuntimeJavadoc.getJavadoc(CreateOpCollection.class);
		System.out.println("Create javadoc: " + createDoc);

		ClassJavadoc testDoc = RuntimeJavadoc.getJavadoc(JavadocOp.class);
		System.out.println("Class javado" + testDoc);
	}

}

/**
 * Test Op used to see if we can't scrape the javadoc.
 * @author Gabriel Selzer
 *
 */
@Plugin(type = Op.class) 
class JavadocOp implements Function<Double, Double> {

	/**
	 * @param t - the input
	 * @return u - the output
	 */
	@Override
	public Double apply(Double t) {
		return t + 1;
	}
	
}
