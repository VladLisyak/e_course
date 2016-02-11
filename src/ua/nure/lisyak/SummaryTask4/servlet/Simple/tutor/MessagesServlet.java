package ua.nure.lisyak.SummaryTask4.servlet.Simple.tutor;

import ua.nure.lisyak.SummaryTask4.servlet.BaseServlet;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MessagesServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = getEntityId(req);
        if(id != null){
            req.setAttribute(Constants.Attributes.ID, id);
            forward(Constants.Pages.Tutor.DIALOG, req, resp);
        }else{
            forward(Constants.Pages.Tutor.UNREAD, req, resp);
        }
    }
}
