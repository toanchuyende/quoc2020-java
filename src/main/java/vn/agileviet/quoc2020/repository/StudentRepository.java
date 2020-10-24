package vn.agileviet.quoc2020.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import vn.agileviet.quoc2020.models.Student;


/**
 * @author tuan@agileviet.vn
 */
@RepositoryRestResource(collectionResourceRel = "student", path = "student")
public interface StudentRepository extends MongoRepository<Student, String> {
    List<Student> findByName(@Param("name") String name);
}