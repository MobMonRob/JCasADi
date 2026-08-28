package de.dhbw.rahmlab.casadimaxima.casaditomaxima;

import de.dhbw.rahmlab.casadimaxima.api.TranspilationException;
import de.dhbw.rahmlab.casadimaxima.api.TranspilationException.Direction;
import java.util.List;

public class ToMaximaTranspiler extends CasadiParserBaseVisitor<String> {

    private final String source;

    public ToMaximaTranspiler() {
        this("");
    }

    public ToMaximaTranspiler(String source) {
        this.source = source;
    }

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
            case "sq":
                validateArity(ctx, funcName, args.size(), 1);
                return "(" + visit(args.get(0)) + ")^2";
            case "sign":
                validateArity(ctx, funcName, args.size(), 1);
                return "signum(" + visit(args.get(0)) + ")";
            case "ceil":
                validateArity(ctx, funcName, args.size(), 1);
                return "ceiling(" + visit(args.get(0)) + ")";
            case "fabs":
                validateArity(ctx, funcName, args.size(), 1);
                return "abs(" + visit(args.get(0)) + ")";
            case "expm1":
                validateArity(ctx, funcName, args.size(), 1);
                return "(exp(" + visit(args.get(0)) + ") - 1)";
            case "log1p":
                validateArity(ctx, funcName, args.size(), 1);
                return "log(1 + (" + visit(args.get(0)) + "))";
            case "log10":
                validateArity(ctx, funcName, args.size(), 1);
                return "(log(" + visit(args.get(0)) + ") / log(10))";
            case "sqrt":
            case "floor":
            case "exp":
            case "log":
            case "erf":
            case "sin":
            case "cos":
            case "tan":
            case "asin":
            case "acos":
            case "atan":
            case "sinh":
            case "cosh":
            case "tanh":
            case "asinh":
            case "acosh":
            case "atanh":
                validateArity(ctx, funcName, args.size(), 1);
                return funcName + "(" + visit(args.get(0)) + ")";
            case "pow":
            case "constpow":
                validateArity(ctx, funcName, args.size(), 2);
                return "(" + visit(args.get(0)) + ")^(" + visit(args.get(1)) + ")";
            case "fmin":
                validateArity(ctx, funcName, args.size(), 2);
                return "min(" + visit(args.get(0)) + ", " + visit(args.get(1)) + ")";
            case "fmax":
                validateArity(ctx, funcName, args.size(), 2);
                return "max(" + visit(args.get(0)) + ", " + visit(args.get(1)) + ")";
            case "hypot":
                validateArity(ctx, funcName, args.size(), 2);
                return "sqrt((" + visit(args.get(0)) + ")^2 + (" + visit(args.get(1)) + ")^2)";
            case "atan2":
                validateArity(ctx, funcName, args.size(), 2);
                return "atan2(" + visit(args.get(0)) + ", " + visit(args.get(1)) + ")";
            case "fmod": {
                validateArity(ctx, funcName, args.size(), 2);
                if (isLiteralZero(args.get(1))) {
                    throw TranspilationException.semantic(Direction.CASADI_TO_MAXIMA, source, ctx,
                            "Function 'fmod' requires a non-zero literal divisor in direction "
                            + Direction.CASADI_TO_MAXIMA);
                }
                String dividend = visit(args.get(0));
                String divisor = visit(args.get(1));
                return "signum(" + dividend + ") * mod(abs(" + dividend + "), abs(" + divisor + "))";
            }
            case "copysign": {
                validateArity(ctx, funcName, args.size(), 2);
                String magnitude = visit(args.get(0));
                String signSource = visit(args.get(1));
                return "abs(" + magnitude + ") * (signum(" + signSource + ") + 1 - signum("
                        + signSource + ")^2)";
            }
            default:
                throw unsupported(ctx, funcName, args.size(), "one of [1, 2]", "is not supported");
        }
    }

    private void validateArity(CasadiParser.FunctionCallContext ctx, String function,
            int actualArity, int expectedArity) {
        if (actualArity != expectedArity) {
            throw TranspilationException.semantic(Direction.CASADI_TO_MAXIMA, source, ctx,
                    String.format("Function '%s' expects arity %d but got %d in direction %s",
                            function, expectedArity, actualArity, Direction.CASADI_TO_MAXIMA));
        }
    }

    private TranspilationException unsupported(CasadiParser.FunctionCallContext ctx, String function,
            int actualArity, String expectedArity, String reason) {
        return TranspilationException.semantic(Direction.CASADI_TO_MAXIMA, source, ctx,
                String.format("Function '%s' %s; expected arity %s, got %d in direction %s",
                        function, reason, expectedArity, actualArity, Direction.CASADI_TO_MAXIMA));
    }

    private boolean isLiteralZero(CasadiParser.ExprContext ctx) {
        if (ctx instanceof CasadiParser.PrimaryContext primary
                && primary.atom().NUMBER() != null) {
            return Double.parseDouble(primary.atom().NUMBER().getText()) == 0.0;
        }
        if (ctx instanceof CasadiParser.ParenthesesContext paren) {
            return isLiteralZero(paren.expr());
        }
        if (ctx instanceof CasadiParser.UnaryOpContext unaryOp && unaryOp.MINUS() != null) {
            return isLiteralZero(unaryOp.expr());
        }
        return false;
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
                return "(" + left + " = " + right + ")";  // Maxima Vergleich
            case "!=":
                return "(" + left + " # " + right + ")";  // Maxima Ungleich
            default:
                return "(" + left + " " + op + " " + right + ")";
        }
    }

    @Override
    public String visitLogicalAnd(CasadiParser.LogicalAndContext ctx) {
        return "(" + visit(ctx.expr(0)) + " and " + visit(ctx.expr(1)) + ")";
    }

    @Override
    public String visitLogicalOr(CasadiParser.LogicalOrContext ctx) {
        return "(" + visit(ctx.expr(0)) + " or " + visit(ctx.expr(1)) + ")";
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
