package ua.nure.lisyak.SummaryTask4.servlet;


import ua.nure.lisyak.SummaryTask4.service.CourseService;
import ua.nure.lisyak.SummaryTask4.service.JdbcService.JdbcCourseService;
import ua.nure.lisyak.SummaryTask4.service.JdbcService.JdbcJournalEntryService;
import ua.nure.lisyak.SummaryTask4.service.JdbcService.JdbcMessageService;
import ua.nure.lisyak.SummaryTask4.service.JdbcService.JdbcUserService;
import ua.nure.lisyak.SummaryTask4.service.JournalEntryService;
import ua.nure.lisyak.SummaryTask4.service.MessageService;
import ua.nure.lisyak.SummaryTask4.service.UserService;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Basic servlet for all servlets. Provides access to 
 * services that are needed for more than one servlet.
 */
public abstract class AbstractServlet extends HttpServlet {
	
	private static final long serialVersionUID = -7053877476429245836L;
    private UserService userService;
    private MessageService messageService;
    private JournalEntryService journalEntryService;
    private CourseService courseService;
    private String defaultLocale;
    private String[] locales;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        userService = (UserService) context.getAttribute(JdbcUserService.class.getName());
        messageService = (MessageService) context.getAttribute(JdbcMessageService.class.getName());
        journalEntryService = (JournalEntryService) context.getAttribute(JdbcJournalEntryService.class.getName());
        courseService = (CourseService) context.getAttribute(JdbcCourseService.class.getName());

        defaultLocale = (String) context.getAttribute(Constants.Attributes.DEFAULT_LOCALE);
        locales = (String[]) context.getAttribute(Constants.Attributes.LOCALES);
        
    }

    protected String getDefaultLocale() {
        return defaultLocale;
    }

	protected String[] getLocales() {
         return locales;
    }

    protected UserService getUserService() {
       return userService;
    }

    protected String getLocale(HttpServletRequest request) {
        return (String)request.getAttribute(Constants.Attributes.CURRENT_LOCALE);
    }

    /**
     * Retrieves specified integer parameter from request.
     * @param param The name of parameter to retrieve
     * @return int value of parameter
     */
    protected Integer getIntParam(HttpServletRequest request, String param) {
        String paramValue = request.getParameter(param);
        if (paramValue == null) {
            return null;
        }
        try {
            return Integer.parseInt(paramValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Retrieves integer parameter from request.
     * Sets to default if parameter is null
     * @param request HttpServletRequest
     * @param param The name of parameter to retrieve
     * @param defaultValue value to set if value from request is null.
     * @return int value of parameter.
     */
    protected int getIntParam(HttpServletRequest request, String param, int defaultValue) {
        Integer value = getIntParam(request, param);
        if (value == null || value < 1) {
             return defaultValue;
        }
        return value;
    }

    public MessageService getMessageService() {
        return messageService;
    }

    public JournalEntryService getJournalEntryService() {
        return journalEntryService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    /**
     * Retrieves specified integer parameters from request. 
     * @param param The name of parameter to retrieve
     * @return <{@link List} list of integer values
     */
    protected List<Integer> getIntParamValues(HttpServletRequest request, String param) {
        String[] values = request.getParameterValues(param);
        if (values == null) {
            return null;
        }
        List<Integer> intValues = new ArrayList<>();
        for (String value : values) {
            try {
                Integer intVal = Integer.parseInt(value);
                intValues.add(intVal);
            } catch (NumberFormatException e) {
                intValues.add(null);
            }
        }
        return intValues;
    }

    /**
     * Retrieves specified String parameter from request. 
     * @param param The name of parameter to retrieve
     * @return {@link String} value of parameter
     */
    protected String getStringParam(HttpServletRequest request, String param) {
        String paramValue = request.getParameter(param);
        if (paramValue == null) {
            return null;
        }
        return paramValue.trim();
    }

    /**
     * Retrieves String parameter from request. 
     * Sets to default if parameter is null
     * @param request HttpServletRequest
     * @param defaultValue value to set if value from request is null.
     * @return String value of parameter.
     */
    protected String getStringParam(HttpServletRequest request, String paramName, String defaultValue) {
        String paramValue = getStringParam(request, paramName);
        if (paramValue == null || paramValue.isEmpty()) {
            return defaultValue;
        }
        return paramValue;
    }

}
