package de.dhbw.rahmlab.casadi.api.core.wrapper.function;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity.SparsityVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.*;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sx.SXVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sx.SXWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.GenericType;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;
import de.dhbw.rahmlab.casadi.impl.std.*;

import java.util.Arrays;

public class FunctionWrapper {

    private final Function function;

    public FunctionWrapper() {
        this.function = new Function();
    }

    public FunctionWrapper(String functionName) {
        this.function = new Function(functionName);
    }

    public FunctionWrapper(String functionName, SXVector exIn, SXVector exOut, Dict opts) {
        this.function = new Function(functionName, exIn.getCasADiObject(), exOut.getCasADiObject(), opts);
    }

    public FunctionWrapper(String functionName, SXVector exIn, SXVector exOut) {
        this.function = new Function(functionName, exIn.getCasADiObject(), exOut.getCasADiObject());
    }

    public FunctionWrapper(String functionName, SXVector exIn, SXVector exOut, StringVector nameIn, StringVector nameOut, Dict opts) {
        this.function = new Function(functionName, exIn.getCasADiObject(), exOut.getCasADiObject(), nameIn.getCasADiObject(), nameOut.getCasADiObject(), opts);
    }

    public FunctionWrapper(String functionName, SXVector exIn, SXVector exOut, StringVector nameIn, StringVector nameOut) {
        this.function = new Function(functionName, exIn.getCasADiObject(), exOut.getCasADiObject(), nameIn.getCasADiObject(), nameOut.getCasADiObject());
    }

    public FunctionWrapper(String functionName, MapStringToSXWrapper dict, StringVector nameIn, StringVector nameOut, Dict opts) {
        this.function = new Function(functionName, dict.getCasADiObject(), nameIn.getCasADiObject(), nameOut.getCasADiObject(), opts);
    }

    public FunctionWrapper(String functionName, MapStringToSXWrapper dict, StringVector nameIn, StringVector nameOut) {
        this.function = new Function(functionName, dict.getCasADiObject(), nameIn.getCasADiObject(), nameOut.getCasADiObject());
    }

    public FunctionWrapper(String functionName, MXVector exIn, MXVector exOut, Dict opts) {
        this.function = new Function(functionName, exIn.getCasADiObject(), exOut.getCasADiObject(), opts);
    }

    public FunctionWrapper(String functionName, MXVector exIn, MXVector exOut) {
        this.function = new Function(functionName, exIn.getCasADiObject(), exOut.getCasADiObject());
    }

    public FunctionWrapper(String functionName, MXVector exIn, MXVector exOut, StringVector nameIn, StringVector nameOut, Dict opts) {
        this.function = new Function(functionName, exIn.getCasADiObject(), exOut.getCasADiObject(), nameIn.getCasADiObject(), nameOut.getCasADiObject(), opts);
    }

    public FunctionWrapper(String functionName, MXVector exIn, MXVector exOut, StringVector nameIn, StringVector nameOut) {
        this.function = new Function(functionName, exIn.getCasADiObject(), exOut.getCasADiObject(), nameIn.getCasADiObject(), nameOut.getCasADiObject());
    }

    public FunctionWrapper(String functionName, MapStringToMXWrapper dict, StringVector nameIn, StringVector nameOut, Dict opts) {
        this.function = new Function(functionName, dict.getCasADiObject(), nameIn.getCasADiObject(), nameOut.getCasADiObject(), opts);
    }

    public FunctionWrapper(String functionName, MapStringToMXWrapper dict, StringVector nameIn, StringVector nameOut) {
        this.function = new Function(functionName, dict.getCasADiObject(), nameIn.getCasADiObject(), nameOut.getCasADiObject());
    }

    public FunctionWrapper(Function other) {
        this.function = new Function(other);
    }

    public FunctionWrapper(FunctionWrapper other) {
        this.function = new Function(other.getCasADiObject());
    }

    public String typeName() {
        return Function.type_name();
    }

    public FunctionWrapper expand() {
        return new FunctionWrapper(this.function.expand());
    }

    public FunctionWrapper expand(String name, Dict opts) {
        return new FunctionWrapper(this.function.expand(name, opts));
    }

