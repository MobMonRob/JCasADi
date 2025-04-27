package de.dhbw.rahmlab.casadi.api;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class Trigometry {
    
/* from  www.java2s.com
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

    /**
     * Calculates inverse hyperbolic tangent of a {@code double} value.
     * <p>
     * Special cases:
     * <ul>
     *    <li>If the argument is NaN, then the result is NaN.
     *    <li>If the argument is zero, then the result is a zero with the same sign as the argument.
     *    <li>If the argument is +1, then the result is positive infinity.
     *    <li>If the argument is -1, then the result is negative infinity.
     *    <li>If the argument's absolute value is greater than 1, then the result is NaN.
     * </ul>
     */
    public static double atanh(double a) {
        final double mult;
        // check the sign bit of the raw representation to handle -0
        if (Double.doubleToRawLongBits(a) < 0) {
            a = Math.abs(a);
            mult = -0.5d;
        } else {
            mult = 0.5d;
        }
        return mult * Math.log((1.0d + a) / (1.0d - a));
    }

    /**
     * Returns {@code x <= 0 ? 0 : Math.floor(Math.log(x) / Math.log(base))}
     * @param base must be {@code > 1}
     */
    public static int log(long x, int base) {
        if (base <= 1) {
            throw new IllegalArgumentException("base must be > 1");
        }
        int ret = 0;
        while (x >= base) {
            x /= base;
            ret++;
        }
        return ret;
    }

    /**
     * Calculates logarithm in a given base with doubles.
     */
    public static double log(double base, double x) {
        return Math.log(x) / Math.log(base);
    }
}
