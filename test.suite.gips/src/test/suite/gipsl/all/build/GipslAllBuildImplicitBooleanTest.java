package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.implicitboolean.connector.ImplicitBooleanConnector;

public class GipslAllBuildImplicitBooleanTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new ImplicitBooleanConnector(MODEL_PATH);
	}

	// Actual tests
	// Positive tests

	@Test
	public void testMap1to1() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		// All mappings must be chosen, according to the objective function
		assertEquals(1, ret.objectiveValue());

		assertEquals(1, ((ImplicitBooleanConnector) con).getVirtualResourceNodeS("v1"));
	}

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		// All mappings must be chosen, according to the objective function
		assertEquals(2, ret.objectiveValue());

		assertEquals(1, ((ImplicitBooleanConnector) con).getVirtualResourceNodeS("v1"));
		assertEquals(1, ((ImplicitBooleanConnector) con).getVirtualResourceNodeS("v2"));
	}

	@Test
	public void testMap02to1() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 11);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		// All mappings must be chosen, according to the objective function
		assertEquals(1, ret.objectiveValue());

		assertEquals(0, ((ImplicitBooleanConnector) con).getVirtualResourceNodeS("v1"));
		assertEquals(1, ((ImplicitBooleanConnector) con).getVirtualResourceNodeS("v2"));
	}

	// Negative tests

	@Test
	public void testMap0to1() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 42);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		// All mappings must be chosen, according to the objective function
		assertEquals(0, Math.abs(ret.objectiveValue()));

		assertEquals(0, ((ImplicitBooleanConnector) con).getVirtualResourceNodeS("v1"));
	}

	@Test
	public void testMap00to1() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 42);
		gen.genVirtualNode("v2", 73);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		// All mappings must be chosen, according to the objective function
		assertEquals(0, Math.abs(ret.objectiveValue()));

		assertEquals(0, ((ImplicitBooleanConnector) con).getVirtualResourceNodeS("v1"));
		assertEquals(0, ((ImplicitBooleanConnector) con).getVirtualResourceNodeS("v2"));
	}

	@Override
	public Class<?> getConnectorClass() {
		return ImplicitBooleanConnector.class;
	}

}
