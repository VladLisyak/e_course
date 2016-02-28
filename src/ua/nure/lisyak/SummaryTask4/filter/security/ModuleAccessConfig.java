package ua.nure.lisyak.SummaryTask4.filter.security;

import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.model.enums.Role;
import ua.nure.lisyak.SummaryTask4.util.Tuple;

/**
 * Defines whether the current users can access the specified module (a collection
 * of actions starting with the same path).
 */
public class ModuleAccessConfig extends AbstractAccessConfig {

    private String basePath;
    private String[] exclude;
    private String redirect;

    /**
     * Creates a new module access configuration.
     *
     * @param userRole allowed user role
     * @param basePath path of the URL the action must start with to be under control of ths configuration
     * @param redirect redirect action in case of denied access
     * @param exclude  actions that start with {@code basePath},
     *                 but must be excluded from the control of this configuration
     */
    public ModuleAccessConfig(Role userRole, String basePath, String redirect, String... exclude) {
        super(userRole);
        this.basePath = basePath;
        this.exclude = exclude;
        this.redirect = redirect;
    }

    @Override
    public Tuple<Boolean, Boolean> isAllowed(String path, User user) {
        for (String s : exclude) {
            if (s.equals(path)) {
                return new Tuple<>(true, false);
            }
        }
        if (basePathMatches(path)) {
            return new Tuple<>(userRoleMatch(user), false);
        }
        return new Tuple<>(true, true);
    }

    @Override
    public String getRedirect() {
        return redirect;
    }

    private boolean basePathMatches(String path) {
        return basePath.isEmpty() && basePath.equals(path) || path.startsWith(basePath);
    }

}
