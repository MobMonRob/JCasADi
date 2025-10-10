package de.dhbw.rahmlab.casadi.delegating.annotation.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Repeatable(GenerateDelegates.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface GenerateDelegate {

    String name();
    Class<?>[] of();
}
