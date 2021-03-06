package ua.nure.lisyak.SummaryTask4.filter.security;

import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.util.Tuple;

/**
 * Defines whether the current users can access the specified path.
 *
 */
public interface AccessConfig {

    /**
     * Defines whether the current users can access the specified path
     *
     * @param path  path to access
     * @return {@code true} if can, {@code false} otherwise
     */
    Tuple<Boolean, Boolean> isAllowed(String path, User user);

    /**
     * Gets action, where the user will be redirected to in case of denied access.
     *
     * @return action, where the user will be redirected to in case of denied access
     */
    String getRedirect();

}
