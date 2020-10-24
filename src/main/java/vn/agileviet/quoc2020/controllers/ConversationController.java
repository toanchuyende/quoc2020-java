package vn.agileviet.quoc2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.agileviet.quoc2020.models.Conversation;
import vn.agileviet.quoc2020.models.ErrorMessage;
import vn.agileviet.quoc2020.repository.ConversationRepository;
import vn.agileviet.quoc2020.repository.UserRepository;
import vn.agileviet.quoc2020.services.UserDetailsImpl;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author tuan@agileviet.vn
 * @create 10/18/2020 4:44 AM
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/conversation")
public class ConversationController {
    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    UserRepository userRepository;

    @ResponseBody
    @PostMapping("/create")
    public HttpEntity<?> edit(@Valid @RequestBody Conversation requestItem) {

        // lay userId gan vao author

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String currentPrincipalName = authentication.getName();
            // java.lang.ClassCastException: vn.agileviet.quoc2020.services.UserDetailsImpl cannot be cast to vn.agileviet.quoc2020.models.User
            UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
//            User user = (User)authentication.getPrincipal();

//            Optional<User> userOptional = userRepository.findByUsername(currentPrincipalName);
//            if (userOptional == null) {
//                return ResponseEntity.of(null);
//            }
//            User user = userOptional.get();


//            requestItem.setCreatedTime((new Date()));
//            requestItem.setLastModified((new Date()));
//            // lay userId gan vao author
            List<String> _members = new ArrayList<String>();
            _members.add(user.getId());
            requestItem.setMembers(_members);

            conversationRepository.save(requestItem);

            return ResponseEntity.ok(requestItem);
        } catch (Exception ex) {
            return ResponseEntity.ok(new ErrorMessage("500", "Lỗi ..."));
        }
    }

//    @ResponseBody
//    @PutMapping("/edit/{id}")
//    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
//    public HttpEntity<?> edit(@PathVariable("id") String id, @Valid @RequestBody Conversation requestItem) {
//
//        Optional<Conversation> optionalConversation = conversationRepository.findById(id);
//        Conversation serverConversation = optionalConversation.get();
//        serverConversation.setTitle(requestItem.getTitle());
//        serverConversation.setAuthor(requestItem.getAuthor());
//        serverConversation.setLastModified((new Date()));
//        serverConversation.setContent(requestItem.getContent());
//        conversationRepository.save(serverConversation);
//
//        return ResponseEntity.ok(serverConversation);
//    }
}