    public FunctionWrapper expand(String name) {
        return new FunctionWrapper(this.function.expand(name));
    }

    public long getNumberOfInputs() {
        return this.function.n_in();
    }

    public long getNumberOfOutputs() {
        return this.function.n_out();
    }

    public long getSize1Input(long ind) {
        return this.function.size1_in(ind);
    }

    public long getSize1Input(String inputName) {
        return this.function.size1_in(inputName);
    }

    public long getSize2Input(long ind) {
        return this.function.size2_in(ind);
    }

    public long getSize2Input(String inputName) {
        return this.function.size2_in(inputName);
    }

    public long getSize1Output(long ind) {
        return this.function.size1_out(ind);
    }

    public long getSize1Output(String outputName) {
        return this.function.size1_out(outputName);
    }

    public long getSize2Output(long ind) {
        return this.function.size2_out(ind);
    }

    public long getSize2Output(String outputName) {
        return this.function.size2_out(outputName);
    }

    public long getNumberOfInputNNZ() {
        return this.function.nnz_in();
    }

    public long getNumberOfInputNNZ(long ind) {
        return this.function.nnz_in(ind);
    }

    public long getNumberOfInputNNZ(String inputName) {
        return this.function.nnz_in(inputName);
    }

    public long getNumberOfOutputNNZ() {
        return this.function.nnz_out();
    }

    public long getNumberOfOutputNNZ(long ind) {
        return this.function.nnz_out(ind);
    }

    public long getNumberOfOutputNNZ(String outputName) {
        return this.function.nnz_out(outputName);
    }

    public long getNumberOfInputElements() {
        return this.function.numel_in();
    }

    public long getNumberOfInputElements(long ind) {
        return this.function.numel_in(ind);
    }

    public long getNumberOfInputElements(String inputName) {
        return this.function.numel_in(inputName);
    }

    public long getNumberOfOutputElements() {
        return this.function.numel_out();
    }

    public long getNumberOfOutputElements(long ind) {
        return this.function.numel_out(ind);
    }

    public long getNumberOfOutputElements(String outputName) {
        return this.function.numel_out(outputName);
    }

    public StringVector nameIn() {
        return new StringVector(this.function.name_in());
    }

    public StringVector nameOut() {
        return new StringVector(this.function.name_out());
    }

    public String getInputSchemeNameByIndex(long ind) {
        return this.function.name_in(ind);
    }

    public String getOutputSchemeNameByIndex(long ind) {
        return this.function.name_out(ind);
    }

    // TODO: Change Name
    public long getIndexIn(String name) {
        return this.function.index_in(name);
    }

    // TODO: Change Name
    public long getIndexOut(String name) {
        return this.function.index_out(name);
    }

    public double getDefaultInputValue(long ind) {
        return this.function.default_in(ind);
    }

    public double getLargestInputValue(long ind) {
        return this.function.max_in(ind);
    }

    public double getSmallestInputValue(long ind) {
        return this.function.min_in(ind);
    }

    public DoubleVector getNominalInputValue(long ind) {
        return new DoubleVector(this.function.nominal_in(ind));
    }

    public DoubleVector getNominalOutputValue(long ind) {
        return new DoubleVector(this.function.nominal_out(ind));
    }

    public Sparsity getSparsityOfGivenInput(long ind) {
        return this.function.sparsity_in(ind);
    }

    public Sparsity getSparsityOfGivenInput(String inputName) {
        return this.function.sparsity_in(inputName);
    }

    public Sparsity getSparsityOfGivenOutput(long ind) {
        return this.function.sparsity_out(ind);
    }

    public Sparsity getSparsityOfGivenOutput(String inputName) {
        return this.function.sparsity_out(inputName);
    }

    public boolean getDifferentiabilityOfInput(long ind) {
        return this.function.is_diff_in(ind);
    }

    public boolean getDifferentiabilityOfOutput(long ind) {
        return this.function.is_diff_out(ind);
    }

    public BooleanVector getDifferentiabilityOfInput() {
        return new BooleanVector(this.function.is_diff_in());
    }

    public BooleanVector getDifferentiabilityOfOutput() {
        return new BooleanVector(this.function.is_diff_out());
    }

