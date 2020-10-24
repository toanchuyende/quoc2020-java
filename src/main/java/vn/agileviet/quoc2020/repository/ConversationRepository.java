package vn.agileviet.quoc2020.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import vn.agileviet.quoc2020.models.Conversation;

import java.util.List;

/**
 * @author tuan@agileviet.vn
 * @create 10/15/2020 8:48 AM
 */
@CrossOrigin(origins = "*")
@RepositoryRestResource(collectionResourceRel = "conversation", path = "conversation")
public interface ConversationRepository extends MongoRepository<Conversation, String> {
    List<Conversation> findByTitle(@Param("title") String name);
}