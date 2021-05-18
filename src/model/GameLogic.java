package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dao.masuDAO;

public class GameLogic {

	//人生ゲームのマスを作成
	public List<Masu> masuCreate() {

		//マスの並びのリスト
		List<Masu> gameList = new ArrayList<Masu>();

		//masuテーブルからマスリストに代入
		masuDAO masuDAO = new masuDAO();
		List<Masu> allList = masuDAO.allSearch();
		List<Masu> jobList = masuDAO.bunruiSearch("職業");
		List<Masu> normalList = masuDAO.bunruiSearch("普通");
		List<Masu> salaryList = masuDAO.bunruiSearch("給料");

//		System.out.println(allList.size());
//		System.out.println(jobList.size());
//		System.out.println(normalList.size());
//		System.out.println(salaryList.size());

		//スタートマスを作成
		Masu start = new Masu("start" , "" , 0 , "" , "");
		gameList.add(start);

		int jobSize = jobList.size();


		//jobListの要素数が0になるまでループ
		while(!jobList.isEmpty()) {

			//jobListの要素数の乱数を発生させる
			int random = new Random().nextInt(jobList.size());

			//jobListからMasu情報を取得
			Masu masu = jobList.get(random);

			//マスリストに追加する
			gameList.add(masu);

			//追加したものをjobListから削除する
			jobList.remove(random);
		}


		//normalListの要素数が0になるまでループ
		while(!normalList.isEmpty()) {

			//normalListの要素数の乱数を発生させる
			int random = new Random().nextInt(normalList.size());

			//normalListからMasu情報を取得
			Masu masu = normalList.get(random);

			//マスリストに追加する
			gameList.add(masu);

			//追加したものをnormalListから削除する
			normalList.remove(random);
		}


		int salaryLstSize = salaryList.size();

		//全マス数を給料マス数で割る
		int quotient = ( allList.size() - jobSize ) / ( salaryList.size() + 1 );

		for(int i = 1 ; i <= salaryLstSize ; i++) {


			//normalListの要素数の乱数を発生させる
			int random = new Random().nextInt(salaryList.size());

			//salaryListからMasu情報を取得
			Masu masu = salaryList.get(random);
			//マスリストに追加する
			gameList.add(quotient * i + jobSize , masu);


			//追加したものをsalaryListから削除する
			salaryList.remove(random);
		}


//		//入っているか確認のFOR文
//		int i = 1 ;
//		for(Masu masu : gameList) {
//			System.out.println(i);
//			System.out.println(masu.getMasu_id());
//			System.out.println(masu.getMasu_bunrui());
//			System.out.println(masu.getMasu_content());
//			System.out.println(masu.getMasu_money());
//			i++;
//		}

		return gameList;
	}


	//プレイヤーを進めるメソッド
	public String advance (List<Masu> gameList , NowPlayer nowPlayer) {

		//ルーレットを回す(0～5)
		int random = new Random().nextInt(6);
		//1～6にする
		random++;
		//このターン進むマスに保存
		nowPlayer.setRandom(random);


		//今いるマスにこのターン進むマスをプラスする
		int advanceNumber = nowPlayer.getAdvanceNumber() + random;

		Masu masu = null;
		boolean count = true;
		String image = null;


		if(advanceNumber == gameList.size()) {
			//ゴールした場合動作


			//かかったターン数×1000を所持金からマイナス
			nowPlayer.setMoney(nowPlayer.getMoney() - nowPlayer.getTurnCount() * 1000);

			count = false;

		}else if(advanceNumber > gameList.size()) {

			//今いるマスがゲームマスより大きい場合動作

			advanceNumber = gameList.size() - ( advanceNumber - gameList.size() );

			count = true;
		}


		if(count) {
			masu = gameList.get(advanceNumber);

			image = masu.getMasu_image();

//			System.out.println(masu.getMasu_id());
//			System.out.println(masu.getMasu_bunrui());
//			System.out.println(masu.getMasu_content());
//			System.out.println(masu.getMasu_money());

			switch(masu.getMasu_bunrui()) {
				case "職業":

					if(nowPlayer.getJob().equals("無職")) {

						//プレイヤー情報に保存
						nowPlayer.setJob(masu.getMasu_content());
						nowPlayer.setSalary(masu.getMasu_money());
						masu.setMasu_content(nowPlayer.getName() + "は、" + nowPlayer.getJob() + "として働き始めた！！");

					}else {

						masu.setMasu_content(nowPlayer.getName() + "は、転職を考えたが思いとどまった。");

					}

					break;
				case "マイナス":

					//所持金からマイナス
					nowPlayer.setMoney(nowPlayer.getMoney() - masu.getMasu_money());

					break;
				case "プラス":

					//所持金にプラス
					nowPlayer.setMoney(nowPlayer.getMoney() + masu.getMasu_money());

					break;
				case "給料":

					//給料の掛け率を計算し所持金にプラス
					nowPlayer.setMoney(nowPlayer.getMoney() + nowPlayer.getSalary() * (masu.getMasu_money() / 10));
					masu.setMasu_content("やった！給料日だ！！");

					break;
				case "休み":

					//ターンをプラス
					nowPlayer.setTurnCount(nowPlayer.getTurnCount() + masu.getMasu_money());

					break;
			}


		}

		//今いるマスを保存する
		nowPlayer.setAdvanceNumber(advanceNumber);

		//ターン数をプラスする
		nowPlayer.setTurnCount(nowPlayer.getTurnCount() + 1);

		return image;
	}
}
