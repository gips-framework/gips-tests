package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.equivalence.shortcut.b.connector.EquivalenceBShortcutConnector;
import gipsl.all.build.equivalence.shortcut.d.connector.EquivalenceDShortcutConnector;

public class GipslAllBuildEquivalenceDShortcutTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new EquivalenceDShortcutConnector(MODEL_PATH);
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
		assertEquals(1, ret.objectiveValue());
	}

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 7);
		gen.genVirtualNode("v2", 42);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.objectiveValue());
	}

	@Test
	public void testMap8to42() {
		gen.genSubstrateNode("s1", 42);
		gen.genVirtualNode("v1", 8);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, Math.abs(ret.objectiveValue()));
		assertEquals(1, ((EquivalenceDShortcutConnector) con).getY());
	}

	@Test
	public void testMap4to1() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 4);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.objectiveValue());
	}

	// Negative tests

	@Test
	public void testMap0to1() {
		gen.genSubstrateNode("s1", 2);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()));
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
		return EquivalenceBShortcutConnector.class;
	}

}
