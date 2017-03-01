package com.service.Bean;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.ScoreDao;
import com.model.Exam;
import com.model.Score;
import com.service.ScoreService;
@Service
public class ScoreServiceBean implements ScoreService{
	@Autowired
	private ScoreDao scoreDao;
	
	@Override
	public List<Score> getTakenScores(String studentId) {
		return scoreDao.getTakenScores(studentId);
	}

	@Override
	public List<Exam> getNotTakenExams(String studentId) {
		// TODO Auto-generated method stub
		return null;
	}

}
