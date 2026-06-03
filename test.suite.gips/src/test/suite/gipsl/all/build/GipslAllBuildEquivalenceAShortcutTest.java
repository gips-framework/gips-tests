package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.equivalence.shortcut.a.connector.EquivalenceAShortcutConnector;

public class GipslAllBuildEquivalenceAShortcutTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new EquivalenceAShortcutConnector(MODEL_PATH);
	}

	// Actual tests
	// Positive tests

	@Test
	public void testMap1to1() {
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 7);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(100, ret.objectiveValue());
		assertEquals(100, ((EquivalenceAShortcutConnector) con).getY());

		// 1 + 24 + 2 + 2*1
		assertEquals(29, con.getNumberOfConstraints());
	}

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 7);
		gen.genVirtualNode("v2", 42);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(100, ret.objectiveValue());
		assertEquals(100, ((EquivalenceAShortcutConnector) con).getY());

		// 1 + 24*2 + 2*2 + 2*2*1
		assertEquals(57, con.getNumberOfConstraints());
	}

	@Test
	public void testMap8to42() {
		gen.genSubstrateNode("s1", 42);
		gen.genVirtualNode("v1", 8);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()));

		// 1 + 24 + 2 + 2*1
		assertEquals(29, con.getNumberOfConstraints());
	}

	@Test
	public void testMap4to1() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 4);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(100, Math.abs(ret.objectiveValue()));
		assertEquals(100, ((EquivalenceAShortcutConnector) con).getY());

		// 1 + 24 + 2 + 2*1
		assertEquals(29, con.getNumberOfConstraints());
	}

	// Negative tests

	@Test
	public void testMap0to1() {
		gen.genSubstrateNode("s1", 2);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()));

		// 1 + 0 + 0 + 0
		assertEquals(1, con.getNumberOfConstraints());
	}

	@Test
	public void testMap1to0() {
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()));
	}

	@Override
	public Class<?> getConnectorClass() {
		return EquivalenceAShortcutConnector.class;
	}

}
