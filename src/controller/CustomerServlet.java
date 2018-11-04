package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javabean.Books;
import javabean.Page;
import javabean.Trolley;
import javabean.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.BackstageService;
import service.CustomerService;
import service.impl.BackstageServiceImpl;
import service.impl.CustomerServiceImpl;
import Utils.WebUtils;

public class CustomerServlet extends HttpServlet {
	private CustomerService cs = new CustomerServiceImpl();
	private BackstageService bs = new BackstageServiceImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 得到一个PrintWrite对象
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if ("findAllBooks".equals(request.getParameter("open"))) { // 查询书籍信息
			try {
				findAllBooks(request, response);
			} catch (Exception e) {
				throw new RuntimeException("加载书籍失败，请重试！");
			}
		} else if ("detailedInfo".equals(request.getParameter("open"))) { // 按id查询书籍信息并转发到detailedInfo.jsp页面
			try {
				detailedInfo(request, response);
			} catch (Exception e) {
				throw new RuntimeException("查询书籍信息失败，请重试！");
			}
		} else if ("trolley".equals(request.getParameter("open"))) { // 查询购物车信息
			try {
				trolley(request, response);
			} catch (Exception e) {
				throw new RuntimeException("显示购物车失败，请重试！");
			}
		} else if ("addTrolley".equals(request.getParameter("open"))) { // 添加商品进购物车
			try {
				addTrolley(request, response);
			} catch (Exception e) {
				throw new RuntimeException("加入购物车转发到购物车页面失败，请重试！");
			}
		} else if ("login".equals(request.getParameter("open"))) { // 用户登入
			try {
				login(request, response, out);
			} catch (Exception e) {
				throw new RuntimeException("登入失败，请重试！");
			}
		} else if ("deleteTrolley".equals(request.getParameter("open"))) { // 删除此条购物车记录
			try {
				deleteTrolley(request, response);
			} catch (Exception e) {
				throw new RuntimeException("删除商品失败，请重试！");
			}
		} else if ("logout".equals(request.getParameter("open"))) {
			try {
				logout(request, response);
			} catch (Exception e) {

			}
		}
	}

	// 注销登入
	private void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		request.getRequestDispatcher("/customer/index.jsp").forward(request, response);
	}

	// 删除此条购物车记录
	private void deleteTrolley(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		User user = null;
		List list = null;
		Trolley t = null;
		HttpSession session = request.getSession();
		if ((user = (User) session.getAttribute("user")) != null) {
			String userId = user.getUserId();
			cs.deleteTrolley(userId, id);
			request.getRequestDispatcher("/servlet/CustomerServlet?open=trolley").forward(request, response);
			return;
		} else {
			list = (List) session.getAttribute("list");
			for (int i = 0; i < list.size(); i++) { // 便利所有list中的trolley 商品id相同，则删除此记录
				t = (Trolley) list.get(i);
				if (id.equals(t.getId())) {
					list.remove(i);
				}
			}
			session.setAttribute("list", list);
		}
	}

	// 用户登入
	private void login(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws Exception {
		HttpSession session = request.getSession();
		if (request.getParameter("userId") == "" && request.getParameter("userId") == null) {
			out.write("用户名不能为空");
		}
		if (request.getParameter("password") == "" && request.getParameter("password") == null) {
			out.write("密码不能为空");
			return;
		}
		User user = WebUtils.fillBean(User.class, request);
		// 验证用户账号密码是否正确
		boolean isTrue = cs.validate(user);
		System.out.println(isTrue);
		if (isTrue) {
			session.setAttribute("user", user);
			out.write("/day00_00NetStore/customer/index.jsp");
			return;
		}
	}

	// 添加一个商品进购物车中
	private void addTrolley(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = null;// 商品id
		List<Trolley> list = new ArrayList<>();
		Trolley t1 = null;
		User user = new User();
		HttpSession session = request.getSession();
		// 处理表单数据
		Trolley t = WebUtils.fillBean(Trolley.class, request);
		t.setNum(Integer.parseInt(request.getParameter("num")));
		// 查询trolley中商品id的信息
		id = t.getId();
		Books book = bs.findBook(id);
		// 封装购物车商品信息，还没封装用户id信息
		t.setId(id);
		t.setPrice(book.getPrice());
		System.out.println(book.getPrice());
		System.out.println(t.getPrice());
		t.setImgPath(book.getImgPath());
		if ((user = (User) session.getAttribute("user")) != null) { // 判断用户是否登入,登入则将商品直接存入数据库
			String userId = user.getUserId();
			int num = t.getNum();
			// 查询数据库中该用户 是否含有此商品id，有则修改数量
			Boolean flag = cs.updateTrolley(userId, id, num, t.getPrice()); // 若含有并修改了，返回true
			if (flag) {
				request.getRequestDispatcher("/servlet/CustomerServlet?open=trolley").forward(request, response);
				return;
			}
			// 往数据库购物车中存入商品的信息
			t.setUserId(user.getUserId());
			cs.addIntoTorlley(t);
			request.getRequestDispatcher("/servlet/CustomerServlet?open=trolley").forward(request, response);
			return;
		} else {// 未登入
			System.out.println("开始未登入状态时的处理");
			// 加入商品信息进购物车 存在session中
			if (session.getAttribute("trolley") != null) {
				list = (List<Trolley>) session.getAttribute("trolley");
			}
			boolean flag = false;
			for (int i = 0; i < list.size(); i++) {
				t1 = list.get(i); // 原来session中保存的trolley对象
				if (t1.getId().equals(t.getId())) { // 与新传入的trolley对象中的商品id比较
					t1.setNum(t1.getNum() + t.getNum()); // 将后面加入购物车的同一件商品的数量相加
					// list.remove(i);
					// list.add(t);
					flag = true;
					break;
				}
			}
			if (!flag) {
				list.add(t);
			}
			session.setAttribute("trolley", list);
			request.getRequestDispatcher("/servlet/CustomerServlet?open=trolley").forward(request, response);
		}

	}

	// 查询购物车信息 并加载到trolley.jsp页面
	private void trolley(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = new User();
		Trolley t = null;
		String userId = null;
		List<Trolley> list = new ArrayList<Trolley>(); // 购物车信息
		HttpSession session = request.getSession();
		System.out.println("user --->" + session.getAttribute("user"));
		if ((user = (User) session.getAttribute("user")) != null) { // 判断用户是否登入,登入则将session中的数据取出
			userId = user.getUserId(); // 取出用户id信息
			if ((list = (List<Trolley>) session.getAttribute("trolley")) != null) {// 若session中的购物车还有商品，则加入进数据库
				// 取出list中的trolley对象信息，并加入用户id
				for (int i = 0; i < list.size(); i++) {
					t = list.get(i);
					t.setUserId(userId);
				}
				// 将session中的商品加入数据库
				cs.addTrolley(list);
				session.removeAttribute("trolley");
			}
			userId = user.getUserId();
			// 往数据库中取该用户的购物车信息
			list = cs.findAllTrolley(userId);
			request.setAttribute("trolley", list);
			request.getRequestDispatcher("/customer/trolley.jsp").forward(request, response);
			return;
		} else {// 未登入
				// 加入购物车则存在session中
			list = (List<Trolley>) session.getAttribute("trolley");
			request.setAttribute("trolley", list);
			request.getRequestDispatcher("/customer/trolley.jsp").forward(request, response);
			return;
		}

	}

	// 按id查询书籍信息并转发到detailedInfo.jsp页面
	private void detailedInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		Books book = cs.findbook(id);
		request.setAttribute("book", book);
		request.getRequestDispatcher("/customer/detailedInfo.jsp").forward(request, response);
	}

	// 查询所有书籍信息
	private void findAllBooks(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/* 分页查询所有书籍 */
		String num = request.getParameter("num");// 得到用户要看的页码
		// 判断是否为上下翻页
		int flag = 0; // 设立判断上下翻页的标识，1为下一页，-1为上一页
		if ("prePageNum".equals(request.getParameter("turnPage"))) {
			flag = -1;
		} else if ("nextPageNum".equals(request.getParameter("turnPage"))) {
			flag = 1;
		}
		// 调用查询分页内容的方法
		Page page = cs.findAllBooks(num, flag);
		request.setAttribute("books", page);
		request.getRequestDispatcher("/customer/IBook.jsp").forward(request, response);
		return;
	}

}