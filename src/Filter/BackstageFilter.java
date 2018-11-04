package Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BackstageFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	/**
	 * 对请求和响应进行一些过滤
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = FilterUtil.getRequest(request);
		HttpServletResponse resp = FilterUtil.getResponse(response);
		// 设置从request中取得的值或从数据库中取出的值
		req.setCharacterEncoding("UTF-8");
		// 设置禁止缓存页面
		resp.setDateHeader("Expires", 0);
		resp.setHeader("Cache-Control", "no-cache");
		resp.setHeader("Pragma", "No-cacha");
		resp.setCharacterEncoding("UTF-8");
		// 设置页面输出编码格式
		resp.setContentType("text/html;charset=UTF-8");
		// resp.setContentType("text/plain;charset=UTF-8"); //告知客户端是纯文本

		chain.doFilter(req, resp);

	}

	@Override
	public void destroy() {

	}

}
