package org.gelbercabrera.ferreteria.addpost;


import org.gelbercabrera.ferreteria.entities.Post;
import org.gelbercabrera.ferreteria.lib.EventBus;
import org.gelbercabrera.ferreteria.lib.GreenRobotEventBus;
import org.gelbercabrera.ferreteria.lib.domain.FirebaseHelper;
import org.gelbercabrera.ferreteria.addpost.events.AddPostEvent;

public class AddPostRepoImpl implements AddPostRepo {
    private EventBus eventBus;
    private FirebaseHelper helper;

    public AddPostRepoImpl() {
        this.eventBus = GreenRobotEventBus.getInstance();
        this.helper = FirebaseHelper.getInstance();
    }

    @Override
    public void addPost(Post post) {
        helper.getUserPostRefernce(post.getEmail_poster())
                .child(post.getDate().toString()).setValue(post);
        AddPostEvent event = new AddPostEvent();
        eventBus.post(event);
    }
}
