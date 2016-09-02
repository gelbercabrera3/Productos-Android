package org.gelbercabrera.ferreteria.addpost;


import org.gelbercabrera.ferreteria.entities.Post;
import org.gelbercabrera.ferreteria.addpost.events.AddPostEvent;

public interface AddPostPresenter {
    void addPost(Post post);

    void onShow();

    void onDestroy();

    void onEventMainThread(AddPostEvent event);
}
