package de.dhbw.rahmlab.casadi.api.core.wrapper;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dbl.DoubleVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dbl.DoubleVectorCollection;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dict.Dictionary;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.MapStringToDMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.function.FunctionWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.index.IndexSlice;
import de.dhbw.rahmlab.casadi.api.core.wrapper.integer.CasADiIntVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MapStringToMXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity.MapStringToSparsity;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity.SparsityWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.str.StringVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sx.MapStringToSXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sx.SXWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.Importer;
import de.dhbw.rahmlab.casadi.impl.casadi.NlpBuilder;
import de.dhbw.rahmlab.casadi.impl.core__;

public class CoreWrapper {

    public static long hashSparsity(long nrow, long ncol, CasADiIntVector colind, CasADiIntVector row) {
        return core__.hash_sparsity(nrow, ncol, colind.getCasADiObject(), row.getCasADiObject());
    }

    public static Dictionary combine(Dictionary first, Dictionary second, boolean recurse) {
        return new Dictionary(core__.combine(first.getCasADiObject(), second.getCasADiObject(), recurse));
    }

    public static Dictionary combine(Dictionary first, Dictionary second) {
        return new Dictionary(core__.combine(first.getCasADiObject(), second.getCasADiObject()));
    }

    public static void updateDict(Dictionary target, Dictionary source, boolean recurse) {
        core__.update_dict(target.getCasADiObject(), source.getCasADiObject(), recurse);
    }

    public static void updateDict(Dictionary target, Dictionary source) {
        core__.update_dict(target.getCasADiObject(), source.getCasADiObject());
    }

    public static IndexSlice toSlice(CasADiIntVector v, boolean ind1) {
        return new IndexSlice(core__.to_slice(v.getCasADiObject(), ind1));
    }

    public static IndexSlice toSlice(CasADiIntVector v) {
        return new IndexSlice(core__.to_slice(v.getCasADiObject()));
    }

    public static boolean isSlice(CasADiIntVector v, boolean ind1) {
        return core__.is_slice(v.getCasADiObject(), ind1);
    }

    public static boolean isSlice(CasADiIntVector v) {
        return core__.is_slice(v.getCasADiObject());
    }

    public static boolean isSlice2(CasADiIntVector v) {
        return core__.is_slice2(v.getCasADiObject());
    }

    public static FunctionWrapper external(String name, Dictionary opts) {
        return new FunctionWrapper(core__.external(name, opts.getCasADiObject()));
    }

    public static FunctionWrapper external(String name) {
        return new FunctionWrapper(core__.external(name));
    }

    public static FunctionWrapper external(String name, String binName, Dictionary opts) {
        return new FunctionWrapper(core__.external(name, binName, opts.getCasADiObject()));
    }

    public static FunctionWrapper external(String name, String binName) {
        return new FunctionWrapper(core__.external(name, binName));
    }

    public static FunctionWrapper external(String name, Importer li, Dictionary opts) {
        return new FunctionWrapper(core__.external(name, li, opts.getCasADiObject()));
    }

    public static FunctionWrapper external(String name, Importer li) {
        return new FunctionWrapper(core__.external(name, li));
    }

    public static FunctionWrapper integrator(String name, String solver, MapStringToSXWrapper dae, Dictionary opts) {
        return new FunctionWrapper(core__.integrator(name, solver, dae.getCasADiObject(), opts.getCasADiObject()));
    }

    public static FunctionWrapper integrator(String name, String solver, MapStringToSXWrapper dae) {
        return new FunctionWrapper(core__.integrator(name, solver, dae.getCasADiObject()));
    }

    public static FunctionWrapper integrator(String name, String solver, MapStringToMXWrapper dae, Dictionary opts) {
        return new FunctionWrapper(core__.integrator(name, solver, dae.getCasADiObject(), opts.getCasADiObject()));
    }

    public static FunctionWrapper integrator(String name, String solver, MapStringToMXWrapper dae) {
        return new FunctionWrapper(core__.integrator(name, solver, dae.getCasADiObject()));
    }

