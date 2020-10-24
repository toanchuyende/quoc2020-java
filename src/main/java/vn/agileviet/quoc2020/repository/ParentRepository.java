package vn.agileviet.quoc2020.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import vn.agileviet.quoc2020.models.Parent;


/**
 * @author tuan@agileviet.vn
 * @create 10/15/2020 8:24 AM
 */
@RepositoryRestResource(collectionResourceRel = "parent", path = "parent")
public interface ParentRepository extends MongoRepository<Parent, String> {
    List<Parent> findByName(@Param("name") String name);
}