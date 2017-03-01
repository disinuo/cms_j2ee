package Test;


import model.Course;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

public class Test {
    @PersistenceContext
    protected EntityManager entitymanager;
    public void test(){

        entitymanager.getTransaction( ).begin( );

        Course course= new Course();
        course.setName("啦啦啦");
        course.setId(800);
        course.setTid(100);

        entitymanager.persist(course);
        entitymanager.getTransaction( ).commit( );

        entitymanager.close( );
    }
	public static void main( String[ ] args ){
	    Test test=new Test();
	    test.test();

	}
}