    public static FunctionWrapper integrator(String name, String solver, FunctionWrapper dae, Dictionary opts) {
        return new FunctionWrapper(core__.integrator(name, solver, dae.getCasADiObject(), opts.getCasADiObject()));
    }

    public static FunctionWrapper integrator(String name, String solver, FunctionWrapper dae) {
        return new FunctionWrapper(core__.integrator(name, solver, dae.getCasADiObject()));
    }

    public static FunctionWrapper integrator(String name, String solver, MapStringToSXWrapper dae, double t0, DoubleVector tout, Dictionary opts) {
        return new FunctionWrapper(core__.integrator(name, solver, dae.getCasADiObject(), t0, tout.getCasADiObject(), opts.getCasADiObject()));
    }

    public static FunctionWrapper integrator(String name, String solver, MapStringToSXWrapper dae, double t0, DoubleVector tout) {
        return new FunctionWrapper(core__.integrator(name, solver, dae.getCasADiObject(), t0, tout.getCasADiObject()));
    }

    public static FunctionWrapper integrator(String name, String solver, MapStringToMXWrapper dae, double t0, DoubleVector tout, Dictionary opts) {
        return new FunctionWrapper(core__.integrator(name, solver, dae.getCasADiObject(), t0, tout.getCasADiObject(), opts.getCasADiObject()));
    }

    public static FunctionWrapper integrator(String name, String solver, MapStringToMXWrapper dae, double t0, DoubleVector tout) {
        return new FunctionWrapper(core__.integrator(name, solver, dae.getCasADiObject(), t0, tout.getCasADiObject()));
    }

    public static FunctionWrapper integrator(String name, String solver, FunctionWrapper dae, double t0, DoubleVector tout, Dictionary opts) {
        return new FunctionWrapper(core__.integrator(name, solver, dae.getCasADiObject(), t0, tout.getCasADiObject(), opts.getCasADiObject()));
    }

    public static FunctionWrapper integrator(String name, String solver, FunctionWrapper dae, double t0, DoubleVector tout) {
        return new FunctionWrapper(core__.integrator(name, solver, dae.getCasADiObject(), t0, tout.getCasADiObject()));
    }

    public static FunctionWrapper integrator(String name, String solver, MapStringToSXWrapper dae, double t0, double tf, Dictionary opts) {
        return new FunctionWrapper(core__.integrator(name, solver, dae.getCasADiObject(), t0, tf, opts.getCasADiObject()));
    }

    public static FunctionWrapper integrator(String name, String solver, MapStringToSXWrapper dae, double t0, double tf) {
        return new FunctionWrapper(core__.integrator(name, solver, dae.getCasADiObject(), t0, tf));
    }

    public static FunctionWrapper integrator(String name, String solver, MapStringToMXWrapper dae, double t0, double tf, Dictionary opts) {
        return new FunctionWrapper(core__.integrator(name, solver, dae.getCasADiObject(), t0, tf, opts.getCasADiObject()));
    }

    public static FunctionWrapper integrator(String name, String solver, MapStringToMXWrapper dae, double t0, double tf) {
        return new FunctionWrapper(core__.integrator(name, solver, dae.getCasADiObject(), t0, tf));
    }

    public static FunctionWrapper integrator(String name, String solver, FunctionWrapper dae, double t0, double tf, Dictionary opts) {
        return new FunctionWrapper(core__.integrator(name, solver, dae.getCasADiObject(), t0, tf, opts.getCasADiObject()));
    }

    public static FunctionWrapper integrator(String name, String solver, FunctionWrapper dae, double t0, double tf) {
        return new FunctionWrapper(core__.integrator(name, solver, dae.getCasADiObject(), t0, tf));
    }

    public static boolean hasIntegrator(String name) {
        return core__.has_integrator(name);
    }

    public static void loadIntegrator(String name) {
        core__.load_integrator(name);
    }

    public static String docIntegrator(String name) {
        return core__.doc_integrator(name);
    }

    public static StringVector integratorIn() {
        return new StringVector(core__.integrator_in());
    }

    public static StringVector integratorOut() {
        return new StringVector(core__.integrator_out());
    }

    public static String integratorIn(long index) {
        return core__.integrator_in(index);
    }

    public static String integratorOut(long index) {
        return core__.integrator_out(index);
    }

