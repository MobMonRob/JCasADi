package de.orat.math.cgacasadi.delegating.annotation.processor.common;

import javax.lang.model.element.Element;

public class WarningException extends Exception {

    public final Element element;

    protected WarningException(Element element, String message, Object... args) {
        super(String.format(message, args));
        this.element = element;
    }

    protected WarningException(Element element, Throwable cause, String message, Object... args) {
        super(String.format(message, args), cause);
        this.element = element;
    }

    public static WarningException create(Element element, String message, Object... args) {
        return new WarningException(element, message, args);
    }

    public static WarningException create(Element element, Throwable cause, String message, Object... args) {
        return new WarningException(element, cause, message, args);
    }
}
