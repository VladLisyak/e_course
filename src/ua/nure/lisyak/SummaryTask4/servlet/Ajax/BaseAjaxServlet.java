package ua.nure.lisyak.SummaryTask4.servlet.Ajax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.lisyak.SummaryTask4.exception.SerializerException;
import ua.nure.lisyak.SummaryTask4.servlet.BaseServlet;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;
import ua.nure.lisyak.SummaryTask4.util.serialization.StreamSerializer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public abstract class BaseAjaxServlet<T> extends BaseServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseAjaxServlet.class);

    protected T getEntityFromRequest(HttpServletRequest request, Class<T> c) throws IOException {
        StreamSerializer serializer = (StreamSerializer) getServletContext().getAttribute(Constants.Attributes.SERIALIZER);
        T instance = null;

        if (serializer == null) {
            return null;
        }
        try {
            instance = serializer.deserialize(request.getInputStream(), c);
        } catch (SerializerException e) {
            LOGGER.warn("Cannot deserialize object", e);
        }

        return instance;
    }

}

