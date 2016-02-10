package ua.nure.lisyak.SummaryTask4.servlet.Simple.user;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.model.enums.Enabled;
import ua.nure.lisyak.SummaryTask4.model.enums.Role;
import ua.nure.lisyak.SummaryTask4.servlet.BaseServlet;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;
import ua.nure.lisyak.SummaryTask4.util.constant.SettingsAndFolderPaths;
import ua.nure.lisyak.SummaryTask4.util.mail.MailService;
import ua.nure.lisyak.SummaryTask4.util.validation.UserValidator;
import ua.nure.lisyak.SummaryTask4.util.validation.Validator;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet(name = "RegisterServlet", urlPatterns = {Constants.ServletPaths.User.REGISTER})
@MultipartConfig(
        location = SettingsAndFolderPaths.Images.TEMP_DIRECTORY,
        fileSizeThreshold = SettingsAndFolderPaths.Images.SIZE_THRESHOLD,
        maxFileSize = SettingsAndFolderPaths.Images.MAX_SIZE
)
public class RegisterServlet extends BaseServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (getCurrentUser(request) != null) {
            redirectToAction(Constants.ServletPaths.User.PROFILE, request, response);
            return;
        }

        String confirmation = getStringParam(request, Constants.Attributes.EMAIL_CONFIRMATION_PARAMETER);

        if (confirmation == null) {
            forward(Constants.Pages.User.REGISTRATION, request, response);
            return;
        }

        String encodedEmail = getStringParam(request, Constants.Attributes.EMAIL_CONFIRMATION_PARAMETER);
        String decodedEmail = null;

        try {
            decodedEmail = new String(Base64.decode(encodedEmail), StandardCharsets.UTF_8);
        } catch (Base64DecodingException e) {
            e.printStackTrace();
        }
        User usr = getUserService().getByEmail(decodedEmail);
        if(usr!=null){
            usr.setEnabled(Enabled.ACTIVE);
            getUserService().update(usr);
            request.setAttribute(Constants.Attributes.REGISTRATION_SUCCEED, true);

            forward(Constants.ServletPaths.User.LOGIN, request, response);
            return;
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        user.setName(request.getParameter("name"));
        user.setEmail(request.getParameter("email"));
        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));
        String locale = getLocale(request);

        Part imagePart = request.getPart(Constants.Attributes.IMAGE);

        Validator validator = new UserValidator(user, locale);

        if (imagePart != null && imagePart.getSize() != 0 && !SettingsAndFolderPaths.isImage(imagePart.getContentType())) {
            validator.putIssue(Constants.Attributes.IMAGE, "validator.invalidFileFormat");
        }

        if (validator.hasErrors()) {
            sendError(request, response, validator);
            return;
        }

        checkUserUniqueness(user, validator);
        if (validator.hasErrors()) {
            sendError(request, response, validator);
            return;
        }

        user.addRole(Role.STUDENT);
        user.setEnabled(Enabled.WAITING);

        User savedUser = saveUser(imagePart, user);

        if (savedUser == null || savedUser.getId()>0) {
            validator.putIssue("error", "validator.error500");
            sendError(request, response, validator);
            return;
        }

        HttpSession session = request.getSession();
        try {
            LOGGER.info("Sending confirmaton letter.");
            sendEmail(request, user);
            LOGGER.info("Sending is done!");
            session.setAttribute(Constants.Attributes.REGISTRATION_SUCCEED, true);
        } catch (MessagingException e) {
            session.setAttribute(Constants.Attributes.REGISTRATION_SUCCEED, false);
            redirectToAction(Constants.ServletPaths.User.COURSE_LIST, request, response);
            LOGGER.warn("Unable to send confirmation message. Account should be confirmed manually.");
        }

        redirectToAction(Constants.Pages.User.HOME, request, response);
    }



    private User saveUser(Part part, User user) {
        User savedUser = getUserService().save(user);
        if (part != null && part.getSize() != 0) {
            String image = getFileService().saveFile(savedUser.getId(), SettingsAndFolderPaths.getUploadUsersDirectory(), part);
            savedUser.setImage(image);
            return getUserService().update(savedUser);
        }
        return null;
    }

    //TODO if there will be any more explicit BundleResource declarations, create class Translator
    private void sendEmail(HttpServletRequest request, User user) throws MessagingException {
        String locale = getLocale(request);
        ResourceBundle bundle = getTranslator();
        String title = bundle.getString("mail.userConfirm.title");

        String to = user.getEmail();
        String template = MailService.getTemplate("/mailTemp/userConfirmed.html");
        String body = bundle.getString("mail.userConfirm.body");
        String linkText = bundle.getString("mail.userConfirm.reff");

        template = template
                .replace("${language}", locale)
                .replace("${title}", title)
                .replace("${body}", body)
                .replace("${link}", generateRefference(request, to))
                .replace("${linkText}", linkText);
        LOGGER.debug(template);
        MailService.send(to, title, template);
    }

    private String generateRefference(HttpServletRequest request, String to){
        StringBuilder sb = new StringBuilder(request.getRequestURL());
        String encodedEmail = Base64.encode(to.getBytes(StandardCharsets.UTF_8));
        return sb.append("?")
                .append(Constants.Attributes.EMAIL_CONFIRMATION_PARAMETER)
                .append("=")
                .append(encodedEmail).toString();
    }

}