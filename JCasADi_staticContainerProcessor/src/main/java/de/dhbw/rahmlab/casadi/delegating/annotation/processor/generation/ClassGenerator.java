package de.dhbw.rahmlab.casadi.delegating.annotation.processor.generation;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import de.dhbw.rahmlab.casadi.delegating.annotation.processor.representation.Clazz;
import de.dhbw.rahmlab.casadi.delegating.annotation.processor.representation.Method;
import de.dhbw.rahmlab.casadi.delegating.annotation.processor.representation.Parameter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;

final class ClassGenerator {

    private ClassGenerator() {

    }

    protected static void generate(Clazz c, Filer filer) throws IOException, ClassNotFoundException {
        String packageName = String.format("%s", c.enclosingQualifiedName);
        String className = c.simpleName;
        ClassName genClass = ClassName.get(packageName, className);
        ClassName T_at = ClassName.get(c.annotatedType);

        List<MethodSpec> methods = new ArrayList<>(c.methods.size() + 1);
        for (Method m : c.methods) {
            MethodSpec delegateMethod = delegateMethod(m);
            methods.add(delegateMethod);
        }

        TypeSpec genClassSpec = TypeSpec.classBuilder(genClass)
            .addJavadoc("@see $L", T_at.canonicalName())
            .addModifiers(Modifier.PUBLIC)
            .addMethods(methods)
            .build();

        JavaFile javaFile = JavaFile.builder(packageName, genClassSpec)
            .skipJavaLangImports(true)
            .indent("\t")
            .build();

        javaFile.writeTo(filer);
    }

    private static MethodSpec delegateMethod(Method m) {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(m.name);

        TypeName T_ret = TypeName.get(m.returnType);

        // Signature
        methodBuilder
            .addJavadoc("@see $L#$L", m.enclosingType, m.name)
            .addModifiers(m.modifiers)
            .returns(T_ret);

        for (Parameter parameter : m.parameters) {
            TypeName T_param = TypeName.get(parameter.type);
            methodBuilder
                .addParameter(T_param, parameter.identifier);
        }

        // Body
        List<String> args = new ArrayList<>(m.parameters.size());
        for (var param : m.parameters) {
            String arg;
            arg = param.identifier;
            args.add(arg);
        }
        String argsString = args.stream().collect(Collectors.joining(", "));

        if (m.returnType.getKind().equals(TypeKind.VOID)) {
            methodBuilder.addStatement("$T.$L($L)", m.enclosingType, m.name, argsString);
        } else {
            methodBuilder.addStatement("return $T.$L($L)", m.enclosingType, m.name, argsString);
        }

        //
        return methodBuilder.build();
    }
}
