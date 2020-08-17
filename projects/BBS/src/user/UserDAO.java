package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() 
	{
		try {
			String dbURL = "jdbc:mysql://localhost:3306/BBS";
			String dbID = "root";
			String dbPassword = "root";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public int login(String userID, String userPassword)
	{
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				if(rs.getString(1).equals(userPassword))
					return 1; // 로그인 성공
				else
					return 0; // 비밀번호 불일치
			}
			return -1; // 아이디가 없음
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return -2; //데이터베이스 오류
	}
	
	public int join(User user)
	{
		String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			return pstmt.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
	
	public String getUserEmail(String userID)
	{
		String SQL = "SELECT userEmail FROM USER WHERE userID = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				return rs.getString(1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null; // 데이터베이스 오류
	}
	
	public boolean getUserEmailChecked(String userID)
	{
		String SQL = "SELECT userEmailChecked FROM USER WHERE userID = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				return rs.getBoolean(1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false; // 데이터베이스 오류
	}
	
	public boolean setUserEmailChecked(String userID)
	{
		String SQL = "UPDATE USER SET userEmailChecked = true WHERE userID = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			pstmt.executeUpdate();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false; // 데이터베이스 오류
	}
}