    public FunctionWrapper factory(String name, StringVector sIn, StringVector sOut, MapStringToStringVector aux, Dict opts) {
        return new FunctionWrapper(this.function.factory(name, sIn.getCasADiObject(), sOut.getCasADiObject(), aux.getCasADiObject(), opts));
    }

    public FunctionWrapper factory(String name, StringVector sIn, StringVector sOut, MapStringToStringVector aux) {
        return new FunctionWrapper(this.function.factory(name, sIn.getCasADiObject(), sOut.getCasADiObject(), aux.getCasADiObject()));
    }

    public FunctionWrapper factory(String name, StringVector sIn, StringVector sOut) {
        return new FunctionWrapper(this.function.factory(name, sIn.getCasADiObject(), sOut.getCasADiObject()));
    }

    public FunctionWrapper getOracle() {
        return new FunctionWrapper(this.function.oracle());
    }

    public FunctionWrapper wrap() {
        return new FunctionWrapper(this.function.wrap());
    }

    public FunctionWrapper wrapAsNeeded(Dict opts) {
        return new FunctionWrapper(this.function.wrap_as_needed(opts));
    }

    public BooleanVector whichDepends(String sIn, StringVector sOut, long order, boolean tr) {
        return new BooleanVector(this.function.which_depends(sIn, sOut.getCasADiObject(), order, tr));
    }

    public BooleanVector whichDepends(String sIn, StringVector sOut, long order) {
        return new BooleanVector(this.function.which_depends(sIn, sOut.getCasADiObject(), order));
    }

    public BooleanVector whichDepends(String sIn, StringVector sOut) {
        return new BooleanVector(this.function.which_depends(sIn, sOut.getCasADiObject()));
    }

    public void printDimensions() {
        this.function.print_dimensions();
    }

    public void printOptions() {
        this.function.print_options();
    }

    public void printOption(String name) {
        this.function.print_option(name);
    }

    public boolean hasOption(String optionName) {
        return this.function.has_option(optionName);
    }

    public void changeOption(String optionName, GenericType optionValue) {
        this.function.change_option(optionName, optionValue);
    }

    public boolean usesOption() {
        return this.function.uses_output();
    }

    public FunctionWrapper jacobian() {
        return new FunctionWrapper(this.function.jacobian());
    }

    public DMVector call(DMVector arg, boolean alwaysInline, boolean neverInline) {
        StdVectorDM output = new StdVectorDM();
        this.function.call(arg.getCasADiObject(), output, alwaysInline, neverInline);
        return new DMVector(output);
    }

    public DMVector call(DMVector arg, boolean alwaysInline) {
        StdVectorDM output = new StdVectorDM();
        this.function.call(arg.getCasADiObject(), output, alwaysInline);
        return new DMVector(output);
    }

    public DMVector call(DMVector arg) {
        StdVectorDM output = new StdVectorDM();
        this.function.call(arg.getCasADiObject(), output);
        return new DMVector(output);
    }

    public DMVector call(DMWrapper... args) {
        StdVectorDM input = new StdVectorDM();
        Arrays.stream(args).forEach(dmValue -> input.add(dmValue.getCasADiObject()));
        StdVectorDM output = new StdVectorDM();
        this.function.call(input, output);
        return new DMVector(output);
    }

    public SXVector call(SXVector arg, boolean alwaysInline, boolean neverInline) {
        StdVectorSX output = new StdVectorSX();
        this.function.call(arg.getCasADiObject(), output, alwaysInline, neverInline);
        return new SXVector(output);
    }

    public SXVector call(SXVector arg, boolean alwaysInline) {
        StdVectorSX output = new StdVectorSX();
        this.function.call(arg.getCasADiObject(), output, alwaysInline);
        return new SXVector(output);
    }

    public SXVector call(SXVector arg) {
        StdVectorSX output = new StdVectorSX();
        this.function.call(arg.getCasADiObject(), output);
        return new SXVector(output);
    }

    public SXVector call(SXWrapper... args) {
        StdVectorSX input = new StdVectorSX();
        Arrays.stream(args).forEach(sxValue -> input.add(sxValue.getCasADiObject()));
        StdVectorSX output = new StdVectorSX();
        this.function.call(input, output);
        return new SXVector(output);
    }

