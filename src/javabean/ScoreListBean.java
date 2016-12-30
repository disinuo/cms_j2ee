package javabean;

import java.io.Serializable;
import java.util.ArrayList;

import model.Score;

public class ScoreListBean implements Serializable {
	private ArrayList<Score> scoreList;

	public ArrayList<Score> getScoreList() {
		return scoreList;
	}

	public void setScoreList(ArrayList<Score> scoreList) {
		this.scoreList = scoreList;
	}
	
}
