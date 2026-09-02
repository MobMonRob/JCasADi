package de.dhbw.rahmlab.casadimaxima.experiments;

import de.dhbw.rahmlab.casadi.DmStatic;
import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.impl.casadi.DM;
import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDM;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDouble;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorSX;

import static de.dhbw.rahmlab.casadi.impl.core__.rootfinder;

/**
 * Demonstrates that a CasADi rootfinder can be embedded in a larger SX graph.
 *
 * <p>The vectors in this example intentionally model dense 16-component
 * multivectors.  The rootfinder is called with symbolic inputs first; Newton
 * iterations happen only when the outer {@code solve} function is evaluated
 * with DM inputs.</p>
 */
public final class SymbolicRootfindingExperiment {

    private static final int COMPONENT_COUNT = 16;
    private static final double TOLERANCE = 1e-9;

    private SymbolicRootfindingExperiment() {
    }

    public static void main(String[] args) {
        SX x = SxStatic.sym("x", COMPONENT_COUNT, 1);
        SX p = SxStatic.sym("p", COMPONENT_COUNT, 1);
        requireDenseColumn("x", x);
        requireDenseColumn("p", p);

        // r(x, p) = x^2 - p, component by component.
        SX residualExpression = SxStatic.minus(SxStatic.sq(x), p);
        requireDenseColumn("residual", residualExpression);
        Function residual = new Function("residual",
            new StdVectorSX(new SX[]{x, p}),
            new StdVectorSX(new SX[]{residualExpression}));

        Function root = rootfinder("root", "newton", residual);

        SX x0 = SxStatic.sym("x0", COMPONENT_COUNT, 1);
        StdVectorSX rootOutput = new StdVectorSX();
        root.call(new StdVectorSX(new SX[]{x0, p}), rootOutput);
        SX symbolicRoot = rootOutput.get(0);
        requireDenseColumn("symbolic root", symbolicRoot);

        // Proves that the rootfinder call remains an SX node that can be used
        // by subsequent symbolic graph construction.
        SX postProcessed = SxStatic.plus(symbolicRoot, p);
        Function solve = new Function("solve",
            new StdVectorSX(new SX[]{x0, p}),
            new StdVectorSX(new SX[]{postProcessed}));

        System.out.println("residual SX: " + residualExpression);
        System.out.println("symbolic root SX: " + symbolicRoot);
        System.out.println("post-processed SX: " + postProcessed);
        System.out.println("solve: " + solve);

        DM initialGuess = DmStatic.ones(COMPONENT_COUNT, 1);
        DM parameter = squares();
        StdVectorDM solveOutput = new StdVectorDM();
        solve.call(new StdVectorDM(new DM[]{initialGuess, parameter}), solveOutput);
        DM numericPostProcessed = solveOutput.get(0);

        StdVectorDM rootValueOutput = new StdVectorDM();
        root.call(new StdVectorDM(new DM[]{initialGuess, parameter}), rootValueOutput);
        DM numericRoot = rootValueOutput.get(0);

        assertRoot(numericRoot);
        assertPostProcessed(numericPostProcessed);

        StdVectorDM residualValueOutput = new StdVectorDM();
        residual.call(new StdVectorDM(new DM[]{numericRoot, parameter}), residualValueOutput);
        assertZeroResidual(residualValueOutput.get(0));

        System.out.println("numeric root: " + numericRoot);
        System.out.println("numeric post-processed result: " + numericPostProcessed);
        System.out.println("Rootfinding POC passed.");
    }

    private static DM squares() {
        double[] values = new double[COMPONENT_COUNT];
        for (int index = 0; index < COMPONENT_COUNT; index++) {
            double component = index + 1;
            values[index] = component * component;
        }
        return new DM(new StdVectorDouble(values));
    }

    private static void requireDenseColumn(String name, SX value) {
        if (value.rows() != COMPONENT_COUNT || value.columns() != 1 || !value.is_dense_()) {
            throw new IllegalStateException(name + " must be a dense " + COMPONENT_COUNT + "x1 SX column.");
        }
    }

    private static void assertRoot(DM root) {
        for (int index = 0; index < COMPONENT_COUNT; index++) {
            assertClose("root", index, index + 1, root.at(index, 0).scalar());
        }
    }

    private static void assertPostProcessed(DM result) {
        for (int index = 0; index < COMPONENT_COUNT; index++) {
            double component = index + 1;
            assertClose("post-processed result", index, component + component * component,
                result.at(index, 0).scalar());
        }
    }

    private static void assertZeroResidual(DM residual) {
        for (int index = 0; index < COMPONENT_COUNT; index++) {
            assertClose("residual", index, 0d, residual.at(index, 0).scalar());
        }
    }

    private static void assertClose(String valueName, int index, double expected, double actual) {
        if (Math.abs(expected - actual) > TOLERANCE) {
            throw new IllegalStateException(String.format(
                "%s component %d: expected %s but was %s", valueName, index, expected, actual));
        }
    }
}
