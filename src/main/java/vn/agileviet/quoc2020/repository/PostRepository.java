package vn.agileviet.quoc2020.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.agileviet.quoc2020.models.Post;
import vn.agileviet.quoc2020.payload.request.SignupRequest;
import vn.agileviet.quoc2020.payload.response.MessageResponse;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


/**
 * @author tuan@agileviet.vn
 * @create 10/15/2020 8:24 AM
 */
@CrossOrigin(origins = "*")
@RepositoryRestResource(collectionResourceRel = "post", path = "post")
public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByTitle(@Param("title") String name);
}