    public MXVector call(MXVector arg, boolean alwaysInline, boolean neverInline) {
        StdVectorMX output = new StdVectorMX();
        this.function.call(arg.getCasADiObject(), output, alwaysInline, neverInline);
        return new MXVector(output);
    }

    public MXVector call(MXVector arg, boolean alwaysInline) {
        StdVectorMX output = new StdVectorMX();
        this.function.call(arg.getCasADiObject(), output, alwaysInline);
        return new MXVector(output);
    }

    public MXVector call(MXVector arg) {
        StdVectorMX output = new StdVectorMX();
        this.function.call(arg.getCasADiObject(), output);
        return new MXVector(output);
    }

    public MXVector call(MXWrapper... args) {
        StdVectorMX input = new StdVectorMX();
        Arrays.stream(args).forEach(mxValue -> input.add(mxValue.getCasADiObject()));
        StdVectorMX output = new StdVectorMX();
        this.function.call(input, output);
        return new MXVector(output);
    }

    public MapStringToDMWrapper call(MapStringToDMWrapper arg, boolean alwaysInline, boolean neverInline) {
        StdMapStringToDM output = new StdMapStringToDM();
        this.function.call(arg.getCasADiObject(), output, alwaysInline, neverInline);
        return new MapStringToDMWrapper(output);
    }

    public MapStringToDMWrapper call(MapStringToDMWrapper arg, boolean alwaysInline) {
        StdMapStringToDM output = new StdMapStringToDM();
        this.function.call(arg.getCasADiObject(), output, alwaysInline);
        return new MapStringToDMWrapper(output);
    }

    public MapStringToDMWrapper call(MapStringToDMWrapper arg) {
        StdMapStringToDM output = new StdMapStringToDM();
        this.function.call(arg.getCasADiObject(), output);
        return new MapStringToDMWrapper(output);
    }

    public MapStringToSXWrapper call(MapStringToSXWrapper arg, boolean alwaysInline, boolean neverInline) {
        StdMapStringToSX output = new StdMapStringToSX();
        this.function.call(arg.getCasADiObject(), output, alwaysInline, neverInline);
        return new MapStringToSXWrapper(output);
    }

    public MapStringToSXWrapper call(MapStringToSXWrapper arg, boolean alwaysInline) {
        StdMapStringToSX output = new StdMapStringToSX();
        this.function.call(arg.getCasADiObject(), output, alwaysInline);
        return new MapStringToSXWrapper(output);
    }

    public MapStringToSXWrapper call(MapStringToSXWrapper arg) {
        StdMapStringToSX output = new StdMapStringToSX();
        this.function.call(arg.getCasADiObject(), output);
        return new MapStringToSXWrapper(output);
    }

    public MapStringToMXWrapper call(MapStringToMXWrapper arg, boolean alwaysInline, boolean neverInline) {
        StdMapStringToMX output = new StdMapStringToMX();
        this.function.call(arg.getCasADiObject(), output, alwaysInline, neverInline);
        return new MapStringToMXWrapper(output);
    }

    public MapStringToMXWrapper call(MapStringToMXWrapper arg, boolean alwaysInline) {
        StdMapStringToMX output = new StdMapStringToMX();
        this.function.call(arg.getCasADiObject(), output, alwaysInline);
        return new MapStringToMXWrapper(output);
    }

    public MapStringToMXWrapper call(MapStringToMXWrapper arg) {
        StdMapStringToMX output = new StdMapStringToMX();
        this.function.call(arg.getCasADiObject(), output);
        return new MapStringToMXWrapper(output);
    }

    public MXVector mapsum(MXVector x, String parallelization) {
        return new MXVector(this.function.mapsum(x.getCasADiObject(), parallelization));
    }

    public MXVector mapsum(MXVector x) {
        return new MXVector(this.function.mapsum(x.getCasADiObject()));
    }

    public MXVector mapsum(MXWrapper... args) {
        StdVectorMX x = new StdVectorMX();
        Arrays.stream(args).forEach(mxValue -> x.add(mxValue.getCasADiObject()));
        return new MXVector(this.function.mapsum(x));
    }

