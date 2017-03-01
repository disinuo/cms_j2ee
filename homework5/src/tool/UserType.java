package tool;

public enum UserType {
	STUDENT,TEACHER,ERROR;
	
	public static UserType toEnum(String str){
		switch(str){
			case "student":return STUDENT;
			case "teacher":return UserType.TEACHER;
		}
		return UserType.ERROR;
	}
	
	@Override
	public String toString(){
		return this.toString().toLowerCase();
	}
}
