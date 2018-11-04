package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javabean.Books;
import javabean.Category;
import javabean.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import service.BackstageService;
import service.impl.BackstageServiceImpl;
import Utils.WebUtils;

public class BackstageServlet extends HttpServlet {

	private BackstageService bs = new BackstageServiceImpl();
	private Category category = null;
	private Books book = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		System.out.println("到BackstageServlet中了....");
		// 得到一个PrintWrite对象
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 创建一个Category的javabean对象

		// 其他业务管理操作 open
		if ("addCategory".equals(request.getParameter("open"))) { // 添加分类
			try {
				addCategory(request, response);
			} catch (Exception e) {
				throw new RuntimeException("添加分类失败，请重试！");
			}
		} else if ("findAllCategory".equals(request.getParameter("open"))) { // 查询所有分类
			try {
				findAllCategory(request, response);
			} catch (Exception e) {
				throw new RuntimeException("查询分类失败，请重试！");
			}

		} else if ("addBook".equals(request.getParameter("open"))) { // 添加书籍页面
			try {
				addBook(request, response);
			} catch (Exception e) {
				throw new RuntimeException("添加书籍失败，请重试！");
			}

		} else if ("findAllBooks".equals(request.getParameter("open"))) { // 查询书籍信息
			try {
				findAllBooks(request, response);
			} catch (Exception e) {
				throw new RuntimeException("查询书籍信息失败，请重试！");
			}
		} else if ("login".equals(request.getParameter("open"))) { // 管理员登入
			try {
				login(request, response, out, session);
			} catch (Exception e) {
				throw new RuntimeException("登入失败，请重试！");
			}
		} else if ("logout".equals(request.getParameter("open"))) { // 注销登入
			try {
				logout(request, response, session);
			} catch (Exception e) {
				throw new RuntimeException("注销失败，请重试！");
			}
		}