    public FunctionWrapper mapaccum(String name, long N, Dict opts) {
        return new FunctionWrapper(this.function.mapaccum(name, N, opts));
    }

    public FunctionWrapper mapaccum(String name, long N) {
        return new FunctionWrapper(this.function.mapaccum(name, N));
    }

    public FunctionWrapper mapaccum(String name, long N, long nAccum, Dict opts) {
        return new FunctionWrapper(this.function.mapaccum(name, N, nAccum, opts));
    }

    public FunctionWrapper mapaccum(String name, long N, long nAccum) {
        return new FunctionWrapper(this.function.mapaccum(name, N, nAccum));
    }

    public FunctionWrapper mapaccum(String name, long n, IntegerVector accumIn, IntegerVector accumOut, Dict opts) {
        return new FunctionWrapper(this.function.mapaccum(name, n, accumIn.getCasADiObject(), accumOut.getCasADiObject(), opts));
    }

    public FunctionWrapper mapaccum(String name, long n, IntegerVector accumIn, IntegerVector accumOut) {
        return new FunctionWrapper(this.function.mapaccum(name, n, accumIn.getCasADiObject(), accumOut.getCasADiObject()));
    }

    public FunctionWrapper mapaccum(String name, long n, StringVector accumIn, StringVector accumOut, Dict opts) {
        return new FunctionWrapper(this.function.mapaccum(name, n, accumIn.getCasADiObject(), accumOut.getCasADiObject(), opts));
    }

    public FunctionWrapper mapaccum(String name, long n, StringVector accumIn, StringVector accumOut) {
        return new FunctionWrapper(this.function.mapaccum(name, n, accumIn.getCasADiObject(), accumOut.getCasADiObject()));
    }

    public FunctionWrapper mapaccum(long N, Dict opts) {
        return new FunctionWrapper(this.function.mapaccum(N, opts));
    }

    public FunctionWrapper mapaccum(long N) {
        return new FunctionWrapper(this.function.mapaccum(N));
    }

    public FunctionWrapper fold(long N, Dict opts) {
        return new FunctionWrapper(this.function.fold(N, opts));
    }

    public FunctionWrapper fold(long N) {
        return new FunctionWrapper(this.function.fold(N));
    }

    public FunctionWrapper map(long n, String parallelization) {
        return new FunctionWrapper(this.function.map(n, parallelization));
    }

    public FunctionWrapper map(long n) {
        return new FunctionWrapper(this.function.map(n));
    }

    public FunctionWrapper map(long n, String parallelization, long maxNumThreads) {
        return new FunctionWrapper(this.function.map(n, parallelization, maxNumThreads));
    }

    public FunctionWrapper map(String name, String parallelization, long n, IntegerVector reduceIn, IntegerVector reduceOut, Dict opts) {
        return new FunctionWrapper(this.function.map(name, parallelization, n, reduceIn.getCasADiObject(), reduceOut.getCasADiObject(), opts));
    }

    public FunctionWrapper map(String name, String parallelization, long n, IntegerVector reduceIn, IntegerVector reduceOut) {
        return new FunctionWrapper(this.function.map(name, parallelization, n, reduceIn.getCasADiObject(), reduceOut.getCasADiObject()));
    }

    public FunctionWrapper map(String name, String parallelization, long n, StringVector reduceIn, StringVector reduceOut, Dict opts) {
        return new FunctionWrapper(this.function.map(name, parallelization, n, reduceIn.getCasADiObject(), reduceOut.getCasADiObject(), opts));
    }

    public FunctionWrapper map(String name, String parallelization, long n, StringVector reduceIn, StringVector reduceOut) {
        return new FunctionWrapper(this.function.map(name, parallelization, n, reduceIn.getCasADiObject(), reduceOut.getCasADiObject()));
    }

    public FunctionWrapper map(long n, BooleanVector reduceIn, BooleanVector reduceOut, Dict opts) {
        return new FunctionWrapper(this.function.map(n, reduceIn.getCasADiObject(), reduceOut.getCasADiObject(), opts));
    }

