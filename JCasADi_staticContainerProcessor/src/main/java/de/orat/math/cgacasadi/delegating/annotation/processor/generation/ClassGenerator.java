package de.orat.math.cgacasadi.delegating.annotation.processor.generation;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import static de.orat.math.cgacasadi.delegating.annotation.processor.generation.Classes.T_Override;
import de.orat.math.cgacasadi.delegating.annotation.processor.representation.Clazz;
import de.orat.math.cgacasadi.delegating.annotation.processor.representation.Method;
import de.orat.math.cgacasadi.delegating.annotation.processor.representation.Parameter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;

final class ClassGenerator {

    private ClassGenerator() {

    }

    protected static void generate(Clazz c, Filer filer) throws IOException, ClassNotFoundException {
        String packageName = String.format("%s.gen", c.enclosingQualifiedName);
        String className = "Delegating" + c.simpleName;
        ClassName genClass = ClassName.get(packageName, className);
        ClassName T_c = ClassName.get(c.enclosingQualifiedName, c.simpleName);

        TypeName annotatedToType = TypeName.get(c.annotatedTo);
        FieldSpec delegate = FieldSpec.builder(annotatedToType, "delegate", Modifier.PROTECTED, Modifier.FINAL)
            .build();

        MethodSpec constructor1 = ClassGenerator.constructor1(annotatedToType);

        MethodSpec createMethod = MethodSpec.methodBuilder("create")
            .addModifiers(Modifier.PROTECTED, Modifier.ABSTRACT)
            .addParameter(annotatedToType, "delegate")
            .returns(T_c)
            .build();

        List<MethodSpec> methods = new ArrayList<>(c.methods.size() + 1);
        methods.add(createMethod);
        for (Method m : c.methods) {
            MethodSpec delegateMethod = delegateMethod(m, T_c);
            methods.add(delegateMethod);
        }

        TypeSpec genClassSpec = TypeSpec.classBuilder(genClass)
            .addJavadoc("@see $L", T_c.canonicalName())
            .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
            .addSuperinterfaces(c.commonSuperTypes.stream().map(TypeName::get).toList())
            .addField(delegate)
            .addMethod(constructor1)
            .addMethods(methods)
            .build();

        JavaFile javaFile = JavaFile.builder(packageName, genClassSpec)
            .skipJavaLangImports(true)
            .indent("\t")
            .build();

        javaFile.writeTo(filer);
    }

    private static MethodSpec constructor1(TypeName annotatedToType) throws ClassNotFoundException {
        MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder();

        // Signature
        constructorBuilder
            .addModifiers(Modifier.PROTECTED)
            .addParameter(annotatedToType, "delegate");

        // Body
        constructorBuilder
            .addStatement("this.delegate = delegate");

        return constructorBuilder.build();
    }

    private static MethodSpec delegateMethod(Method m, ClassName T_c) {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(m.name);

        TypeName T_ret = TypeName.get(m.returnType);

        // Signature
        methodBuilder
            .addJavadoc("@see $L#$L", m.enclosingType, m.name)
            .addAnnotation(T_Override)
            .addModifiers(m.modifiers)
            .returns(T_ret);

        for (Parameter parameter : m.parameters) {
            TypeName T_param = TypeName.get(parameter.type);
            methodBuilder
                .addParameter(T_param, parameter.identifier);
        }

        // Body
        String annotatedClassName = T_c.canonicalName();

        List<String> args = new ArrayList<>(m.parameters.size());
        for (var param : m.parameters) {
            String arg;
            if (param.type.toString().equals(annotatedClassName)) {
                arg = String.format("%s.delegate", param.identifier);
            } else {
                arg = param.identifier;
            }
            args.add(arg);
        }
        String argsString = args.stream().collect(Collectors.joining(", "));

        if (m.returnType.toString().equals(annotatedClassName)) {
            methodBuilder.addStatement("return create(this.delegate.$L($L))", m.name, argsString);
        } else {
            methodBuilder.addStatement("return this.delegate.$L($L)", m.name, argsString);
        }

        //
        return methodBuilder.build();
    }
}