    public static long getIntegratorNumberOfInputs() {
        return core__.integrator_n_in();
    }

    public static long getIntegratorNumberOfOutputs() {
        return core__.integrator_n_out();
    }

    public static StringVector getSimulatorInputScheme() {
        return new StringVector(core__.dyn_in());
    }

    public static StringVector getSimulatorOutputScheme() {
        return new StringVector(core__.dyn_out());
    }

    public static String getSimulatorInputScheme(long index) {
        return core__.dyn_in(index);
    }

    public static String getSimulatorOutputScheme(long index) {
        return core__.dyn_out(index);
    }

    public static long getSimulatorNumberOfInputs() {
        return core__.dyn_n_in();
    }

    public static long getSimulatorNumberOfOutputs() {
        return core__.dyn_n_out();
    }

    public static FunctionWrapper conic(String name, String solver, MapStringToSparsity qp, Dictionary opts) {
        return new FunctionWrapper(core__.conic(name, solver, qp.getCasADiObject(), opts.getCasADiObject()));
    }

    public static FunctionWrapper conic(String name, String solver, MapStringToSparsity qp) {
        return new FunctionWrapper(core__.conic(name, solver, qp.getCasADiObject()));
    }

    public static FunctionWrapper qpsol(String name, String solver, MapStringToSXWrapper qp, Dictionary opts) {
        return new FunctionWrapper(core__.qpsol(name, solver, qp.getCasADiObject(), opts.getCasADiObject()));
    }

    public static FunctionWrapper qpsol(String name, String solver, MapStringToSXWrapper qp) {
        return new FunctionWrapper(core__.qpsol(name, solver, qp.getCasADiObject()));
    }

    public static FunctionWrapper qpsol(String name, String solver, MapStringToMXWrapper qp, Dictionary opts) {
        return new FunctionWrapper(core__.qpsol(name, solver, qp.getCasADiObject(), opts.getCasADiObject()));
    }

    public static FunctionWrapper qpsol(String name, String solver, MapStringToMXWrapper qp) {
        return new FunctionWrapper(core__.qpsol(name, solver, qp.getCasADiObject()));
    }

    public static StringVector getConicInputScheme() {
        return new StringVector(core__.conic_in());
    }

    public static StringVector getConicOutputScheme() {
        return new StringVector(core__.conic_out());
    }

    public static String getConicInputScheme(long index) {
        return core__.conic_in(index);
    }

    public static String getConicOutputScheme(long index) {
        return core__.conic_out(index);
    }

    public static long getConicNumberOfInputs() {
        return core__.conic_n_in();
    }

    public static long getConicNumberOfOutputs() {
        return core__.conic_n_out();
    }

    public static StringVector getConicOptions(String name) {
        return new StringVector(core__.conic_options(name));
    }

    public static String getConicOptionType(String name, String option) {
        return core__.conic_option_type(name, option);
    }

    public static String getConicOptionInfo(String name, String option) {
        return core__.conic_option_info(name, option);
    }

    public static boolean hasConic(String name) {
        return core__.has_conic(name);
    }

    public static void loadConic(String name) {
        core__.load_conic(name);
    }

    public static String getConicDocumentation(String name) {
        return core__.doc_conic(name);
    }

    public static void conicDebug(FunctionWrapper function, String filename) {
        core__.conic_debug(function.getCasADiObject(), filename);
    }

    // Wrapper für nlpsol
    public static FunctionWrapper nlpsol(String name, String solver, MapStringToSXWrapper nlp, Dictionary opts) {
        return new FunctionWrapper(core__.nlpsol(name, solver, nlp.getCasADiObject(), opts.getCasADiObject()));
    }

    public static FunctionWrapper nlpsol(String name, String solver, MapStringToSXWrapper nlp) {
        return new FunctionWrapper(core__.nlpsol(name, solver, nlp.getCasADiObject()));
    }

    public static FunctionWrapper nlpsol(String name, String solver, MapStringToMXWrapper nlp, Dictionary opts) {
        return new FunctionWrapper(core__.nlpsol(name, solver, nlp.getCasADiObject(), opts.getCasADiObject()));
    }

