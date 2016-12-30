package service.Impl;

import java.util.ArrayList;

import dao.ScoreDao;
import factory.DaoFactory;
import model.Exam;
import model.Score;
import service.ShowScoreService;

public class ShowScoreServiceImpl implements ShowScoreService{
	private static ShowScoreServiceImpl showScoreService=new ShowScoreServiceImpl();
	private ScoreDao scoreDao=DaoFactory.getScoreDao();
	
	private ShowScoreServiceImpl(){}
	public static ShowScoreService getInstance(){
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
