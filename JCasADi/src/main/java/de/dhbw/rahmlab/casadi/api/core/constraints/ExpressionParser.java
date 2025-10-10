package de.dhbw.rahmlab.casadi.api.core.constraints;

import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for parsing mathematical expressions and constraints.
 * Provides methods to convert infix expressions to MXWrapper objects and evaluate constraints.
 */
public class ExpressionParser {

    /** Regular expression pattern to tokenize numbers, variables, and operators. */
    private static final Pattern TOKEN = Pattern.compile(
            "\\s*(?<num>\\d+(?:\\.\\d*)?)" +   // Numbers
                    "|\\s*(?<var>[a-zA-Z_]\\w*)"       +   // Variables
                    "|\\s*(?<op>[+\\-*/^()])"             // Operators and parentheses
    );

    /**
     * Main method to convert an infix expression string into an MXWrapper object.
     *
     * @param expr the infix expression string
     * @return an MXWrapper representing the parsed expression
     */
    public static MXWrapper parse(String expr) {
        List<String> tokens = tokenize(expr);
        List<String> rpn    = toRPN(tokens);
        return evalRPN(rpn);
    }

    /**
     * Parses a constraint expression string and returns an AbstractConstraint object.
     *
     * @param expr the constraint expression string
     * @return an AbstractConstraint representing the parsed constraint
     */
    public static AbstractConstraint parseConstraint(String expr) {
        List<String> tokens = tokenize(expr);
        List<String> rpn    = toRPN(tokens);
        return evalConstraintRPN(rpn);
    }

    /**
     * Tokenizes the input expression string into a list of tokens.
     *
     * @param expr the expression string
     * @return a list of tokens extracted from the expression
     */
    private static List<String> tokenize(String expr) {
        Matcher m = TOKEN.matcher(expr);
        List<String> toks = new ArrayList<>();
        while (m.find()) {
            if (m.group("num") != null)  toks.add(m.group("num"));
            if (m.group("var") != null)  toks.add(m.group("var"));
            if (m.group("op")  != null)  toks.add(m.group("op"));
        }
        return toks;
    }

    /**
     * Converts a list of tokens from infix notation to Reverse Polish Notation (RPN) using the Shunting-Yard algorithm.
     *
     * @param tokens the list of tokens in infix notation
     * @return a list of tokens in RPN
     */
    private static List<String> toRPN(List<String> tokens) {
        List<String> out = new ArrayList<>();
        Deque<String>  ops = new ArrayDeque<>();
        Map<String,Integer> prec = Map.of(
                "^", 4,
                "*", 3, "/", 3,
                "+", 2, "-", 2,
                "(", 0, ")", 0
        );
        for (String t: tokens) {
            if (t.matches("\\d+(?:\\.\\d*)?") || t.matches("[a-zA-Z_]\\w*")) {
                out.add(t);
            } else if ("(".equals(t)) {
                ops.push(t);
            } else if (")".equals(t)) {
                while (!ops.isEmpty() && !ops.peek().equals("(")) {
                    out.add(ops.pop());
                }
                ops.pop();  // Remove "("
            } else {
                // Operator
                while (!ops.isEmpty() && prec.get(ops.peek()) >= prec.get(t)) {
                    out.add(ops.pop());
                }
                ops.push(t);
            }
        }
        while (!ops.isEmpty()) {
            out.add(ops.pop());
        }
        return out;
    }

    /**
     * Evaluates a list of tokens in RPN and returns an MXWrapper object.
     *
     * @param rpn the list of tokens in RPN
     * @return an MXWrapper representing the evaluated expression
     */
    private static MXWrapper evalRPN(List<String> rpn) {
        Deque<MXWrapper> stack = new ArrayDeque<>();
        for (String t: rpn) {
            if (t.matches("\\d+(?:\\.\\d*)?")) {
                // Number → constant DM
                double v = Double.parseDouble(t);
                stack.push(new MXWrapper(v));
            } else if (t.matches("[a-zA-Z_]\\w*")) {
                // Variable → symbolic MX
                stack.push(MXWrapper.sym(t));
            } else {
                // Operator
                MXWrapper b = stack.pop(), a = stack.pop();
                switch (t) {
                    case "+" : stack.push(a.add(b));        break;
                    case "-" : stack.push(a.subtract(b));   break;
                    case "*" : stack.push(a.multiply(b));   break;
                    case "/" : stack.push(a.divide(b));     break;
                    case "^" : stack.push(a.pow(b));      break;
                    default: throw new IllegalArgumentException("Unknown op "+t);
                }
            }
        }
        return stack.pop();
    }

    /**
     * Evaluates a list of tokens in RPN and returns an AbstractConstraint object.
     *
     * @param rpn the list of tokens in RPN
     * @return an AbstractConstraint representing the evaluated constraint
     */
    private static AbstractConstraint evalConstraintRPN(List<String> rpn) {
        Deque<Object> stack = new ArrayDeque<>();
        for (String t : rpn) {
            if (t.matches("\\d+(?:\\.\\d*)?")) {
                stack.push(new MXWrapper(Double.parseDouble(t)));
            } else if (t.matches("[a-zA-Z_]\\w*")) {
                stack.push(MXWrapper.sym(t));
            } else if (t.matches("<=|>=|<|>|=")) {
                // Constraint operator
                MXWrapper rhs = (MXWrapper) stack.pop();
                MXWrapper lhs = (MXWrapper) stack.pop();
                Comparison cmp = parseCmp(t);
                stack.push(ConstraintBuilder.of(lhs).cmp(cmp).rhs(rhs).build());
            } else {
                // Arithmetic operators
                MXWrapper b = (MXWrapper) stack.pop();
                MXWrapper a = (MXWrapper) stack.pop();
                MXWrapper res = switch (t) {
                    case "+" -> a.add(b);
                    case "-" -> a.subtract(b);
                    case "*" -> a.multiply(b);
                    case "/" -> a.divide(b);
                    case "^" -> a.pow(b);
                    default  -> throw new IllegalArgumentException("Unknown op: " + t);
                };
                stack.push(res);
            }
        }
        Object result = stack.pop();
        if (result instanceof AbstractConstraint c) {
            return c;
        } else if (result instanceof MXWrapper expr) {
            // Fallback: expr == 0
            return (AbstractConstraint) ConstraintBuilder.of(expr)
                    .cmp(Comparison.EQ)
                    .rhs(new MXWrapper(0))
                    .build();
        } else {
            throw new IllegalStateException("Unexpected RPN result: " + result);
        }
    }

    /**
     * Parses a comparison symbol and returns the corresponding Comparison enum.
     *
     * @param sym the comparison symbol
     * @return the Comparison enum corresponding to the symbol
     * @throws IllegalArgumentException if the symbol is unknown
     */
    private static Comparison parseCmp(String sym) {
        return switch (sym) {
            case "="  -> Comparison.EQ;
            case "<=" -> Comparison.LE;
            case ">=" -> Comparison.GE;
            case "<"  -> Comparison.LT;
            case ">"  -> Comparison.GT;
            default   -> throw new IllegalArgumentException("Unknown comparison: " + sym);
        };
    }

}
