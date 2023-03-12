package numberDirectoryProject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class DBConnection {
	private Connection connection = null;

	public void connect() {
		Properties properties = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(
					"C:/SelfStudy.java/numberDirectoryProject/src/numberDirectoryProject/db.properties");
			properties.load(fis);
		} catch (FileNotFoundException e) {
			System.out.println("FileInputStream error" + e.getStackTrace());
		} catch (IOException e) {
			System.out.println("Properties.load error" + e.getStackTrace());
		}
		try {
			Class.forName(properties.getProperty("driverName"));
			connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"),
					properties.getProperty("password"));
		} catch (ClassNotFoundException e) {
			System.err.println("[데이터베이스 로드 오류]" + e.getStackTrace());
		} catch (SQLException e) {
			System.err.println("[데이터베이스 연결 오류]" + e.getStackTrace());
		}
	}

	public int insert(NumberDirectory numberDirectory) {
		this.connect();
		PreparedStatement ps = null;
		int returnValue = -1;
		String query = "insert into numberTBL values(null,?,?,?,?,?)";
		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, numberDirectory.getName());
			ps.setInt(2, numberDirectory.getAge());
			ps.setString(3, numberDirectory.getGender());
			ps.setString(4, numberDirectory.getNum());
			ps.setString(5, numberDirectory.getAddress());
			returnValue = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("insert 오류 발생" + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("ps close 오류" + e.getMessage());
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("connection close 오류" + e.getMessage());
			}
		}
		System.out.println("삽입성공");
		return returnValue;
	}

	public ArrayList<NumberDirectory> select() {
		ArrayList<NumberDirectory> list = new ArrayList<NumberDirectory>();
		this.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select * from numberTBL";
		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			}
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String gender = rs.getString("gender");
				String num = rs.getString("num");
				String address = rs.getString("address");
				list.add(new NumberDirectory(id, name, age, gender, num, address));
			}
		} catch (Exception e) {
			System.out.println("select 오류 발생" + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("ps close 오류" + e.getMessage());
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("connection close 오류" + e.getMessage());
			}
		}
		return list;
	}

	public ArrayList<NumberDirectory> nameSearchSelect(String dataName) {
		ArrayList<NumberDirectory> list = new ArrayList<>();
		this.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select * from numberTBL where name like ?";

		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, "%" + dataName + "%");
			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			}
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String gender = rs.getString("gender");
				String num = rs.getString("num");
				String address = rs.getString("address");

				list.add(new NumberDirectory(id, name, age, gender, num, address));
			}
		} catch (Exception e) {
			System.out.println("select 오류발생" + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("ps close 오류" + e.getMessage());
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("connection close 오류" + e.getMessage());
			}
		}
		return list;
	}

	public NumberDirectory selectId(int dataId) {
		NumberDirectory numberDirectory = null;
		this.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select * from numberTBL where id = ?";

		try {
			ps = connection.prepareStatement(query);
			ps.setInt(1, dataId);
			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			}
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String gender = rs.getString("gender");
				String num = rs.getString("num");
				String address = rs.getString("address");

				numberDirectory = new NumberDirectory(id, name, age, gender, num, address);
			}
		} catch (Exception e) {
			System.out.println("select id 오류발생" + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("ps close 오류" + e.getMessage());
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("connection close 오류" + e.getMessage());
			}
		}
		return numberDirectory;
	}

	public int update(NumberDirectory updateNumberDirectory) {
		this.connect();
		PreparedStatement ps = null;
		int returnValue = -1;
		String query = "update numberTBL set age = ?, num = ?, address = ? where id = ?";

		try {
			ps = connection.prepareStatement(query);
			ps.setInt(1, updateNumberDirectory.getAge());
			ps.setString(2, updateNumberDirectory.getNum());
			ps.setString(3, updateNumberDirectory.getAddress());
			ps.setInt(4, updateNumberDirectory.getId());
			returnValue = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("update 오류발생" + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("ps close 오류" + e.getMessage());
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("connection close 오류" + e.getMessage());
			}
		}
		return returnValue;
	}

	public ArrayList<NumberDirectory> selectSort() {
		ArrayList<NumberDirectory> list = new ArrayList<>();
		this.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "select * from numberTBL order by age desc";

		try {
			ps = connection.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs == null) {
				return null;
			}
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String gender = rs.getString("gender");
				String num = rs.getString("num");
				String address = rs.getString("address");
				list.add(new NumberDirectory(id, name, age, gender, num, address));
			}
		} catch (Exception e) {
			System.out.println("select 오류발생" + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("ps close 오류" + e.getMessage());
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("connection close 오류" + e.getMessage());
			}
		}
		return list;
	}

	public int delete(int deleteId) {
		this.connect();
		PreparedStatement ps = null;
		int returnValue = -1;
		String query = "delete from numberTBL where id = ?";
		try {
			ps = connection.prepareStatement(query);
			ps.setInt(1, deleteId);
			returnValue = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("insert 오류발생" + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("ps close 오류" + e.getMessage());
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("connection close 오류" + e.getMessage());
			}
		}
		return returnValue;
	}

}