    public static FunctionWrapper nlpsol(String name, String solver, MapStringToMXWrapper nlp) {
        return new FunctionWrapper(core__.nlpsol(name, solver, nlp.getCasADiObject()));
    }

    public static FunctionWrapper nlpsol(String name, String solver, String fname, Dictionary opts) {
        return new FunctionWrapper(core__.nlpsol(name, solver, fname, opts.getCasADiObject()));
    }

    public static FunctionWrapper nlpsol(String name, String solver, String fname) {
        return new FunctionWrapper(core__.nlpsol(name, solver, fname));
    }

    public static FunctionWrapper nlpsol(String name, String solver, Importer compiler, Dictionary opts) {
        return new FunctionWrapper(core__.nlpsol(name, solver, compiler, opts.getCasADiObject()));
    }

    public static FunctionWrapper nlpsol(String name, String solver, Importer compiler) {
        return new FunctionWrapper(core__.nlpsol(name, solver, compiler));
    }

    public static FunctionWrapper nlpsol(String name, String solver, NlpBuilder nl, Dictionary opts) {
        return new FunctionWrapper(core__.nlpsol(name, solver, nl, opts.getCasADiObject()));
    }

    public static FunctionWrapper nlpsol(String name, String solver, NlpBuilder nl) {
        return new FunctionWrapper(core__.nlpsol(name, solver, nl));
    }

    public static StringVector getNlpsolInputScheme() {
        return new StringVector(core__.nlpsol_in());
    }

    public static StringVector getNlpsolOutputScheme() {
        return new StringVector(core__.nlpsol_out());
    }

    public static String getNlpsolInputScheme(long index) {
        return core__.nlpsol_in(index);
    }

    public static String getNlpsolOutputScheme(long index) {
        return core__.nlpsol_out(index);
    }

    public static long getNlpsolNumberOfInputs() {
        return core__.nlpsol_n_in();
    }

    public static long getNlpsolNumberOfOutputs() {
        return core__.nlpsol_n_out();
    }

    public static double getNlpsolDefaultInput(long index) {
        return core__.nlpsol_default_in(index);
    }

    public static DoubleVector getNlpsolDefaultInput() {
        return new DoubleVector(core__.nlpsol_default_in());
    }

    public static StringVector getNlpsolOptions(String name) {
        return new StringVector(core__.nlpsol_options(name));
    }

    public static String getNlpsolOptionType(String name, String option) {
        return core__.nlpsol_option_type(name, option);
    }

    public static String getNlpsolOptionInfo(String name, String option) {
        return core__.nlpsol_option_info(name, option);
    }

    public static boolean hasNlpsol(String name) {
        return core__.has_nlpsol(name);
    }

    public static void loadNlpsol(String name) {
        core__.load_nlpsol(name);
    }

    public static String getNlpsolDocumentation(String name) {
        return core__.doc_nlpsol(name);
    }

    public static FunctionWrapper rootfinder(String name, String solver, MapStringToSXWrapper rfp, Dictionary opts) {
        return new FunctionWrapper(core__.rootfinder(name, solver, rfp.getCasADiObject(), opts.getCasADiObject()));
    }

    public static FunctionWrapper rootfinder(String name, String solver, MapStringToSXWrapper rfp) {
        return new FunctionWrapper(core__.rootfinder(name, solver, rfp.getCasADiObject()));
    }

    public static FunctionWrapper rootfinder(String name, String solver, MapStringToMXWrapper rfp, Dictionary opts) {
        return new FunctionWrapper(core__.rootfinder(name, solver, rfp.getCasADiObject(), opts.getCasADiObject()));
    }

    public static FunctionWrapper rootfinder(String name, String solver, MapStringToMXWrapper rfp) {
        return new FunctionWrapper(core__.rootfinder(name, solver, rfp.getCasADiObject()));
    }

    public static FunctionWrapper rootfinder(String name, String solver, FunctionWrapper f, Dictionary opts) {
        return new FunctionWrapper(core__.rootfinder(name, solver, f.getCasADiObject(), opts.getCasADiObject()));
    }

