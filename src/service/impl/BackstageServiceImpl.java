package service.impl;

import java.util.List;

import javabean.Books;
import javabean.Category;
import javabean.Page;
import service.BackstageService;
import dao.BackstageDao;
import dao.impl.BackstageDaoImpl;

public class BackstageServiceImpl implements BackstageService {

	BackstageDao bd = new BackstageDaoImpl();

	// 添加分类
	@Override
	public void addCategory(Category category) {
		bd.save(category);
	}

	// 查询所有分类
	@Override
	public List<Category> findAllCategory() {
		List<Category> list = bd.findAll();
		return list;
	}

	// 按分类id查询分类
	@Override
	public Category findCategoryById(String categoryId) {
		Category category = bd.findCategoryById(categoryId);
		return category;
	}

	// 删除分类
	@Override
	public void deleteCategory(String categoryId) {
		bd.deleteCategory(categoryId);

	}

	// 修改分类信息
	@Override
	public void modifyCategory(Category category) {
		bd.modifyCategory(category);
		
	}

	// 分页查看分类信息 flag是上下分页的标识符,0为不翻页
	@Override
	public Page showCategoryPage(String pageNum, int flag) {
		int num = 1;// 用户要看的页码,默认是1
		if (pageNum != null && !pageNum.trim().equals("")) {// 解析用户要看的页码
			num = Integer.parseInt(pageNum);
		}
		int totalRecords = bd.getTotalRecordsNum();// 得到总记录的条数
		Page page = new Page(num, totalRecords);// 对象创建出来后,当前页码、总页数、每页开始的索引和总记录条数已经计算完毕

		// 判断是否为上下翻页
		if (flag != 0) {
			if (flag == -1) {
				num = page.getPrePageNum();
				page = new Page(num, totalRecords); // 对初始数据重新封装
			} else if (flag == 1) {
				num = page.getNextPageNum();
				page = new Page(num, totalRecords); // 对初始数据重新封装
			}
		}
		// 查询分页记录
		List<Category> records = (List<Category>) bd.getRecords(page.getStartIndex(), page.getPageSize());
		page.setRecords(records);
		return page;
	}

	// 保存书籍信息
	@Override
	public void addBook(Books book) {
		// 调用保存书籍信息进数据库的方法
		bd.saveBook(book);
	}

	/**
	 * 分页查询所有书籍
	 * @param pageNum
	 * @param flag
	 * @return
	 */
	@Override
	public Page findAllBooks(String pageNum, int flag) {
		int num = 1;// 用户要看的页码,默认是1
		int pageSize = 5;	//每页显示的条数
		if (pageNum != null && !pageNum.trim().equals("")) {// 解析用户要看的页码
			num = Integer.parseInt(pageNum);
		}
		int totalRecords = bd.getTotalBooksRecordsNum();// 得到总记录的条数
		Page page = new Page(num, totalRecords,pageSize);// 对象创建出来后,当前页码、总页数、每页开始的索引和总记录条数已经计算完毕

		// 判断是否为上下翻页
		if (flag != 0) {
			if (flag == -1) {
				num = page.getPrePageNum();
				page = new Page(num, totalRecords,pageSize); // 对初始数据重新封装
			} else if (flag == 1) {
				num = page.getNextPageNum();
				page = new Page(num, totalRecords,pageSize); // 对初始数据重新封装
			}
		}
		// 查询分页记录
		List<Books> records = (List<Books>) bd.findAllBooks(page.getStartIndex(), page.getPageSize());
		page.setRecords(records);
		return page;
	}

	//按id查找book的信息
	@Override
	public Books findBook(String id) {
		Books book = new Books();
		book = bd.findBook(id);
		return book;
	}

	//修改书籍信息
	@Override
	public void modifyBook(Books book) {
		bd.modifyBook(book);
		
	}

	@Override
	public void deleteBook(String id) {
		bd.deleteBook(id);
	}

	//验证管理员信息
	@Override
	public boolean validate(String name, String password) {
		boolean isTrue = bd.findManage(name,password);
		return isTrue;
	}

}
