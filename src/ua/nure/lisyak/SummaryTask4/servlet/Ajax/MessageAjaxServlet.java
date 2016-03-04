package ua.nure.lisyak.SummaryTask4.servlet.Ajax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.lisyak.SummaryTask4.model.Message;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Servlet for operating {@link Message} entities
 */
@WebServlet(urlPatterns = {Constants.ServletPaths.MESSAGES})
public class MessageAjaxServlet extends BaseAjaxServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageAjaxServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer referrerId = getIntParam(req, Constants.Attributes.REFERRER_ID);
        Integer userId = getCurrentUser(req).getId();
        Boolean count = getBooleanParam(req, Constants.Attributes.COUNT, false);
        LOGGER.debug("doGet for messages");
        if(count){
            print(req,resp, new Integer(getMessageService().getUnreadCount(userId, referrerId)));
            return;
        }


            List<Message> dialog = getMessageService().getDialog(userId, referrerId);
            print(req, resp, dialog);
            return;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Message newMessage = (Message) getEntityFromRequest(req, Message.class);
        LOGGER.debug("doPost for {}", newMessage);
        newMessage.setFromId(getCurrentUser(req).getId());
        newMessage.setReferrerName(getUserService().get(newMessage.getToId()).getName());
        newMessage.setDate(new Timestamp(new Date().getTime()));
        newMessage.setRead(false);
        getMessageService().save(newMessage);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Message newMessage = (Message) getEntityFromRequest(req, Message.class);
        LOGGER.debug("doPut for {}", newMessage);
        getMessageService().update(newMessage);
    }
}
