package org.scijava.ops.hints;

public class DefaultOpHints {

	public static class Simplifiable {
		public static final String prefix = "simplification";
		public static final String YES = Hints.BASE + "." + prefix + ".YES";
		public static final String NO = Hints.BASE + "." + prefix + ".NO";
	}

	public static class Adaptable {
		public static final String prefix = "adaptation";
		public static final String YES = Hints.BASE + "." + prefix + ".YES";
		public static final String NO = Hints.BASE + "." + prefix + ".NO";
	}

}