    public static FunctionWrapper rootfinder(String name, String solver, FunctionWrapper f) {
        return new FunctionWrapper(core__.rootfinder(name, solver, f.getCasADiObject()));
    }

    public static StringVector getRootfinderInputScheme() {
        return new StringVector(core__.rootfinder_in());
    }

    public static StringVector getRootfinderOutputScheme() {
        return new StringVector(core__.rootfinder_out());
    }

    public static String getRootfinderInputScheme(long index) {
        return core__.rootfinder_in(index);
    }

    public static String getRootfinderOutputScheme(long index) {
        return core__.rootfinder_out(index);
    }

    public static long getRootfinderNumberOfInputs() {
        return core__.rootfinder_n_in();
    }

    public static long getRootfinderNumberOfOutputs() {
        return core__.rootfinder_n_out();
    }

    public static StringVector getRootfinderOptions(String name) {
        return new StringVector(core__.rootfinder_options(name));
    }

    public static String getRootfinderOptionType(String name, String option) {
        return core__.rootfinder_option_type(name, option);
    }

    public static String getRootfinderOptionInfo(String name, String option) {
        return core__.rootfinder_option_info(name, option);
    }

    public static boolean hasRootfinder(String name) {
        return core__.has_rootfinder(name);
    }

    public static void loadRootfinder(String name) {
        core__.load_rootfinder(name);
    }

    public static String getRootfinderDocumentation(String name) {
        return core__.doc_rootfinder(name);
    }

    public static boolean hasLinsol(String name) {
        return core__.has_linsol(name);
    }

    public static void loadLinsol(String name) {
        core__.load_linsol(name);
    }

    public static String getLinsolDocumentation(String name) {
        return core__.doc_linsol(name);
    }

    //---
    // Wrapper für dplesol
    public static FunctionWrapper dplesol(String name, String solver, MapStringToSparsity st, Dictionary opts) {
        return new FunctionWrapper(core__.dplesol(name, solver, st.getCasADiObject(), opts.getCasADiObject()));
    }

    public static FunctionWrapper dplesol(String name, String solver, MapStringToSparsity st) {
        return new FunctionWrapper(core__.dplesol(name, solver, st.getCasADiObject()));
    }

    public static MXWrapper dplesol(MXWrapper A, MXWrapper V, String solver, Dictionary opts) {
        return new MXWrapper(core__.dplesol(A.getCasADiObject(), V.getCasADiObject(), solver, opts.getCasADiObject()));
    }

    public static MXWrapper dplesol(MXWrapper A, MXWrapper V, String solver) {
        return new MXWrapper(core__.dplesol(A.getCasADiObject(), V.getCasADiObject(), solver));
    }

    public static MXVector dplesol(MXVector A, MXVector V, String solver, Dictionary opts) {
        return new MXVector(core__.dplesol(A.getCasADiObject(), V.getCasADiObject(), solver, opts.getCasADiObject()));
    }

    public static MXVector dplesol(MXVector A, MXVector V, String solver) {
        return new MXVector(core__.dplesol(A.getCasADiObject(), V.getCasADiObject(), solver));
    }

    public static DMVector dplesol(DMVector A, DMVector V, String solver, Dictionary opts) {
        return new DMVector(core__.dplesol(A.getCasADiObject(), V.getCasADiObject(), solver, opts.getCasADiObject()));
    }

    public static DMVector dplesol(DMVector A, DMVector V, String solver) {
        return new DMVector(core__.dplesol(A.getCasADiObject(), V.getCasADiObject(), solver));
    }

    public static StringVector getDpleInputScheme() {
        return new StringVector(core__.dple_in());
    }

    public static StringVector getDpleOutputScheme() {
        return new StringVector(core__.dple_out());
    }

    public static String getDpleInputScheme(long index) {
        return core__.dple_in(index);
    }

    public static String getDpleOutputScheme(long index) {
        return core__.dple_out(index);
    }

    public static long getDpleNumberOfInputs() {
        return core__.dple_n_in();
    }

    public static long getDpleNumberOfOutputs() {
        return core__.dple_n_out();
    }

    public static boolean hasDple(String name) {
        return core__.has_dple(name);
    }

    public static void loadDple(String name) {
        core__.load_dple(name);
    }

