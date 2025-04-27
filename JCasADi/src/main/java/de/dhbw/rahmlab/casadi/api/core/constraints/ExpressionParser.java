package de.dhbw.rahmlab.casadi.api.core.constraints;

import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionParser {

    private static final Pattern TOKEN = Pattern.compile(
            "\\s*(?<num>\\d+(?:\\.\\d*)?)" +   // Zahlen
                    "|\\s*(?<var>[a-zA-Z_]\\w*)"       +   // Variablen
                    "|\\s*(?<op>[+\\-*/^()])"             // Operatoren + Klammern
    );

    /** Hauptmethode: wandelt Infix-String in MXWrapper um */
    public static MXWrapper parse(String expr) {
        List<String> tokens = tokenize(expr);
        List<String> rpn    = toRPN(tokens);
        return evalRPN(rpn);
    }

    /** 1) Tokenisierung */
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

    /** 2) Shunting-Yard: Infix → RPN */
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
                ops.pop();  // "(" entfernen
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

    /** 3) RPN-Evaluation → MXWrapper */
    private static MXWrapper evalRPN(List<String> rpn) {
        Deque<MXWrapper> stack = new ArrayDeque<>();
        for (String t: rpn) {
            if (t.matches("\\d+(?:\\.\\d*)?")) {
                // Zahl → konstantes DM
                double v = Double.parseDouble(t);
                stack.push(new MXWrapper(v));
            } else if (t.matches("[a-zA-Z_]\\w*")) {
                // Variable → symbolische MX
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
}
