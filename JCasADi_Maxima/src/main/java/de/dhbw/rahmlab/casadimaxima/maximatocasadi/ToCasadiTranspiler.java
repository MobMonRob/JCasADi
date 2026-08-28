package de.dhbw.rahmlab.casadimaxima.maximatocasadi;

import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorSX;
import de.dhbw.rahmlab.casadimaxima.api.TranspilationException;
import de.dhbw.rahmlab.casadimaxima.api.TranspilationException.Direction;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class ToCasadiTranspiler extends MaximaParserBaseVisitor<SX> {

    private final Map<String, SX> variables;
    private final String source;

    public ToCasadiTranspiler(Map<String, SX> initialVariables) {
        this(initialVariables, "");
    }

    public ToCasadiTranspiler(Map<String, SX> initialVariables, String source) {
        this.variables = new HashMap<>(initialVariables);
        this.source = source;
    }

    @Override
    public SX visitRoot(MaximaParser.RootContext ctx) {
        return visit(ctx.content());
    }

    @Override
    public SX visitSimpleArray(MaximaParser.SimpleArrayContext ctx) {
        return visit(ctx.arrayExpr());
    }

    @Override
    public SX visitFullBlock(MaximaParser.FullBlockContext ctx) {
        // Ignore variable declarations.

        // Variable Assignments
        visit(ctx.definitions());

        // Array at the end
        return visit(ctx.arrayExpr());
    }

    @Override
    public SX visitDefinitions(MaximaParser.DefinitionsContext ctx) {
        for (var assignCtx : ctx.assignment()) {
            visit(assignCtx);
        }
        return null;
    }

    @Override
    public SX visitAssignment(MaximaParser.AssignmentContext ctx) {
        String id = ctx.ID().getText();
        SX value = visit(ctx.expression());
        variables.put(id, value);
        return value;
    }

    @Override
    public SX visitArrayExpr(MaximaParser.ArrayExprContext ctx) {
        List<SX> expressions = new ArrayList<>();
        for (var exprCtx : ctx.expression()) {
            expressions.add(visit(exprCtx));
        }

        // We want to get a multivector as return value again.
        // If not: split into two visitors. One for expr. One for compunds like lists.
        var vec = new StdVectorSX(expressions);
        SX result = SxStatic.vertcat(vec);
        return result;
    }

    @Override
    public SX visitNumber(MaximaParser.NumberContext ctx) {
        return new SX(Double.parseDouble(ctx.getText()));
    }

    @Override
    public SX visitVariable(MaximaParser.VariableContext ctx) {
        String name = ctx.getText();
        SX sx = variables.get(name);
        if (sx == null) {
            throw new RuntimeException(String.format("Illegal free variable: %s", name));
        }
        return sx;
    }

    @Override
    public SX visitConstantE(MaximaParser.ConstantEContext ctx) {
        return SxStatic.exp(new SX(1.0));
    }

    // private final SX pi = new SX(Math.PI);
    private final SX PI = SxStatic.acos(new SX(-1));

    @Override
    public SX visitConstantPi(MaximaParser.ConstantPiContext ctx) {
        return PI;
    }

    @Override
    public SX visitParenExpr(MaximaParser.ParenExprContext ctx) {
        return visit(ctx.expression());
    }

    private final SX ZERO_SPARSE = new SX(new Sparsity(1, 1));

    @Override
    public SX visitUnaryMinus(MaximaParser.UnaryMinusContext ctx) {
        SX val = visit(ctx.expression());
        return SxStatic.minus(ZERO_SPARSE, val);
    }

    @Override
    public SX visitAddSubExpr(MaximaParser.AddSubExprContext ctx) {
        SX left = visit(ctx.expression(0));
        SX right = visit(ctx.expression(1));
        return ctx.op.getType() == MaximaLexer.ADD
            ? SxStatic.plus(left, right)
            : SxStatic.minus(left, right);
    }

    @Override
    public SX visitMulDivExpr(MaximaParser.MulDivExprContext ctx) {
        SX left = visit(ctx.expression(0));
        SX right = visit(ctx.expression(1));
        return ctx.op.getType() == MaximaLexer.MUL
            ? SxStatic.times(left, right)
            : SxStatic.rdivide(left, right);
    }

    @Override
    public SX visitPowerExpr(MaximaParser.PowerExprContext ctx) {
        SX base = visit(ctx.expression(0));
        SX exponent = visit(ctx.expression(1));
        return SxStatic.pow(base, exponent);
    }

    @Override
    public SX visitCompareExpr(MaximaParser.CompareExprContext ctx) {
        SX left = visit(ctx.expression(0));
        SX right = visit(ctx.expression(1));
        int type = ctx.op.getType();

        return switch (type) {
            case MaximaLexer.LT ->
                SxStatic.lt(left, right);
            case MaximaLexer.LTE ->
                SxStatic.le(left, right);
            case MaximaLexer.GT ->
                SxStatic.gt(left, right);
            case MaximaLexer.GTE ->
                SxStatic.ge(left, right);
            case MaximaLexer.EQ ->
                SxStatic.eq(left, right);
            case MaximaLexer.NEQ ->
                SxStatic.ne(left, right);
            default ->
                throw new AssertionError();
        };
    }

    @Override
    public SX visitLogicalAndExpr(MaximaParser.LogicalAndExprContext ctx) {
        return SxStatic.logic_and(visit(ctx.expression(0)), visit(ctx.expression(1)));
    }

    @Override
    public SX visitLogicalOrExpr(MaximaParser.LogicalOrExprContext ctx) {
        return SxStatic.logic_or(visit(ctx.expression(0)), visit(ctx.expression(1)));
    }

    @Override
    public SX visitNotExpr(MaximaParser.NotExprContext ctx) {
        return SxStatic.logic_not(visit(ctx.expression()));
    }

    @Override
    public SX visitIfExpr(MaximaParser.IfExprContext ctx) {
        SX cond = visit(ctx.expression(0));
        SX thenE = visit(ctx.expression(1));
        SX elseE = visit(ctx.expression(2));
        return SxStatic.if_else(cond, thenE, elseE);
    }

    @Override
    public SX visitFunctionCall(MaximaParser.FunctionCallContext ctx) {
        String funcName = ctx.ID().getText().toLowerCase();
        validateFunction(ctx, funcName, ctx.expression().size());
        List<SX> args = new ArrayList<>();
        for (var exprCtx : ctx.expression()) {
            args.add(visit(exprCtx));
        }

        return mapFunction(funcName, args);
    }

    private SX mapFunction(String name, List<SX> args) {
        SX a = args.get(0);
        SX b = args.size() == 2 ? args.get(1) : null;

        return switch (name.toLowerCase()) {
            case "sin" ->
                SxStatic.sin(a);
            case "cos" ->
                SxStatic.cos(a);
            case "tan" ->
                SxStatic.tan(a);
            case "asin" ->
                SxStatic.asin(a);
            case "acos" ->
                SxStatic.acos(a);
            case "atan" ->
                SxStatic.atan(a);
            case "atan2" ->
                SxStatic.atan2(a, b);

            case "sinh" ->
                SxStatic.sinh(a);
            case "cosh" ->
                SxStatic.cosh(a);
            case "tanh" ->
                SxStatic.tanh(a);
            case "asinh" ->
                SxStatic.asinh(a);
            case "acosh" ->
                SxStatic.acosh(a);
            case "atanh" ->
                SxStatic.atanh(a);

            case "exp" ->
                SxStatic.exp(a);
            case "log" ->
                SxStatic.log(a);

            case "floor" ->
                SxStatic.floor(a);
            case "ceiling" ->
                SxStatic.ceil(a);
            case "abs" ->
                SxStatic.abs(a);
            case "signum" ->
                SxStatic.sign(a);

            case "sqrt" ->
                SxStatic.sqrt(a);

            case "erf" ->
                SxStatic.erf(a);

            case "min" ->
                SxStatic.fmin(a, b);
            case "max" ->
                SxStatic.fmax(a, b);
            default -> throw new AssertionError("Validated function was not mapped: " + name);
        };
    }

    private void validateFunction(MaximaParser.FunctionCallContext ctx, String function,
            int actualArity) {
        int expectedArity = switch (function) {
            case "sin", "cos", "tan", "asin", "acos", "atan", "sinh", "cosh", "tanh",
                    "asinh", "acosh", "atanh", "exp", "log", "floor", "ceiling", "abs",
                    "signum", "sqrt", "erf" -> 1;
            case "atan2", "min", "max" -> 2;
            default -> -1;
        };
        if (expectedArity < 0) {
            throw TranspilationException.semantic(Direction.MAXIMA_TO_CASADI, source, ctx,
                    String.format("Function '%s' is not supported; expected arity one of [1, 2], got %d in direction %s",
                            function, actualArity, Direction.MAXIMA_TO_CASADI));
        }
        if (actualArity != expectedArity) {
            throw TranspilationException.semantic(Direction.MAXIMA_TO_CASADI, source, ctx,
                    String.format("Function '%s' expects arity %d but got %d in direction %s",
                            function, expectedArity, actualArity, Direction.MAXIMA_TO_CASADI));
        }
    }
}
