package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javabean.Books;
import javabean.Trolley;
import javabean.User;

import org.apache.commons.dbutils.QueryRunner;

import Utils.DBCPUtils;
import Utils.JDBCUtils;
import dao.CustomerDao;

public class CustomerDaoImpl implements CustomerDao {
	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	private QueryRunner qr = new QueryRunner();

	// 分多个 list 集合分装书籍信息 ，再把list封装成一个allList
	@Override
	public List<List> findAllBooks(int startIndex, int offset) {
		int item = 3; // 每个list封装的条数
		try {
			conn = DBCPUtils.getConection();
			String sql = "SELECT * FROM books LIMIT ?,?";// 从第一个？开始，一共现实第二个？参数的条数
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, startIndex);
			stmt.setInt(2, offset);
			rs = stmt.executeQuery();
			// 创建一个List 分装 “封装了3条记录的list”
			List<List> allList = new ArrayList<>();
			// 穿建一个封装指定条数的list
			List<Books> list = null;
			for (int i = 0; rs.next(); i++) {
				if (i % 3 == 0) {
					if (list != null) {
						allList.add(list);
					}
					// 创建一个Books的List 进行封装 bean对象数据并放在集合中
					list = new ArrayList<>();
				}
				// 封装数据
				Books book = new Books(); // 创建一个新的对象，封装不同书籍的信息
				book.setId(rs.getString(1));
				book.setName(rs.getString(2));
				book.setAuthor(rs.getString(3));
				book.setPrice(rs.getDouble(4));
				book.setImgPath(rs.getString(5));
				book.setDescription(rs.getString(6));
				book.setCategory(rs.getString(7));
				list.add(book);

			}
			if (list != null) {
				allList.add(list);
			}

			return allList;
		} catch (Exception e) {
			throw new RuntimeException("数据库分页查询信息失败");
		} finally {
			DBCPUtils.relese(conn, stmt, rs);
		}
	}

	// 存入多个商品进购物车的数据库
	@Override
	public void addTrolley(List list) {
		try {
			conn = DBCPUtils.getConection();
			conn.setAutoCommit(false); // 相当于start transaction
			String sql = "INSERT INTO user_trolley VALUES (?,?,?,?)";
			for (int i = 0; i < list.size(); i++) { // 往数据库插入数据
				stmt = conn.prepareStatement(sql);
				Trolley t = new Trolley();
				t = (Trolley) list.get(i);
				stmt.setString(1, t.getUserId());
				stmt.setString(2, t.getId());
				stmt.setInt(3, t.getNum());
				stmt.setDouble(4, t.getTotalPrice());
				stmt.executeUpdate();
			}
			conn.commit(); // 提交事务
		} catch (Exception e) {
			throw new RuntimeException("存入商品进购物车失败");
		} finally {
			DBCPUtils.relese(conn, stmt, rs);
		}

	}

	// 往数据库查找user用户的购物车信息
	@Override
	public List<Trolley> findUserTrolley(String userId) {
		List<Trolley> list = new ArrayList<>();
		Trolley t1 = null;
		try {
			conn = DBCPUtils.getConection();
			String sql = "SELECT * FROM user_trolley WHERE (userId=?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Trolley t = new Trolley();
				t.setUserId(userId);
				t.setId(rs.getString(2)); // 封装书籍id
				t.setNum(rs.getInt(3));
				t.setTotalPrice(rs.getDouble(4));
				list.add(t);
			}
			return list;
		} catch (Exception e) {
			System.out.println("查询用户购物车信息失败");
		} finally {
			DBCPUtils.relese(conn, stmt, rs);
		}
		return null;
	}

	// 查找书籍信息并封装进list中的trolley对象
	@Override
	public List<Trolley> findBook(List list) {
		try {
			conn = DBCPUtils.getConection();
			List<Trolley> allList = new ArrayList<>();
			Trolley t = new Trolley();
			Trolley t1 = null;
			String Id = null;
			for (int i = 0; i < list.size(); i++) { // 遍历出list中的trolley对象
				t = (Trolley) list.get(i);
				Id = t.getId(); // 查询该书籍的信息
				String sql = "SELECT * FROM books WHERE id=?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, Id);
				rs = stmt.executeQuery();
				if (rs.next()) {
					t.setName(rs.getString(2));
					t.setPrice(rs.getDouble(4));
					t.setImgPath(rs.getString(5));
					allList.add(t);
				}
			}
			return allList; // 返回封装完全部信息的list
		} catch (Exception e) {
			throw new RuntimeException("查询书籍信息失败");
		} finally {
			DBCPUtils.relese(conn, stmt, rs);
		}
	}

	// 往数据库购物车中存入单个商品的信息
	@Override
	public void addIntoTorlley(Trolley t) {

		try {
			conn = DBCPUtils.getConection();
			String sql = "INSERT INTO user_trolley VALUES (?,?,?,?)";
			// 往数据库插入数据
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, t.getUserId());
			stmt.setString(2, t.getId());
			stmt.setInt(3, t.getNum());
			stmt.setDouble(4, t.getTotalPrice());
			stmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException("存入商品进购物车失败");
		} finally {
			DBCPUtils.relese(conn, stmt, rs);
		}
	}

	@Override
	public boolean validate(User user) {
		try {
			conn = DBCPUtils.getConection();
			String sql = "SELECT * FROM user WHERE userId=? AND password=?"; // 在数据库中查找是否有此账号密码
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getUserId());
			stmt.setString(2, user.getPassword());
			rs = stmt.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false; // 返回不存在此账号密码
		} catch (Exception e) {
			throw new RuntimeException("查询用户失败");
		} finally {
			DBCPUtils.relese(conn, stmt, rs);
		}
	}

	// 在数据库中查找并修改，若没有此记录则返回false
	@Override
	public boolean updateTrolley(String userId, String id, int num1, double price) { // sum为后追加数量
		try {
			boolean flag = false;
			int num = 0;
			double totalPrice = 0;
			conn = DBCPUtils.getConection();
			String sql = "SELECT * FROM user_trolley WHERE userId=? AND id=?"; // 在数据库中查找是否有此商品
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			stmt.setString(2, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				flag = true;
				num = num1 + rs.getInt(3);
				totalPrice = num * price;
			} else {
				return false;
			}
			sql = "UPDATE user_trolley SET num=? , totalPrice=? where userId=? AND id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, num);
			stmt.setDouble(2, totalPrice);
			stmt.setString(3, userId);
			stmt.setString(4, id);
			stmt.executeUpdate();
			return flag; // 返回不存在此账号密码
		} catch (Exception e) {
			throw new RuntimeException("查询用户失败");
		} finally {
			DBCPUtils.relese(conn, stmt, rs);
		}
	}

	// 删除此条购物车记录
	@Override
	public void deleteTrolley(String userId, String id) {

		try {
			conn = JDBCUtils.getConnection(); // 得到数据库连接
			String sql = "DELETE FROM user_trolley WHERE userId=? AND id=? ";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			stmt.setString(2, id);
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("删除分类失败");
		} finally {
			JDBCUtils.relese(conn, stmt, null);
		}

	}

}
