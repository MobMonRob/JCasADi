package de.dhbw.rahmlab.casadimaxima.implementation.maxima;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Runs a complete Maxima batch program and returns the text between caller-provided result markers.
 */
public final class MaximaProcessExecutor {

    private static final int MAXIMA_BATCH_STRING_THRESHOLD_BYTES = 120 * 1024;
    private static final int MAXIMA_BATCH_STRING_SAFE_DIRECT_THRESHOLD_CHARS
        = MAXIMA_BATCH_STRING_THRESHOLD_BYTES / 3;

    private MaximaProcessExecutor() {
    }

    public static String execute(String maximaInput, String resultBegin, String resultEnd) {
        Path temporaryBatchFile = null;
        try {
            List<String> maximaArguments = new ArrayList<>(List.of(
                "maxima",
                "--very-quiet",
                "-X",
                "--dynamic-space-size 1024 --disable-ldb --lose-on-corruption"
            ));
            if (requiresBatchFile(maximaInput)) {
                temporaryBatchFile = Files.createTempFile("maxima-batch-", ".mac");
                Files.writeString(temporaryBatchFile, maximaInput, StandardCharsets.UTF_8);
                maximaArguments.add("--batch=" + temporaryBatchFile.toAbsolutePath());
            } else {
                maximaArguments.add("--batch-string");
                maximaArguments.add(maximaInput);
            }

            Process process = new ProcessBuilder(maximaArguments).start();
            process.getOutputStream().close(); // Never send subsequent input to Maxima.

            ProcessOutputReader.Result result;
            try (var reader = new ProcessOutputReader(process)) {
                result = reader.await();
            }
            if (result.exitCode() != 0 || !result.stderr().isEmpty()) {
                throw new RuntimeException("Maxima error:\n" + result.stderr());
            }
            return extractResult(result.stdout(), resultBegin, resultEnd);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to run Maxima", ex);
        } finally {
            if (temporaryBatchFile != null) {
                try {
                    Files.deleteIfExists(temporaryBatchFile);
                } catch (IOException ignored) {
                    // Best effort: the system temporary directory is cleaned up separately.
                }
            }
        }
    }

    private static boolean requiresBatchFile(String maximaInput) {
        int characterCount = maximaInput.length();
        if (characterCount < MAXIMA_BATCH_STRING_SAFE_DIRECT_THRESHOLD_CHARS) {
            return false;
        }
        if (characterCount >= MAXIMA_BATCH_STRING_THRESHOLD_BYTES) {
            return true;
        }
        return maximaInput.getBytes(StandardCharsets.UTF_8).length
            >= MAXIMA_BATCH_STRING_THRESHOLD_BYTES;
    }

    private static String extractResult(String output, String resultBegin, String resultEnd) {
        // --batch-string echoes the printf command itself, hence lastIndexOf.
        int start = output.lastIndexOf(resultBegin);
        if (start < 0) {
            throw new RuntimeException("Maxima result start marker not found:\n" + output);
        }
        start += resultBegin.length();

        int finish = output.indexOf(resultEnd, start);
        if (finish < 0) {
            throw new RuntimeException("Maxima result end marker not found:\n" + output);
        }
        return output.substring(start, finish).trim();
    }
}
