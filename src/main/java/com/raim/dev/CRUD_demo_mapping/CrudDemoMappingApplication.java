package com.raim.dev.CRUD_demo_mapping;

import com.raim.dev.CRUD_demo_mapping.dao.AppDao;
import com.raim.dev.CRUD_demo_mapping.entity.Course;
import com.raim.dev.CRUD_demo_mapping.entity.Instructor;
import com.raim.dev.CRUD_demo_mapping.entity.InstructorDetail;
import com.raim.dev.CRUD_demo_mapping.entity.Review;
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
			removeCourseWithReviews(appDao);
		};
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
		int id = 12;
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