    public static String getDpleDocumentation(String name) {
        return core__.doc_dple(name);
    }

    public static FunctionWrapper expmsol(String name, String solver, SparsityWrapper A, Dictionary opts) {
        return new FunctionWrapper(core__.expmsol(name, solver, A.getCasADiObject(), opts.getCasADiObject()));
    }

    public static FunctionWrapper expmsol(String name, String solver, SparsityWrapper A) {
        return new FunctionWrapper(core__.expmsol(name, solver, A.getCasADiObject()));
    }

    public static long getExpmNumberOfInputs() {
        return core__.expm_n_in();
    }

    public static long getExpmNumberOfOutputs() {
        return core__.expm_n_out();
    }

    public static boolean hasExpm(String name) {
        return core__.has_expm(name);
    }

    public static void loadExpm(String name) {
        core__.load_expm(name);
    }

    public static String getExpmDocumentation(String name) {
        return core__.doc_expm(name);
    }

    public static FunctionWrapper interpolant(String name, String solver, DoubleVectorCollection grid, DoubleVector values, Dictionary opts) {
        return new FunctionWrapper(core__.interpolant(name, solver, grid.getCasADiObject(), values.getCasADiObject(), opts.getCasADiObject()));
    }

    public static FunctionWrapper interpolant(String name, String solver, DoubleVectorCollection grid, DoubleVector values) {
        return new FunctionWrapper(core__.interpolant(name, solver, grid.getCasADiObject(), values.getCasADiObject()));
    }

    public static FunctionWrapper interpolant(String name, String solver, DoubleVectorCollection grid, long m, Dictionary opts) {
        return new FunctionWrapper(core__.interpolant(name, solver, grid.getCasADiObject(), m, opts.getCasADiObject()));
    }

    public static FunctionWrapper interpolant(String name, String solver, DoubleVectorCollection grid, long m) {
        return new FunctionWrapper(core__.interpolant(name, solver, grid.getCasADiObject(), m));
    }

    public static FunctionWrapper interpolant(String name, String solver, DoubleVectorCollection grid) {
        return new FunctionWrapper(core__.interpolant(name, solver, grid.getCasADiObject()));
    }

    public static FunctionWrapper interpolant(String name, String solver, CasADiIntVector gridDims, long m, Dictionary opts) {
        return new FunctionWrapper(core__.interpolant(name, solver, gridDims.getCasADiObject(), m, opts.getCasADiObject()));
    }

    public static FunctionWrapper interpolant(String name, String solver, CasADiIntVector gridDims, long m) {
        return new FunctionWrapper(core__.interpolant(name, solver, gridDims.getCasADiObject(), m));
    }

    public static FunctionWrapper interpolant(String name, String solver, CasADiIntVector gridDims) {
        return new FunctionWrapper(core__.interpolant(name, solver, gridDims.getCasADiObject()));
    }

    public static FunctionWrapper interpolant(String name, String solver, CasADiIntVector gridDims, DoubleVector values, Dictionary opts) {
        return new FunctionWrapper(core__.interpolant(name, solver, gridDims.getCasADiObject(), values.getCasADiObject(), opts.getCasADiObject()));
    }

    public static FunctionWrapper interpolant(String name, String solver, CasADiIntVector gridDims, DoubleVector values) {
        return new FunctionWrapper(core__.interpolant(name, solver, gridDims.getCasADiObject(), values.getCasADiObject()));
    }

    public static boolean hasInterpolant(String name) {
        return core__.has_interpolant(name);
    }

    public static void loadInterpolant(String name) {
        core__.load_interpolant(name);
    }

    public static String getInterpolantDocumentation(String name) {
        return core__.doc_interpolant(name);
    }

    public static DoubleVector getCollocationPoints(long order, String scheme) {
        return new DoubleVector(core__.collocation_points(order, scheme));
    }

    public static DoubleVector getCollocationPoints(long order) {
        return new DoubleVector(core__.collocation_points(order));
    }

    public static void collocationInterpolators(DoubleVector tau, DoubleVectorCollection arg1, DoubleVector arg2) {
        core__.collocation_interpolators(tau.getCasADiObject(), arg1.getCasADiObject(), arg2.getCasADiObject());
    }