		/* 管理操作 oper */
		if ("deleteCategory".equals(request.getParameter("oper"))) { // 删除分类
			try {
				deleteCategory(request, response);
			} catch (Exception e) {
				throw new RuntimeException("删除分类失败，请重试！");
			}
		} else if ("modifyCategory".equals(request.getParameter("oper"))) { // 修改分类--->跳到修改的页面
			try {
				modifyCategory(request, response);
			} catch (Exception e) {
				throw new RuntimeException("加载修改分类页面失败，请重试！");
			}
		} else if ("modify".equals(request.getParameter("oper"))) { // 修改分类 --> 修改数据库信息
			try {
				modify(request, response);
			} catch (Exception e) {
				// e.printStackTrace();
				throw new RuntimeException("修改分类失败，请重试！");
			}

		} else if ("saveBook".equals(request.getParameter("oper"))) {// 添加书籍具体操作
			try {
				saveBook(request, response);
			} catch (Exception e) {
				throw new RuntimeException("添加书籍失败，请重试！");
			}
		} else if ("modifyBook".equals(request.getParameter("oper"))) { // 转到修改书籍信息页面 初始化
																		// --->调到修改的页面
			try {
				modifyBook(request, response);
			} catch (Exception e) {
				throw new RuntimeException("查询书籍失败，请重试！");
			}

		} else if ("modifyBookInfo".equals(request.getParameter("oper"))) { // 修改书籍信息 ---> 修改数据库信息
			try {
				modifyBookInfo(request, response);
			} catch (Exception e) {
				throw new RuntimeException("修改书籍信息失败，请重试！");
			}
		} else if ("deleteBook".equals(request.getParameter("oper"))) { // 删除书籍信息
			try {
				deleteBook(request, response);
			} catch (Exception e) {
				throw new RuntimeException("删除书籍信息失败，请重试！");
			}
		}

	}

	// 注销登入
	private void logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		session.removeAttribute("name");
		request.getRequestDispatcher("/customer/index.jsp").forward(request, response);
	}

	// 管理员登入验证
	private void login(HttpServletRequest request, HttpServletResponse response, PrintWriter out, HttpSession session) throws Exception {

		String name = request.getParameter("name");
		String password = request.getParameter("password");
		boolean isTrue = bs.validate(name, password);
		if (isTrue) { // 登入成功后转到后台管理页面
			session.setAttribute("name", name);
			// request.getRequestDispatcher("/manage/welcome.jsp").forward(request, response);
			out.write("/day00_00NetStore/manage/welcome.jsp");
		} else {
			out.write("用户名或密码错误");
			return;
		}
	}

	// 删除书籍信息
	private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		bs.deleteBook(id);
		response.sendRedirect("http://localhost:8080/day00_00NetStore/servlet/BackstageServlet?open=findAllBooks");

	}

	// 修改书籍信息
	private void modifyBookInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request); // 判断用户提交的正文类型是不是multipart/form/data
		boolean flag = false;
		if (!isMultipart) {
			request.setAttribute("msg", "正文类型不是multipart/form/data");
			request.getRequestDispatcher("/manage/fail.jsp").forward(request, response);
			return;
		}

		// 创建DiskFileItemFactory
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload parser = new ServletFileUpload(factory);
		List<FileItem> items = null;
		try {
			items = parser.parseRequest(request); // 解析请求内容 ，返回一个List<FileItem>
		} catch (FileUploadException e1) {
			e1.printStackTrace();
		}

		Books book = new Books();
		// 封装数据
		// 处理请求内容
		for (FileItem item : items) {
			// 封装基本数据到Book中
			if (item.isFormField()) { // 普遍表单字段 一个字段的name 和 value 分别对应下述方法取出
				String fieldName = item.getFieldName(); // 取得标签的 name
				String fieldValue = item.getString(request.getCharacterEncoding()); // 取得标签的 值
																					// 相当于getParameter();
																					// 放入一个编码格式
				try {
					BeanUtils.setProperty(book, fieldName, fieldValue); // 把fieldName属性的值fieldValue封装到book中
				} catch (Exception e) {

				}
			} else { // 上传字段
				flag = true; // 有图片文件上传

				// 文件上传
				String fileName = item.getName(); // 得到上传文件的文件名
				System.out.println("fileName = " + fileName);
				if (fileName != null && !fileName.trim().equals("")) {
					// 改文件名：唯一的文件名
					fileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(fileName);
					// 计算存储路径
					String storeDirectory = getServletContext().getRealPath("/booksImages"); // 绝对路径
					// F:\apache-tomcat-7.0.54\webapps\day00_00NetStore\booksImages

					// 删原源文件
					// 删除源文件时，用getRealPath得到服务器的绝对路径 然后加上文件名 进行删除
					String oldImg = request.getParameter("oldImg");
					oldImg = oldImg.substring(oldImg.lastIndexOf("\\"));
					File file = new File(storeDirectory + "\\" + oldImg);
					if (file.isFile() && file.exists()) {
						boolean succeedDelete = file.delete();
						System.out.println(succeedDelete);
					}

					// String path = makeDirs(storeDirectory, fileName);// /dir1/dir2

					book.setImgPath("booksImages\\" + fileName);

					// 上传
					try {
						System.out.println("到了写文件的地方");
						item.write(new File(storeDirectory + "\\" + fileName)); // 把文件写到文件目录中
						System.out.println("test2 --->" + storeDirectory + "\\" + fileName);
					} catch (Exception e) {
						System.out.println("写入图片失败");
						e.printStackTrace();
					}
				}
			}
			if (!flag) { // 若没有文件上传，则保留原图片
				String imgPath = request.getParameter("oldImg");// 相对路径
				book.setImgPath(imgPath);
			}
		}
		System.out.println(book);
		// 保存书籍信息到数据库中
		bs.modifyBook(book);
		response.sendRedirect("http://localhost:8080/day00_00NetStore/servlet/BackstageServlet?open=findAllBooks");
		return;
	}

	// 转到修改书籍页面 并初始化book 的信息
	private void modifyBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id"); // 得到要修改的书籍的id
		Books book = bs.findBook(id); // 按 要修改book的id 查询book的信息
		// 得到所有分类
		List<Category> list = bs.findAllCategory();
		// HttpSession session = request.getSession();
		// session.setAttribute("cs", list);
		request.setAttribute("cs", list);

		request.setAttribute("book", book);
		System.out.println("修改前的book信息 ---->" + book);
		request.getRequestDispatcher("/manage/modifyBook.jsp").forward(request, response); // 把book的信息带到修改页面
		return;

	}

	// 修改分类
	private void modify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		category = WebUtils.fillBean(Category.class, request);
		bs.modifyCategory(category);
		response.sendRedirect("http://localhost:8080/day00_00NetStore/servlet/BackstageServlet?open=findAllCategory");

		// 为什么转发不行，会报错
		// request.getRequestDispatcher("/servlet/BackstageServlet?open=findAllCategory").forward(request,
		// response);
	}

	// 加载修改修改分类页面
	private void modifyCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String categoryId = request.getParameter("id");
		category = bs.findCategoryById(categoryId);
		if (category == null) { // 若没查询到，则退出
			request.getRequestDispatcher("/manage/fail.jsp").forward(request, response); // 操作失败后转发到
			return;
		}
		request.setAttribute("c", category);
		request.getRequestDispatcher("/manage/modifyCategory.jsp").forward(request, response); // 转发到修改的页面
		return;

	}

	// 删除分类
	private void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String categoryId = request.getParameter("id"); // 得到该分类的id
		// 调用删除分类 的方法
		bs.deleteCategory(categoryId);
		request.getRequestDispatcher("/manage/success.jsp").forward(request, response);
		// Thread.sleep(3000);
		// response.sendRedirect("http://localhost:8080/day00_00NetStore/servlet/BackstageServlet?open=findAllCategory");
		return;
	}

	/* 业务操作 open */

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
		Page page = bs.findAllBooks(num, flag);

		request.setAttribute("books", page);
		request.getRequestDispatcher("/manage/findBooks.jsp").forward(request, response);
		return;
	}

	// 添加书籍
	private void saveBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request); // 判断用户提交的正文类型是不是multipart/form/data
		boolean flag = false;
		if (!isMultipart) {
			request.setAttribute("msg", "正文类型不是multipart/form/data");
			request.getRequestDispatcher("/manage/fail.jsp").forward(request, response);
			return;
		}
		// 创建DiskFileItemFactory
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload parser = new ServletFileUpload(factory);
		List<FileItem> items = null;
		try {
			items = parser.parseRequest(request); // 解析请求内容 ，返回一个List<FileItem>
		} catch (FileUploadException e1) {
			e1.printStackTrace();
		}

		Books book = new Books();
		// 封装数据
		String uuid = UUID.randomUUID() + ""; // 给种类一个id
		book.setId(uuid); // 封装 id
		// 处理请求内容
		for (FileItem item : items) {
			// 封装基本数据到Book中
			if (item.isFormField()) { // 普遍表单字段 一个字段的name 和 value 分别对应下述方法取出
				String fieldName = item.getFieldName(); // 取得标签的 name
				String fieldValue = item.getString(request.getCharacterEncoding()); // 取得标签的 值
																					// 相当于getParameter();
																					// 放入一个编码格式
				try {
					BeanUtils.setProperty(book, fieldName, fieldValue); // 把fieldName属性的值fieldValue封装到book中
				} catch (Exception e) {

				}
			} else { // 上传字段
				flag = true; // 有图片文件上传
				// 文件上传
				String fileName = item.getName();
				if (fileName != null && !fileName.trim().equals("")) {
					// 改文件名：唯一的文件名
					fileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(fileName);
					// 计算存储路径
					String storeDirectory = getServletContext().getRealPath("/booksImages"); // 绝对路径

					// String path = makeDirs(storeDirectory, fileName);// /dir1/dir2

					book.setImgPath("booksImages\\" + fileName);

					// 上传
					try {
						System.out.println("到了写文件的地方");
						item.write(new File(storeDirectory + "\\" + fileName)); // 把文件写到文件目录中
					} catch (Exception e) {
						System.out.println("写入图片失败");
						e.printStackTrace();
					}
				}
			}
			if (!flag) { // 若没有文件上传，则给定一个默认路径
				String imgPath = getServletContext().getRealPath("/booksImages") + "\\1.jpg";// 绝对路径
				book.setImgPath(imgPath);
			}
		}
		System.out.println(book);
		// 保存书籍信息到数据库中
		bs.addBook(book);
		request.getRequestDispatcher("/manage/success.jsp").forward(request, response);
		return;
	}

	// 查询所有分类并加载到添加书籍页面
	private void addBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/* 不分页查询所有分类 */
		List<Category> list = bs.findAllCategory();
		if (list == null) { // 若没查询到，则退出
			request.getRequestDispatcher("/manage/fail.jsp").forward(request, response); // 操作失败后转发到
			return;
		}
		request.setAttribute("cs", list);
		request.getRequestDispatcher("/manage/addBooks.jsp").forward(request, response); // 查询成功后转到查询分类页面
		return;
	}

	// 分页查询分类内容
	private void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/* 不分页查询所有分类 */
		// List<Category> list = bs.findAllCategory();
		// if (list == null) { // 若没查询到，则退出
		// request.getRequestDispatcher("/manage/fail.jsp").forward(request, response); //
		// // 操作失败后转发到
		// // “操作失败页面”
		// return;
		// }
		// request.setAttribute("cs", list);
		// request.getRequestDispatcher("/manage/selectCategory.jsp").forward(request,
		// response); // 查询成功后转到查询分类页面
		// return;

		/* 分页查询所有分类 */
		String num = request.getParameter("num");// 得到用户要看的页码
		// 判断是否为上下翻页
		int flag = 0; // 设立判断上下翻页的标识，1为下一页，-1为上一页
		if ("prePageNum".equals(request.getParameter("turnPage"))) { // 如果是向上翻页，则从
			flag = -1;
		} else if ("nextPageNum".equals(request.getParameter("turnPage"))) {
			flag = 1;
		}

		// 调用查询分页内容的方法
		Page page = bs.showCategoryPage(num, flag);

		request.setAttribute("cs", page);
		request.getRequestDispatcher("/manage/selectCategory.jsp").forward(request, response);
		return;
	}

	// 保存分类信息
	private void addCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		category = new Category();
		// 封装数据
		String uuid = UUID.randomUUID() + ""; // 给种类一个id
		category.setId(uuid); // 封装 id
		category.setName(request.getParameter("name")); // 封装 种类名称
		category.setDescription(request.getParameter("description")); // 封装 种类描述
		// 调用保存分类 的方法
		bs.addCategory(category);

		request.getRequestDispatcher("/manage/success.jsp").forward(request, response); // 保存成功后转发到“保存成功页面”
		return;
	}

	// 为防止一个目录下面出现太多文件，所以使用hash算法打散存储.
	public String makeDirs(String storeDirecotry, String filename) {
		int hashCode = filename.hashCode();
		int dir1 = hashCode & 0xf;
		int dir2 = (hashCode & 0xf0) >> 4;

		String newPath = "/" + dir1 + "/" + dir2;
		File file = new File(storeDirecotry, newPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		return newPath;
	}
}
