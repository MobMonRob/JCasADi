package de.orat.math.cgacasadi.delegating.annotation.processor.generation;

import de.orat.math.cgacasadi.delegating.annotation.processor.representation.Clazz;
import java.io.IOException;
import java.util.List;
import javax.annotation.processing.Filer;

public final class ClassesGenerator {

    private ClassesGenerator() {

    }

    public static void generate(List<Clazz> clazzs, Filer filer) throws IOException, ClassNotFoundException {
        for (Clazz c : clazzs) {
            ClassGenerator.generate(c, filer);
        }
    }
}
