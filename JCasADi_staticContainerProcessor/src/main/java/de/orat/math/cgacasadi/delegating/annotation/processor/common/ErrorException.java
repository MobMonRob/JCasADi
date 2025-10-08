package de.orat.math.cgacasadi.delegating.annotation.processor.common;

import javax.lang.model.element.Element;

public class ErrorException extends Exception {

    public final Element element;

    protected ErrorException(Element element, String message, Object... args) {
        super(String.format(message, args));
        this.element = element;
    }

    protected ErrorException(Element element, Throwable cause, String message, Object... args) {
        super(String.format(message, args), cause);
        this.element = element;
    }

    public static ErrorException create(Element element, String message, Object... args) {
        return new ErrorException(element, message, args);
    }

    public static ErrorException create(Element element, Throwable cause, String message, Object... args) {
        return new ErrorException(element, cause, message, args);
    }
}
