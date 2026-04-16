package de.dhbw.rahmlab.casadimaxima.casaditomaxima;

import java.util.List;

public class MaximaTranspiler extends CasadiParserBaseVisitor<String> {

    @Override
    public String visitFile(CasadiParser.FileContext ctx) {
        StringBuilder sb = new StringBuilder();
        // Alle Zuweisungen abarbeiten
        for (CasadiParser.AssignmentContext assign : ctx.assignment()) {
            sb.append(visit(assign)).append("$\n"); // $ unterdrückt die Ausgabe in Maxima
        }
        // Das Array/die Liste am Ende
        if (ctx.array() != null) {
            sb.append("vn : ").append(visit(ctx.array())).append("$");
        }
        return sb.toString();
    }

    @Override
    public String visitAssignment(CasadiParser.AssignmentContext ctx) {
        // Maxima nutzt ':' für Zuweisungen
        // @1 -> v1
        String varName = ctx.VAR().getText().replace("@", "v");
        return varName + " : " + visit(ctx.expr());
    }

    @Override
    public String visitArray(CasadiParser.ArrayContext ctx) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < ctx.expr().size(); i++) {
            sb.append(visit(ctx.expr(i)));
            if (i < ctx.expr().size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public String visitParentheses(CasadiParser.ParenthesesContext ctx) {
        return "(" + visit(ctx.expr()) + ")";
    }

    @Override
    public String visitFunctionCall(CasadiParser.FunctionCallContext ctx) {
        String funcName = ctx.ID().getText();
        List<CasadiParser.ExprContext> args = ctx.expr();

        switch (funcName) {
            // --- Unäre Operationen ---
            case "sq":
                return "(" + visit(args.get(0)) + ")^2";
            case "sign":
                return "signum(" + visit(args.get(0)) + ")";
            case "ceil":
                return "ceiling(" + visit(args.get(0)) + ")";
            case "fabs":
                return "abs(" + visit(args.get(0)) + ")";
            case "expm1":
                return "(exp(" + visit(args.get(0)) + ") - 1)";
            case "log1p":
                return "log(1 + (" + visit(args.get(0)) + "))";
            case "log10":
                // CasADi gibt (0.434294 * log(a)) aus, aber falls der Visitor den Call log10 sieht:
                return "(log(" + visit(args.get(0)) + ") / log(10))";

            // --- Binäre Operationen ---
            case "pow":
            case "constpow":
                return "(" + visit(args.get(0)) + ")^(" + visit(args.get(1)) + ")";
            case "fmod":
                return "mod(" + visit(args.get(0)) + ", " + visit(args.get(1)) + ")";
            case "fmin":
                return "min(" + visit(args.get(0)) + ", " + visit(args.get(1)) + ")";
            case "fmax":
                return "max(" + visit(args.get(1)) + ", " + visit(args.get(1)) + ")";
            case "hypot":
                return "sqrt((" + visit(args.get(0)) + ")^2 + (" + visit(args.get(1)) + ")^2)";
            case "copysign":
                return "(abs(" + visit(args.get(0)) + ") * signum(" + visit(args.get(1)) + "))";

            // Standardfall für sin, cos, atan2, etc.
            default:
                StringBuilder sb = new StringBuilder();
                sb.append(funcName).append("(");
                for (int i = 0; i < args.size(); i++) {
                    sb.append(visit(args.get(i)));
                    if (i < args.size() - 1) {
                        sb.append(", ");
                    }
                }
                sb.append(")");
                return sb.toString();
        }
    }

    @Override
    public String visitUnaryOp(CasadiParser.UnaryOpContext ctx) {
        String operand = visit(ctx.expr());
        // Unterscheidung zwischen - und !
        if (ctx.getChild(0).getText().equals("!")) {
            return "not " + operand;
        } else {
            return "-(" + operand + ")";
        }
    }

    @Override
    public String visitMultiplicative(CasadiParser.MultiplicativeContext ctx) {
        String operator = ctx.op.getText();
        if (operator.equals("./")) {
            operator = "/";
        }
        return visit(ctx.expr(0)) + " " + operator + " " + visit(ctx.expr(1));
    }

    @Override
    public String visitAdditive(CasadiParser.AdditiveContext ctx) {
        return visit(ctx.expr(0)) + " " + ctx.op.getText() + " " + visit(ctx.expr(1));
    }

    @Override
    public String visitRelationalOps(CasadiParser.RelationalOpsContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        String op = ctx.op.getText();

        switch (op) {
            case "==":
                return left + " = " + right;  // Maxima Vergleich
            case "!=":
                return left + " # " + right;  // Maxima Ungleich
            case "&&":
                return left + " and " + right;
            case "||":
                return left + " or " + right;
            default:
                return left + " " + op + " " + right;
        }
    }

    @Override
    public String visitTernaryOp(CasadiParser.TernaryOpContext ctx) {
        String condition = visit(ctx.expr(0));
        String thenExpr = visit(ctx.expr(1));
        String elseExpr = visit(ctx.expr(2));

        // Maxima Syntax: if condition then b else c
        return "if " + condition + " then " + thenExpr + " else " + elseExpr;
    }

    @Override
    public String visitAtom(CasadiParser.AtomContext ctx) {
        // 1. Variablen wie @1 -> v1
        if (ctx.VAR() != null) {
            return ctx.VAR().getText().replace("@", "v");
        }

        // 2. Argumente wie arg0_0 -> bleiben gleich (Maxima versteht Unterstriche)
        if (ctx.ARG() != null) {
            return ctx.ARG().getText();
        }

        // 3. Zahlen: Direkt übernehmen
        if (ctx.NUMBER() != null) {
            if (ctx.NUMBER().getText().equals("00")) {
                return "0";
            }
            return ctx.NUMBER().getText();
        }

        // 4. IDs (z.B. Konstanten oder andere Bezeichner)
        if (ctx.ID() != null) {
            return ctx.ID().getText();
        }

        return ""; // Sollte theoretisch nie erreicht werden
    }
}
