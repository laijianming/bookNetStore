package service;

import java.util.List;

import javabean.Books;
import javabean.Page;
import javabean.Trolley;
import javabean.User;

public interface CustomerService {

	/**
	 * 按分页查找书籍信息并返回servlet一个page
	 * @param num
	 * @param flag
	 * @return
	 */
	public Page findAllBooks(String num, int flag);

	/**
	 * 按书籍id查询该书
	 * @param id
	 * @return 
	 */
	public Books findbook(String id);

	/**
	 * 存入商品进购物车的数据库
	 * @param list
	 */
	public void addTrolley(List list);

	/**
	 * 往数据库中取该用户的购物车信息
	 * @param user
	 * @return 
	 */
	public List<Trolley> findAllTrolley(String user);

	/**
	 * 往数据库购物车中存入商品的信息
	 * @param t
	 */
	public void addIntoTorlley(Trolley t);

	/**
	 * //验证用户账号密码是否正确
	 * @param user
	 * @return
	 */
	public boolean validate(User user);

	/**
	 * 查询数据库中该用户 是否含有此商品id，有则修改数量
	 * @param userId
	 * @param id
	 * @param num 
	 * @param price 
	 * @return 若含有并修改了，返回true
	 */
	public Boolean updateTrolley(String userId, String id, int num, double price);

	/**
	 * 删除此条购物车记录
	 * @param userId
	 * @param id
	 */
	public void deleteTrolley(String userId, String id);

}
