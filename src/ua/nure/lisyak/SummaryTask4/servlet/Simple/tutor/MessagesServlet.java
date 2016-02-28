package ua.nure.lisyak.SummaryTask4.servlet.Simple.tutor;

import ua.nure.lisyak.SummaryTask4.servlet.BaseServlet;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Constants.ServletPaths.Tutor.MESSAGES)
public class MessagesServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = getEntityId(req);
            req.setAttribute(Constants.Attributes.ID, id);

            forward(Constants.Pages.Tutor.MESSAGES, req, resp);

    }
}
