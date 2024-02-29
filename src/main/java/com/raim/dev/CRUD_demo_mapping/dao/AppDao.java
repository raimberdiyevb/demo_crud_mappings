package com.raim.dev.CRUD_demo_mapping.dao;

import com.raim.dev.CRUD_demo_mapping.entity.Course;
import com.raim.dev.CRUD_demo_mapping.entity.Instructor;
import com.raim.dev.CRUD_demo_mapping.entity.InstructorDetail;
import com.raim.dev.CRUD_demo_mapping.entity.Student;

import java.util.List;

public interface AppDao {
    void save(Instructor instructor);
    Instructor findInstructorById(int id);

    void deleteInstructorById(int id);

    InstructorDetail findInstructorDetailById(int id);

    void deleteInstructorDetailById(int id);
    Course findCourseById(int id);

    List<Course> findCoursesByInstructorId(int id);

    Instructor findInstructorByIdWithCoursesJoinFetch(int id);
    void updateInstructor(Instructor instructor);

    void updateCourse(Course course);

    void deleteCourseById(int id);

    void save(Course course);

    Course findCourseAndReviewsByCourseId(int id);

    void deleteCourseWithItsReviewsById(int id);

    Course findCourseAndStudentsByCourseId(int id);
    Student findStudentAndCoursesByStudentId(int id);

    void update(Student student);
}
