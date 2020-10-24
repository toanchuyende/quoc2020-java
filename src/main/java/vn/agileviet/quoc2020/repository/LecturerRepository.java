package vn.agileviet.quoc2020.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import vn.agileviet.quoc2020.models.Lecturer;

import java.util.List;

/**
 * @author tuan@agileviet.vn
 * @create 10/15/2020 8:48 AM
 */
@RepositoryRestResource(collectionResourceRel = "lecturer", path = "lecturer")
public interface LecturerRepository extends MongoRepository<Lecturer, String> {
    List<Lecturer> findByName(@Param("name") String name);
}