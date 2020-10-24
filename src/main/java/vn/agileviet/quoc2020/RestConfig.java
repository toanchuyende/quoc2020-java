package vn.agileviet.quoc2020;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import vn.agileviet.quoc2020.models.Conversation;
import vn.agileviet.quoc2020.models.Message;
import vn.agileviet.quoc2020.models.Post;
import vn.agileviet.quoc2020.models.User;

/**
 * https://stackoverflow.com/a/61279863/5812106
 */
@Component
public class RestConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Post.class);
        config.exposeIdsFor(Conversation.class);
        config.exposeIdsFor(Message.class);
        config.exposeIdsFor(User.class);
        //config.exposeIdsFor(Library.class);
    }

}
