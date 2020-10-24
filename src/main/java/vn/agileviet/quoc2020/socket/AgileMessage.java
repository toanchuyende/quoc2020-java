package vn.agileviet.quoc2020.socket;

import org.springframework.data.annotation.Id;
import vn.agileviet.quoc2020.models.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AgileMessage {
    private MessageUser user;

    public MessageUser getUser() {
        return user;
    }

    public void setUser(MessageUser user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    private String conversationId;
}