    public static void collocationCoeff(DoubleVector tau, DMWrapper arg1, DMWrapper arg2, DMWrapper arg3) {
        core__.collocation_coeff(tau.getCasADiObject(), arg1.getCasADiObject(), arg2.getCasADiObject(), arg3.getCasADiObject());
    }

    public static FunctionWrapper simpleRK(FunctionWrapper f, long N, long order) {
        return new FunctionWrapper(core__.simpleRK(f.getCasADiObject(), N, order));
    }

    public static FunctionWrapper simpleRK(FunctionWrapper f, long N) {
        return new FunctionWrapper(core__.simpleRK(f.getCasADiObject(), N));
    }

    public static FunctionWrapper simpleRK(FunctionWrapper f) {
        return new FunctionWrapper(core__.simpleRK(f.getCasADiObject()));
    }

    // Wrapper für simpleIRK
    public static FunctionWrapper simpleIRK(FunctionWrapper f, long N, long order, String scheme, String solver, Dictionary solverOptions) {
        return new FunctionWrapper(core__.simpleIRK(f.getCasADiObject(), N, order, scheme, solver, solverOptions.getCasADiObject()));
    }

    public static FunctionWrapper simpleIRK(FunctionWrapper f, long N, long order, String scheme, String solver) {
        return new FunctionWrapper(core__.simpleIRK(f.getCasADiObject(), N, order, scheme, solver));
    }

    public static FunctionWrapper simpleIRK(FunctionWrapper f, long N, long order, String scheme) {
        return new FunctionWrapper(core__.simpleIRK(f.getCasADiObject(), N, order, scheme));
    }

    public static FunctionWrapper simpleIRK(FunctionWrapper f, long N, long order) {
        return new FunctionWrapper(core__.simpleIRK(f.getCasADiObject(), N, order));
    }

    public static FunctionWrapper simpleIRK(FunctionWrapper f, long N) {
        return new FunctionWrapper(core__.simpleIRK(f.getCasADiObject(), N));
    }

    public static FunctionWrapper simpleIRK(FunctionWrapper f) {
        return new FunctionWrapper(core__.simpleIRK(f.getCasADiObject()));
    }

    public static FunctionWrapper simpleIntegrator(FunctionWrapper f, String integrator, Dictionary integratorOptions) {
        return new FunctionWrapper(core__.simpleIntegrator(f.getCasADiObject(), integrator, integratorOptions.getCasADiObject()));
    }

    public static FunctionWrapper simpleIntegrator(FunctionWrapper f, String integrator) {
        return new FunctionWrapper(core__.simpleIntegrator(f.getCasADiObject(), integrator));
    }

    public static FunctionWrapper simpleIntegrator(FunctionWrapper f) {
        return new FunctionWrapper(core__.simpleIntegrator(f.getCasADiObject()));
    }

    public static MapStringToMXWrapper daeReduceIndex(MapStringToMXWrapper dae, Dictionary output, Dictionary opts) {
        return new MapStringToMXWrapper(core__.dae_reduce_index(dae.getCasADiObject(), output.getCasADiObject(), opts.getCasADiObject()));
    }

    public static MapStringToMXWrapper daeReduceIndex(MapStringToMXWrapper dae, Dictionary output) {
        return new MapStringToMXWrapper(core__.dae_reduce_index(dae.getCasADiObject(), output.getCasADiObject()));
    }

    public static MapStringToSXWrapper daeReduceIndex(MapStringToSXWrapper dae, Dictionary output, Dictionary opts) {
        return new MapStringToSXWrapper(core__.dae_reduce_index(dae.getCasADiObject(), output.getCasADiObject(), opts.getCasADiObject()));
    }

    public static MapStringToSXWrapper daeReduceIndex(MapStringToSXWrapper dae, Dictionary output) {
        return new MapStringToSXWrapper(core__.dae_reduce_index(dae.getCasADiObject(), output.getCasADiObject()));
    }

