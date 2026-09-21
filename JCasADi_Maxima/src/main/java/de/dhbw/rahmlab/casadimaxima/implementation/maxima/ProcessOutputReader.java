package de.dhbw.rahmlab.casadimaxima.implementation.maxima;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Reads stdout and stderr concurrently to prevent deadlock or data loss when one pipe gets filled and the
 * writer blocks in turn while the reader waits for the other pipe to end.
 */
public final class ProcessOutputReader implements AutoCloseable {

    private final Process process;
    private final Charset charset;
    private final ExecutorService executor;

    private final Future<byte[]> stdoutFuture;
    private final Future<byte[]> stderrFuture;

    public ProcessOutputReader(Process process) {
        this(process, StandardCharsets.UTF_8);
    }

    public ProcessOutputReader(Process process, Charset charset) {
        this.process = process;
        this.charset = charset;

        executor = Executors.newVirtualThreadPerTaskExecutor();

        // Start reading immediately.
        stdoutFuture = executor.submit(() -> process.getInputStream().readAllBytes());
        stderrFuture = executor.submit(() -> process.getErrorStream().readAllBytes());
    }

    /**
     * Waits for the process to terminate and for stdout/stderr to be completely consumed.
     */
    public Result await() throws InterruptedException, IOException {
        int exitCode = process.waitFor();

        try {
            byte[] stdoutBytes = stdoutFuture.get();
            byte[] stderrBytes = stderrFuture.get();

            return new Result(
                exitCode,
                new String(stdoutBytes, charset),
                new String(stderrBytes, charset)
            );

        } catch (ExecutionException e) {
            Throwable cause = e.getCause();

            if (cause instanceof IOException io) {
                throw io;
            }

            if (cause instanceof RuntimeException runtime) {
                throw runtime;
            }

            throw new IOException("Failed to read process output", cause);
        }
    }

    @Override
    public void close() {
        executor.close();
    }

    public static record Result(
        int exitCode,
        String stdout,
        String stderr
        ) {}
}
