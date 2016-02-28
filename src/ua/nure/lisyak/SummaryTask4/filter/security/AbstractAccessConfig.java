package ua.nure.lisyak.SummaryTask4.filter.security;

import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.model.enums.Role;

/**
 * Provides basic functionality of {@link AccessConfig} interface.
 */
public abstract class AbstractAccessConfig implements AccessConfig {

    private Role userRole;

    protected AbstractAccessConfig(Role userRole) {
        this.userRole = userRole;
    }

    protected boolean userRoleMatch(User user) {
        if (user == null) {
            return false;
        }
            if (user.getRoles().contains(userRole)) {
                return true;
            }

        return false;
    }
}
