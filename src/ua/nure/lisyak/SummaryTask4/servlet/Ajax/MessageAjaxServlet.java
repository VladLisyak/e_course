package ua.nure.lisyak.SummaryTask4.servlet.Ajax;

import ua.nure.lisyak.SummaryTask4.model.Message;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = {Constants.ServletPaths.MESSAGES})
public class MessageAjaxServlet extends BaseAjaxServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer referrerId = getIntParam(req, Constants.Attributes.REFERRER_ID);
        Boolean isDialog = getBooleanParam(req, Constants.Attributes.IS_DIALOG, false);
        Integer userId = getCurrentUser(req).getId();
        Boolean count = getBooleanParam(req, Constants.Attributes.COUNT, false);

        if(count){
            print(req,resp, new Integer(getMessageService().getUnreadCount(userId, referrerId)));
            return;
        }


        /*if(isDialog){*/
            List<Message> dialog = getMessageService().getDialog(userId, referrerId);
            print(req, resp, dialog);
            return;
        /*}else{
            Message unreadMessage = getMessageService().getUnread(userId, referrerId);
            print(req, resp, unreadMessage);
            return;
        }*/
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Message newMessage = (Message) getEntityFromRequest(req, Message.class);
        newMessage.setFromId(getCurrentUser(req).getId());
        newMessage.setReferrerName(getUserService().get(newMessage.getToId()).getName());
        newMessage.setDate(new Date());
        newMessage.setRead(false);
        getMessageService().save(newMessage);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Message newMessage = (Message) getEntityFromRequest(req, Message.class);
        getMessageService().update(newMessage);
    }
}
