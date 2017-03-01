package com.dao.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dao.BaseDao;
import com.dao.ScoreDao;
import com.model.Score;

@Repository
public class ScoreDaoImpl implements ScoreDao{
	@Autowired
	private BaseDao baseDao;
	@Override
	public List<Score> getTakenScores(String studentId) {
		int sid=Integer.parseInt(studentId);
		String hql="SELECT s FROM Exam e,Course c,Score s WHERE s.exam.id=e.id AND c.id=e.course.id AND s.sid=:sid";
		List<Score> scores=(List<Score>)baseDao.findWithIntParamater(hql,"sid",sid);
		return scores;
	}


	@Override
	public List<Score> getAllScores() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Score> getScoresOfACourse(String courseId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Score> getScoresOfAnExam(String examId) {
		// TODO Auto-generated method stub
		return null;
	}

}
