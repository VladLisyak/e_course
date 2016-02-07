package ua.nure.lisyak.SummaryTask4.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the method must be executed within a transaction.
 * It make sense to place this annotation on the method in interface that is implemented
 * by classes, marked with {@link Service}
 * annotation.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Transactional {
}
