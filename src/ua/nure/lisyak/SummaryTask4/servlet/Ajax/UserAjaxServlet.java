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
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            Map<String, List<User>> users = new HashMap<>();

            users.put("tutors", getUserService().getAllByRole(Role.TUTOR));
            users.put("students", getUserService().getAllByRole(Role.STUDENT));
            users.put("admins", getUserService().getAllByRole(Role.ADMIN));

            print(req, resp, users);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) getEntityFromRequest(req, User.class);
        String locale = getStringParam(req,Constants.Attributes.LANG);
        LocaleUtil translator = getTranslator();

        Part imagePart = req.getPart(Constants.Attributes.IMAGE);

        Validator validator = new UserValidator(user, locale);

        if (imagePart != null && imagePart.getSize() != 0 && !SettingsAndFolderPaths.isImage(imagePart.getContentType())) {
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

        user.addRole(Role.STUDENT);
        user.addRole(Role.TUTOR);
        user.setEnabled(Enabled.ACTIVE);

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
        String locale = getStringParam(req,Constants.Attributes.LANG);

        Part imagePart = req.getPart(Constants.Attributes.IMAGE);

        Validator validator = new UserValidator(user, locale);

        if (imagePart != null && imagePart.getSize() != 0 && !SettingsAndFolderPaths.isImage(imagePart.getContentType())) {
            validator.putIssue(Constants.Attributes.IMAGE, "validator.invalidFileFormat");
        }

        if (validator.hasErrors()) {
            sendError(req, resp, validator);
            return;
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
