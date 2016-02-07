package ua.nure.lisyak.SummaryTask4.servlet;

import org.apache.log4j.Logger;
import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;
import ua.nure.lisyak.SummaryTask4.util.file.FileService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 * Basic servlet that provides functionality for operating 
 * with current user. And safe retrieving of parameters.
 */
public abstract class BaseServlet extends AbstractServlet {
	private static final Logger LOGGER = Logger.getLogger(BaseServlet.class);

	private static final long serialVersionUID = -9037428387637041940L;
    private FileService fileService;

    @Override
    public void init() throws ServletException {
    	LOGGER.info("Basic servlet init started.");
        super.init();
        fileService = (FileService) getServletContext().getAttribute(Constants.Service.FILE_PROC_SERVICE);
        LOGGER.info("Basic servlet init finished.");
    }

    protected FileService getFileService() {
        return fileService;
    }

    protected User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String currUserId = (String)session.getAttribute(Constants.Attributes.CURRENT_USER);
        Object userObj = session.getAttribute(currUserId);
        
        return userObj == null ? null : (User) userObj;
    }

    /**
     * Set current user in {@link HttpSession}
     * @param request
     * @param user user to set
     */
    protected void setCurrentUser(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute(Constants.Attributes.CURRENT_USER, String.valueOf(user.getId()));
        session.setAttribute(String.valueOf(user.getId()), user);
    }

    /**
     * Unset current user in {@link HttpSession}
     * @param request
     */
    protected void unsetLoggedUser(HttpServletRequest request) {
    	HttpSession session = request.getSession();
        String currUser = (String)session.getAttribute(Constants.Attributes.CURRENT_USER);
        session.removeAttribute(currUser);
        session.removeAttribute(Constants.Attributes.CURRENT_USER);
    }

    protected void forward(String page, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(page).forward(request, response);
    }

    protected void redirectToAction(String uri, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath()+uri);
    }

/*    *//**
     * Checks if the specified login unique
     * @param usr {@link User} to check
     * @param validator {@link Validator} entity for testing.
     *//*
    protected void checkUniqueness(User usr, Validator validator) {
        User existingUser = getUserService().getByLogin(usr.getLogin(), usr.getId());
        if (existingUser != null){
        	
            validator.putIssue("login", "validator.loginTaken");
        }
        existingUser = getUserService().getByEmail(usr.getEmail(), usr.getId());
        if (existingUser != null){
        	
            validator.putIssue("email", "validator.emailTaken");
        }
    }*/

}
