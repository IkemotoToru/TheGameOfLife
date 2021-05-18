package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Textlog;

public class textlogDAO {

	//データベース接続に必要な情報
	private final String URL = "jdbc:postgresql://localhost:5432/thegameoflife";
	private final String USER = "postgres";
	private final String PASSWORD = "test";

	Connection con = null;
	PreparedStatement st = null;
	ResultSet rs= null;

	public List<Textlog> all() {

		List<Textlog> logList = new ArrayList<Textlog>();

		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);

			String sql = "SELECT * FROM textlog";
			sql += " ORDER BY hiniti DESC;";
			st = con.prepareStatement(sql);
			rs = st.executeQuery();

			while(rs.next()) {
				Textlog textlog = new Textlog();

				textlog.setName(rs.getString("name"));
				textlog.setRadio(rs.getString("radio"));
				textlog.setText(rs.getString("text"));
				textlog.setMail(rs.getString("email"));

				logList.add(textlog);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}finally {
			if(st != null) {
				try {
					st.close();
				} catch (SQLException e) {}
			}

			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {}
			}
		}
		return logList;
	}


	public int add(String name , String radio , String text , String mail) {

		int addint = 0;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			String sql =null;
			if(mail != null) {
				sql = "INSERT INTO textlog VALUES ( (SELECT CURRENT_TIMESTAMP) , ? , ? , ? , ? );";
			}else {
				sql = "INSERT INTO textlog VALUES ( (SELECT CURRENT_TIMESTAMP) , ? , ? , ? );";
			}
			st = con.prepareStatement(sql);
			st.setString(1 , name);
			st.setString(2 , radio);
			st.setString(3 , text);

			if(mail != null) {
				st.setString(4 , mail);
			}
			addint = st.executeUpdate();


		} catch (Exception e) {
			e.printStackTrace();

		}finally {
			if(st != null) {
				try {
					st.close();
				} catch (SQLException e) {}
			}

			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {}
			}
		}
		return addint;
	}
}
