package ua.nure.lisyak.SummaryTask4.filter.security;


import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.model.enums.Role;
import ua.nure.lisyak.SummaryTask4.util.Tuple;

/**
 * Defines whether the current users can access the specified action.
 */
public class ActionAccessConfig extends AbstractAccessConfig {

    private String redirect;
    private String[] actions;

    /**
     * Creates a new action access configuration.
     *
     * @param userRole allowed user role
     * @param redirect redirect action in case of denied access
     * @param actions  collection of actions that must be put under access control
     */
    public ActionAccessConfig(Role userRole, String redirect, String... actions) {
        super(userRole);
        this.redirect = redirect;
        this.actions = actions;
    }

    @Override
    public Tuple<Boolean, Boolean> isAllowed(String path, User user) {
        for (String s : actions) {
            if (s.equals(path)) {
                return new Tuple<>(userRoleMatch(user), false);
            }
        }
        return new Tuple<>(true, true);
    }

    @Override
    public String getRedirect() {
        return redirect;
    }
}
