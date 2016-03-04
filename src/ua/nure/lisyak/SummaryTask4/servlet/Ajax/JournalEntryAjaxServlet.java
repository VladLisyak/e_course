package ua.nure.lisyak.SummaryTask4.servlet.Ajax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.lisyak.SummaryTask4.model.JournalEntry;
import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.model.enums.Role;
import ua.nure.lisyak.SummaryTask4.model.enums.Status;
import ua.nure.lisyak.SummaryTask4.util.LocaleUtil;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet for operating {@link JournalEntry} entity
 */
@WebServlet(urlPatterns = {Constants.ServletPaths.SUBSCRIPTION})
public class JournalEntryAjaxServlet extends BaseAjaxServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(JournalEntryAjaxServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = getEntityId(request);
        String locale = getStringParam(request,Constants.Attributes.LANG);
        User user = getCurrentUser(request);
        if(user == null){
            redirectToAction(Constants.ServletPaths.User.LOGIN, request, response);
            return;
        }
        JournalEntry journalEntryToSave = new JournalEntry(id, getCurrentUser(request).getId());
        if(id != null && getJournalEntryService().save(journalEntryToSave)!=null){
            print(request, response, getTranslator().translate("object.saved", locale));
            LOGGER.debug("doPost for {}", journalEntryToSave);
            return;
        }

        response.sendError(HttpServletResponse.SC_BAD_REQUEST, getTranslator().translate("object.error", locale));
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = getEntityId(request);
        LocaleUtil translator = getTranslator();
        String locale = getLocale(request);
        if(id!=null){
            User user = getCurrentUser(request);
            LOGGER.debug("doDelete for entity with id {}", id);

            if(user!= null && getJournalEntryService().deleteByStudent(user.getId(),id)){
                print(request, response,  translator.translate("object.deleted", locale));
                return;
            }
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND, translator.translate("object.notFound",locale));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getCurrentUser(req);
        JournalEntry entry = (JournalEntry) getEntityFromRequest(req, JournalEntry.class);
        LocaleUtil translator = getTranslator();
        String locale = getStringParam(req,Constants.Attributes.LANG);
        if(getJournalEntryService().getByTutorId(user.getId(), entry.getId())!=null){

        LOGGER.debug("doPut for {} entity", entry);
            if(getJournalEntryService().update(entry)!=null){
                print(req, resp,  translator.translate("object.saved", locale));
                return;
            }
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, translator.translate("object.notFound",locale));
            return;
        }
        resp.sendError(HttpServletResponse.SC_NOT_FOUND, translator.translate("object.notFound",locale));
    }

    /**
     * Sorting by status are held in servlet instead of sorting in database because of cashing system,
     * as example of performance enhancing through single call to database and further retrieving data from cache
     * for operations instead of multiple calls to DB
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer courseId = getEntityId(request);
        User user = getCurrentUser(request);
        String locale = getLocale(request);
        String param = getStringParam(request, Constants.Attributes.SORT_BY);
        List<JournalEntry> courses;
        List<JournalEntry> filteredCourses = new ArrayList<>();

        LOGGER.debug("doGet for JournalEntries");
        if(user!=null){
            if(user.getRoles().contains(Role.TUTOR)){
                    courses = getJournalEntryService().getAllByCourseId(courseId);
            }else{
                courses = getJournalEntryService().getAllByStudentId(user.getId());
                Status stat =  param!=null?Status.valueOf(param.toUpperCase()):null;
                if(stat!=null){
                    for (JournalEntry entry:courses) {
                        if (entry.getStatus().equals(stat)){
                            filteredCourses.add(entry);
                        }
                    }
                    print(request, response, filteredCourses);
                    return;
                }
            }
            print(request, response, courses);
            return;
        }

        response.sendError(HttpServletResponse.SC_BAD_REQUEST, getTranslator().translate("txt.nothingFound", locale));
    }


}
