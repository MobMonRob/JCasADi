package de.dhbw.rahmlab.casadi.api.core.variables;

import de.dhbw.rahmlab.casadi.api.MX;

public interface Variable {

        /**
         * Returns the name of the variable
         * @return name of the variable
         */
        String getName();

        /**
         * Sets the name of the variable
         * @param name new name of the variable
         */
        void setName(String name);

        /**
         * Returns the current value of the variable
         * @return current value of the variable
         */
        double getValue();

        /**
         * Sets the value of the variable
         * @param value new value of the variable
         */
        void setValue(double value);

        /**
         * Returns the symbolic representation of the variable.
         * This representation can be used in mathematical expressions.
         * @return symbolic representation of the variables
         */
        MX getSymbolicRepresentation();

        /**
         * Sets the symbolic representation of the variables
         * @param expression new symbolic representation of the variables
         */
        void setSymbolicRepresentation(MX expression);

        /**
         * Returns the lower bound of the variable
         * @return lower bound of the variable
         */
        double getLowerBound();

        /**
         * Sets the lower bound of the variable
         * @param lowerBound new lower bound of the variable
         */
        void setLowerBound(double lowerBound);

        /**
         * Returns the upper limit of the variable
         * @return upper bound of the variable
         */
        double getUpperBound();

        /**
         * Sets the upper limit of the variables
         * @param upperBound new upper bound of the variable
         */
        void setUpperBound(double upperBound);

}
