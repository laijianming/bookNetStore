package dao;

import java.util.List;

import javabean.Trolley;
import javabean.User;

public interface CustomerDao {

	/**
	 * 分多个 list 集合分装书籍信息 ，再把list封装成一个allList
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<List> findAllBooks(int startIndex, int pageSize);

	/**
	 * 存入商品进购物车的数据库
	 * @param list
	 */
	public void addTrolley(List list);

	/**
	 * 往数据库查找user用户的购物车信息
	 * @param user
	 * @return 
	 */
	public List<Trolley> findUserTrolley(String user);

	/**
	 * 查找书籍信息并封装进list中的trolley对象
	 * @param list
	 * @return
	 */
	public List<Trolley> findBook(List list);

	/**
	 * 往数据库购物车中存入单个商品的信息
	 * @param t
	 */
	public void addIntoTorlley(Trolley t);

	/**
	 * 在数据库中验证用户账号密码是否正确
	 * @param user
	 * @return
	 */
	public boolean validate(User user);

	/**
	 * 在数据库中查找并修改，若没有此记录则返回false
	 * @param userId
	 * @param id
	 * @param sum 
	 * @param price 
	 * @return
	 */
	public boolean updateTrolley(String userId, String id, int sum, double price);

	/**
	 * 删除此条购物车记录
	 * @param userId
	 * @param id
	 */
	public void deleteTrolley(String userId, String id);

}
