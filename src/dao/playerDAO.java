package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.NowPlayer;
import model.RankingBeans;
import model.UserRegistrationBeans;


public class playerDAO {

	//データベース接続に必要な情報
	private final String URL = "jdbc:postgresql://localhost:5432/thegameoflife";
	private final String USER = "postgres";
	private final String PASSWORD = "test";

   //0413追加
	public ArrayList<UserRegistrationBeans> getPlayerList() {
		ArrayList<UserRegistrationBeans> playerList = new ArrayList<UserRegistrationBeans>();
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs= null;

		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);

			String sql = "SELECT player_name FROM player";
			st = con.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				UserRegistrationBeans ubr =new UserRegistrationBeans();
				ubr.setName(rs.getString(1));
				ubr.setPass(rs.getInt(2));
				playerList.add(ubr);
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
		return playerList;
	}

  //0413追加
	public UserRegistrationBeans findPlayer(String player_name) throws ClassNotFoundException {
		UserRegistrationBeans ubr =new UserRegistrationBeans();
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			Class.forName("org.postgresql.Driver");
			con =DriverManager.getConnection(URL,USER,PASSWORD);

			String sql="SELECT * FROM player WHERE player_name = ?";
			st = con.prepareStatement(sql);
			st.setString(1,player_name);
			rs= st.executeQuery();
			if(!rs.next()) {return null;}
			ubr.setName(rs.getString("player_name"));
		}catch(SQLException e) {
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
		return ubr;
	}

	public int playerCreate(String player_name, int player_pass) {
		Connection con = null;
		PreparedStatement st= null;
		int addCnt = 0 ;

		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);

			String sql = "INSERT INTO PLAYER(player_name,player_password,player_money)VALUES(?,?,0)";
			st = con.prepareStatement(sql);
			st.setString(1, player_name);
			st.setInt(2, player_pass);
			addCnt = st.executeUpdate();

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
	}
		return addCnt;
	}


	public boolean bestScore(NowPlayer nowPlayer) {

		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;

		int addCnt = 0;
		boolean update = true;

		try{

			//SELECT文の準備
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(URL , USER , PASSWORD);
			ps = con.prepareStatement("SELECT * FROM player WHERE player_name = ?");


			//SELECT文の？に追加
			ps.setString(1 , nowPlayer.getName());

			//SELECTを実行
			rs = ps.executeQuery();

			//ベストスコアを取得

			rs.next();
			int bestMoney = rs.getInt("player_money");

			if(bestMoney == 0) {

				//ベストスコアより今回のスコアが多い場合動作しベストスコアが書き変わる
				//ベストスコアのUPDATE文
				String sql = "UPDATE player SET player_money = " + nowPlayer.getMoney();
				sql += " WHERE player_name = '" + nowPlayer.getName() + "' ;";
				//更新日のUPDATE文
				sql += " UPDATE player SET player_date = (SELECT CURRENT_DATE) ";
				sql += "WHERE player_name = '" + nowPlayer.getName() + "' ;";
				ps2 = con.prepareStatement(sql);


				//実行
				addCnt = ps2.executeUpdate();

				update = true;

			}else {

				if(nowPlayer.getMoney() > bestMoney) {
					//ベストスコアより今回のスコアが多い場合動作しベストスコアが書き変わる
					//ベストスコアのUPDATE文
					String sql = "UPDATE player SET player_money = " + nowPlayer.getMoney();
					sql += " WHERE player_name = '" + nowPlayer.getName() + "' ;";
					//更新日のUPDATE文
					sql += " UPDATE player SET player_date = (SELECT CURRENT_DATE) ";
					sql += "WHERE player_name = '" + nowPlayer.getName() + "' ;";
					ps2 = con.prepareStatement(sql);


					//実行
					addCnt = ps2.executeUpdate();

					update = true;

				}else {
					//ベストスコアより今回のスコアが少ない場合UPDATE文は動作しない
					update = false;
				}
			}

		}catch(Exception e) {
			e.printStackTrace();
		}finally {

			try {
				if(rs != null) {
					rs.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}

			try {
				if(ps != null) {
					ps.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			if(addCnt != 0) {
				try {
					if(rs2 != null) {
						rs2.close();
					}
				}catch(Exception e) {
					e.printStackTrace();
				}

				try {
					if(ps2 != null) {
						ps2.close();
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}

			try {
				if(con != null) {
					con.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return update;
	}


	public List<UserRegistrationBeans> all() {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<UserRegistrationBeans> urbList = null;

		try {

			//SELECT文の準備
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(URL , USER , PASSWORD);
			ps = con.prepareStatement("SELECT * FROM player");

			//SELECTを実行
			rs = ps.executeQuery();

			if(rs != null) {
				urbList = new ArrayList<>();
				while(rs.next()) {

					String name = rs.getString("player_name");
					int pass = rs.getInt("player_password");

					UserRegistrationBeans urb = new UserRegistrationBeans(name , pass);
					urbList.add(urb);
				}
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
		return urbList;
	}


	public List<RankingBeans> ranking() {

		List<RankingBeans> pRanking = new ArrayList<>();

			Connection con = null;
			PreparedStatement st = null;
			ResultSet rs = null;

			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection(URL, USER, PASSWORD);

				//sql文準備(上位３まで表示)
				String sql = "SELECT player_date,player_name,player_money "
						+ "from player "
						+ "WHERE player_date IS NOT NULL "
						+ "order by player_money desc limit 3;";
				st = con.prepareStatement(sql);
				rs = st.executeQuery();

				//結果をリストに移し替える
				pRanking = makeRankingData(rs);

			}catch(Exception e) {
				System.out.println("DBアクセス時にエラーが発生しました。");
				e.printStackTrace();
			}finally {
				if(rs != null) {
					try {
						rs.close();
					}catch(SQLException e) {}
				}

				if(st != null) {
					try {
						st.close();
					}catch(SQLException e) {}
				}

				if(con != null) {
					try {
						con.close();
					}catch(SQLException e) {}
				}
			}
			return pRanking;
	}

	/*
	 * ランキング結果をリストで返すメソッド
	 */
		public ArrayList<RankingBeans> makeRankingData(ResultSet rs) throws Exception{

			ArrayList<RankingBeans> pDatas = new ArrayList<RankingBeans>();

			while(rs.next()) {
				Date player_date = rs.getDate("player_date");
				String player_name = rs.getString("player_name");
				int player_money = rs.getInt("player_money");

				RankingBeans pData = new RankingBeans(player_date, player_name, player_money);

				pDatas.add(pData);
			}
			return pDatas;
		}
}