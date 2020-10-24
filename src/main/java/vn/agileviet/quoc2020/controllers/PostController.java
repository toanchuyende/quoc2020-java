package vn.agileviet.quoc2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.agileviet.quoc2020.models.Post;
import vn.agileviet.quoc2020.models.User;
import vn.agileviet.quoc2020.repository.PostRepository;
import vn.agileviet.quoc2020.repository.UserRepository;
import vn.agileviet.quoc2020.services.UserDetailsImpl;

import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @ResponseBody
    @PostMapping("/create")
    public HttpEntity<?> edit(@Valid @RequestBody Post requestItem) {

//        Optional<Post> optionalPost = postRepository.findById(id);
//        Post serverPost = optionalPost.get();

        // lay userId gan vao author

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String currentPrincipalName = authentication.getName();
            // java.lang.ClassCastException: vn.agileviet.quoc2020.services.UserDetailsImpl cannot be cast to vn.agileviet.quoc2020.models.User
            UserDetailsImpl user = (UserDetailsImpl)authentication.getPrincipal();
//            User user = (User)authentication.getPrincipal();

//            Optional<User> userOptional = userRepository.findByUsername(currentPrincipalName);
//            if (userOptional == null) {
//                return ResponseEntity.of(null);
//            }
//            User user = userOptional.get();


            requestItem.setCreatedTime((new Date()));
            requestItem.setLastModified((new Date()));
            // lay userId gan vao author
            requestItem.setAuthor(user.getId());

            postRepository.save(requestItem);

            return ResponseEntity.ok(requestItem);
        }catch (Exception ex){
            return HttpEntity.EMPTY;
        }
    }

    @ResponseBody
    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public HttpEntity<?> edit(@PathVariable("id") String id, @Valid @RequestBody Post requestItem) {

        Optional<Post> optionalPost = postRepository.findById(id);
        Post serverPost = optionalPost.get();
        serverPost.setTitle(requestItem.getTitle());
        serverPost.setAuthor(requestItem.getAuthor());
        serverPost.setLastModified((new Date()));
        serverPost.setContent(requestItem.getContent());
        postRepository.save(serverPost);

        return ResponseEntity.ok(serverPost);
    }
}
