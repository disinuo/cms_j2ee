package model;
import java.io.Serializable;
import tool.Sex;

public class Student extends User implements Serializable{
	private String chineseName;
	private String email;
	private Sex sex;
	private int grade;
	private int classNum;
	private String major;
	

	

	public String getChineseName() {
		return chineseName;
	}


	public void setChineseName(String name) {
		this.chineseName = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Sex getSex() {
		return sex;
	}


	public void setSex(Sex sex) {
		this.sex = sex;
	}


	public int getGrade() {
		return grade;
	}


	public void setGrade(int grade) {
		this.grade = grade;
	}


	public int getClassNum() {
		return classNum;
	}


	public void setClassNum(int classNum) {
		this.classNum = classNum;
	}


	public String getMajor() {
		return major;
	}


	public void setMajor(String major) {
		this.major = major;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
