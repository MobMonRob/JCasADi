package de.dhbw.rahmlab.casadimaxima;

public class TypeTest3 {

    public static interface BSuper {

    }

    public static interface BSub extends BSuper {

    }

    // Alternativ, wenn man eine Methode nutzen möchte aus BSuper:
    // public static interface ASuper<BSUPERSUB extends BSuper> {
    public static interface ASuper<BSUPERSUB> {

        void method1(BSUPERSUB sup);

        void method2(BSuper sup);
    }

    public static interface ASub extends ASuper<BSub> {

        @Override
        void method1(BSub sup);

        // geht nicht!
        // @Override
        void method2(BSub sup);
    }
}
