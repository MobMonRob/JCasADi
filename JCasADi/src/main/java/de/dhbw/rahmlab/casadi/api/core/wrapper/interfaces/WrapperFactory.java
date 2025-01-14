package de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces;

public interface WrapperFactory<T extends Wrapper> {

    T createWrapper(Object casADiObject);

}
