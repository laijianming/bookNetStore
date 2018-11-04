package service;

import java.util.List;

import javabean.Books;
import javabean.Category;
import javabean.Page;

public interface BackstageService {

	/**
	 * 添加分类
	 * 
	 * @param category
	 */
	public void addCategory(Category category);

	/**
	 * 查询分类
	 * 
	 * @return
	 */
	public List<Category> findAllCategory();

	/**
	 * 根据id查找分类
	 * 
	 * @param categoryId
	 * @return
	 */
	public Category findCategoryById(String categoryId);

	/**
	 * 根据id删除分类
	 * @param categoryId
	 */
	public void deleteCategory(String categoryId);

	/**
	 * 修改分类信息
	 * @param category
	 */
	public void modifyCategory(Category category);

	/**
	 * 
	 * @param flag 
	 * @return
	 */
	public Page showCategoryPage(String pageNum, int flag);

	/**
	 * 保存书籍的信息
	 * @param book	封装了书籍信息的bean对象
	 */
	public void addBook(Books book);

	/**
	 * 查询所有书籍信息
	 * @return
	 */
	public Page findAllBooks(String pageNum, int flag);

	
	/**
	 * 按id查找book的信息
	 * @param id
	 * @return
	 */
	public Books findBook(String id);

	/**
	 * 修改书籍信息
	 * @param book
	 */
	public void modifyBook(Books book);

	/**
	 * 删除书籍信息
	 * @param id
	 */
	public void deleteBook(String id);

	/**
	 * 验证管理员信息
	 * @param name
	 * @param password
	 * @return
	 */
	public boolean validate(String name, String password);
}
