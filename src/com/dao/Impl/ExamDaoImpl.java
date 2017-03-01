package com.dao.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dao.BaseDao;
import com.dao.ExamDao;
import com.model.Exam;

@Repository
public class ExamDaoImpl implements ExamDao{
	@Autowired
	private BaseDao baseDao;
	@Override
	public List<Exam> getChosenExams(String studentId) {
		int sid=Integer.parseInt(studentId);
		String hql="SELECT e FROM SelectC sel,Exam e,Course c WHERE sel.cid = e.course.id AND c.id=sel.cid AND sel.sid =:sid";
		List<Exam> exams=(List<Exam>)baseDao.findWithIntParamater(hql, "sid", sid);
		return exams;
	}

	@Override
	public List<Exam> getTakenExams(String studentId) {
		int sid=Integer.parseInt(studentId);
		String hql="SELECT e FROM Exam e,Course c,Score s WHERE s.exam.id=e.id AND c.id=e.course.id AND s.sid=:sid";
		List<Exam> exams=(List<Exam>)baseDao.findWithIntParamater(hql, "sid", sid);
		return exams;
	}

	@Override
	public List<Exam> getAllExams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exam getExamByID(String examId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Exam> getExamOfACourse(String courseId) {
		// TODO Auto-generated method stub
		return null;
	}

}
