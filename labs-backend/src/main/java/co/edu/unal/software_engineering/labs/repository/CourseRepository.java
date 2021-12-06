package co.edu.unal.software_engineering.labs.repository;

import co.edu.unal.software_engineering.labs.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer>{

}
