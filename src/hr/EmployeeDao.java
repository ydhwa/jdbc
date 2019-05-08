package hr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {

	public List<EmployeeVo> getList(String keyword) {
		List<EmployeeVo> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 1. JDBC Driver(MariaDB) 로딩
			Class.forName("org.mariadb.jdbc.Driver");

			// 2. 연결하기 (설정같은 건 수정하기 쉽게 밖에 변수로 빼놔도 될 것 같다.)
			String url = "jdbc:mariadb://192.168.1.48:3307/employees";
			conn = DriverManager.getConnection(url, "hr", "hr"); // url, username, password

			// 3. sql 준비
			String sql = 	"select emp_no, first_name, last_name, hire_date " +
							"from employees " + 
							"where first_name like ? " +
							"or last_name like ?";	// sql Injection 방지용 바인딩
			pstmt = conn.prepareStatement(sql);

			// 4. 바인딩
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			
			// 5. 쿼리 실행
			// JDBC가 query를 가지고 있음. 따라서 pstmt.executeQuery() 안에 작성해 둔 sql을 넣으면 안됨.
			rs = pstmt.executeQuery();
			
			// 6. 결과 가져오기
			while (rs.next()) {
				Long no = rs.getLong(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String hireDate = rs.getString(4);

				EmployeeVo vo = new EmployeeVo();
				vo.setNo(no);
				vo.setFirstName(firstName);
				vo.setLastName(lastName);
				vo.setHireDate(hireDate);
				
				result.add(vo);
			}

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
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
