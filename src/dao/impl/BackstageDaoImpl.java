package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javabean.Books;
import javabean.Category;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import Utils.C3P0Utils;
import Utils.DBCPUtils;
import Utils.JDBCUtils;
import dao.BackstageDao;
import exception.DaoException;

public class BackstageDaoImpl implements BackstageDao {

	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	private QueryRunner qr = new QueryRunner();

	// 添加分类
	@Override
	public void save(Category category) {
		String sql = "INSERT INTO category VALUES (?,?,?)";
		Object[] params = { category.getId(), category.getName(),
				category.getDescription() };
		try {
			conn = DBCPUtils.getConection();
			qr.update(conn, sql, params);
			System.out.println("保存分类成功");
		} catch (Exception e) {
			System.out.println("添加分类失败");
			throw new DaoException();
		} finally {
			DBCPUtils.relese(conn, stmt, rs);
		}
	}

	// 查询所有分类，不分页显示
	@Override
	public List<Category> findAll() {
		String sql = "SELECT * FROM category";
		try {
			conn = DBCPUtils.getConection();
			List<Category> result = qr.query(conn, sql,
					new BeanListHandler<Category>(Category.class)); // 取出数据库的值并放入List集合中
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("查询所有分类失败");
		} finally {
			DBCPUtils.relese(conn, stmt, rs);
		}
		return null;
	}

	// 按id 删除分类
	@Override
	public void deleteCategory(String id) {
		try {
			conn = JDBCUtils.getConnection(); // 得到数据库连接
			String sql = "DELETE FROM category WHERE id=? ";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("删除分类失败");
		} finally {
			JDBCUtils.relese(conn, stmt, null);
		}

	}

