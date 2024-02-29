package com.raim.dev.CRUD_demo_mapping;

import com.raim.dev.CRUD_demo_mapping.dao.AppDao;
import com.raim.dev.CRUD_demo_mapping.entity.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CrudDemoMappingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudDemoMappingApplication.class, args);
	}

	@Bean// executed after the beans are created
	public CommandLineRunner commandLineRunner (AppDao appDao){
		return runner -> {
			//createInstructor(appDao);
			//findInstructorByID(appDao);
			//deleteInstructor(appDao);
			//findInstructorDetailByID(appDao);
			//deleteInstructorDetailByID(appDao);
			//createInstructorWithCourses(appDao);
			//findInstructorWithCourses(appDao);
			//findInstructorWithCoursesUsingJoinFetch(appDao);
			//updateInstructor(appDao);
			//updateCourse(appDao);
			//deleteInstructor(appDao);
			//deleteCourse(appDao);
			//createCourseAndReviews(appDao);
			//getCourseAndReviews(appDao);
			//removeCourseWithReviews(appDao);
			//createCourseAndStudents(appDao);
			//findCourseAndItsStudents(appDao);
			//addCoursesForStudent(appDao);
			//deleteCourse(appDao);
			deleteStudent(appDao);
		};
	}

	private void deleteStudent(AppDao appDao) {
		int id = 1;
		appDao.deleteStudentById(id);
		System.out.println("DONE!");
	}

	private void addCoursesForStudent(AppDao appDao) {
		var id = 2;
		Student tempStudent = appDao.findStudentAndCoursesByStudentId(id);
		System.out.println("BEFORE : ");
		System.out.println(tempStudent);
		Course course1 = new Course("Python - from Zero to Hero");
		Course course2 = new Course("Java Masterclass");
		Course course3 = new Course("Javascript - Front-end professional");

		tempStudent.addCourses(course1);
		tempStudent.addCourses(course2);
		tempStudent.addCourses(course3);

		appDao.update(tempStudent);
		System.out.println("AFTER : ");
		System.out.println(appDao.findStudentAndCoursesByStudentId(id));
	}

	private void findCourseAndItsStudents(AppDao appDao) {
		int id = 10;
		Course course = appDao.findCourseAndStudentsByCourseId(id);

		System.out.println("Found Course : ");
		System.out.println(course);

		System.out.println("Students taking this course : ");
		System.out.println(course.getStudents());
	}

	private void createCourseAndStudents(AppDao appDao) {
		// create a course
		Course course1 = new Course("Temu - Buy Freely!");
		// create the students
		Student student1 = new Student("Nill", "Cha", "nill@gmail.com");
		Student student2 = new Student("Mia", "Cha", "mia@gmail.com");
		Student student3 = new Student("Sunny", "Cha", "sunny@gmail.com");
		// add students to the course
		course1.addStudent(student1);
		course1.addStudent(student2);
		course1.addStudent(student3);
		// save the course and obviously its students
		System.out.println("Saving a course : " + course1);
		System.out.println("Saving a student : " + student1);
		System.out.println("Saving a student : " + student2);
		System.out.println("Saving a student : " + student3);

		appDao.save(course1);

		System.out.println("Done!");
	}

	private void removeCourseWithReviews(AppDao appDao) {
		int id = 10;
		appDao.deleteCourseWithItsReviewsById(id);
	}

	private void getCourseAndReviews(AppDao appDao) {
		int id = 10;
		Course course = appDao.findCourseAndReviewsByCourseId(id);
		System.out.println("Course : " + course);
		System.out.println("Course reviews : " + course.getReviews());
	}

	private void createCourseAndReviews(AppDao appDao) {
		// create a course
		Course course = new Course("Volleyball Masterclass");
		// add reviews
		Review review = new Review("Nice course, I am a pro now");
		Review review1 = new Review("Thank you");


		course.addReview(review);
		course.addReview(review1);
		// save the course
		appDao.save(course);
	}

	private void deleteCourse(AppDao appDao) {
		int id = 10;
		appDao.deleteCourseById(id);
		System.out.println("DONE!");
	}

	private void updateCourse(AppDao appDao) {
		int id = 12;
		System.out.println("Finding Course with id : "  + id);
		Course course = appDao.findCourseById(id);
		System.out.println("Before Instructor : " + course);
		course.setTitle("Advanced Shibari");
		System.out.println("After Instructor : " + course);
		appDao.updateCourse(course);
		System.out.println("Finished!");
	}

	private void updateInstructor(AppDao appDao) {
		int id = 1;
		System.out.println("Finding Instructor with id : "  + id);
		Instructor instructor = appDao.findInstructorById(id);
		System.out.println("Before Instructor : " + instructor);
		instructor.setEmail("mikasa@google.dev");
		System.out.println("After Instructor : " + instructor);
		appDao.updateInstructor(instructor);
		System.out.println("Finished!");
	}

	private void findInstructorWithCoursesUsingJoinFetch(AppDao appDao) {
		int id = 1;
		System.out.println("Finding Instructor with id : "  + id);
		Instructor instructor = appDao.findInstructorByIdWithCoursesJoinFetch(id);
		System.out.println("Instructor : " + instructor);
		System.out.println("His courses : " + instructor.getCourses());
		System.out.println("Finished!");
	}

	private void findInstructorWithCourses(AppDao appDao) {
		int id = 2;
		System.out.println("Finding Instructor with id : "  + id);

		Instructor instructor = appDao.findInstructorById(id);
		System.out.println("Instructor : " + instructor);
		instructor.setCourses(getCoursesByInstructorId(appDao,id));
		System.out.println("His courses : " + instructor.getCourses());

		System.out.println("Finished!");
	}
	private List<Course> getCoursesByInstructorId(AppDao appDao, int id){
		return appDao.findCoursesByInstructorId(id);
	}

	private void createInstructorWithCourses(AppDao appDao) {
		Instructor instructor = new Instructor("Eren","Yeager","eren@gmail.com");
		InstructorDetail instructorDetail =
				new InstructorDetail("www.youtube.channel.com", "humans");
		instructor.setInstructorDetail(instructorDetail);

		// create some courses
		Course course1 = new Course("Boxing from Zero to Hero");
		Course course2 = new Course("Conquer Data Structures and Algorithms");
		Course course3 = new Course("Swimming");

		instructor.addCourse(course1);
		instructor.addCourse(course2);
		instructor.addCourse(course3);

		// saving the instructor
		// which also saves courses and instructor detail
		System.out.println("Saving " + instructor);
		System.out.println("Courses " + instructor.getCourses());
		appDao.save(instructor);
	}

	private void deleteInstructorDetailByID(AppDao appDao) {
		int id = 2;
		System.out.println("Deleting ONLY Instructor Detail with Id =" + id);
		appDao.deleteInstructorDetailById(id);
		//InstructorDetail will be deleted, but The Instructor it is related to won't be deleted. Because of Cascade type;
		//WORKS!!!
	}

	private void findInstructorDetailByID(AppDao appDao) {
		int id = 2;
		System.out.println("Finding instructorDetail by id = " + id);
		InstructorDetail tempInsDetails = appDao.findInstructorDetailById(id);
		//should also fetch related Instructor;
		System.out.println(tempInsDetails);
		System.out.println(tempInsDetails.getRelatedInstructor());
	}

	private void deleteInstructor(AppDao appDao) {
		int id = 1;
		System.out.println("Deleting Instructor with id = " + id);
		appDao.deleteInstructorById(id);
		//this also deletes records related to this
		// instructor from instructor_details, because of CascadeTypeAll
	}

	private void findInstructorByID(AppDao appDao) {
		int id = 1;
		System.out.println("Finding Instructor with ID = " + id);

		Instructor instructor = appDao.findInstructorById(id);

		System.out.println(instructor);
		//notice that it is fetching the instructor detail as well ,
		// this is because by default the fetch type of OneToOne mapping is Eager
		System.out.println(instructor.getInstructorDetail());
	}

	private void createInstructor(AppDao appDao) {
//		Instructor instructor = new Instructor("Leo","Messi","messi@gmail.com");
//		InstructorDetail instructorDetail =
//				new InstructorDetail("www.youtube.channel.com", "footbal");
		Instructor instructor = new Instructor("Eren","Yeager","eren@gmail.com");
		InstructorDetail instructorDetail =
				new InstructorDetail("www.youtube.channel.com", "titans");
		/*
			creating some objects, and saving them in the database,but
			I am only saving the instructor, but because I have a OneToOne CascadeType All,
			 it will save the InstructorDetail as well
		 */
		instructor.setInstructorDetail(instructorDetail);
		System.out.println("Saving instructor : " + instructor);
		appDao.save(instructor);
	}
}
