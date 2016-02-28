package ua.nure.lisyak.SummaryTask4.servlet.Ajax;

import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.model.enums.Enabled;
import ua.nure.lisyak.SummaryTask4.model.enums.Role;
import ua.nure.lisyak.SummaryTask4.util.LocaleUtil;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;
import ua.nure.lisyak.SummaryTask4.util.constant.SettingsAndFolderPaths;
import ua.nure.lisyak.SummaryTask4.util.validation.UserValidator;
import ua.nure.lisyak.SummaryTask4.util.validation.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@MultipartConfig(
        location = SettingsAndFolderPaths.Images.TEMP_DIRECTORY,
        fileSizeThreshold = SettingsAndFolderPaths.Images.SIZE_THRESHOLD,
        maxFileSize = SettingsAndFolderPaths.Images.MAX_SIZE
)
@WebServlet(Constants.ServletPaths.USER)
public class UserAjaxServlet extends BaseAjaxServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = getEntityId(req);
        User excludedUser = getCurrentUser(req);
        if(id!=null){
            User user = getUserService().get(id);
            if(user!=null){
                print(req, resp, user);
                return;
            }

            String locale = getLocale(req);
            LocaleUtil translator = getTranslator();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, translator.translate("object.notFound",locale));

            return;
        }
        else{
            List<User> users = new ArrayList<>();

            String param = getStringParam(req, Constants.Attributes.GET_BY);
            if(param!=null && Enabled.valueOf(param)!=null){
                users = getUserService().getAllByStatus(param);
            }
            param = getStringParam(req, Constants.Attributes.ROLE);
            if(param!=null && Role.valueOf(param)!=null){
                users = getUserService().getAllByRole(Role.valueOf(param), excludedUser!=null?excludedUser.getId():0);
            }

            print(req, resp, users);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) getEntityFromRequest(req, User.class);
        String locale = getLocale(req);

        LocaleUtil translator = getTranslator();

        String imagePart = getStringParam(req, Constants.Attributes.IMAGE);

        if(imagePart==null){
            imagePart = user.getImage();
        }

            if(user==null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "txt.nothingFound");
                return;
        }

        Validator validator = new UserValidator(user, locale);

        if (imagePart != null && imagePart.length()>0 && !SettingsAndFolderPaths.isImage(imagePart)) {
            validator.putIssue(Constants.Attributes.IMAGE, "validator.invalidFileFormat");
        }

        if (validator.hasErrors()) {
            sendError(req, resp, validator);
            return;
        }

        checkUserUniqueness(user, validator);
        if (validator.hasErrors()) {
            sendError(req, resp, validator);
            return;
        }
        User currentUser = getCurrentUser(req);
        if(currentUser!=null) {
            user.addRole(((currentUser.getRoles().contains(Role.ADMIN))) ?
                    Role.TUTOR :
                    Role.STUDENT);
            user.setEnabled(Enabled.ACTIVE);
        }else{
            user.setEnabled(Enabled.WAITING);
        }
        User savedUser = saveUser(imagePart, user);

        if (savedUser == null) {
            validator.putIssue("error", "object.error");
            sendError(req, resp, validator);
            return;
        }

        print(req, resp, savedUser);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User)getEntityFromRequest(req, User.class);
        String locale = getLocale(req);

        String imagePart = getStringParam(req, Constants.Attributes.IMAGE);

        Validator validator = new UserValidator(user, locale);

        String image = user.getImage();
        if(imagePart==null && !(image == null || user.getImage().equals(getUserService().get(user.getId()).getImage()))){
            imagePart = user.getImage();
        }

        if (imagePart != null && !SettingsAndFolderPaths.isImage(imagePart)) {
            validator.putIssue(Constants.Attributes.IMAGE, "validator.invalidFileFormat");
        }

        if (validator.hasErrors()) {
            sendError(req, resp, validator);
            return;
        }

        User savedUser = updateUser(imagePart, user);

        if (savedUser == null) {
            validator.putIssue("error", "object.error");
            sendError(req, resp, validator);
            return;
        }

        print(req, resp, savedUser);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = getEntityId(req);
        String locale = getStringParam(req,Constants.Attributes.LANG);
        LocaleUtil translator = getTranslator();

        if(id!=null){
            if(getUserService().delete(id)){
                print(req, resp,  translator.translate("object.deleted", locale));
                return;
            }
        }

        resp.sendError(HttpServletResponse.SC_NOT_FOUND, translator.translate("object.notFound",locale));
    }
}