    public FunctionWrapper map(long n, BooleanVector reduceIn, BooleanVector reduceOut) {
        return new FunctionWrapper(this.function.map(n, reduceIn.getCasADiObject(), reduceOut.getCasADiObject()));
    }

    public FunctionWrapper map(long n, BooleanVector reduceIn) {
        return new FunctionWrapper(this.function.map(n, reduceIn.getCasADiObject()));
    }

    public FunctionWrapper slice(String name, IntegerVector orderIn, IntegerVector orderOut, Dict opts) {
        return new FunctionWrapper(this.function.slice(name, orderIn.getCasADiObject(), orderOut.getCasADiObject(), opts));
    }

    public FunctionWrapper slice(String name, IntegerVector orderIn, IntegerVector orderOut) {
        return new FunctionWrapper(this.function.slice(name, orderIn.getCasADiObject(), orderOut.getCasADiObject()));
    }

    public FunctionWrapper forward(long numForwardDerivatives) {
        return new FunctionWrapper(this.function.forward(numForwardDerivatives));
    }

    public FunctionWrapper reverse(long numAdjointDerivatives ) {
        return new FunctionWrapper(this.function.reverse(numAdjointDerivatives));
    }

    public SparsityVector getJacobianSparsity(boolean compact) {
        return new SparsityVector(this.function.jac_sparsity(compact));
    }

    public SparsityVector getJacobianSparsity() {
        return new SparsityVector(this.function.jac_sparsity());
    }

    public Sparsity getJacobianSparsity(long outputIndex, long inputIndex, boolean compact) {
        return new Sparsity(this.function.jac_sparsity(outputIndex, inputIndex, compact));
    }

    public Sparsity getJacobianSparsity(long outputIndex, long inputIndex) {
        return new Sparsity(this.function.jac_sparsity(outputIndex, inputIndex));
    }

    public String generate(String fileName, Dict opts) {
        return this.function.generate(fileName, opts);
    }

    public String generate(String fileName) {
        return this.function.generate(fileName);
    }

    public String generate(Dict opts) {
        return this.function.generate(opts);
    }

    public String generate() {
        return this.function.generate();
    }

    public String generateDependencies(String fileName, Dict opts) {
        return this.function.generate_dependencies(fileName, opts);
    }

    public String generateDependencies(String fileName) {
        return this.function.generate_dependencies(fileName);
    }

    public void generateInputFile(String fileName, DMVector arg) {
        this.function.generate_in(fileName, arg.getCasADiObject());
    }

    public void generateInputFile(String fileName) {
        this.function.generate_in(fileName);
    }

    public void generateOutputFile(String fileName, DMVector arg) {
        this.function.generate_out(fileName, arg.getCasADiObject());
    }

    public DMVector generateOutputFile(String fileName) {
        return new DMVector(this.function.generate_out(fileName));
    }

    public void exportCode(String language, String fileName, Dict opts) {
        this.function.export_code(language, fileName, opts);
    }

    public void exportCode(String language, String fileName) {
        this.function.export_code(language, fileName);
    }

    public String serialize(Dict opts) {
        return this.function.serialize(opts);
    }

    public String serialize() {
        return this.function.serialize();
    }

    public void save(String fileName, Dict opts) {
        this.function.save(fileName, opts);
    }

    public void save(String fileName) {
        this.function.save(fileName);
    }

    public void exportCode(String language, Dict opts) {
        this.function.export_code(language, opts);
    }

    public void exportCode(String language) {
        this.function.export_code(language);
    }

    public Dict getStatistics(int mem) {
        return this.function.stats(mem);
    }

    public Dict getStatistics() {
        return this.function.stats();
    }

    public SXWrapper getSymbolicExpressionByIndex(long inputIndex) {
        return new SXWrapper(this.function.sx_in(inputIndex));
    }

    public SXWrapper getSymbolicExpressionByName(String inputName) {
        return new SXWrapper(this.function.sx_in(inputName));
    }

    public SXVector getAllSymbolicExpressions() {
        return new SXVector(this.function.sx_in());
    }

    public MXWrapper getMatrixExpressionByIndex(long inputIndex) {
        return new MXWrapper(this.function.mx_in(inputIndex));
    }

