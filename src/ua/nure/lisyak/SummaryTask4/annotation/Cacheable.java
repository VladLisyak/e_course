package ua.nure.lisyak.SummaryTask4.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that method will be cached and
 * repeateble invocations will return result from cache
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)public
@interface Cacheable {

}
