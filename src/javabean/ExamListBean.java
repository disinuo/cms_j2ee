package javabean;

import java.util.ArrayList;

import model.Exam;

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
