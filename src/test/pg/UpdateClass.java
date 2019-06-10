package test.pg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateClass {

	public static void main(String[] args) {
		boolean result = update(1L, "동화");
		if(result) {
			System.out.println("업데이트 성공!");
		}
	}

	public static boolean update(Long no, String name) {
		boolean result = false;
		Connection conn = null;
		Statement stmt = null;

		// update나 delete는 ResultSet이 필요 없다.
		try {
			// 1. JDBC Driver(MariaDB) 로딩
			Class.forName("org.mariadb.jdbc.Driver");

			// 2. 연결하기
			String url = "jdbc:postgresql://192.168.1.48:5432/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb"); // url, username, password

			// 3. Statement 객체 생성
			stmt = conn.createStatement();

			// 4. SQL문 실행
			String sql = "update author set name='" + name + "' where no=" + no;
			int count = stmt.executeUpdate(sql);
			result = count == 1;
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
}
