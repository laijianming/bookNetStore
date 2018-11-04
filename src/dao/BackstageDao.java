package dao;

import java.util.List;

import javabean.Books;
import javabean.Category;

public interface BackstageDao {

	/**
	 * 保存分类
	 * 
	 * @param category
	 */
	public void save(Category category);

	/**
	 * 查询所有分类
	 * 
	 * @return
	 */
	public List<Category> findAll();

	/**
	 * 按分类id删除
	 * @param id
	 */
	public void deleteCategory(String id);

	/**
	* 按id查找分类
	*/
	public Category findCategoryById(String categoryId);

	/**
	 * 修改分类
	 * @param category 封装修改信息的对象
	 */
	public void modifyCategory(Category category);

	/**
	 * 得到总记录条数
	 * @return
	 */
	public int getTotalRecordsNum();

	/**
	 * 按页码搜索数据库
	 * @param startIndex
	 * @param offset
	 * @return
	 */
	public List<?> getRecords(int startIndex, int offset);

	/**
	 * 把书籍信息存入数据库
	 * @param book 封装了书籍信息的bean对象
	 */
	public void saveBook(Books book);

	/**
	 * 查询所有书籍信息
	 * @param offset 
	 * @param startIndex 
	 * @return
	 */
	public List<?> findAllBooks(int startIndex, int offset);

	
	/**
	 * 得到书籍的总记录条数
	 * @return
	 */
	public int getTotalBooksRecordsNum();

	/**
	 * 按book的id在数据库中查询book的信息
	 * @param id
	 * @return
	 */
	public Books findBook(String id);

	/**
	 * 修改数据库中book的信息
	 * @param book
	 */
	public void modifyBook(Books book);

	/**
	 * 删除书籍信息
	 * @param id
	 */
	public void deleteBook(String id);

	/**
	 * 往数据库查询是否有此管理员
	 * @param name
	 * @param password
	 * @return
	 */
	public boolean findManage(String name, String password);

}
