package de.orat.math.cgacasadi.delegating.annotation.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Doc:
 * https://github.com/orat/CGACasADi/blob/master/CGACasADi_NumericMultivectorDelegatingProcessor/README.md
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface GenerateDelegate {

    Class<?> to();
}
