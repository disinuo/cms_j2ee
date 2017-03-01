package javabean;

import model.Score;

import java.io.Serializable;
import java.util.ArrayList;

public class ScoreListBean implements Serializable {
	private ArrayList<Score> scoreList;

	public ArrayList<Score> getScoreList() {
		return scoreList;
	}

	public void setScoreList(ArrayList<Score> scoreList) {
		this.scoreList = scoreList;
	}
	public Score getScore(int i){
		return scoreList.get(i);
	}
	
}
