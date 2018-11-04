package Filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 把ServletRequest 转化成 HTTPServletRequest
 * 
 * @author Administrator
 * 
 */
public class FilterUtil {

	public static HttpServletRequest getRequest(ServletRequest req) {
		HttpServletRequest req1 = null;
		req1 = (HttpServletRequest) req;
		return req1;
	}

	public static HttpServletResponse getResponse(ServletResponse resp) {
		HttpServletResponse resp1 = null;
		resp1 = (HttpServletResponse) resp;
		return resp1;
	}

}
