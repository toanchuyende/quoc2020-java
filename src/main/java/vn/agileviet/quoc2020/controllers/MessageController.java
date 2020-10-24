package vn.agileviet.quoc2020.controllers;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import vn.agileviet.quoc2020.models.Conversation;
import vn.agileviet.quoc2020.models.Message;
import vn.agileviet.quoc2020.models.Post;
import vn.agileviet.quoc2020.payload.response.MessageResponse;
import vn.agileviet.quoc2020.repository.MessageRepository;
import vn.agileviet.quoc2020.repository.PostRepository;
import vn.agileviet.quoc2020.socket.AgileMessage;
import vn.agileviet.quoc2020.socket.HelloMessage;
import vn.agileviet.quoc2020.socket.MaskMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class MessageController {

    @Autowired
    private MessageRepository mssageRepository;

    @MessageMapping("/say-hello")
    @SendTo("/topic/greetings")
    public MaskMessage greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new MaskMessage("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }


    @MessageMapping("/client-send")
    @SendTo("/topic/client-receive")
    public AgileMessage agileClient01(String message) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            //read json file and convert to customer object
            AgileMessage data = objectMapper.readValue(message, AgileMessage.class);
            if (saveNewMessage(data)) {

            }
//            Thread.sleep(1000); // simulated delay
            return data;

        } catch (Exception ex) {
            return null;
        }

    }

    private boolean saveNewMessage(AgileMessage data) {
        try {
            Message msg = new Message();

            msg.setBody(data.getMessage());
            msg.setConversationId(data.getConversationId());
            msg.setSender(data.getUser().getId());
            msg.setSendTime((new Date()));
            mssageRepository.save(msg);

            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    @ResponseBody
    @GetMapping("/api/message/findByConversationId/{id}")
    public HttpEntity<?> findByConversationId(@PathVariable("id") String id) {

        try {
            List<Message> optionalListMessage = mssageRepository.findByConversationId(id);
            return ResponseEntity.ok(optionalListMessage);
        } catch (Exception ex) {
            return ResponseEntity.ok(new MessageResponse("loi day danh danh message."));
        }

    }
}