    public MXWrapper getMatrixExpressionByName(String inputName) {
        return new MXWrapper(this.function.mx_in(inputName));
    }

    public MXVector getAllMatrixExpressions() {
        return new MXVector(this.function.mx_in());
    }

    public SXWrapper getSymbolicOutputByIndex(long outputIndex) {
        return new SXWrapper(this.function.sx_out(outputIndex));
    }

    public SXWrapper getSymbolicOutputByName(String outputName) {
        return new SXWrapper(this.function.sx_out(outputName));
    }

    public SXVector getAllSymbolicOutputs() {
        return new SXVector(this.function.sx_out());
    }

    public MXWrapper getMatrixOutputByIndex(long outputIndex) {
        return new MXWrapper(this.function.mx_out(outputIndex));
    }

    public MXWrapper getMatrixOutputByName(String outputName) {
        return new MXWrapper(this.function.mx_out(outputName));
    }

    public MXVector getAllMatrixOutputs() {
        return new MXVector(this.function.mx_out());
    }

    public DoubleVector convertNonZerosFromInput(DMVector arg) {
        return new DoubleVector(this.function.nz_from_in(arg.getCasADiObject()));
    }

    public DoubleVector convertNonZerosFromOutput(DMVector arg) {
        return new DoubleVector(this.function.nz_from_out(arg.getCasADiObject()));
    }

    public DMVector convertNonZerosToInput(DoubleVector arg) {
        return new DMVector(this.function.nz_to_in(arg.getCasADiObject()));
    }

    public DMVector convertNonZerosToOutput(DoubleVector arg) {
        return new DMVector(this.function.nz_to_out(arg.getCasADiObject()));
    }

    public MapStringToDMWrapper convertDMVectorToMap(DMVector arg) {
        return new MapStringToDMWrapper(this.function.convert_in(arg.getCasADiObject()));
    }

    public DMVector convertMapToDMVector(MapStringToDMWrapper arg) {
        return new DMVector(this.function.convert_in(arg.getCasADiObject()));
    }

    public MapStringToDMWrapper convertDMVectorToMapOutput(DMVector arg) {
        return new MapStringToDMWrapper(this.function.convert_out(arg.getCasADiObject()));
    }

    public DMVector convertMapToDMVectorOutput(MapStringToDMWrapper arg) {
        return new DMVector(this.function.convert_out(arg.getCasADiObject()));
    }

    public MapStringToSXWrapper convertSXVectorToMap(SXVector arg) {
        return new MapStringToSXWrapper(this.function.convert_in(arg.getCasADiObject()));
    }

    public SXVector convertMapToSXVector(MapStringToSXWrapper arg) {
        return new SXVector(this.function.convert_in(arg.getCasADiObject()));
    }

    public MapStringToSXWrapper convertSXVectorToMapOutput(SXVector arg) {
        return new MapStringToSXWrapper(this.function.convert_out(arg.getCasADiObject()));
    }

    public SXVector convertMapToSXVectorOutput(MapStringToSXWrapper arg) {
        return new SXVector(this.function.convert_out(arg.getCasADiObject()));
    }

    public MapStringToMXWrapper convertMXVectorToMap(MXVector arg) {
        return new MapStringToMXWrapper(this.function.convert_in(arg.getCasADiObject()));
    }

    public MXVector convertMapToMXVector(MapStringToMXWrapper arg) {
        return new MXVector(this.function.convert_in(arg.getCasADiObject()));
    }

    public MapStringToMXWrapper convertMXVectorToMapOutput(MXVector arg) {
        return new MapStringToMXWrapper(this.function.convert_out(arg.getCasADiObject()));
    }

    public MXVector convertMapToMXVectorOutput(MapStringToMXWrapper arg) {
        return new MXVector(this.function.convert_out(arg.getCasADiObject()));
    }

    public boolean hasFreeVariables() {
        return this.function.has_free();
    }

    public StringVector getFreeVariablesAsString() {
        return new StringVector(this.function.get_free());
    }

    public SXVector getAllFreeVariablesSX() {
        return new SXVector(this.function.free_sx());
    }

