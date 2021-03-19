package org.scijava.ops.conversionLoss;

import org.scijava.ops.OpField;
import org.scijava.ops.core.OpCollection;
import org.scijava.ops.simplify.Unsimplifiable;
import org.scijava.plugin.Plugin;

@Plugin(type = OpCollection.class)
public class PrimitiveArrayLossReporters {

	@Unsimplifiable
	@OpField(names = "lossReporter")
	public final LossReporter<Byte[], Integer[]> bArrIArr = (from, to) -> 0.;
	
	@Unsimplifiable
	@OpField(names = "lossReporter")
	public final LossReporter<Double[], Integer[]> dArrIArr = (from, to) -> 0.;

}
