package org.fatmansoft.teach.repository;
import org.fatmansoft.teach.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CourseRespository extends JpaRepository<Course, String> {
    List<Course> findByStudentsContains(String stuName);
    @Override
    Optional<Course> findById(String s);
    List<Course> findByTeacher(String teaName);
    List<Course> findByTime(String time);
    List<Course> findByProperty(String pro);
    List<Course> findByTerm(String term);
}
