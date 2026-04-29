package gipsl.all.build.equivalence.shortcut.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GlobalTestConfig;

public class EquivalenceShortcutConnector extends AConnector {

	public EquivalenceShortcutConnector(final String modelPath) {
//		api = new ShortcutGipsAPI();
//		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(String outputPath) {
		final SolverOutput output = solve();
//		((ShortcutGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}
