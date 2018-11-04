package Utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

public class WebUtils {

	public static <T> T fillBean(Class<T> clazz, HttpServletRequest request) {
		// 上一层不能处理异常,所以用try/catch处理
		try {
			// 用BeanUtil来进行填充JavaBean；
			T bean = clazz.newInstance();
			BeanUtils.populate(bean, request.getParameterMap());

			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
