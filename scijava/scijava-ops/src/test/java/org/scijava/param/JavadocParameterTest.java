package org.scijava.param;

import com.github.therapi.runtimejavadoc.ClassJavadoc;
import com.github.therapi.runtimejavadoc.MethodJavadoc;
import com.github.therapi.runtimejavadoc.RuntimeJavadoc;
//import com.github.therapi.runtimejavadoc.internal.JavadocAnnotationProcessor;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.junit.Test;
import org.scijava.ops.core.Op;
import org.scijava.ops.create.CreateOpCollection;
import org.scijava.ops.simplify.SimplificationUtils;
import org.scijava.plugin.Plugin;

/**
 * Tests the ability of a Javadoc parser to scrape an Op's parameters out of its Javadoc
 * @author G
 *
 */
public class JavadocParameterTest {
	
	@Test
	public void testJavadocMethod() {
		Method opMethod = SimplificationUtils.findFMethod(JavadocOp.class);
		MethodJavadoc javadoc = RuntimeJavadoc.getJavadoc(opMethod);
		System.out.println("Functional Method javadoc" + javadoc);
		
		ClassJavadoc classdoc = RuntimeJavadoc.getJavadoc(org.scijava.ops.OpInfo.class);
		System.out.println("Class javadoc" + classdoc);
		
		ClassJavadoc createDoc = RuntimeJavadoc.getJavadoc(CreateOpCollection.class);
		System.out.println("Create javadoc: " + createDoc);
	}
//	 @Test
//   public void runAnnoationProcessor() throws Exception {
//      String source = "my.project/src";
//
//      Iterable<JavaFileObject> files = getSourceFiles(source);
//
//      JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//
//      CompilationTask task = compiler.getTask(new PrintWriter(System.out), null, null, null, null, files);
//      task.setProcessors(Arrays.asList(new JavadocAnnotationProcessor()));
//
//      task.call();
//   }
//
//   private Iterable<JavaFileObject> getSourceFiles(String p_path) throws Exception {
//     JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//     StandardJavaFileManager files = compiler.getStandardFileManager(null, null, null);
//
//     files.setLocation(StandardLocation.SOURCE_PATH, Arrays.asList(new File(p_path)));
//
//     Set<Kind> fileKinds = Collections.singleton(Kind.SOURCE);
//     return files.list(StandardLocation.SOURCE_PATH, "", fileKinds, true);
//   }
}

/**
 * Does crap.
 * @author G
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
