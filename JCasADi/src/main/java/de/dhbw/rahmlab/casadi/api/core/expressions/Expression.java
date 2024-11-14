package de.dhbw.rahmlab.casadi.api.core.expressions;

public interface Expression<T> {

    /**
     * Returns the symbolic representation of the expression
     * @return the symbolic representation of the expression
     */
    T getSymbolicExpression();

    /**
     * Sets the symbolic representation of the expression
     * @param symbolicExpression the new symbolic representation of the expression
     */
    void setSymbolicExpression(T symbolicExpression);

    /**
     * Adds another expression to this expression
     * @param other the other expression
     * @return a new expression representing the sum of the two expressions
     */
    Expression<T> add(Expression<T> other);

    /**
     * Subtracts another expression from this expression
     * @param other the other expression
     * @return a new expression representing the difference of the two expressions
     */
    Expression<T> subtract(Expression<T> other);

    /**
     * Multiplies this expression with another expression
     * @param other the other expression
     * @return a new expression representing the product of the two expressions
     */
    Expression<T> multiply(Expression<T> other);

    /**
     * Divides this expression by another expression
     * @param other the other expression
     * @return a new expression representing the quotient of the two expressions
     */
    Expression<T> divide(Expression<T> other);

    /**
     * Returns the value of the expression for given variable values
     * @param values the values of the variables
     * @return the value of the expression
     */
    double evaluate(double... values);

}
