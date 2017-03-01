package javabean;

import model.Exam;

import java.util.ArrayList;

public class ExamListBean {
	private ArrayList<Exam> examList;

	public ArrayList<Exam> getExamList() {
		return examList;
	}

	public void setExamList(ArrayList<Exam> examList) {
		this.examList = examList;
	}
	public Exam getExam(int i){
		return examList.get(i);
	}
}
