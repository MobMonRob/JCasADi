package de.dhbw.rahmlab.casadi.api.core.wrapper.sx;

import de.dhbw.rahmlab.casadi.impl.casadi.SXElem;

public class SXComponent {

    private final SXElem sxElement;
    private String symbol;

    public SXComponent() {
        this.sxElement = new SXElem();
    }

    public SXComponent(SXElem sxElement) {
        this.sxElement = sxElement;
    }

    public SXComponent(SXComponent other) {
        this.sxElement = new SXElem(other.getCasADiObject());
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public SXElem clone() {
        return new SXElem(SXElem.getCPtr(this.sxElement), false);
    }

    public SXElem getCasADiObject() {
        return this.sxElement;
    }

    public SXComponentVector toVector() {
        return new SXComponentVector(this);
    }

}