	// 按id查找分类,给修改分类用
	@Override
	public Category findCategoryById(String categoryId) {
		Category category = new Category();
		try {
			conn = C3P0Utils.getConnection();
			String sql = "select * from category WHERE id = ?";
			Object[] result = qr.query(conn, sql, categoryId,
					new ArrayHandler());// 取出对象数组
			// 封装数据
			if (result.length == 3) {
				category.setId((String) result[0]);
				category.setName((String) result[1]);
				category.setDescription((String) result[2]);
			}
			return category;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				C3P0Utils.relese(conn, stmt, rs);
			} catch (SQLException e) {
				System.out.println("C3P0释放资源失败");
			}
		}
		return null;
	}

	// 修改种类信息
	@Override
	public void modifyCategory(Category category) {
		try {
			conn = DBCPUtils.getConection();
			// UPDATE 表名 SET 字段名1=字段值1,...,字段名n=字段值n WHERE 查询条件;
			String sql = "UPDATE category SET name = ?, description = ? WHERE id = ? ";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, category.getName());
			stmt.setString(2, category.getDescription());
			stmt.setString(3, category.getId());
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("修改分类失败");

		} finally {
			DBCPUtils.relese(conn, stmt, rs);
		}
	}

	// 得到数据库中category的总记录条数
	@Override
	public int getTotalRecordsNum() {
		try {
			conn = DBCPUtils.getConection();
			String sql = "SELECT count(*) FROM category";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} catch (Exception e) {
			System.out.println("查询总记录失败");
			e.printStackTrace();
		} finally {
			DBCPUtils.relese(conn, stmt, rs);
		}
		return 0;
	}

	// 往数据库按指定索引搜索内容,按分页查询内容 category
	@Override
	public List<?> getRecords(int startIndex, int offset) {

		try {
			conn = DBCPUtils.getConection();
			String sql = "SELECT * FROM category LIMIT ?,?";// 从第一个？开始，一共现实第二个？参数的条数
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, startIndex);
			stmt.setInt(2, offset);
			rs = stmt.executeQuery();
			// 创建一个Category的List 进行封装bean对象数据并放在集合中
			List<Category> list = new ArrayList<>();
			while (rs.next()) {
				Category c = new Category();
				c.setId(rs.getString("id"));
				c.setName(rs.getString("name"));
				c.setDescription(rs.getString("description"));
				list.add(c);
			}
			return list;
		} catch (Exception e) {
			throw new RuntimeException("数据库分页查询信息失败");
		} finally {
			DBCPUtils.relese(conn, stmt, rs);
		}
	}

	// 把book对象中的信息存入数据库
	@Override
	public void saveBook(Books book) {

		try {
			conn = DBCPUtils.getConection();
			String sql = "INSERT INTO books VALUES (?,?,?,?,?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, book.getId());
			stmt.setString(2, book.getName());
			stmt.setString(3, book.getAuthor());
			stmt.setDouble(4, book.getPrice());
			stmt.setString(5, book.getImgPath());
			stmt.setString(6, book.getDescription());
			stmt.setString(7, book.getCategory());
			stmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException("添加书籍信息进数据库失败");
		} finally {
			DBCPUtils.relese(conn, stmt, rs);
		}

	}

	// 往数据库中分页查找Books
	@Override
	public List<?> findAllBooks(int startIndex, int offset) {
		try {
			conn = DBCPUtils.getConection();
			String sql = "SELECT * FROM books LIMIT ?,?";// 从第一个？开始，一共现实第二个？参数的条数
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, startIndex);
			stmt.setInt(2, offset);
			rs = stmt.executeQuery();
			// 创建一个Books的List 进行封装 bean对象数据并放在集合中
			List<Books> list = new ArrayList<>();
			while (rs.next()) {
				Books book = new Books();
				book.setId(rs.getString(1));
				book.setName(rs.getString(2));
				book.setAuthor(rs.getString(3));
				book.setPrice(rs.getDouble(4));
				book.setImgPath(rs.getString(5));
				book.setDescription(rs.getString(6));
				book.setCategory(rs.getString(7));
				list.add(book);
			}
			return list;
		} catch (Exception e) {
			throw new RuntimeException("数据库分页查询信息失败");
		} finally {
			DBCPUtils.relese(conn, stmt, rs);
		}
	}

	@Override
	public int getTotalBooksRecordsNum() {
		try {
			conn = DBCPUtils.getConection();
			String sql = "SELECT count(*) FROM books";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} catch (Exception e) {
			System.out.println("查询总书籍记录失败");
			throw new RuntimeException("查询总书籍记录失败");
		} finally {
			DBCPUtils.relese(conn, stmt, rs);
		}
	}

	@Override
	public Books findBook(String id) {
		try {
			Books book = new Books();
			conn = DBCPUtils.getConection();
			String sql = "SELECT * FROM books WHERE id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				book.setId(rs.getString(1));
				book.setName(rs.getString(2));
				book.setAuthor(rs.getString(3));
				book.setPrice(rs.getDouble(4));
				book.setImgPath(rs.getString(5));
				book.setDescription(rs.getString(6));
				book.setCategory(rs.getString(7));
			}
			return book;
		} catch (Exception e) {
			System.out.println("查询书籍信息失败");
			throw new RuntimeException("查询书籍信息失败");
		} finally {
			DBCPUtils.relese(conn, stmt, rs);
		}
	}

	// 修改数据库中book的信息
	@Override
	public void modifyBook(Books book) {
		try {
			conn = DBCPUtils.getConection();
			// UPDATE 表名 SET 字段名1=字段值1,...,字段名n=字段值n WHERE 查询条件;
			String sql = "UPDATE books SET name = ?, author=?,  price=?, imgPath=?,description = ?,category=?  WHERE id = ? ";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, book.getName());
			stmt.setString(2, book.getAuthor());
			stmt.setDouble(3, book.getPrice());
			stmt.setString(4, book.getImgPath());
			stmt.setString(5, book.getDescription());
			stmt.setString(6, book.getCategory());
			stmt.setString(7, book.getId());
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("修改书籍信息失败");

		} finally {
			DBCPUtils.relese(conn, stmt, rs);
		}
	}

	// 删除数据库中指定id的书籍信息
	@Override
	public void deleteBook(String id) {
		try {
			conn = DBCPUtils.getConection();
			String sql = "DELETE FROM books WHERE id=? ";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException("删除书籍信息失败");
		}

	}

	// 往数据库查询是否有此管理员
	@Override
	public boolean findManage(String name, String password) {
		try {
			conn = DBCPUtils.getConection();
			String sql = "SELECT * FROM manager WHERE name=? and password=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if (rs.next()) { // 若能查询到，则返回true ，否则返回false
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new RuntimeException("管理员登入失败");
		}

	}
}
