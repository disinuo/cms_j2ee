package javabean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.Score;

public class ScoreListBean implements Serializable {
	private List<Score> scoreList;

//	public List<Score> getScoreList() {
//		return scoreList;
//	}
	
	public ArrayList<Score> getScoreList() {
		ArrayList<Score> res=new ArrayList<Score>();
		for(Score l:scoreList){
			res.add(l);
		}
		return res;
	}

	public void setScoreList(List<Score> scoreList) {
		this.scoreList = scoreList;
	}
	public Score getScore(int i){
		return scoreList.get(i);
	}
	
}