    public static MapStringToMXWrapper daeMapSemiExpl(MapStringToMXWrapper dae, MapStringToMXWrapper daeRed, FunctionWrapper arg2, FunctionWrapper arg3) {
        return new MapStringToMXWrapper(core__.dae_map_semi_expl(dae.getCasADiObject(), daeRed.getCasADiObject(), arg2.getCasADiObject(), arg3.getCasADiObject()));
    }

    public static MapStringToSXWrapper daeMapSemiExpl(MapStringToSXWrapper dae, MapStringToSXWrapper daeRed, FunctionWrapper arg2, FunctionWrapper arg3) {
        return new MapStringToSXWrapper(core__.dae_map_semi_expl(dae.getCasADiObject(), daeRed.getCasADiObject(), arg2.getCasADiObject(), arg3.getCasADiObject()));
    }

    public static FunctionWrapper daeInitGen(MapStringToMXWrapper dae, MapStringToMXWrapper daeRed, String initSolver, MapStringToDMWrapper initStrength, Dictionary initSolverOptions) {
        return new FunctionWrapper(core__.dae_init_gen(dae.getCasADiObject(), daeRed.getCasADiObject(), initSolver, initStrength.getCasADiObject(), initSolverOptions.getCasADiObject()));
    }

    public static FunctionWrapper daeInitGen(MapStringToMXWrapper dae, MapStringToMXWrapper daeRed, String initSolver, MapStringToDMWrapper initStrength) {
        return new FunctionWrapper(core__.dae_init_gen(dae.getCasADiObject(), daeRed.getCasADiObject(), initSolver, initStrength.getCasADiObject()));
    }

    public static FunctionWrapper daeInitGen(MapStringToMXWrapper dae, MapStringToMXWrapper daeRed, String initSolver) {
        return new FunctionWrapper(core__.dae_init_gen(dae.getCasADiObject(), daeRed.getCasADiObject(), initSolver));
    }

    public static FunctionWrapper daeInitGen(MapStringToSXWrapper dae, MapStringToSXWrapper daeRed, String initSolver, MapStringToDMWrapper initStrength, Dictionary initSolverOptions) {
        return new FunctionWrapper(core__.dae_init_gen(dae.getCasADiObject(), daeRed.getCasADiObject(), initSolver, initStrength.getCasADiObject(), initSolverOptions.getCasADiObject()));
    }

    public static FunctionWrapper daeInitGen(MapStringToSXWrapper dae, MapStringToSXWrapper daeRed, String initSolver, MapStringToDMWrapper initStrength) {
        return new FunctionWrapper(core__.dae_init_gen(dae.getCasADiObject(), daeRed.getCasADiObject(), initSolver, initStrength.getCasADiObject()));
    }

    public static FunctionWrapper daeInitGen(MapStringToSXWrapper dae, MapStringToSXWrapper daeRed, String initSolver) {
        return new FunctionWrapper(core__.dae_init_gen(dae.getCasADiObject(), daeRed.getCasADiObject(), initSolver));
    }

    public static void detectSimpleBounds(SXWrapper xX, SXWrapper p, SXWrapper g, SXWrapper lbg, SXWrapper ubg, CasADiIntVector arg5, SXWrapper arg6, SXWrapper arg7, FunctionWrapper arg8, FunctionWrapper arg9) {
        core__.detect_simple_bounds(xX.getCasADiObject(), p.getCasADiObject(), g.getCasADiObject(), lbg.getCasADiObject(), ubg.getCasADiObject(), arg5.getCasADiObject(), arg6.getCasADiObject(), arg7.getCasADiObject(), arg8.getCasADiObject(), arg9.getCasADiObject());
    }

    public static void detectSimpleBounds(MXWrapper xX, MXWrapper p, MXWrapper g, MXWrapper lbg, MXWrapper ubg, CasADiIntVector arg5, MXWrapper arg6, MXWrapper arg7, FunctionWrapper arg8, FunctionWrapper arg9) {
        core__.detect_simple_bounds(xX.getCasADiObject(), p.getCasADiObject(), g.getCasADiObject(), lbg.getCasADiObject(), ubg.getCasADiObject(), arg5.getCasADiObject(), arg6.getCasADiObject(), arg7.getCasADiObject(), arg8.getCasADiObject(), arg9.getCasADiObject());
    }


}
