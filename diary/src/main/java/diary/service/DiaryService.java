package diary.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import diary.util.DataConnection;

public class DiaryService {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public void addDiary(String title, String content) {
		try {
			conn = DataConnection.getConnection();
			String sql = "INSERT INTO diary (title, content) VALUES(?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, title);
			ps.setString(2, content);
			int count = ps.executeUpdate();
			
			if(count == 0) {
				throw new  SQLException("생성 실패");
			}
			
			System.out.println("생성 성공");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) conn.close();
				if(ps != null) ps.close();
				if(rs != null) rs.close();

			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void getAllDiaries() {
		try {
			conn = DataConnection.getConnection();
			String sql = "SELECT * FROM diary";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getInt("ID"));
				System.out.println(rs.getString("title"));
				System.out.println(rs.getString("content"));
				System.out.println(rs.getString("write_date"));
			};
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) conn.close();
				if(ps != null) ps.close();
				if(rs != null) rs.close();

			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void getOneDiary(int id) {
		try {
			conn = DataConnection.getConnection();
			String sql = "SELECT * FROM diary WHERE id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				System.out.println(rs.getInt("ID"));
				System.out.println(rs.getString("title"));
				System.out.println(rs.getString("content"));
				System.out.println(rs.getString("write_date"));
			} else {
				System.out.println("없는 일기 번호입니다.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		} finally {
			try {
				if(conn != null) conn.close();
				if(ps != null) ps.close();
				if(rs != null) rs.close();

			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void getRemoveDiary(int id) {
		try {
			conn = DataConnection.getConnection();
			String sql = "DELETE FROM diary WHERE id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			int count = ps.executeUpdate();
			
			if(count != 0) {
				System.out.println("정상적으로 삭제되었습니다.");
			} else {
				System.out.println("없는 일기 번호입니다.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		} finally {
			try {
				if(conn != null) conn.close();
				if(ps != null) ps.close();
				if(rs != null) rs.close();

			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateDiary(int id,String title, String content) {
		// TODO Auto-generated method stub
		try {
			conn = DataConnection.getConnection();
			String sql = "UPDATE diary SET title = ?, content = ? WHERE id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, title);
			ps.setString(2, content);
			ps.setInt(3, id);
			int count = ps.executeUpdate();
			
			if(count == 0) {
				throw new  SQLException("수정 실패");
			}
			
			System.out.println("수정 성공");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) conn.close();
				if(ps != null) ps.close();
				if(rs != null) rs.close();

			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void serviceDiary(String kwd) {
		// TODO Auto-generated method stub
		try {
			conn = DataConnection.getConnection();
			String sql = "SELECT * FROM diary WHERE title LIKE ? ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + kwd + "%");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getInt("ID"));
				System.out.println(rs.getString("title"));
				System.out.println(rs.getString("content"));
				System.out.println(rs.getString("write_date"));
			};
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null) conn.close();
				if(ps != null) ps.close();
				if(rs != null) rs.close();

			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
