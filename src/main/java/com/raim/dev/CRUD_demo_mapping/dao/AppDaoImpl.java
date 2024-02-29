package com.raim.dev.CRUD_demo_mapping.dao;

import com.raim.dev.CRUD_demo_mapping.entity.Course;
import com.raim.dev.CRUD_demo_mapping.entity.Instructor;
import com.raim.dev.CRUD_demo_mapping.entity.InstructorDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AppDaoImpl implements AppDao{
    private EntityManager entityManager;

    @Autowired
    public AppDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Instructor instructor) {
        entityManager.persist(instructor);
    }

    @Override
    public Instructor findInstructorById(int id) {
        return entityManager.find(Instructor.class,id);
    }

    @Override
    @Transactional
    public void deleteInstructorById(int id) {
        Instructor toBeDeleted = findInstructorById(id);
        List<Course> courses = toBeDeleted.getCourses();
        courses.iterator().forEachRemaining(course ->
            course.setInstructor(null)
        );
        entityManager.remove(toBeDeleted);
    }

    @Override
    public InstructorDetail findInstructorDetailById(int id) {
        return entityManager.find(InstructorDetail.class, id);
    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(int id) {
        InstructorDetail instructorDetail = findInstructorDetailById(id);
        instructorDetail.getRelatedInstructor().setInstructorDetail(null);
        entityManager.remove(instructorDetail);
    }

    @Override
    public Course findCourseById(int id) {
        return entityManager.find(Course.class,id);
    }

    @Override
    public List<Course> findCoursesByInstructorId(int id) {
        //create a query
        // next time I should understand why this syntax in a query string
        TypedQuery<Course> query = entityManager.createQuery("from Course where instructor.id = :data", Course.class);

        query.setParameter("data",id);

        return query.getResultList();
    }

    @Override
    public Instructor findInstructorByIdWithCoursesJoinFetch(int id) {
        TypedQuery<Instructor> query = entityManager.createQuery(
                                                    "select i from Instructor i "
                                                        + "JOIN FETCH i.courses "
                                                        + "JOIN FETCH i.instructorDetail "
                                                        + "where i.id = :data", Instructor.class);
        query.setParameter("data",id);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void updateInstructor(Instructor instructor) {
        entityManager.merge(instructor);
    }

    @Override
    @Transactional
    public void updateCourse(Course course) {
        entityManager.merge(course);
    }

    @Override
    @Transactional
    public void deleteCourseById(int id) {
        entityManager.remove(findCourseById(id));
    }

    @Override
    @Transactional
    public void save(Course course) {
        entityManager.persist(course);
    }

    @Override
    public Course findCourseAndReviewsByCourseId(int id) {
        TypedQuery<Course> query = entityManager.createQuery(
                "select c from Course c "
                    + "JOIN FETCH c.reviews "
                    + "where c.id = :data", Course.class
        );
        query.setParameter("data",id);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void deleteCourseWithItsReviewsById(int id) {
        entityManager.remove(findCourseAndReviewsByCourseId(id));
    }

    @Override
    public Course findCourseAndStudentsByCourseId(int id) {
        TypedQuery<Course> query = entityManager.createQuery(
                "select c from Course c "
                        + "JOIN FETCH c.students "
                        + "where c.id = :data", Course.class
        );
        query.setParameter("data", id);
        return query.getSingleResult();
    }
}
