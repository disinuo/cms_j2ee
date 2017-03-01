package service.Impl;

import java.util.ArrayList;

import dao.ScoreDao;
import factory.DaoFactory;
import model.Exam;
import model.Score;
import service.ScoreService;

public class ScoreServiceImpl implements ScoreService{
	private static ScoreServiceImpl showScoreService=new ScoreServiceImpl();
	private ScoreDao scoreDao=DaoFactory.getScoreDao();
	
	private ScoreServiceImpl(){}
	public static ScoreService getInstance(){
		return showScoreService;
	}
	@Override
	public ArrayList<Score> getTakenScores(String studentId) {
		return scoreDao.getTakenScores(studentId);
	}

	@Override
	public ArrayList<Exam> getNotTakenExams(String studentId) {
		// TODO Auto-generated method stub
		return null;
	}

}
