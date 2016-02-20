package ua.nure.lisyak.SummaryTask4.servlet.Ajax;

import ua.nure.lisyak.SummaryTask4.model.JournalEntry;
import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.model.enums.Role;
import ua.nure.lisyak.SummaryTask4.util.LocaleUtil;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {Constants.ServletPaths.SUBSCRIPTION})
public class JournalEntryAjaxServlet extends BaseAjaxServlet {

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

            if(user!= null && getJournalEntryService().deleteByStudent(user.getId(),id)){
                print(request, response,  translator.translate("object.deleted", locale));
                return;
            }
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND, translator.translate("object.notFound",locale));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getUserService().get(getIntParam(req, Constants.Attributes.ID));
        JournalEntry entry = (JournalEntry) getEntityFromRequest(req, JournalEntry.class);
        LocaleUtil translator = getTranslator();
        String locale = getStringParam(req,Constants.Attributes.LANG);
        if(getJournalEntryService().getByTutorId(user.getId(), entry.getId())!=null){


            if(getJournalEntryService().update(entry)!=null){
                print(req, resp,  translator.translate("object.saved", locale));
                return;
            }
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, translator.translate("object.notFound",locale));
            return;
        }
        resp.sendError(HttpServletResponse.SC_NOT_FOUND, translator.translate("object.notFound",locale));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = getCurrentUser(request);
        String locale = getLocale(request);

        List<JournalEntry> courses;
        if(user!=null){
            if(user.getRoles().contains(Role.TUTOR)){
                courses = getJournalEntryService().getAllByTutorId(user.getId());
            }else{
                courses = getJournalEntryService().getAllByStudentId(user.getId());
            }
            print(request, response, courses);
            return;
        }

        response.sendError(HttpServletResponse.SC_BAD_REQUEST, getTranslator().translate("object.error", locale));
    }


}
