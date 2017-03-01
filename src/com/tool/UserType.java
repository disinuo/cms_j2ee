package com.tool;

public enum UserType {
	STUDENT,TEACHER,ERROR;
	
	public static UserType toEnum(String str){
		if(str.equals("student")) return STUDENT;
		if(str.equals( "teacher"))return UserType.TEACHER;
		return UserType.ERROR;
	}
	
	@Override
	public String toString(){
		return this.toString().toLowerCase();
	}
}
