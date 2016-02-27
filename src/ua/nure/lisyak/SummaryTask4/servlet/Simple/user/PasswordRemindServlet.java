package ua.nure.lisyak.SummaryTask4.servlet.Simple.user;

import ua.nure.lisyak.SummaryTask4.model.User;
import ua.nure.lisyak.SummaryTask4.servlet.BaseServlet;
import ua.nure.lisyak.SummaryTask4.util.LocaleUtil;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;
import ua.nure.lisyak.SummaryTask4.util.mail.MailService;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Constants.ServletPaths.User.PASSWORD_REMIND)
public class PasswordRemindServlet extends BaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward(Constants.Pages.User.REMIND_PAGE, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = getStringParam(req, Constants.Attributes.EMAIL);
        User userToRemind = getUserService().getByEmail(email);
        String locale = getLocale(req);
        LocaleUtil translator = getTranslator();

        if(userToRemind!=null){
            try {
                if(sendEmail(req, userToRemind)){
                    print(req, resp, "success");
                }else{
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, translator.translate("validator.error500", locale));
                }
                return;
            } catch (MessagingException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, translator.translate("validator.error500", locale));
                return;
            }
        }
        resp.sendError(HttpServletResponse.SC_NOT_FOUND, translator.translate("validator.userNotFound", locale));
    }

    private boolean sendEmail(HttpServletRequest request, User user) throws MessagingException {
        String locale = getLocale(request);
        LocaleUtil translator = getTranslator();

        String title = translator.translate("mail.password.title", locale);

        String to = user.getEmail();
        String template = MailService.getTemplate("/templates/mail/userConfirmed.html");
        String body = translator.translate("mail.password.body", locale);

        template = template
                .replace("${language}", locale)
                .replace("${title}", title)
                .replace("${body}", body)
                .replace("${link}", "")
                .replace("${linkText}", user.getPassword());
        return MailService.send(to, title, template);
    }
}
