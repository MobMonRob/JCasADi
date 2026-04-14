package de.dhbw.rahmlab.casadimaxima.maximatocasadi;

import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorSX;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class CasadiTranspiler extends MaximaParserBaseVisitor<SX> {

    private final Map<String, SX> symbolTable = new HashMap<>();

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
        symbolTable.put(id, value);
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
        // TODO: Fix a_0, a_1.
        return symbolTable.computeIfAbsent(name, SxStatic::sym);
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
        int type = ctx.compOp().getChild(0).getPayload().getType(); // Hol den Token Typ

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
                throw new RuntimeException("Unbekannter Operator");
        };
    }

    @Override
    public SX visitLogicalExpr(MaximaParser.LogicalExprContext ctx) {
        SX left = visit(ctx.expression(0));
        SX right = visit(ctx.expression(1));
        return ctx.op.getType() == MaximaLexer.AND
            ? SxStatic.logic_and(left, right)
            : SxStatic.logic_or(left, right);
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
        List<SX> args = new ArrayList<>();
        for (var exprCtx : ctx.expression()) {
            args.add(visit(exprCtx));
        }

        return mapFunction(funcName, args);
    }

    private SX mapFunction(String name, List<SX> args) {
        return switch (name) {
            case "sin" ->
                SxStatic.sin(args.get(0));
            case "cos" ->
                SxStatic.cos(args.get(0));
            case "tan" ->
                SxStatic.tan(args.get(0));
            case "exp" ->
                SxStatic.exp(args.get(0));
            case "log" ->
                SxStatic.log(args.get(0));
            case "sqrt" ->
                SxStatic.sqrt(args.get(0));
            case "abs" ->
                SxStatic.abs(args.get(0));
            case "asin" ->
                SxStatic.asin(args.get(0));
            case "acos" ->
                SxStatic.acos(args.get(0));
            case "atan" ->
                SxStatic.atan(args.get(0));
            case "atan2" ->
                SxStatic.atan2(args.get(0), args.get(1));
            case "min" ->
                SxStatic.fmin(args.get(0), args.get(1));
            case "max" ->
                SxStatic.fmax(args.get(0), args.get(1));
            case "floor" ->
                SxStatic.floor(args.get(0));
            case "ceil" ->
                SxStatic.ceil(args.get(0));
            default ->
                throw new UnsupportedOperationException("Funktion " + name + " nicht implementiert.");
        };
    }
}
