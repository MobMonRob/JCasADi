package de.dhbw.rahmlab.casadimaxima;

import java.util.function.Supplier;

public class TypeTest2 {

    public static void main(String[] args) {
        new CachedPayload().op();
    }

    public static class Payload {

        public Payload op() {
            System.out.println("Super Methode.");
            return this;
        }
    }

    public static class CachedPayload extends Payload {

        public Payload cacheIt2(Supplier<Payload> func) {
            return func.get();
        }

        @Override
        public Payload op() {
            return cacheIt2(super::op);
        }
    }
}
