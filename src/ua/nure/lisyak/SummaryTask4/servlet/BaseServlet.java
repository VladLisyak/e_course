package ua.nure.lisyak.SummaryTask4.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.lisyak.SummaryTask4.exception.SerializerException;
import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;
import ua.nure.lisyak.SummaryTask4.util.file.FileService;
import ua.nure.lisyak.SummaryTask4.util.serialization.StreamSerializer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Basic servlet that provides functionality for operating 
 * with current user. And safe retrieving of parameters.
 */
public abstract class BaseServlet extends AbstractServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseServlet.class);

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
    protected void unsetCurrentUser(HttpServletRequest request) {
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

    protected void print(HttpServletRequest request, HttpServletResponse response, Object o) throws IOException {
        StreamSerializer serializer = (StreamSerializer) getServletContext().getAttribute(Constants.Attributes.SERIALIZER);
        if (serializer == null) {
            response.sendError(404);
            return;
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType(serializer.getContentType());
        try {
            serializer.serialize(response.getOutputStream(), o);
        } catch (SerializerException e) {
            LOGGER.warn("Cannot serialize object", e);
            response.setContentType("text/html");
            response.sendError(500);
        }

    }

    /**
     * Prints a list of items to response
     *
     * @param request  request
     * @param response response
     * @param list     of items to print
     * @param c        class of items stored in the list
     * @param <T>      type of items stored in the list
     * @throws IOException if an input or output exception occurred
     */
    protected <T> void print(HttpServletRequest request, HttpServletResponse response, List<T> list, Class<T> c) throws IOException {
        StreamSerializer serializer = (StreamSerializer) getServletContext().getAttribute(Constants.Attributes.SERIALIZER);
        if (serializer == null) {
            response.sendError(404);
            return;
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType(serializer.getContentType());
        try {
            serializer.serializeList(response.getOutputStream(), list, c);
        } catch (SerializerException e) {
            LOGGER.warn("Cannot serialize list of objects", e);
            response.setContentType("text/html");
            response.sendError(500);
        }
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
