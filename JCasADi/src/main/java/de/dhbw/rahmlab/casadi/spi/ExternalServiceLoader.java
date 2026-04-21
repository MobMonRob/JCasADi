package de.dhbw.rahmlab.casadi.spi;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;

public final class ExternalServiceLoader {

    private static ExternalServiceLoader instance;
    private final ServiceLoader<ICasADiExternalProcessor> loader;

    public static synchronized ExternalServiceLoader instance() {
        if (instance == null) {
            instance = new ExternalServiceLoader();
        }
        return instance;
    }

    private ExternalServiceLoader() {
        loader = ServiceLoader.load(ICasADiExternalProcessor.class);
    }

    public Optional<ICasADiExternalProcessor> getProcessor() {
        List<ICasADiExternalProcessor> impls = loader.stream()
            .map(Provider::get)
            .toList();
        if (impls.isEmpty()) {
            return Optional.empty();
        }
        if (impls.size() > 1) {
            throw new RuntimeException(String.format("Found %s implementations of ICasADiExternalProcessor.",
                impls.size()));
        }
        return Optional.of(impls.get(0));
    }

    public static ICasADiExternalProcessor getProcessorThrowing(String algebra) throws NoSuchElementException {
        return ExternalServiceLoader.instance().getProcessor().orElseThrow();
    }
}
