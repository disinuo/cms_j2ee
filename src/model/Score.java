package model;

public class Score {
	private int examID;
	private String examName;
	private int score;
	
	public Score(int examID, String examName, int score) {
		super();
		this.examID = examID;
		this.examName = examName;
		this.score = score;
	}
	public int getExamID() {
		return examID;
	}
	public void setExamID(int examID) {
		this.examID = examID;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
}
