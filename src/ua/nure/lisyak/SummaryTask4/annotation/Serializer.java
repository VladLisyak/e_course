package ua.nure.lisyak.SummaryTask4.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates a serializer.
 * @see ua.nure.bekuzarov.SummaryTask4.api.serialization.StreamSerializer
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Serializer {

    /**
     * Prefix for serializer (name of the returned type if strongly recommended)
     * @return prefix for serializer
     */
    String value();

}
