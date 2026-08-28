package de.dhbw.rahmlab.casadimaxima.implementation.maxima;

import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadimaxima.implementation.transpilation.CasadiToMaximaTranspilerService;

/**
 * Internal Maxima LaTeX renderer; not a stable API.
 */
public final class MaximaLatexRenderer {

    private static final String RESULT_BEGIN = "__LATEX_RESULT_BEGIN__";
    private static final String RESULT_END = "__LATEX_RESULT_END__";

    public static String render_pipeline(SX casadiIn) {
        StringBuilder print = new StringBuilder();

        print.append("\n");
        print.append("->casadiIn: ").append(casadiIn.toString());
        print.append("\n");

        String maximaIn = new CasadiToMaximaTranspilerService().casadiToMaxima(casadiIn);
        print.append("->maximaIn: ").append(maximaIn);
        print.append("\n");

        String maximaOut = render_internal(maximaIn);
        print.append("->maximaOut: ").append(maximaOut);
        print.append("\n");

        synchronized (System.out) {
            System.out.flush();
            System.out.println(print.toString());
            System.out.flush();
        }

        return maximaOut;
    }

    public static String render_internal(String maximaExpr) throws RuntimeException {
        String trimmedMaximaExpr = maximaExpr.trim();
        if (!trimmedMaximaExpr.endsWith("$") && !trimmedMaximaExpr.endsWith(";")) {
            maximaExpr += "$";
        }

        String maximaInput = "";
        maximaInput += "display2d:false$\n"; // Do not visualize formulas
        maximaInput += maximaExpr + "\n"; // Add expr
        maximaInput += "\n"; // Add expr
        maximaInput += "transpose(matrix(%))$\n"; // Column vector
        maximaInput += "tex_raw: tex1(%)$\n"; // tex single line
        maximaInput += "short_tex: ssubst(\"\\\\begin{pmatrix}\", \"\\\\ifx\\\\endpmatrix\\\\undefined\\\\pmatrix{\\\\else\\\\begin{pmatrix}\\\\fi \", tex_raw)$\n";
        maximaInput += "short_tex: ssubst(\"\\\\end{pmatrix}\", \"\\\\ifx\\\\endpmatrix\\\\undefined}\\\\else\\\\end{pmatrix}\\\\fi\", short_tex)$\n";
        maximaInput += "printf(true,\"" + RESULT_BEGIN + "~%~a~%" + RESULT_END + "~%\", short_tex)$\n";

        return MaximaProcessExecutor.execute(maximaInput, RESULT_BEGIN, RESULT_END);
    }
}
