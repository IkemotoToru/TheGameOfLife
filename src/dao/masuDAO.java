package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Masu;

public class masuDAO {

	//データベース接続に必要な情報
	private final String JDBC_URL = "jdbc:postgresql://localhost:5432/thegameoflife";
	private final String DB_USER = "postgres";
	private final String DB_PASS = "test";


	//masuの全件検索
	public List<Masu> allSearch() {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Masu> masuList = new ArrayList<Masu>();

		try {

			//SELECT文の準備
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(JDBC_URL , DB_USER , DB_PASS);
			ps = con.prepareStatement("SELECT * FROM masu");

			//SELECTを実行
			rs = ps.executeQuery();

			while(rs.next()) {

				//データベースから商品情報を取得
				String masu_id = rs.getString("masu_id");
				String masu_content = rs.getString("masu_content");
				int masu_money = rs.getInt("masu_money");
				String masu_bunrui = rs.getString("masu_bunrui");
				String masu_image = rs.getString("masu_image");

				//データベースの情報をMasuに保存
				Masu masu = new Masu(masu_id , masu_content , masu_money , masu_bunrui , masu_image);

				//リストに追加
				masuList.add(masu);
			}

		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {

			try {
				if(rs != null) {
					rs.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}

			try {
				if(ps != null) {
					ps.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}

			try {
				if(con != null) {
					con.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return masuList;
	}


	//masuの分類指定検索
	public List<Masu> bunruiSearch (String bunrui) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Masu> masuList = new ArrayList<Masu>();

		try {

			//SELECT文の準備
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(JDBC_URL , DB_USER , DB_PASS);

			String sql = "SELECT * FROM masu WHERE masu_bunrui = ?";

			if(bunrui.equals("普通")) {
				sql += " or masu_bunrui = ?";
				sql += " or masu_bunrui = ?";

				ps = con.prepareStatement(sql);

				//SELECT文の？に追加
				ps.setString(1 , "マイナス");
				ps.setString(2 , "プラス");
				ps.setString(3 , "休み");
			}else {

				ps = con.prepareStatement(sql);

				//SELECT文の？に追加
				ps.setString(1 , bunrui);
			}

			//SELECTを実行
			rs = ps.executeQuery();


			while(rs.next()) {

				//データベースから商品情報を取得
				String masu_id = rs.getString("masu_id");
				String masu_content = rs.getString("masu_content");
				int masu_money = rs.getInt("masu_money");
				String masu_bunrui = rs.getString("masu_bunrui");
				String masu_image = rs.getString("masu_image");

				//データベースの情報をMasuに保存
				Masu masu = new Masu(masu_id , masu_content , masu_money , masu_bunrui , masu_image);

				//リストに追加
				masuList.add(masu);

			}

		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {

			try {
				if(rs != null) {
					rs.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}

			try {
				if(ps != null) {
					ps.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}

			try {
				if(con != null) {
					con.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return masuList;
	}
}
