package de.dhbw.rahmlab.casadi.api.core.wrapper.function;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dict.Dictionary;
import de.dhbw.rahmlab.casadi.impl.casadi.CodeGenerator;
import de.dhbw.rahmlab.casadi.impl.casadi.GenericType;
import de.dhbw.rahmlab.casadi.impl.std.Dict;

public class CodeGeneratorWrapper {

    private final CodeGenerator codeGenerator;

    public CodeGeneratorWrapper(String fileName, Dictionary opts) {
        this.codeGenerator = new CodeGenerator(fileName, opts.getCasADiObject());
    }

    public CodeGeneratorWrapper(String fileName) {
        this.codeGenerator = new CodeGenerator(fileName);
    }

    public static CodeGeneratorWrapper withHeader(String fileName) {
        Dict options = new Dict();
        options.put("with_header", new GenericType(true));
        return new CodeGeneratorWrapper(fileName, new Dictionary(options));
    }

    public void add(FunctionWrapper f) {
        this.codeGenerator.add(f.getCasADiObject());
    }

    public void add(FunctionWrapper f, boolean withJacobianSparsity) {
        this.codeGenerator.add(f.getCasADiObject(), withJacobianSparsity);
    }

    public void addInclude(String include) {
        this.codeGenerator.add_include(include);
    }

    public String generate() {
        return this.codeGenerator.generate();
    }

    public String generate(String prefix) {
        return this.codeGenerator.generate(prefix);
    }

    public String dump() {
        return this.codeGenerator.dump();
    }

    public CodeGenerator getCasADiObject() {
        return this.codeGenerator;
    }
}