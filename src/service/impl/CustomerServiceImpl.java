package service.impl;

import java.util.List;

import javabean.Books;
import javabean.Page;
import javabean.Trolley;
import javabean.User;
import service.CustomerService;
import dao.BackstageDao;
import dao.CustomerDao;
import dao.impl.BackstageDaoImpl;
import dao.impl.CustomerDaoImpl;

public class CustomerServiceImpl implements CustomerService {
	private BackstageDao bs = new BackstageDaoImpl();
	private CustomerDao cd = new CustomerDaoImpl();

	// 分页查询书籍信息
	@Override
	public Page findAllBooks(String pageNum, int flag) {
		int num = 1;// 用户要看的页码,默认是1
		int pageSize = 12; // 每页显示的条数
		if (pageNum != null && !pageNum.trim().equals("")) {// 解析用户要看的页码
			num = Integer.parseInt(pageNum);
		}
		int totalRecords = bs.getTotalBooksRecordsNum();// 得到总记录的条数
		Page page = new Page(num, totalRecords, pageSize);// 对象创建出来后,当前页码、总页数、每页开始的索引和总记录条数已经计算完毕

		// 判断是否为上下翻页
		if (flag != 0) {
			if (flag == -1) {
				num = page.getPrePageNum();
				page = new Page(num, totalRecords, pageSize); // 对初始数据重新封装
			} else if (flag == 1) {
				num = page.getNextPageNum();
				page = new Page(num, totalRecords, pageSize); // 对初始数据重新封装
			}
		}
		// 查询分页记录
		List<List> records = (List<List>) cd.findAllBooks(page.getStartIndex(), page.getPageSize());
		page.setRecords(records);
		return page;
	}

	// 按书籍id查找该书
	@Override
	public Books findbook(String id) {
		Books book = bs.findBook(id);
		return book;
	}

	// 存入商品进购物车的数据库
	@Override
	public void addTrolley(List list) {
		cd.addTrolley(list);

	}

	// 往数据库中取该用户的购物车信息
	@Override
	public List<Trolley> findAllTrolley(String userId) {
		// 往数据库查找user用户的购物车信息
		List<Trolley> list = cd.findUserTrolley(userId);
		// 继续封装购物车商品的信息
		list = cd.findBook(list);
		return list;

	}

	// 往数据库购物车中存入单个商品的信息
	@Override
	public void addIntoTorlley(Trolley t) {
		cd.addIntoTorlley(t);
	}

	// 验证用户账号密码是否正确
	@Override
	public boolean validate(User user) {
		// 若能查询到，则证明账号密码正确
		boolean isTrue = cd.validate(user);
		return isTrue;
	}

	// 查询数据库中该用户 是否含有此商品id，有则修改数量
	@Override
	public Boolean updateTrolley(String userId, String id, int sum, double price) {
		boolean flag = cd.updateTrolley(userId, id, sum, price);// 在数据库中查找并修改，若没有此记录则返回false
		return flag;
	}

	// 删除此条购物车记录
	@Override
	public void deleteTrolley(String userId, String id) {
		cd.deleteTrolley(userId, id);
	}

}
