package de.dhbw.rahmlab.casadimaxima;

import java.util.function.Consumer;

public class TypeTest5 {

    interface Reparierbar {
    }

    static class Fahrrad implements Reparierbar {

        void flicken() {
            System.out.println("Fahrrad ist wieder heil!");
        }
    }

    // --- DER VERGLEICH ---
    // 1. Die unflexible Methode (Interface)
    // Sie sagt: "Gib mir IRGENDWAS Reparierbares und eine Anleitung für IRGENDWAS Reparierbares."
    static void reparaturInterface(Reparierbar ding, Consumer<Reparierbar> anleitung) {
        anleitung.accept(ding);
    }

    // 2. Die schlaue Methode (Generic)
    // Sie sagt: "Sag mir, was du bringst (T). Ich nehme genau dieses Teil und eine Anleitung für genau dieses Teil."
    static <T extends Reparierbar> void reparaturGeneric(T ding, Consumer<T> anleitung) {
        anleitung.accept(ding);
    }

    public static void main(String[] args) {
        Fahrrad meinRad = new Fahrrad();

        // FEHLER!
        // Warum? Die Methode 'flicken' braucht ein Fahrrad.
        // 'reparaturInterface' verlangt aber eine Anleitung, die mit JEDEM Reparierbar klarkommt.
        // reparaturInterface(meinRad, Fahrrad::flicken);
        // ERFOLG!
        // Hier erkennt Java: T ist ein Fahrrad.
        // Also wird eine Anleitung für ein Fahrrad erwartet. Das passt!
        reparaturGeneric(meinRad, Fahrrad::flicken);
    }
}
