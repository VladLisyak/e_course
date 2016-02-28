package ua.nure.lisyak.SummaryTask4.filter.security;

import ua.nure.lisyak.SummaryTask4.filter.BaseFilter;
import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.model.enums.Role;
import ua.nure.lisyak.SummaryTask4.util.Tuple;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filters request and defines whether the current user has access to the action.
 * In case of denied access redirects to the appropriate action.
 */
@WebFilter(filterName = "AccessFilter")
public class AccessFilter extends BaseFilter {

    private static final AccessConfig[] CONFIGS = new AccessConfig[]{
            new ActionAccessConfig(
                    Role.STUDENT,
                    Constants.ServletPaths.User.LOGIN,
                    Constants.ServletPaths.User.MESSAGES,
                    Constants.ServletPaths.User.PROFILE
            ),
            new ModuleAccessConfig(
                    Role.ADMIN,
                    Constants.ServletPaths.Admin.ADMIN,
                    Constants.ServletPaths.Admin.LOGIN,
                    Constants.ServletPaths.Admin.LOGIN
            ),
            new ModuleAccessConfig(
                    Role.TUTOR,
                    Constants.ServletPaths.Tutor.TUTOR,
                    Constants.ServletPaths.Tutor.LOGIN,
                    Constants.ServletPaths.Tutor.LOGIN
            )
    };

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpSession session = request.getSession();
        String url = request.getRequestURL().toString();
        String contextPath = request.getContextPath();
        String path = request.getServletPath();
        User user = (User) session.getAttribute(Constants.Attributes.CURRENT_USER);
        String redirect = isAllowed(path, user);
        if (redirect != null) {
            response.sendRedirect(redirect);
            return;
        }
        chain.doFilter(request, response);
    }

    private String isAllowed(String path, User user) {
        for (AccessConfig config : CONFIGS) {
            Tuple<Boolean, Boolean> t = config.isAllowed(path, user);
            if (t.getSecondEntity()) {
                continue;
            }
            return t.getFirstEntity() ? null : config.getRedirect();
        }
        return null;
    }

}
