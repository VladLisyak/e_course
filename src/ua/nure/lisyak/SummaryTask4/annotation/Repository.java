package ua.nure.lisyak.SummaryTask4.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates a repository. Repositories must have a constructor
 * taking {@code ConnectionHolder} object as  a parameter.
 *
 * @see ua.nure.bekuzarov.SummaryTask4.db.holder.ConnectionHolder
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Repository {
}
