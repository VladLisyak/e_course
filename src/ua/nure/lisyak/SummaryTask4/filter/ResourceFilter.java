package ua.nure.lisyak.SummaryTask4.filter;

import org.slf4j.LoggerFactory;
import ua.nure.lisyak.SummaryTask4.util.constant.Constants;
import ua.nure.lisyak.SummaryTask4.util.constant.SettingsAndFolderPaths;
import ua.nure.lisyak.SummaryTask4.util.file.FileService;
import ua.nure.lisyak.SummaryTask4.util.file.FileServletUtil;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;



/**
 * Filters requests to static resources as files, images.
 */
//@WebFilter("ResourceFilter")
public class ResourceFilter extends BaseFilter {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ResourceFilter.class);
    private FileService fileServ;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	LOGGER.info("Initialization started.");
        fileServ = (FileService) filterConfig.getServletContext()
        									 .getAttribute(Constants.Service.FILE_PROC_SERVICE);
        LOGGER.info("Initialization finished.");
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String route = request.getRequestURI();
        if (route.contains(SettingsAndFolderPaths.getResourcePath())) {
            receiveFromStorage(request, response);
            return;
        }
        route = route.replace(request.getContextPath(), "");
        request.getRequestDispatcher(URLDecoder.decode(route, "UTF-8")).forward(request, response);
    }

    /**
     * Gets file from locale storage.
     */
    private void receiveFromStorage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getRequestURI();
        String filePath = path.replace(SettingsAndFolderPaths.getResourcePath(), "");
        File file = fileServ.getFile(filePath);
        LOGGER.debug("Getting file "+ file.getAbsoluteFile());
        if (!file.exists()) {
            LOGGER.warn("File wasn't found in " + file.getAbsoluteFile());
            response.sendError(404);
            return;
        }
        FileServletUtil.write(file, response);
    }

}
