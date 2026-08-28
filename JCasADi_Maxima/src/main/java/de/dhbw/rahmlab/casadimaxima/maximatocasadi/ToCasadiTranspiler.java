package de.dhbw.rahmlab.casadimaxima.maximatocasadi;

import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorSX;
import de.dhbw.rahmlab.casadimaxima.api.TranspilationException;
import de.dhbw.rahmlab.casadimaxima.api.TranspilationException.Direction;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ToCasadiTranspiler extends MaximaParserBaseVisitor<SX> {

    private final Map<String, SX> inputVariables;
    private Set<String> cseNames = Collections.emptySet();
    private final Map<String, SX> cseValues = new HashMap<>();
    private final String source;

    public ToCasadiTranspiler(Map<String, SX> initialVariables) {
        this(initialVariables, "");
    }

    public ToCasadiTranspiler(Map<String, SX> initialVariables, String source) {
        this.inputVariables = new HashMap<>(initialVariables);
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
        cseNames = new HashSet<>();
        for (var cseName : ctx.varList().CSE_VAR()) {
            cseNames.add(cseName.getText());
        }
        cseValues.clear();

        visit(ctx.definitions());
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
        String cseName = ctx.CSE_VAR().getText();
        if (!cseNames.contains(cseName)) {
            throw TranspilationException.semantic(Direction.MAXIMA_TO_CASADI, source, ctx,
                    "CSE variable '" + cseName + "' is not declared in the block variable list");
        }
        SX value = visit(ctx.expression());
        cseValues.put(cseName, value);
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
        SX inputVariable = inputVariables.get(name);
        if (inputVariable != null) {
            return inputVariable;
        }
        throw TranspilationException.semantic(Direction.MAXIMA_TO_CASADI, source, ctx,
                "Unknown free variable: " + name);
    }

    @Override
    public SX visitCseVariable(MaximaParser.CseVariableContext ctx) {
        String cseName = ctx.getText();
        if (!cseNames.contains(cseName)) {
            throw TranspilationException.semantic(Direction.MAXIMA_TO_CASADI, source, ctx,
                    "CSE variable '" + cseName + "' is not declared in the block variable list");
        }
        SX cseValue = cseValues.get(cseName);
        if (cseValue == null) {
            throw TranspilationException.semantic(Direction.MAXIMA_TO_CASADI, source, ctx,
                    "CSE variable '" + cseName + "' is used before its first assignment");
        }
        return cseValue;
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
        if (isComparison(ctx.expression(0)) || isComparison(ctx.expression(1))) {
            throw TranspilationException.semantic(Direction.MAXIMA_TO_CASADI, source, ctx,
                    "Comparison chains are not supported in direction " + Direction.MAXIMA_TO_CASADI);
        }
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

    private boolean isComparison(MaximaParser.ExpressionContext context) {
        MaximaParser.ExpressionContext unwrapped = context;
        while (unwrapped instanceof MaximaParser.ParenExprContext parentheses) {
            unwrapped = parentheses.expression();
        }
        return unwrapped instanceof MaximaParser.CompareExprContext;
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
        String funcName = ctx.ID().getText();
        validateFunction(ctx, funcName, ctx.expression().size());
        if (funcName.equals("mod") && isLiteralZero(ctx.expression(1))) {
            throw TranspilationException.semantic(Direction.MAXIMA_TO_CASADI, source, ctx,
                    "Function 'mod' requires a non-zero literal divisor in direction "
                    + Direction.MAXIMA_TO_CASADI);
        }
        List<SX> args = new ArrayList<>();
        for (var exprCtx : ctx.expression()) {
            args.add(visit(exprCtx));
        }

        return mapFunction(funcName, args);
    }

    private SX mapFunction(String name, List<SX> args) {
        SX a = args.get(0);

        return switch (name) {
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
                SxStatic.atan2(a, args.get(1));

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

            case "mod" ->
                SxStatic.minus(a, SxStatic.times(args.get(1),
                        SxStatic.floor(SxStatic.rdivide(a, args.get(1)))));
            case "min" ->
                foldMin(args);
            case "max" ->
                foldMax(args);
            default -> throw new AssertionError("Validated function was not mapped: " + name);
        };
    }

    private SX foldMin(List<SX> args) {
        SX result = args.get(0);
        for (int i = 1; i < args.size(); i++) {
            result = SxStatic.fmin(result, args.get(i));
        }
        return result;
    }

    private SX foldMax(List<SX> args) {
        SX result = args.get(0);
        for (int i = 1; i < args.size(); i++) {
            result = SxStatic.fmax(result, args.get(i));
        }
        return result;
    }

    private boolean isLiteralZero(MaximaParser.ExpressionContext ctx) {
        if (ctx instanceof MaximaParser.NumberContext) {
            return Double.parseDouble(ctx.getText()) == 0.0;
        }
        if (ctx instanceof MaximaParser.ParenExprContext paren) {
            return isLiteralZero(paren.expression());
        }
        if (ctx instanceof MaximaParser.UnaryMinusContext unaryMinus) {
            return isLiteralZero(unaryMinus.expression());
        }
        return false;
    }

    private void validateFunction(MaximaParser.FunctionCallContext ctx, String function,
            int actualArity) {
        int expectedArity = switch (function) {
            case "sin", "cos", "tan", "asin", "acos", "atan", "sinh", "cosh", "tanh",
                    "asinh", "acosh", "atanh", "exp", "log", "floor", "ceiling", "abs",
                    "signum", "sqrt", "erf" -> 1;
            case "atan2", "mod" -> 2;
            case "min", "max" -> {
                if (actualArity < 1) {
                    throw TranspilationException.semantic(Direction.MAXIMA_TO_CASADI, source, ctx,
                            String.format("Function '%s' expects at least one argument but got %d in direction %s",
                                    function, actualArity, Direction.MAXIMA_TO_CASADI));
                }
                yield actualArity;
            }
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
