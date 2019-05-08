package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectTest {

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			// 1. JDBC Driver(MariaDB) 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			// 2. 연결하기
			String url = "jdbc:mariadb://192.168.1.48:3307/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb"); // url, username, password
			
			// 3. Statement 객체 생성
			stmt = conn.createStatement();
			
			// 4. SQL문 실행
			String sql = "select no, name from department";
			/* 여러 쿼리 날릴 때 ;로 구분. 쿼리 하나만 때릴 때는 ; 쓰지 않기
			 * 필요한 것만 가져오기
			 * password 같은 건 가져오지 않기
			 */
			rs = stmt.executeQuery(sql);
			
			// 5. 결과 가져오기
			while(rs.next()) {
				// 컬럼은 0번부터가 아니라 1번부터 시작한다.
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				
				System.out.printf("%d: %s\n", no, name);
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
