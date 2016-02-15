package ua.nure.lisyak.SummaryTask4.filter;

import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * Sets request charset encoding. 
 */
//@WebFilter("/*")
public class EncodingFilter implements Filter{
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(EncodingFilter.class);

	private static final String DEFAULT_ENCODING = "UTF-8";
	private String encoding;
	
	public void init(FilterConfig fConfig) throws ServletException {
		LOGGER.trace("Filter initialization starts");
		encoding = fConfig.getInitParameter("encoding");
		if (encoding == null){
			LOGGER.trace("Encoding from Filter Config --> " + encoding);
			encoding = DEFAULT_ENCODING;
		}
		
		LOGGER.trace("Filter initialization finished");
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		LOGGER.debug("Filter starts");
		//String requestEncoding = request.getCharacterEncoding();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		response.setCharacterEncoding(encoding);
		
		LOGGER.debug("Filter finished");		
		chain.doFilter(request, response);
	}

	
	public void destroy() {
		LOGGER.debug("Filter destruction starts");
		// no op
		LOGGER.debug("Filter destruction finished");
	}
}
