package nl.waisda.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class CookieInterceptor extends HandlerInterceptorAdapter {

	private Logger log = Logger.getLogger(CookieInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession(true);
		if (session == null || session.isNew()) {
			String targetUrl = request.getServletPath();
			if (!request.getMethod().equals("GET")) {
				targetUrl = "";
			}
			if (targetUrl == null) {
				targetUrl = "";
			}

			String action = "redirect:/check-cookies?targetUrl=" + targetUrl;
			log.info(action);
			throw new ModelAndViewDefiningException(new ModelAndView(action));
		} else {
			return true;
		}
	}

}
