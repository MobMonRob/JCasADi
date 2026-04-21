package de.dhbw.rahmlab.casadimaxima;

import java.util.function.Consumer;
import java.util.function.Function;

public class TypeTest4 {

    public static interface BSuper {
    }

    public static interface BSub extends BSuper {

    }

    // Alternativ, wenn man eine Methode nutzen möchte aus BSuper:
    // public static interface ASuper<BSUPERSUB extends BSuper> {
    public static class ASuper<BSUPERSUB> implements BSuper {

        public void method1(Consumer<ASuper> func) {

        }

        public void method2() {

        }

        public void methodInvocation() {
            method1(ASuper::method2);
        }
    }
}
