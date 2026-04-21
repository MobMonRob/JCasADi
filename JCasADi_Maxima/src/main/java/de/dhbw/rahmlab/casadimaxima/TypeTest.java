package de.dhbw.rahmlab.casadimaxima;

import java.util.function.Function;

public class TypeTest {

    public static void main(String[] args) {
        new CachedPayload().op();
    }

    public static class Payload {
        // Useful stuff
        public Payload op() {
            System.out.println("Super Methode.");
            return this;
        }
    }

    public static interface ICached {
        // Dummy
    }

    // Generierter Code.
    // Alternativ:
    // public static class CachedPayload extends Payload implements ICached {
    public static class CachedPayload extends Payload {

        private Cache<CachedPayload> cache() {
            return new Cache<>();
        }

        @Override
        public Payload op() {
            // Ich kann hier die Methode übergeben, die auf dem CachedPayload operiert.
            // Obwohl Cache nur CACHED kennt.
            return cache().cacheIt(this, CachedPayload::op_super);
        }

        private Payload op_super() {
            return super.op();
        }
    }

    // Alternativ:
    // public static class Cache<CACHED extends ICached> {
    public static class Cache<CACHED> {

        // ICached geht nicht.
        // Vorteil: Ich kann arg auch noch manipulieren.
        public Payload cacheIt(CACHED arg, Function<CACHED, Payload> func) {
            return func.apply(arg);
        }
    }
}
