package javabean;

import java.util.ArrayList;
import java.util.List;

import model.Exam;

public class ExamListBean {
	private List<Exam> examList;

	public ArrayList<Exam> getExamList() {
		ArrayList<Exam> res=new ArrayList<Exam>();
		for(Exam e:examList){
			res.add(e);
		}
		return res;
	}

	public void setExamList(List<Exam> examList) {
		this.examList = examList;
	}
	public Exam getExam(int i){
		return examList.get(i);
	}
}
