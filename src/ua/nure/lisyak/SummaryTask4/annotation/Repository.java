package ua.nure.lisyak.SummaryTask4.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * That annotated class is repository. Repositories must contain a constructor
 * taking {@code ConnectionHolder} object as parameter.
 *
 * @see ua.nure.lisyak.SummaryTask4.db.holder.ConnectionHolder
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Repository {
}