    public MXVector getAllFreeVariablesMX() {
        return new MXVector(this.function.free_mx());
    }
    public void generateLiftedFunctions(FunctionWrapper arg0, FunctionWrapper arg1) {
        this.function.generate_lifted(arg0.getCasADiObject(), arg1.getCasADiObject());
    }

    public long getNumberOfNodes() {
        return this.function.n_nodes();
    }

    public long getNumberOfInstructions() {
        return this.function.n_instructions();
    }

    public long getInstructionId(long k) {
        return this.function.instruction_id(k);
    }

    public IntegerVector getInstructionInputLocations(long k) {
        return new IntegerVector(this.function.instruction_input(k));
    }

    public double getInstructionConstant(long k) {
        return this.function.instruction_constant(k);
    }

    public IntegerVector getInstructionOutputLocations(long k) {
        return new IntegerVector(this.function.instruction_output(k));
    }

    public MXWrapper getInstructionMXNode(long k) {
        return new MXWrapper(this.function.instruction_MX(k));
    }

    public SXWrapper getAllInstructionsSX() {
        return new SXWrapper(this.function.instructions_sx());
    }

    public boolean hasSeedForwardPropagation() {
        return this.function.has_spfwd();
    }

    public boolean hasSeedReversePropagation() {
        return this.function.has_sprev();
    }

    public long getRequiredArgLength() {
        return this.function.sz_arg();
    }

    public long getRequiredResLength() {
        return this.function.sz_res();
    }

    public long getRequiredIwLength() {
        return this.function.sz_iw();
    }

    public long getRequiredWLength() {
        return this.function.sz_w();
    }

    public String name() {
        return this.function.name();
    }

    public boolean isOfTypeWithRecursion(String type, boolean recursive) {
        return this.function.is_a(type, recursive);
    }

    public boolean isOfType(String type) {
        return this.function.is_a(type);
    }

    public void assertSizeInDimension(long i, long nrow, long ncol) {
        this.function.assert_size_in(i, nrow, ncol);
    }

    public void assertSizeOutDimension(long i, long nrow, long ncol) {
        this.function.assert_size_out(i, nrow, ncol);
    }

    public void assertSparsityOut(long i, Sparsity sp, long n, boolean allowAllZeroSparse) {
        this.function.assert_sparsity_out(i, sp, n, allowAllZeroSparse);
    }

    public void assertSparsityOut(long i, Sparsity sp, long n) {
        this.function.assert_sparsity_out(i, sp, n);
    }

    public void assertSparsityOut(long i, Sparsity sp) {
        this.function.assert_sparsity_out(i, sp);
    }

    public long checkoutMemoryObject() {
        return this.function.checkout();
    }

    public void releaseMemoryObject(int mem) {
        this.function.release(mem);
    }

    public Dict getAllFunctionsInCache() {
        return this.function.cache();
    }

    public StringVector getAllFunctions() {
        return new StringVector(this.function.get_function());
    }

    public FunctionWrapper getDependencyFunction(String name) {
        return new FunctionWrapper(this.function.get_function(name));
    }

    public boolean containsFunction(String functionName) {
        return this.function.has_function(functionName);
    }

    public FunctionVector getAllEmbeddedFunctionsWithDepth(long maxDepth) {
        return new FunctionVector(this.function.find_functions(maxDepth));
    }

    public FunctionVector getAllEmbeddedFunctions() {
        return new FunctionVector(this.function.find_functions());
    }

    public FunctionWrapper getEmbeddedFunctionByNameWithDepth(String name, long maxDepth) {
        return new FunctionWrapper(this.function.find_function(name, maxDepth));
    }

    public FunctionWrapper getEmbeddedFunctionByName(String name) {
        return new FunctionWrapper(this.function.find_function(name));
    }

    public Dict info() {
        return this.function.info();
    }

    public String getClassName() {
        return this.function.class_name();
    }

    public String toString(boolean more) {
        return this.function.toString(more);
    }

    public String toString() {
        return this.function.toString();
    }

    public boolean isNull() {
        return this.function.is_null();
    }

    public long getHash() {
        return this.function.__hash__();
    }

    public Function getCasADiObject() {
        return this.function;
    }

    public FunctionVector toVector() {
        return new FunctionVector(this);
    }

}
